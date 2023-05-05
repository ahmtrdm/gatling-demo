package proClient;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LoadDashboardTest extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://security.testing.pcc.pro-client.de")
    .inferHtmlResources()
    .acceptHeader("application/json, text/plain, */*")
    .contentTypeHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
    .originHeader("http://pcc.testing.pcc.pro-client.de")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");

  
  private String uri1 = "https://runtime.testing.pcc.pro-client.de/api/dashboard";

  private String uri2 = "https://runtime.testing.pcc.pro-client.de";
  
  //private String uri2 = "https://user-profile.testing.pcc.pro-client.de/api/user-profiles/ebdef984-ecd8-43b6-b9c0-af8ca7aa882b";
  
  private String uri3 = "https://designer.testing.pcc.pro-client.de/api/guides/runnable/quick-search";
  
  private String uri5 = "https://notifications.testing.pcc.pro-client.de/api/notifications/aggregate";
  
  private String uri6 = "https://dashboard.testing.pcc.pro-client.de/api";


  private ScenarioBuilder scn = scenario("LoadDashboardTest")
          .exec(
                  http("Token")
                          .post("/api/users/token")
                          .body(RawFileBody("/src/test/resources/proClient/loginloadtest/loginrequest.json"))
                          .check(status().is(200))
                          .check(jsonPath("$..tokenString").exists().saveAs("authToken"))

          ).exec(http("Get Running Cases")
                  .get(uri1 + "/my-cases/running")
                  .header("Authorization","Bearer ${authToken}")
                  .header("applicationkey","Guides")
                  .check(status().is(200))

          ).exec(http("Get My Cases")
                  .get(uri2 + "/api/views/my-cases")
                  .header("Authorization","Bearer ${authToken}")
                  .header("applicationkey","Guides")
                  .check(status().is(200))

          ).exec(http("Get Inbox")
                  .get(uri2 + "/api/views/inbox")
                  .header("Authorization","Bearer ${authToken}")
                  .header("applicationkey","Guides")
                  .check(status().is(200))

          ).exec(http("Get Dashboard Inbox")
                  .get(uri2 + "/api/dashboard/inbox")
                  .header("Authorization","Bearer ${authToken}")
                  .header("applicationkey","Guides")
                  .check(status().is(200))

          ).exec(http("Get Profiles")
                  .get(uri6 + "/profiles/ebdef984-ecd8-43b6-b9c0-af8ca7aa882b")
                  .header("Authorization","Bearer ${authToken}")
                  .header("applicationkey","Guides")
                  .check(status().is(200))

          );

  {
      // general way
      //mvn gatling:test -Dgatling.simulationClass=proClient.LoadDashboardTest
      //setUp(scn.injectOpen(rampUsers(30).during(30)).protocols(httpProtocol));

      //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(initialUserCount)).protocols(httpProtocol));

      //For this scenario mvn run command like:
      // mvn gatling:test -Dgatling.simulationClass=proClient.LoadDashboardTest -DinitialUserCount=50
      //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(initialUserCount)).protocols(httpProtocol));
      //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(100)).protocols(httpProtocol));

      setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(100)).protocols(httpProtocol));


      //For this scenario mvn run command like:
      // mvn gatling:test -Dgatling.simulationClass=proClient.LoadDashboardTest
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

      /*
      setUp(scn.injectOpen(
              rampUsers(50).during(10),
              rampUsers(100).during(10),
              rampUsers(150).during(10),
              rampUsers(200).during(10)
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
