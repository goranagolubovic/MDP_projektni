package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;

import com.google.gson.Gson;

import model.Line;

public class RestMethods {
	public static Line[] method(String nameOfMethod,String id) {
	URL url;
	Line [] line=null;
	try {
		url = new URL("http://localhost:8080/CZSMDP/api/line/"+id);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return line;
	}

	public static void methodPut(String nameOfMethod,String id,Line line) {
		URL url;
		try {
			url = new URL("http://localhost:8080/CZSMDP/api/line/"+id);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static Line[] method(String nameOfMethod) {
		URL url;
		Line [] line=null;
		try {
			url = new URL("http://localhost:8080/CZSMDP/api/line/");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
		}

	public static boolean methodDelete(String nameOfMethod,String signOfLine) {
		URL url;
		try { 
			url = new URL("http://localhost:8080/CZSMDP/api/line/"+signOfLine);
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				conn.setRequestMethod(nameOfMethod);
				if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
					return false;
					//throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				}
				else {
					return true;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}

	public static boolean createSchedule(String nameOfMethod, Line line) {
		WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:8080/CZSMDP/api/line");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.json(line));
		return response.getStatus()==200;
	}
}