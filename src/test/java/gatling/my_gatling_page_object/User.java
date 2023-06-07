package gatling.my_gatling_page_object;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class User {
    private static final FeederBuilder.Batchable<String> loginData =
            csv("src/test/resources/gatling_feeders/my_feeders/credentials.csv").circular();

    public static ChainBuilder login(String username, String password) {
        if (username.equals("") && password.equals("")) {
            return feed(loginData)
                    .exec(http("Login")
                            .post("/login")
                            .formParam("_csrf", "${csrf}")
                            .formParam("username", "${userName}")
                            .formParam("password", "${password}")
                    );
        } else {
            return exec(http("Login")
                    .post("/login")
                    .formParam("_csrf", "${csrf}")
                    .formParam("username", username)
                    .formParam("password", password)
            );
        }
    }
}
