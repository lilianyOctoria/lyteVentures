package page.object;

import driver.driverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class submitOrder {
	WebDriver driver = driverFactory.getWebDriver();
	WebDriver driver = driverFactory.getWebDriver();

	By inpFirstrName = By.id("first-name");
	By inpLastName = By.id("last-name");
	By inpZip = By.id("postal-code");
	By btnSubmit = By.id("continue");
	By btnFinish = By.id("finish");

	public void submitOrder(String firstName, String lastName, String zip) {
		driver.findElement(inpFirstrName).sendKeys(firstName);
		driver.findElement(inpLastName).sendKeys(lastName);
		driver.findElement(inpZip).sendKeys(zip);
		
		driver.findElement(btnSubmit).click();
		driver.findElement(btnFinish).click();
	}

}
