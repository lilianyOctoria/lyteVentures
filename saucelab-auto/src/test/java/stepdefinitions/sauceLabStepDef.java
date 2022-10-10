package stepdefinitions;

import driver.driverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.utils.Captures;

import java.util.List;

public class sauceLabStepDef extends commonSetup {
	WebDriver driver = driverFactory.getWebDriver();

	@Given("User add to cart(.*)$")
	public void userAddToCart throws Throwable {
		autoObj.addToCart();		
		Captures.takeSimpleFullScreenShot();
	}

	@When("User proceed cart$")
	public void userProceedCart throws Throwable {
		autoObj.processCart();
		Captures.takeSimpleFullScreenShot();
	}

	@And("User continue to submit (.*)(.*)(.*)$")
	public void userContinueSubmit (String firstName, String lastName, Sting zip) throws Throwable {
		submitObj.submitOrder(firstName, lastName, zip);
		Captures.takeSimpleFullScreenShot();
	}

	@Then("User should see the cart complete $")
	public void userShouldSeetheCartComplete throws Throwable {
		Captures.takeSimpleFullScreenShot();
	}

	

	@When("User sort products based on (.*)$")
	public void userSortProductsBasedOn(String Decision) throws Throwable {
		autoObj.sortingProductsBasedOn(Decision);
		Captures.takeSimpleFullScreenShot();

	}

	@Then("User should see the products sorted$")
	public void userShouldSeeProductsSorted throws Throwable {
		Captures.takeSimpleFullScreenShot();
	}

}
