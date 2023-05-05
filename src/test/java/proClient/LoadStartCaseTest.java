package proClient;

import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class LoadStartCaseTest extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://security.testing.pcc.pro-client.de")
    .inferHtmlResources()
    .acceptHeader("application/json, text/plain, */*")
    .contentTypeHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
    .originHeader("http://pcc.testing.pcc.pro-client.de")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");



  private String uri3 = "https://designer.testing.pcc.pro-client.de";
  
  private String uri2 = "https://runtime.testing.pcc.pro-client.de";


  private ScenarioBuilder scn = scenario("StartCase")
          .exec(
                  http("Token")
                          .post("/api/users/token")
                          .body(RawFileBody("/src/test/resources/proClient/loginloadtest/loginrequest.json"))
                          .check(status().is(200))
                          .check(jsonPath("$..tokenString").exists().saveAs("authToken"))

          )
            .exec(http("Run Case")
                  .post(uri2 + "/api/guides/cf1510d3-78b6-45dd-a991-4d0600426749/instances")
                  .header("Authorization","Bearer ${authToken}")
                  .header("ApplicationKey","Guides")
                  .body(StringBody("{\"preview\":false}"))
            .check(status().is(200))
            );

  {
      // general way
      //mvn gatling:test -Dgatling.simulationClass=proClient.LoadStartCaseTest

      //setUp(scn.injectOpen(rampUsers(1).during(1)).protocols(httpProtocol));

      //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(initialUserCount)).protocols(httpProtocol));

      //For this scenario mvn run command like:
      // mvn gatling:test -Dgatling.simulationClass=proClient.LoadStartCaseTest -DinitialUserCount=50
      //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(initialUserCount)).protocols(httpProtocol));
      //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(100)).protocols(httpProtocol));

      setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(100)).protocols(httpProtocol));


      //For this scenario mvn run command like:
      // mvn gatling:test -Dgatling.simulationClass=proClient.LoadStartCaseTest
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
              rampUsers(200).during(10),
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
