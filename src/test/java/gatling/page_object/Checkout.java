package gatling.page_object;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public final class Checkout{
    public static final ChainBuilder viewCart =
            doIf(session -> !session.getBoolean("customerLoggedIn"))
                    .then(exec(Customer.logIn))
                    .exec(http("View cart")
                            .get("/cart/view")
                            .check(css("#grandTotal").isEL("$#{cartTotal}")));

    public static final ChainBuilder completeCheckout =
            exec(http("Checkout")
                    .get("/cart/checkout")
                    .check(substring("Thanks for your order! See you soon!")));
}
