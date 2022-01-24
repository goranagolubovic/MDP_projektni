package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.google.gson.Gson;

import controllers.ZSMDPController;
import main.FXMain;
import model.Line;
import properties.ConfigProperties;

public class RestMethods {
	private static ConfigProperties configProperties=new ConfigProperties();
	private static Logger log = Logger.getLogger(RestMethods.class.getName());
	public static Line[] method(String nameOfMethod,String id) {
	URL url;
	Line [] line=null;
	try {
		url = new URL(configProperties.getProperties().getProperty("URL")+id);
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setRequestMethod(nameOfMethod);
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			// citanje odgovora
			Gson gson=new Gson();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				 line=gson.fromJson(output, Line[].class);	
			}
			br.close();
			conn.disconnect();
			
		} catch (IOException e) {
			Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
	} catch (MalformedURLException e) {
		Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
	}
	return line;
	}

	public static void methodPut(String nameOfMethod,String id,Line line) {
		URL url;
		try {
			url = new URL(configProperties.getProperties().getProperty("URL"));
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				conn.setRequestMethod(nameOfMethod);
				JSONObject input = new JSONObject(line);
				// slanje body dijela
				OutputStream os = conn.getOutputStream();
				os.write(input.toString().getBytes());
				os.flush();
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				}
				
				conn.disconnect();
				
			} catch (IOException e) {
				Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			
		} catch (MalformedURLException e) {
			Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
	
	}

}
