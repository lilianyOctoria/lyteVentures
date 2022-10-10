package stepdefinitions;

import driver.driverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import page.utils.Captures;
import page.utils.settings;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static page.utils.settings.getCurrentScenario;

public class _StepHooks {
    private String reportFolderPath;
    page.utils.settings settings = new settings();
    private static Scenario currentScenario;

    @Before
    public void open(Scenario scenario) throws Exception {
        this.reportFolderPath = "target" + "/" + "screencapture" + "/" + UUID.randomUUID();
        settings.setCurrentScreenCaptureFolderPath(this.reportFolderPath);
            driverFactory.getWebDriver().manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
            try {
                driverFactory.getWebDriver().get(settings.getHomePageURL());
            } catch (org.openqa.selenium.TimeoutException e) {
                try {
                    Actions actions = new Actions(driverFactory.getWebDriver());
                    actions.sendKeys(Keys.ESCAPE);
                    actions.build().perform();
                } catch (Exception e1) {
                }

                driverFactory.getWebDriver().manage().timeouts().pageLoadTimeout(settings.getDefaultTimeOut(),
                    TimeUnit.SECONDS);
                driverFactory.getWebDriver().manage().timeouts().implicitlyWait(settings.getDefaultTimeOut(), TimeUnit.SECONDS);

            settings.setCurrentScenario(scenario);

        }
    }

    @After
    public void close() throws Exception {
        if (driverFactory.getWebDriver() != null) {
            if (getCurrentScenario().isFailed()) {
                captureLastState();
                captureBrowserLog();
            }
            driverFactory.tearDown();
        }
        handleScreenShotAfterExectution(true, settings.shoudlDeleteOriginalScreenCaptures());
  }

    public void handleScreenShotAfterExectution(boolean embedImagesToScenario, boolean deleteOriginalImages)
            throws IOException {
        if (embedImagesToScenario) {
            Captures.embedFolderImagesToScenario(settings.getCurrentScreenCaptureFolderPath(), getCurrentScenario());
        }

        if (deleteOriginalImages) {
            Captures.deleteAllScreenShots(settings.getCurrentScreenCaptureFolderPath());
        }
    }

    private void captureLastState() {
        try {
            String lastScreenCapturePath = settings.getCurrentScreenCaptureFolderPath()
                    + (new SimpleDateFormat("yyyyMMdd HH-mm-ss")).format(new Date()) + " last state" + ".jpg";
            Captures.takeScreenShot(driverFactory.getWebDriver(), lastScreenCapturePath);
        } catch (Exception e) {
        }
    }

    private void captureBrowserLog() {
        getCurrentScenario().log("Browser logs : ");
        getCurrentScenario().log(System.lineSeparator());

    }
}
