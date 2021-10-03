package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.google.gson.Gson;

import model.Line;
import model.StationTime;

import javax.swing.JTextArea;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Color;

public class ViewTimeTable {

	private JFrame frame;
	private String id;
	private JTextArea textArea;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public ViewTimeTable(String id) {
		this.id=id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(id);
		frame.getContentPane().setBackground(new Color(155, 133, 22));
		frame.setBounds(100, 100, 533, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(79, 84, 364, 230);
		textArea.setBackground(new Color(218,165,32));
		frame.getContentPane().add(textArea);
		
		URL url;
		try {
			url = new URL("http://localhost:8080/CZSMDP/api/line/"+id);
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				}
				
				// citanje odgovora
				Gson gson=new Gson();
				String content="";
				Line [] line=null;
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
					 line=gson.fromJson(output, Line[].class);
					 for(Line l:line) {
						 content+=l.toString()+"\n";
					 }
					 System.out.print(content);
					  
					/*for(StationTime st:line.getStations()) {
						content+=st.toString()+"\n";
					}*/
				}
				br.close();
				conn.disconnect();
				textArea.setText(content);
				textArea.setEditable(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		JTextArea txtrpregledRedaVoznje = new JTextArea();
		txtrpregledRedaVoznje.setEditable(false);
		txtrpregledRedaVoznje.setBackground(new Color(155, 133, 22));
		txtrpregledRedaVoznje.setFont(new Font("Monospaced", Font.BOLD, 19));
		txtrpregledRedaVoznje.setText("-PREGLED REDA VO\u017DNJE-");
		txtrpregledRedaVoznje.setBounds(150, 26, 246, 48);
		frame.getContentPane().add(txtrpregledRedaVoznje);
		
		
		frame.setVisible(true);
	}
}
