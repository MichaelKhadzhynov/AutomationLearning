package gatling.simulations;

import gatling.page_object.CmsPages;
import gatling.page_object.Catalog;
import gatling.page_object.Checkout;

import gatling.utils.SessionId;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class GatlingDemoStoreSimulation extends Simulation {
    private static final String DOMAIN = "demostore.gatling.io";

    private static final HttpProtocolBuilder HTTP_PROTOCOL = http.baseUrl("https://" + DOMAIN);

    private static final int USER_COUNT = Integer.parseInt(System.getProperty("USERS", "3"));

    private static final Duration RAMP_DURATION =
            Duration.ofSeconds(Integer.parseInt(System.getProperty("RAMP_DURATION", "10")));

    private static final Duration TEST_DURATION =
            Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION", "60")));

    @Override
    public void before() {
        System.out.printf("Running test with %d users%n", USER_COUNT);
        System.out.printf("Ramping users over %d seconds%n", RAMP_DURATION.getSeconds());
        System.out.printf("Total test duration: %d seconds%n", TEST_DURATION.getSeconds());
    }

    @Override
    public void after() {
        System.out.println("Stress test complete");
    }

    private static final ChainBuilder initSession =
            exec(flushCookieJar())
                    .exec(session -> session.set("randomNumber", ThreadLocalRandom.current().nextInt()))
                    .exec(session -> session.set("customerLoggedIn", false))
                    .exec(session -> session.set("cartTotal", 0.00))
                    .exec(addCookie(Cookie("sessionId", SessionId.random()).withDomain(DOMAIN)));

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
            .exec(Checkout.viewCart)
            .pause(2)
            .exec(Checkout.completeCheckout);

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
                        .exec(Checkout.viewCart)
                        .pause(MIN_PAUSE, MAX_PAUSE)
                        .exec(Checkout.completeCheckout);
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
        //Parallel scenario
        setUp(
              Scenarios.defaultPurchase
                      .injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION)).protocols(HTTP_PROTOCOL),
              Scenarios.highPurchase
                      .injectOpen(rampUsers(2).during(Duration.ofSeconds(10))).protocols(HTTP_PROTOCOL));

        //Sequential scenario
//        setUp(
//              Scenarios.defaultPurchase
//                 .injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION)).protocols(HTTP_PROTOCOL)
//                 .andThen(
//                    Scenarios.highPurchase
//                            .injectOpen(rampUsers(USER_COUNT).during(Duration.ofSeconds(10))).protocols(HTTP_PROTOCOL)));

        //Multiple scenarios
//        setUp(
//              Scenarios.defaultPurchase
//                      .injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION))
//                      .protocols(HTTP_PROTOCOL));
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
//              scn.injectClosed(
//                      constantConcurrentUsers(5).during(Duration.ofSeconds(20)),
//                      rampConcurrentUsers(1).to(5).during(Duration.ofSeconds(20))))
//              .protocols(HTTP_PROTOCOL);

        //Open model Regular simulation
//        setUp(
//              scn.injectOpen(
//                      atOnceUsers(3),
//                      nothingFor(Duration.ofSeconds(5)),
//                      rampUsers(10).during(Duration.ofSeconds(20)),
//                      nothingFor(Duration.ofSeconds(5)),
//                      constantUsersPerSec(1).during(Duration.ofSeconds(20))))
//              .protocols(HTTP_PROTOCOL);
    }
}
