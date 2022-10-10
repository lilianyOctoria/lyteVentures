package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(stepNotifications = true,
		// tags = "@SauceLab",
		dryRun = false, features = { "src/test/resources/features/sauceLab.feature:12" }, plugin = { "pretty",
				"json:target/cucumber/sauceLab.json",
				"html:target/cucumber/sauceLab.html" }, monochrome = true, glue = { "stepdefinitions" })

public class BBCrunner {
}
