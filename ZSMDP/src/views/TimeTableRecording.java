package views;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import com.google.gson.Gson;

import model.Line;
import model.StationTime;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;

public class TimeTableRecording {

	private JFrame frame;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeTableRecording window = new TimeTableRecording();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimeTableRecording() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 528, 446);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(205,175,63));
		frame.getContentPane().setLayout(null);
		
		final JTextArea txtrOznakaLinije = new JTextArea();
		txtrOznakaLinije.setText("Oznaka linije");
		txtrOznakaLinije.setEditable(false);
		txtrOznakaLinije.setBackground(new Color(255,245,167));
		txtrOznakaLinije.setBounds(119, 86, 135, 22);
		frame.getContentPane().add(txtrOznakaLinije);
		
		textField = new JTextField();
		textField.setBounds(267, 86, 135, 22);
		textField.setBackground(new Color(255,245,167));
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		table.setBackground(new Color(255, 250, 211));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Stanica", "Vrijeme"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setBounds(119, 118, 283, 144);
		frame.getContentPane().add(table);
		
		JButton btnNewButton = new JButton("Dodaj");
		btnNewButton.setBackground(new Color(255, 245, 167));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Properties prop=new Properties();
				
					prop.load(new FileInputStream("resources"+File.separator+"config.properties"));
					
					
					// priprema i otvaranje HTTP zahtjeva
					URL url = new URL("http://localhost:8080/CZSMDP/api/line/sign");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST"); 
					conn.setRequestProperty("Content-Type", "application/json");
					// podaci za body dio zahtjeva
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					String station;
					String time;
					String content="";
					content="{\"sign\":\""+textField.getText()+"\",\"stations\":[";
					List<StationTime> listOfStations=new ArrayList<>();
					for(int i=0;i<model.getRowCount();i++){
						if(model.getValueAt(i, 0)!=null && model.getValueAt(i, 1)!=null) {
							station=model.getValueAt(i, 0).toString();
							time=model.getValueAt(i, 1).toString();
							listOfStations.add(new StationTime(station,time));
							StationTime st=new StationTime(station,time);
							//content+="\""+station+" "+time+"\",";
							content+=st.toString()+",";
							
						}
					}
					content=content.substring(0, content.length()-1);
					content+="]}";
					System.out.println(content);
						JSONObject input = new JSONObject(content);
						OutputStream os = conn.getOutputStream();
						os.write(input.toString().getBytes());
						os.flush();
						// prijem odgovora na zahtjev
						if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
							throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
						}
						
						// citanje odgovora
						BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
						String output;
						while ((output = br.readLine()) != null) {
							System.out.println(output);
						}
						os.close();
						br.close();
						conn.disconnect();
					} 
					
				catch(Exception e2) {
					e2.printStackTrace();
				}
			
					// JSONObject input = new JSONObject(new Line(txtrOznakaLinije.getText(),tableInputs));
					// slanje body dijela
					//Gson gson=new Gson();
					
			}
		});
		btnNewButton.setBounds(317, 283, 85, 21);
				frame.getContentPane().add(btnNewButton);
				
				JTextPane txtpnEvidentiranjeRedaVonje = new JTextPane();
				txtpnEvidentiranjeRedaVonje.setEditable(false);
				txtpnEvidentiranjeRedaVonje.setFont(new Font("Tahoma", Font.BOLD, 18));
				txtpnEvidentiranjeRedaVonje.setText("-EVIDENTIRANJE REDA VO\u017DNJE-");
				txtpnEvidentiranjeRedaVonje.setBackground(new Color(255,245,167));
				txtpnEvidentiranjeRedaVonje.setBounds(98, 22, 320, 42);
				frame.getContentPane().add(txtpnEvidentiranjeRedaVonje);
	}
}
