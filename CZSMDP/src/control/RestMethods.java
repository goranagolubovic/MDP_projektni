package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.google.gson.Gson;

import main.FXMain;
import model.Line;
import properties.ConfigProperties;

public class RestMethods {
	private static ConfigProperties configProperties=new ConfigProperties();
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
			Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
	} catch (MalformedURLException e) {
		Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
	}
	return line;
	}

	public static void methodPut(String nameOfMethod,String id,Line line) {
		URL url;
		try {
			url = new URL(configProperties.getProperties().getProperty("URL")+id);
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
				Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			
		} catch (MalformedURLException e) {
			Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
		
	}

	public static Line[] method(String nameOfMethod) {
		URL url;
		Line [] line=null;
		try {
			url = new URL(configProperties.getProperties().getProperty("URL"));
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
				Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			
		} catch (MalformedURLException e) {
			Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return line;
		}

	public static boolean methodDelete(String nameOfMethod,String signOfLine) {
		URL url;
		try { 
			url = new URL(configProperties.getProperties().getProperty("URL")+signOfLine);
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				conn.setRequestMethod(nameOfMethod);
				if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
					return false;
				}
				else {
					return true;
				}
				
			} catch (IOException e) {
				Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			
		} catch (MalformedURLException e) {
			Logger.getLogger(RestMethods.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}	
		return false;
	}

	public static boolean createSchedule(String nameOfMethod, Line line) {
		WebTarget webTarget = ClientBuilder.newClient().target(configProperties.getProperties().getProperty("URL"));
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.json(line));
		return response.getStatus()==200;
	}
}
