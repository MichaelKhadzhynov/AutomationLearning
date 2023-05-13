package gatling.simulations;

import com.redis.S;
import io.gatling.core.scenario.Scenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class GatlingDemoStore extends Simulation {
    private static final String DOMAIN = "demostore.gatling.io";
    private static final HttpProtocolBuilder HTTP_PROTOCOL = http.baseUrl("https://" + DOMAIN);

    private static final int USER_COUNT = Integer.parseInt(System.getProperty("USERS", "5"));

    private static final Duration RAMP_DURATION =
            Duration.ofSeconds(Integer.parseInt(System.getProperty("RAMP_DURATION", "10")));

    private static final Duration TEST_DURATION =
            Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION", "60")));

    private static final FeederBuilder<String> categoryFeeder =
            csv("gatling_feeders/gatling_demo_site/categories.csv").circular();
    private static final FeederBuilder<Object> productFeeder =
            jsonFile("gatling_feeders/gatling_demo_site/productDetails.json").random();
    private static final FeederBuilder<String> logInDetailsFeeder=
            csv("gatling_feeders/gatling_demo_site/logInDetails.csv").circular();

    private static final ChainBuilder initSession =
            exec(flushCookieJar())
                    .exec(session -> session.set("randomNumber", ThreadLocalRandom.current().nextInt()))
                    .exec(session -> session.set("customerLoggedIn", false))
                    .exec(session -> session.set("cartTotal", 0.00))
                    .exec(addCookie(Cookie("sessionId", SessionId.random()).withDomain(DOMAIN)));

    private static class CmsPages {
        private static final ChainBuilder homePage =
                exec(
                 http("Load home page")
                    .get("/")
                    .check(regex("<title>Gatling Demo-Store</title>").exists())
                    .check(css("#_csrf", "content").saveAs("csrfValue")));

        private static final ChainBuilder aboutUsPage =
                exec(
                 http("About us")
                    .get("/about-us")
                    .check(substring("About Us")));
    }

    private static class Catalog{
        private static class Category{
            private static final ChainBuilder view =
                    feed(categoryFeeder)
                    .exec(
                      http("Load category - #{categoryName}")
                         .get("/category/#{categorySlug}")
                         .check(css("#CategoryName").isEL("#{categoryName}")));
        }

        private static class Product{
            private static final ChainBuilder view =
                    feed(productFeeder)
                    .exec(
                      http("Load product page -#{name}")
                         .get("/product/#{slug}")
                         .check(css("#ProductDescription").isEL("#{description}")));

            private static final ChainBuilder add =
                    exec(view)
                        .exec(http("Add product to cart")
                                .get("/cart/add/#{id}")
                                .check(substring("items in your cart")))
                        .exec(
                                session -> {
                                    double currentCartTotal = session.getDouble("cartTotal");
                                    double itemPrice = session.getDouble("price");
                                    return session.set("cartTotal", (currentCartTotal + itemPrice));
                            }
                        )
                        /*.exec(
                                session -> {
                                    System.out.println("cartTotal:" + session.get("cartTotal"));
                                    return session;
                            }
                        )*/;
        }
    }

    private static class CheckOut{
        private static final ChainBuilder viewCart =
                doIf(session -> !session.getBoolean("customerLoggedIn"))
                        .then(exec(Customer.logIn))
                .exec(http("View cart")
                        .get("/cart/view")
                        .check(css("#grandTotal").isEL("$#{cartTotal}")));

        private static final ChainBuilder completeCheckout =
                exec(http("Checkout")
                        .get("/cart/checkout")
                        .check(substring("Thanks for your order! See you soon!")));
    }

    private static class Customer{
        private static final ChainBuilder logIn =
                feed(logInDetailsFeeder)
                .exec(
                  http("Load login page")
                     .get("/login")
                     .check(substring("Username:")))
                /*.exec(
                        session -> {
                            System.out.println("customerLoggedIn:" + session.get("customerLoggedIn"));
                            return session;
                        })*/
                .exec(
                   http("LogIn user")
                      .post("/login")
                      .formParam("_csrf", "#{csrfValue}")
                      .formParam("username", "#{username}")
                      .formParam("password", "#{password}"))
                .exec(session -> session.set("customerLoggedIn", true))
                /*.exec(
                        session -> {
                            System.out.println("customerLoggedIn:" + session.get("customerLoggedIn"));
                            return session;
                        }
                )*/;
    }

    private static final ScenarioBuilder scn = scenario("GatlingDemoStore")
            .exec(initSession)
            .exec(CmsPages.homePage)
            .pause(2)
            .exec(CmsPages.aboutUsPage)
            .pause(2)
            .exec(Catalog.Category.view)
            .pause(2)
            .exec(Catalog.Product.add)
            .pause(2)
            .exec(CheckOut.viewCart)
            .pause(2)
            .exec(CheckOut.completeCheckout);

    private static class UsersJourneys{
        private static final Duration MIN_PAUSE = Duration.ofMillis(100);
        private static final Duration MAX_PAUSE = Duration.ofMillis(500);

        private static final ChainBuilder browseStore =
                exec(initSession)
                        .exec(CmsPages.homePage)
                        .pause(MAX_PAUSE)
                        .exec(CmsPages.aboutUsPage)
                        .pause(MAX_PAUSE)
                        .repeat(5)
                        .on(
                            exec(Catalog.Category.view)
                              .pause(MIN_PAUSE, MAX_PAUSE)
                              .exec(Catalog.Product.view)
                        );

        private static final ChainBuilder abandonCart =
                exec(initSession)
                        .exec(CmsPages.homePage)
                        .pause(MAX_PAUSE)
                        .exec(Catalog.Category.view)
                        .pause(MIN_PAUSE, MAX_PAUSE)
                        .exec(Catalog.Product.view)
                        .pause(MIN_PAUSE, MAX_PAUSE)
                        .exec(Catalog.Product.add);

        private static final ChainBuilder completePurchase =
                exec(initSession)
                        .exec(CmsPages.homePage)
                        .pause(MAX_PAUSE)
                        .exec(Catalog.Category.view)
                        .pause(MIN_PAUSE, MAX_PAUSE)
                        .exec(Catalog.Product.view)
                        .pause(MIN_PAUSE, MAX_PAUSE)
                        .exec(Catalog.Product.add)
                        .pause(MIN_PAUSE, MAX_PAUSE)
                        .exec(CheckOut.viewCart)
                        .pause(MIN_PAUSE, MAX_PAUSE)
                        .exec(CheckOut.completeCheckout);
    }

    private static class Scenarios {

        private static final ScenarioBuilder defaultPurchase =
                scenario("Default Load Test")
                 .during(TEST_DURATION)
                 .on(
                   randomSwitch()
                     .on(
                         Choice.withWeight(75.0, exec(UsersJourneys.browseStore)),
                         Choice.withWeight(15.0, exec(UsersJourneys.abandonCart)),
                         Choice.withWeight(10.0, exec(UsersJourneys.completePurchase))));

        private static final ScenarioBuilder highPurchase =
                scenario("High Purchase")
                 .during(TEST_DURATION)
                 .on(
                   randomSwitch()
                     .on(
                         Choice.withWeight(25.0, exec(UsersJourneys.browseStore)),
                         Choice.withWeight(25.0, exec(UsersJourneys.abandonCart)),
                         Choice.withWeight(50.0, exec(UsersJourneys.completePurchase))));
    }

    {


        //Multiple scenarios
//        setUp(
//                Scenarios.defaultPurchase
//                        .injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION))
//                        .protocols(HTTP_PROTOCOL));
//
        //Shaping Throughput
//        setUp(scn.injectOpen(constantUsersPerSec(1).during(Duration.ofMinutes(3)))
//                .protocols(HTTP_PROTOCOL)
//                .throttle(reachRps(10).during(Duration.ofSeconds(30)),
//                        holdFor(Duration.ofSeconds(60)),
//                        jumpToRps(20),
//                        holdFor(Duration.ofSeconds(60))))
//                .maxDuration(Duration.ofMinutes(3));


        //Closed model
//        setUp(
//                scn.injectClosed(
//                        constantConcurrentUsers(5).during(Duration.ofSeconds(20)),
//                        rampConcurrentUsers(1).to(5).during(Duration.ofSeconds(20))))
//                .protocols(HTTP_PROTOCOL);

        //Open model Regular simulation
//        setUp(
//                scn.injectOpen(
//                        atOnceUsers(3),
//                        nothingFor(Duration.ofSeconds(5)),
//                        rampUsers(10).during(Duration.ofSeconds(20)),
//                        nothingFor(Duration.ofSeconds(5)),
//                        constantUsersPerSec(1).during(Duration.ofSeconds(20))))
//                .protocols(HTTP_PROTOCOL);
    }
}
