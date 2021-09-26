package control;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import model.User;


public class XMLDeserializer {
public User deserializeWithXML(String XMLuser) {
		
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
