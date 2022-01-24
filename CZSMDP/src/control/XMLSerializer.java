package control;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.FXMain;
import model.User;
import properties.ConfigProperties;

public class XMLSerializer {
	private static Properties properties;
	public static void serializeWithXML(User user){

		XMLEncoder encoder;
		properties = new Properties();
		try {
			String projectPath=System.getenv("MDP_project");
			File f=new File(projectPath+File.separator+"properties\\config.properties");
			FileInputStream fw = new FileInputStream(projectPath+File.separator+"properties\\config.properties");
			properties.load(fw);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//configProperties=new ConfigProperties();
		//Logger.getLogger(XMLSerializer.class.getName()).addHandler(FXMain.handler);
		File f = new File(
				properties.getProperty("XML_PATH")+ File.separator + user.getUsername()
				//configProperties.getProperties().getProperty("XML_PATH") + File.separator + user.getUsername()
				);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(XMLSerializer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		} else {
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(XMLSerializer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
		try {
			encoder = new XMLEncoder(new FileOutputStream(f));
			encoder.writeObject(user);
			encoder.close();
		} catch (FileNotFoundException e) {
			Logger.getLogger(XMLSerializer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}

	}

	public static User deserializeWithXML(String XMLuser) {

		XMLDecoder decoder;
		properties = new Properties();
		try {
			String projectPath=System.getenv("MDP_project");
			FileInputStream fw = new FileInputStream(projectPath+File.separator+"properties\\config.properties");
			properties.load(fw);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File(
				properties.getProperty("XML_PATH")+ File.separator + XMLuser
				);
		if (f.exists()) {
			try {
				decoder = new XMLDecoder(new FileInputStream(f));
				User user = (User) decoder.readObject();
				decoder.close();
				return user;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Logger.getLogger(XMLSerializer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}

		return null;

	}

	public static User[] deserializeWithXML() {
		XMLDecoder decoder;
		properties = new Properties();
		try {
			String projectPath=System.getenv("MDP_project");
			FileInputStream fw = new FileInputStream(projectPath+File.separator+"properties\\config.properties");
			properties.load(fw);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Logger.getLogger(XMLSerializer.class.getName()).addHandler(FXMain.handler);
		List<User> users = new ArrayList<>();
		File folder = new File(properties.getProperty("XML_PATH"));
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			File f = new File(listOfFiles[i].getAbsolutePath());
			try {
				decoder = new XMLDecoder(new FileInputStream(f));
				User user = (User) decoder.readObject();
				decoder.close();
				users.add(user);
			} catch (FileNotFoundException e) {
				Logger.getLogger(XMLSerializer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
		return users.toArray(new User[users.size()]);
	}

	public static boolean deleteUser(String XMLuser) {
		properties = new Properties();
		try {
			String projectPath=System.getenv("MDP_project");
			FileInputStream fw = new FileInputStream(projectPath+File.separator+"properties\\config.properties");
			properties.load(fw);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File(properties.getProperty("XML_PATH") + File.separator + XMLuser);

		if (f.exists()) {
			f.delete();
			return true;
		}
		return false;
	}

	public static String showAllUsers() {
		properties = new Properties();
		try {
			String projectPath=System.getenv("MDP_project");
			FileInputStream fw = new FileInputStream(projectPath+File.separator+"properties\\config.properties");
			properties.load(fw);
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String allUsers = "";
		File folder = new File(properties.getProperty("XML_PATH"));
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			allUsers += listOfFiles[i].getName() + "\n";
		}
		return allUsers;
	}

}
