package gatling.simulations;

import gatling.my_gatling_page_object.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class DemoGatlingSiteSimulation extends Simulation {

  private static final String DOMAIN = "demostore.gatling.io";
  private static final HttpProtocolBuilder HTTP_PROTOCOL = http.baseUrl("https://"+DOMAIN)
          .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");


  private ScenarioBuilder scn = scenario("DemoGatlingSiteSimulation")
    .exec(GatlingDS.homePage)
    .pause(2)
    .exec(Categories.get(""))
    .pause(2)
    .exec(Products.open(""))
    .pause(2)
    .exec(Products.add(""))
    .pause(2)
    .exec(User.login("",""))
    .pause(2)
    .exec(Order.viewCart())
    .pause(2)
    .exec(Categories.get(""))
    .pause(2)
    .exec(Products.open(""))
    .pause(2)
    .exec(Products.add(""))
    .pause(2)
    .exec(Order.viewCart())
    .pause(2)
    .exec(Order.checkout());

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(HTTP_PROTOCOL);
  }
}
