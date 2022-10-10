package page.object;

import driver.driverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

public class auto {
	WebDriver driver = driverFactory.getWebDriver();
	By btnCart = By.xpath("//a[@class='shopping_cart_link']");
	By btnCheckOut = By.xpath("//button[@class='btn btn_action btn_medium checkout_button']");
	By btnContinueShopping = By.xpath("//*[@id='continue-shopping']");
	By listProducts = By.xpath("//a[@class='shopping_cart_link']");
	By btnAddCart = By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");

	public void addToCart() {
		driver.findElement(btnAddCart).click();
	}

	public void processCart() {
		driver.findElement(btnCart).click();
		driver.findElement(btnCheckOut).click();
	}

	public void sortingProductsBasedOn(String Decision) {
		driver.findElement(listProducts).click();
		new Select(driver.findElement(listProducts)).selectByValue(Decision);
	}

}
