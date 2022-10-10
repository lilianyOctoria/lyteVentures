package page.utils;


import com.sun.scenario.Settings;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class settings {

    final private static long defaultTimeOut = 50;
    private static Scenario currentScenario;
    private static String currentScreencaptureFolderPath;
    private final Properties configProperties = new Properties();
    public static long getDefaultTimeOut() {
        return defaultTimeOut;
    }

    private void readConfigFile() throws IOException {
        configProperties.load(new FileInputStream("Config.properties"));
    }

    private String getProperty(String key) throws IOException {
        if (configProperties.isEmpty()) {
            readConfigFile();
        }
        return configProperties.getProperty(key);
    }

    public String getDriverRemoteURL()throws IOException {
        return getProperty("driverRemoteURL");
    }

    public String getHomePageURL()throws IOException {
        return getProperty("homePageURL");
    }


    public static void setCurrentScreenCaptureFolderPath(String folderPath) {
        currentScreencaptureFolderPath = folderPath;
    }

    public static String getCurrentScreenCaptureFolderPath() {
        if (!currentScreencaptureFolderPath.endsWith(File.separator))
            currentScreencaptureFolderPath = currentScreencaptureFolderPath + "/";
        return currentScreencaptureFolderPath;
    }

    public static Scenario getCurrentScenario() {
        return currentScenario;
    }

    public static void setCurrentScenario(Scenario currentScenario) {

        settings.currentScenario = currentScenario;
    }

    public static boolean shoudlDeleteOriginalScreenCaptures() {
        // TODO Auto-generated method stub
        return false;
    }

}
