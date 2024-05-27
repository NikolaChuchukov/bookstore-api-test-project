package runner;

import common.Helper;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeSuite;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefs", "hooks"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "not @WIP"
)

public class Runner extends AbstractTestNGCucumberTests {
    @BeforeSuite
    public void beforeSuite() {
        // Initialize token before suite starts
        Helper helper = new Helper();
        helper.generateToken();
    }
}
