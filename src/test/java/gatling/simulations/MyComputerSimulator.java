package gatling.simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class MyComputerSimulator extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://computer-database.gatling.io")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,uk;q=0.6")
            .upgradeInsecureRequestsHeader("1")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");

    FeederBuilder.Batchable<String> feederBuilder =
            csv("gatling_feeders/search.csv").random();


    ChainBuilder searchComputer =
            exec(http("Load home page")
                    .get("/computers"))
                    .pause(2)
                    .feed(feederBuilder)
            .exec(http("Search computer_#{searchCriterion}")
                    .get("/computers?f=#{searchComputerName}")
                    .check(css("a:contains('#{searchComputerName}')", "href")
                            .saveAs("computerUrl")))
                    .pause(1);


    ChainBuilder loadComputer =
            exec(http("Load computer_#{searchCriterion}")
                    .get("#{computerUrl}"))
                    .pause(1);

    ChainBuilder openHome =
            exec(http("Open home")
                    .get("/computers"))
                    .pause(2);

    ChainBuilder openCreateNewComputer =
            exec(http("Open create new computer")
                    .get("/computers/new"))
                    .pause(2);

    FeederBuilder.Batchable<String> createComputerFeeder =
            csv("gatling_feeders/create_computer.csv").circular();

    ChainBuilder creatNewComputer =
            feed(createComputerFeeder)
                    .exec(http("Create computer #{computerName}")
                    .post("/computers")
                    .formParam("name", "#{computerName}")
                    .formParam("introduced", "#{introduced}")
                    .formParam("discontinued", "#{discontinued}")
                    .formParam("company", "#{company}"));

    private final ScenarioBuilder admin = scenario("Admin")
            .exec(openHome,
                    openCreateNewComputer,
                    creatNewComputer);

    private final ScenarioBuilder user = scenario("User")
            .exec(searchComputer, loadComputer);

    {
        setUp(admin.injectOpen(atOnceUsers(4)),
                user.injectOpen(atOnceUsers(2))).protocols(httpProtocol);
    }
}
