package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features   = "src/test/resources/features",
        glue       = "steps",
        tags       = "@login",
        plugin     = {
                "pretty",
                "html:target/reports/cucumber-report.html",
                "json:target/reports/cucumber.json"
        },
        monochrome = true
)
public class TestRunner {}