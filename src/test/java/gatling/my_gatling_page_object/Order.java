package gatling.my_gatling_page_object;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Order {

    public static ChainBuilder viewCart(){
        return exec(http("View car")
                .get("/cart/view")
        );
    }

    public static ChainBuilder checkout(){
        return exec(http("Checkout")
                .get("/cart/checkout")
        );
    }
}
