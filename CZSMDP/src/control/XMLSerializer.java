package control;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import model.User;


public class XMLSerializer {
public static void serializeWithXML(User user) {
		
		XMLEncoder encoder;
		
			/*Properties prop=new Properties();
			try {
				prop.load(new FileInputStream("resources"+File.separator+"config.properties"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			File f=new File("C:\\Users\\goran\\Desktop\\MDP-projektni\\XMLfiles"+File.separator+user.getUsername());
			if(!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				f.delete();
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				encoder = new XMLEncoder(new FileOutputStream(f));
				encoder.writeObject(user);
				encoder.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	}
public static User deserializeWithXML(String XMLuser) {
		
		XMLDecoder  decoder;
		
			/*Properties prop=new Properties();
			try {
				prop.load(new FileInputStream("resources"+File.separator+"config.properties"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			File f=new File("C:\\Users\\goran\\Desktop\\MDP-projektni\\XMLfiles"+File.separator+XMLuser);
			
			if(f.exists()) {
			try {
				decoder = new XMLDecoder(new FileInputStream(f));
				User user=(User) decoder.readObject();
				decoder.close();
				return user;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			return null;
			
	}
  

}