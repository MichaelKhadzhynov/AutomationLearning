package gatling.page_object;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public final class CmsPages {
    public static final ChainBuilder homePage =
            exec(
                    http("Load home page")
                            .get("/")
                            .check(regex("<title>Gatling Demo-Store</title>").exists())
                            .check(css("#_csrf", "content").saveAs("csrfValue")));

    public static final ChainBuilder aboutUsPage =
            exec(
                    http("About us")
                            .get("/about-us")
                            .check(substring("About Us")));
}