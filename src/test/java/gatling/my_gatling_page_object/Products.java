package gatling.my_gatling_page_object;

import com.redis.S;
import io.gatling.javaapi.core.ChainBuilder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Products {

    public static ChainBuilder open(String product) {
        if (product.equals("")) {
            return exec(http("Open product - #{productName}")
                    .get("/product/#{productName}")
                    .check(css("a", "data-id").saveAs("productId"))
            );

        } else {
            product = product.toLowerCase().replace(" ", "-");
            return exec(http("Open " + product)
                    .get("/product/" + product)
                    .check(css("a", "data-id").saveAs("productId"))
            );
        }
    }

    public static ChainBuilder add(String id) {
        if (id.equals("")) {
            return exec(http("Add to cart - ${productId}")
                    .get("/cart/add/${productId}"));
        }
        return exec(http("Add to cart - " + id)
                .get("/cart/add/" + id));
    }
}
