package gatling.simulations;

import io.cucumber.java.it.Ma;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import javax.sql.ConnectionPoolDataSource;
import java.sql.Connection;
import java.time.Duration;
import java.util.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class DemostoreApiSimulation extends Simulation {

    private static final String DOMAIN = "demostore.gatling.io";
    private static final HttpProtocolBuilder HTTP_PROTOCOL = http.baseUrl("https://" + DOMAIN)
            .header("Cache-Control", "no-cache")
            .contentTypeHeader("application/json")
            .acceptHeader("application/json");

    private static final int USER_COUNT = Integer.parseInt(System.getProperty("USERS", "5"));
    private static final Duration RAMP_DURATION =
            Duration.ofSeconds(Integer.parseInt(System.getProperty("RAMP_DURATION", "10")));
    private static final Duration TEST_DURATION =
            Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION", "60")));

    private static final Map<CharSequence, String> authorizationHeader = Map.ofEntries(
            Map.entry("authorization", "Bearer #{jwt}")
    );
    private static final ChainBuilder initSession = exec(session -> session.set("authenticated", false));

    private static class Authentication {
        private static final ChainBuilder authenticate =
                doIf(session -> !session.getBoolean("authenticated"))
                        .then(exec(http("Authenticate")
                                .post("/api/authenticate")
                                .body(RawFileBody("gatling_api/request/authenticate-admin.json"))
                                .check(status().is(200))
                                .check(jmesPath("token").saveAs("jwt")))
                                .exec(session -> session.set("authenticated", true)));
    }

    private static class Categories {

        private static final FeederBuilder.Batchable<String> myCategories =
                csv("gatling_feeders/gatling_demosite_api/categories.csv").circular();
        private static final ChainBuilder list =
                exec(http("List categories")
                        .get("/api/category")
                        .check(jmesPath("[? id == `6`].name").ofList().is(List.of("For Her"))));

        private static final ChainBuilder update =
                feed(myCategories)
                .exec(Authentication.authenticate)
                .exec(http("Update category")
                        .put("/api/category/#{categoryId}")
                        .headers(authorizationHeader)
                        .body(RawFileBody("gatling_api/request/update-category.json"))
                        .check(jmesPath("name").is("Everyone")));
    }

    private static class Products {
        private static final FeederBuilder.Batchable<String> createProducts=
                csv("gatling_feeders/gatling_demosite_api/products.csv").circular();

        private static final ChainBuilder listAll =
                exec(http("List all products")
                        .get("/api/product")
                        .check(jmesPath("[*]").ofList().saveAs("allProducts")));
        private static final ChainBuilder list =
                exec(http("List products")
                        .get("/api/product?category=7")
                        .check(jmesPath("[? categoryId != '7'] ").ofList().is(Collections.emptyList()))
                        .check(jmesPath("[*].id").ofList().saveAs("allProductIds")));

        private static final ChainBuilder get =
                exec(session -> {
                    List<Integer> allProductsIds = session.getList("allProductIds");
                    return session.set("productId", allProductsIds.get(new Random().nextInt(allProductsIds.size())));
                })
//                .exec(session -> {
//                            System.out.println("allProductsIds captured:" + session.get("allProductIds"));
//                            System.out.println("productId selected:" + session.get("productId"));
//                            return session;})
                .exec(http("Get product")
                        .get("/api/product/#{productId}")
                        .check(jmesPath("id").ofInt().isEL("#{productId}"))
                        .check(jmesPath("@").ofMap().saveAs("product")))
                /*.exec(
                        session -> {
                            System.out.println("value of product:" + session.get("product"));
                            return session;
                        }
                )*/;

        private static final ChainBuilder update =
                exec(Authentication.authenticate)
                .exec(
                        session -> {
                            Map<String, Object> product = session.getMap("product");
                            return session
                                    .set("productCategoryId", product.get("categoryId"))
                                    .set("productName", product.get("name"))
                                    .set("productDescription", product.get("description"))
                                    .set("productImage", product.get("image"))
                                    .set("productPrice", product.get("price"))
                                    .set("productId", product.get("id"));
                        }
                )
                .exec(http("Updating product #{productName}")
                        .put("/api/product/#{productId}")
                        .headers(authorizationHeader)
                        .body(ElFileBody("gatling_api/request/create-product.json"))
                        .check(jmesPath("price").isEL("#{productPrice}")));

        private static final ChainBuilder create =
                exec(Authentication.authenticate)
                    .feed(createProducts)
                    .exec(http("Create product #{productName}")
                            .post("/api/product")
                            .headers(authorizationHeader)
                            .body(ElFileBody("gatling_api/request/create-product.json")));

    }

    private static class UserJourneys{


        private static final Duration MIN_PAUSE = Duration.ofMillis(200);
        private static final Duration MAX_PAUSE = Duration.ofSeconds(3);
        public static final ChainBuilder admin =
                exec(initSession)
                .exec(Categories.list)
                .pause(MIN_PAUSE, MAX_PAUSE)
                .exec(Products.list)
                .pause(MIN_PAUSE, MAX_PAUSE)
                .exec(Products.get)
                .pause(MIN_PAUSE, MAX_PAUSE)
                .exec(Products.update)
                .pause(MIN_PAUSE, MAX_PAUSE)
                .repeat(3).on(exec(Products.create))
                .pause(MIN_PAUSE, MAX_PAUSE)
                .exec(Categories.update);

        private static final ChainBuilder priceScrapper =
                exec(Categories.list)
                .pause(MIN_PAUSE, MAX_PAUSE)
                .exec(Products.listAll);


        private static final ChainBuilder priceUpdater =
                exec(initSession)
                .pause(MIN_PAUSE, MAX_PAUSE)
                .exec(Products.listAll)
                .repeat("#{allProducts.size()}", "productIndex").on(
                        exec(session -> {
                            int index = session.getInt("productIndex");
                            List<Object> allProducts = session.getList("allProducts");
                            return session.set("product", allProducts.get(index));
                        })
                .exec(Products.update)
                .pause(MIN_PAUSE, MAX_PAUSE));
    }

    private static class Scenarios{
        private static final ScenarioBuilder defaultScn = scenario("Default load test")
                .during(TEST_DURATION)
                .on(
                        randomSwitch().on(
                                Choice.withWeight(20d, exec(UserJourneys.admin)),
                                Choice.withWeight(40d, exec(UserJourneys.priceScrapper)),
                                Choice.withWeight(40d, exec(UserJourneys.priceUpdater))
                        ));
        private static final ScenarioBuilder noAdmin = scenario("Load test without admin users")
                .during(TEST_DURATION)
                .on(
                        randomSwitch().on(
                                Choice.withWeight(60d, exec(UserJourneys.priceScrapper)),
                                Choice.withWeight(40d, exec(UserJourneys.priceUpdater))
                        ));
    }

    {

        // Parallel
        setUp(
                Scenarios.defaultScn.injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION)),
                Scenarios.noAdmin.injectOpen(rampUsers(5).during(Duration.ofSeconds(10))))
                .protocols(HTTP_PROTOCOL);

        // Sequential
//        setUp(
//                Scenarios.defaultScn.injectOpen(rampUsers(USER_COUNT).during(RAMP_DURATION)).protocols(HTTP_PROTOCOL)
//                        .andThen(
//                                Scenarios.noAdmin.injectOpen(rampUsers(5).during(Duration.ofSeconds(10)))
//                                        .protocols(HTTP_PROTOCOL)));

        // Single scenario
//        setUp(
//                Scenarios.defaultScn.injectOpen(
//                        rampUsers(USER_COUNT).during(RAMP_DURATION))
//                        .protocols(HTTP_PROTOCOL));

        //throttle simulation
//        setUp(
//                Scenarios.defaultScn.injectOpen(
//                constantUsersPerSec(2).during(Duration.ofMinutes(3)))
//                .protocols(HTTP_PROTOCOL)
//                .throttle(
//                        reachRps(10).in(Duration.ofSeconds(10)),
//                        holdFor(Duration.ofSeconds(6)),
//                        jumpToRps(20),
//                        holdFor(Duration.ofSeconds(60))))
//                .maxDuration(Duration.ofMinutes(3));

        //Closed model
//        setUp(scn.injectClosed(
//                rampConcurrentUsers(1).to(5).during(Duration.ofSeconds(20)),
//                constantConcurrentUsers(5).during(Duration.ofSeconds(20)))
//                .protocols(HTTP_PROTOCOL));

        // Open model
//        setUp(scn.injectOpen(
//                atOnceUsers(3),
//                nothingFor(Duration.ofSeconds(5)),
//                rampUsers(10).during(Duration.ofSeconds(20)),
//                nothingFor(Duration.ofSeconds(10)),
//                constantUsersPerSec(1).during(Duration.ofSeconds(20))))
//                .protocols(HTTP_PROTOCOL);

    }
}
