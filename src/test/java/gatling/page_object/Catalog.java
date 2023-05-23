package gatling.page_object;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.substring;
import static io.gatling.javaapi.http.HttpDsl.http;

public final class Catalog{
    private static final FeederBuilder<String> categoryFeeder =
            csv("gatling_feeders/gatling_demo_site/categories.csv").circular();
    private static final FeederBuilder<Object> productFeeder =
            jsonFile("gatling_feeders/gatling_demo_site/productDetails.json").random();

    public static class Category{
        public static final ChainBuilder view =
                feed(categoryFeeder)
                  .exec(
                      http("Load category - #{categoryName}")
                         .get("/category/#{categorySlug}")
                         .check(css("#CategoryName").isEL("#{categoryName}")));
    }

    public static class Product{
        public static final ChainBuilder view =
                feed(productFeeder)
                        .exec(
                                http("Load product page -#{name}")
                                        .get("/product/#{slug}")
                                        .check(css("#ProductDescription").isEL("#{description}")));

        public static final ChainBuilder add =
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
