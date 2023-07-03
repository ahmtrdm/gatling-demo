package proClient;

import io.gatling.http.request.BodyPart;
import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class LoadOrganizationalStructureTest extends Simulation {

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

    private String runtime = "https://runtime.testing.pcc.pro-client.de";

    //private String uri2 = "https://user-profile.testing.pcc.pro-client.de/api/user-profiles/ebdef984-ecd8-43b6-b9c0-af8ca7aa882b";

    private String designer = "https://designer.testing.pcc.pro-client.de";

    private String dynamicData = "https://dynamic-data.testing.pcc.pro-client.de";

    private String systemData = "https://system-data.testing.pcc.pro-client.de";





    private ScenarioBuilder scn = scenario("LoadDashboardTest")
            .exec(
                    http("Token")
                            .post("/api/users/token")
                            .body(RawFileBody("/src/test/resources/proClient/loginloadtest/loginrequest.json"))
                            .check(status().is(200))
                            .check(jsonPath("$..tokenString").exists().saveAs("authToken"))

            ).exec(http("Get Organizational Structure details")
                            .get(systemData + "/api/org-units/tree-structure")
                            .header("Authorization","Bearer ${authToken}")
                            .header("applicationkey","Guides")
                            .check(status().is(200))

          ).exec(http("Save Organizational Structure details")
                  .put(systemData + "/api/org-units/d937d230-2eba-4d27-bda1-f82b775aff7e")
                    .body(RawFileBody("/src/test/resources/proClient/loginloadtest/saveOrganization.json"))
                  .header("Authorization","Bearer ${authToken}")
                  .header("applicationkey","Guides")
                  .check(status().is(200))
          ).exec(http("Get Guide Library details")
                    .get(designer + "/api/guides?$orderby=createdDateTime%20desc&$top=10&$skip=0&$count=true")
                    .header("Authorization","Bearer ${authToken}")
                    .header("applicationkey","Guides")
                    .check(status().is(200))
            ).exec(http("Get Case Views")
                    .get(runtime + "/api/views/list")
                    .header("Authorization","Bearer ${authToken}")
                    .header("applicationkey","Guides")
                    .check(status().is(200))
            ).exec(http("Get Dynamic Data details")
                    .get(dynamicData + "/api/entity-definitions/DATATEST")
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
