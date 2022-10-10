package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import page.utils.settings;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public final class driverFactory {
    private static RemoteWebDriver driver;
    private static String defaultWindowHandle;
    private static Integer defaultTimeOut = 180;
    static settings settings = new settings();


    public final static WebDriver getWebDriver(){
        if (driver == null) {
            try {
                //createNewDriverInstance();
                driver= localBrowser();
            } catch (Exception e) {
                System.out.println("Error on creating driver");
                e.printStackTrace();
            }

        }
        return driver;
    }

    public static RemoteWebDriver localBrowser(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver=new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return (RemoteWebDriver) driver;
    }

    public static void createNewDriverInstance() throws IOException {
        final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability("acceptInsecureCerts", true);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        final URL remoteAddress = new URL(settings.getDriverRemoteURL());
        driver = new RemoteWebDriver(remoteAddress, capabilities);
        driver.setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        defaultWindowHandle = driver.getWindowHandle();
    }


    public static void tearDown(){
        if (driver != null) {
            try {
                driver.quit();
                Thread.sleep(5000);
            }catch(Exception e)
            {
                //
            }
            finally {
                driver = null;
            }
        }
    }

}
