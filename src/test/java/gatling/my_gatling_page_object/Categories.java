package gatling.my_gatling_page_object;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.exec.Execs;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static groovy.xml.dom.DOMCategory.text;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Categories {

    private static final FeederBuilder.Batchable<String> categories =
            csv("gatling_feeders/my_feeders/categories.csv").random();

    public static ChainBuilder get(String category) {
        category = category.toLowerCase();
        if (category.equals("")) {
            return feed(categories)
                    .exec(http("Get category - #{categoryName}")
                            .get("/category/#{categorySlug}")
                            .check(css("h4").findAll().saveAs("productList"))
                    ).exec(randomProductName());
        } else {
            return exec(http("Get category -" + category)
                    .get("/category/" + category)
                    .check(css("h4").findAll().saveAs("productList"))
            )
                    .exec(randomProductName());

        }
    }

    private static ChainBuilder randomProductName(){
        return exec(session -> {
            List<String> productList = session.get("productList");
            assert productList != null;
            return session.set("productName", productList.get(new Random().nextInt(productList.size()))
                    .toLowerCase().replace(" ", "-"));
        });
    }


}
