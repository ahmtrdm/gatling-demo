package proClient;

import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;


public class LoginAndGetUserSeperated extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://security.testing.pcc.pro-client.de")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("application/json, text/plain, */*")
            .contentTypeHeader("application/json")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7")
            .originHeader("http://pcc.testing.pcc.pro-client.de")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");


    private ScenarioBuilder scn = scenario("Login")
            .exec(
                    http("Token")
                            .post("/api/users/token")
                            .body(RawFileBody("/src/test/resources/proClient/loginloadtest/loginrequest.json"))
                            .check(status().is(200))
                            .check(jsonPath("$..tokenString").exists().saveAs("authToken"))
            )



            .exec(http("GetUser")
                    .get("/api/users")
                    .header("Authorization","Bearer ${authToken}")
                    .header("applicationkey","Guides")
                    .check(status().is(200))
                    .check(jsonPath("$.items.ExceptionMessage").optional().saveAs("response"))
                    //.check(jsonPath("$.totalCount").optional().saveAs("id"))
                    .check(status().exists().saveAs("status"))


            );/*
            .doIf(session -> {
                int status = session.getInt("status");
                return status < 200 || status > 304;
            }).then(
                    exec(
                            session -> {
                                String errorMessage = session.getString("response");
                                // perform accountId storage
                                System.out.println("exception occured..");
                                return session;
                            }
                    )
            )*/


    {


        //mvn gatling:test -Dgatling.simulationClass=proClient.LoginAndGetUser
        //setUp(scn.injectOpen(OpenInjectionStep.atOnceUsers(1000)).protocols(httpProtocol));

        //For this scenario mvn run command like:
        // mvn gatling:test -Dgatling.simulationClass=proClient.LoginAndGetUser

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

        //Seperated injection

        setUp(
                scn.injectOpen(rampUsers(300).during(10)
                        /*rampUsers(20).during(10),
                        rampUsers(30).during(10),
                        rampUsers(40).during(10),
                        rampUsers(50).during(10),
                        rampUsers(60).during(10),
                        rampUsers(70).during(10),
                        rampUsers(80).during(10),
                        rampUsers(90).during(10),
                        rampUsers(100).during(10),
                        rampUsers(200).during(10),
                        rampUsers(300).during(10),
                        rampUsers(400).during(10),
                        rampUsers(500).during(10),
                        rampUsers(600).during(10))*/
        ).protocols(httpProtocol));


    }


}
