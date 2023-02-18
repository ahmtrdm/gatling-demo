import io.cucumber.java.en.When;
import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;
import proClient.LoginLoadTest;

public class Engine {

  public static void main(String[] args) {
    GatlingPropertiesBuilder props = new GatlingPropertiesBuilder()
      .resourcesDirectory(IDEPathHelper.mavenResourcesDirectory.toString())
      .resultsDirectory(IDEPathHelper.resultsDirectory.toString())
      .binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString());

    Gatling.fromMap(props.build());
  }
}
