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

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.awt.event.ActionEvent;

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
		frame.setBounds(100, 100, 450, 329);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(205,175,63));
		frame.getContentPane().setLayout(null);
		
		final JTextArea txtrOznakaLinije = new JTextArea();
		txtrOznakaLinije.setText("Oznaka linije");
		txtrOznakaLinije.setEditable(false);
		txtrOznakaLinije.setBackground(new Color(215,116,63));
		txtrOznakaLinije.setBounds(60, 39, 135, 22);
		frame.getContentPane().add(txtrOznakaLinije);
		
		textField = new JTextField();
		textField.setBounds(205, 40, 135, 22);
		textField.setBackground(new Color(215,116,63));
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		table.setBackground(new Color(144, 103, 44));
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
		table.setBounds(57, 71, 283, 144);
		frame.getContentPane().add(table);
		
		JButton btnNewButton = new JButton("Dodaj");
		btnNewButton.setBackground(new Color(189, 183, 107));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Properties prop=new Properties();
				
					prop.load(new FileInputStream("resources"+File.separator+"config.properties"));
					
					
					// priprema i otvaranje HTTP zahtjeva
					URL url = new URL("http://localhost:8080/CZSMDP/api/line/");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST"); 
					conn.setRequestProperty("Content-Type", "application/json");
					// podaci za body dio zahtjeva
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					String [] tableInputs=new String[100];
					for(int i=0;i<model.getRowCount();i++) {
						if(model.getValueAt(i, 0)!=null && model.getValueAt(i, 1)!=null) {
						tableInputs[i]=model.getValueAt(i, 0).toString()+" "+model.getValueAt(i, 1).toString();
						OutputStream os = conn.getOutputStream();
						Gson gson=new Gson();
						os.write(gson.toJson(new Line(txtrOznakaLinije.getText(),tableInputs).toString()).getBytes());
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
					}
						
					}
				catch(Exception e2) {
					e2.printStackTrace();
				}
			
					// JSONObject input = new JSONObject(new Line(txtrOznakaLinije.getText(),tableInputs));
					// slanje body dijela
					//Gson gson=new Gson();
					
			}
		});
		btnNewButton.setBounds(157, 241, 85, 21);
				frame.getContentPane().add(btnNewButton);
	}
}
