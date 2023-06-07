package gatling.my_gatling_page_object;

import io.gatling.javaapi.core.ChainBuilder;

import java.util.List;
import java.util.Random;

import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;

public final class GatlingDS {

    public static final ChainBuilder homePage =
            exec(http("Get home page")
                    .get("/")
                    .check(css("#_csrf", "content").saveAs("csrf"))
            );
}
