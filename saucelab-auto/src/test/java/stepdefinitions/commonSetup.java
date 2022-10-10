package stepdefinitions;

import page.object.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class commonSetup {
	private final Properties configProperties = new Properties();
	int timeOut = 90;
	String UserName;
	String Pass;

	auto autoObj = new auto();
	login loginObj = new login();
	submitObj submitOrder = new submitObj();
	String Path = System.getProperty("user.dir");
	public String uploadFile = Path + File.separator + "src/Attachment/upload.txt";

	private void readConfigFile() throws IOException {
		configProperties.load(new FileInputStream("Config.properties"));
	}

	private String getProperty(String key) throws IOException {
		if (configProperties.isEmpty()) {
			readConfigFile();
		}
		return configProperties.getProperty(key);
	}

	public String GetProperties(String Name) throws IOException {
		return getProperty(Name);
	}
}
