package gatling.page_object;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public final class Customer{
    private static final FeederBuilder<String> logInDetailsFeeder=
            csv("gatling_feeders/gatling_demo_site/logInDetails.csv").circular();
    public static final ChainBuilder logIn =
            feed(logInDetailsFeeder)
                    .exec(
                            http("Load login page")
                                    .get("/login")
                                    .check(substring("Username:")))
                    /*.exec(
                            session -> {
                                System.out.println("customerLoggedIn:" + session.get("customerLoggedIn"));
                                return session;
                            })*/
                    .exec(
                            http("LogIn user")
                                    .post("/login")
                                    .formParam("_csrf", "#{csrfValue}")
                                    .formParam("username", "#{username}")
                                    .formParam("password", "#{password}"))
                    .exec(session -> session.set("customerLoggedIn", true))
                /*.exec(
                        session -> {
                            System.out.println("customerLoggedIn:" + session.get("customerLoggedIn"));
                            return session;
                        }
                )*/;
}
