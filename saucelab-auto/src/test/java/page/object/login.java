package page.object;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class login {
    WebDriver driver = driverFactory.getWebDriver();
    
    By inpUserName = By.id("user-name");
    By inpPass = By.id("password");
    By btnLogin = By.id("login-button");
    

    public void loginTrackit(String UserName, String Pass) {
        driver.findElement(inpUserName).sendKeys(UserName);
        driver.findElement(inpPass).sendKeys(Pass);
        driver.findElement(btnLogin).click();
    }
}
