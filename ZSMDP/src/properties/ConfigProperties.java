package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigProperties {
	private static Properties properties;

	public static Properties getProperties() {
		return properties;
	}

	public ConfigProperties() {
		properties = new Properties();
		try {
			String path=System.getenv("MDP_project");
			FileInputStream fw = new FileInputStream(path+File.separator+"..\\ZSMDP\\properties\\config.properties");
			properties.load(fw);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
