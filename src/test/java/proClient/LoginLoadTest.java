package proClient;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class LoginLoadTest extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
          .baseUrl("https://security.testing.pcc.pro-client.de")
          .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
          .acceptHeader("application/json, text/plain, */*")
          .contentTypeHeader("application/json")
          .acceptEncodingHeader("gzip, deflate")
          .acceptLanguageHeader("en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
          .originHeader("http://pcc.testing.pcc.pro-client.de")
          .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");

  // we don't need that but for now lets keep it

  private ScenarioBuilder scn = scenario("LoginLoadTest")
          .exec(
                  http("Token")
                          .post("/api/users/token")
                          .body(RawFileBody("/src/test/resources/proClient/loginloadtest/loginrequest.json"))
                          .check(status().is(200))
                          .check(jmesPath("tokenString").exists())


    );




  {

    // general way
    //mvn gatling:test -Dgatling.simulationClass=proClient.LoginLoadTest
    //setUp(scn.injectOpen(rampUsers(30).during(30)).protocols(httpProtocol));

    //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(initialUserCount)).protocols(httpProtocol));

    //For this scenario mvn run command like:
    // mvn gatling:test -Dgatling.simulationClass=proClient.LoginLoadTest -DinitialUserCount=50
    //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(100)).protocols(httpProtocol));

    setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(50)).protocols(httpProtocol));


    //For this scenario mvn run command like:
    // mvn gatling:test -Dgatling.simulationClass=proClient.LoginLoadTest

      /*
      setUp(scn.injectOpen(rampUsers(10).during(10),
            rampUsers(20).during(10),
            rampUsers(30).during(10),
            rampUsers(40).during(10),
            rampUsers(50).during(10),
            rampUsers(100).during(10),
            rampUsers(200).during(10),
            rampUsers(300).during(10),
            rampUsers(400).during(10),
            rampUsers(500).during(10),
            rampUsers(600).during(10),
            rampUsers(700).during(10),
            rampUsers(800).during(10),
            rampUsers(900).during(10),
            rampUsers(1000).during(10)
            ).protocols(httpProtocol));
        */
      /*setUp(scn.injectOpen(
              rampUsers(50).during(10),
              rampUsers(100).during(10),
              rampUsers(150).during(10)
      ).protocols(httpProtocol));
        */
    /*
    setUp(
            // generate an open workload injection profile
            // with levels of 10, 15, 20, 25 and 30 arriving users per second
            // each level lasting 10 seconds
            // separated by linear ramps lasting 10 seconds
            scn.injectOpen(
                    incrementUsersPerSec(5.0)
                            .times(repeatCount)
                            .eachLevelLasting(10)
                            .separatedByRampsLasting(10)
                            .startingFrom(initialUserCount) // Double
            )
    );

     */
  }
}
