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

import control.RestMethods;
import model.Line;
import model.StationTime;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JCheckBox;

public class TrainPassingRecording {

	private JFrame frame;
	private JTextField textField1;
	private JTextField textField4;
	private JTextField textField7;
	private JTextField textField2;
	private JTextField textField5;
	private JTextField textField8;
	private JTextField textField3;
	private JTextField textField6;
	private JTextField textField9;
	private JTextField textField10;
	private JTextField textField11;
	private JTextField textField12;
	private String id;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainPassingRecording window = new TrainPassingRecording();
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
	public TrainPassingRecording() {
		initialize();
	}
	public TrainPassingRecording(String id) {
		this.id=id;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 897, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(205,175,63));
		frame.getContentPane().setLayout( null);
		
		JTextPane txtpnEvidentiranjeRedaVonje = new JTextPane();
		txtpnEvidentiranjeRedaVonje.setEditable(false);
		txtpnEvidentiranjeRedaVonje.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtpnEvidentiranjeRedaVonje.setText("                            -EVIDENTIRANJE PROLASKA VOZA-");
		txtpnEvidentiranjeRedaVonje.setBackground(new Color(255,245,167));
		txtpnEvidentiranjeRedaVonje.setBounds(124, 22, 575, 41);
		frame.getContentPane().add(txtpnEvidentiranjeRedaVonje);
		
		final JTextArea txtrOznakaLinije = new JTextArea();
		txtrOznakaLinije.setText("Oznaka linije");
		txtrOznakaLinije.setEditable(false);
		txtrOznakaLinije.setBackground(new Color(255,245,167));
		txtrOznakaLinije.setBounds(66, 73, 146, 41);
		frame.add(txtrOznakaLinije);
		GridLayout layout = new GridLayout(0,7);
		textField1 = new JTextField();
		textField1.setBounds(66, 121, 146, 27);
		textField1.setBackground(new Color(255,245,167));
		frame.getContentPane().add(textField1);
		textField1.setColumns(10);
		
		JButton btnNewButton = new JButton("Evidentiraj");
		btnNewButton.setBackground(new Color(255, 245, 167));
		/*btnNewButton.addActionListener(new ActionListener() {
			/*public void actionPerformed(ActionEvent e) {
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
					content="{\"sign\":\""+textField1.getText()+"\",\"stations\":[";
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
		}
			});*/
		
		btnNewButton.setBounds(407, 265, 85, 21);
				frame.getContentPane().add(btnNewButton);
				
				
				
				textField4 = new JTextField();
				textField4.setColumns(10);
				textField4.setBackground(new Color(255, 245, 167));
				textField4.setBounds(66, 158, 146, 27);
				frame.getContentPane().add(textField4);
				
				textField7 = new JTextField();
				textField7.setColumns(10);
				textField7.setBackground(new Color(255, 245, 167));
				textField7.setBounds(66, 195, 146, 27);
				frame.getContentPane().add(textField7);
				
				JTextArea txtrOznakaStanice = new JTextArea();
				txtrOznakaStanice.setText("Oznaka stanice");
				txtrOznakaStanice.setEditable(false);
				txtrOznakaStanice.setBackground(new Color(255, 245, 167));
				txtrOznakaStanice.setBounds(222, 73, 142, 41);
				frame.getContentPane().add(txtrOznakaStanice);
				
				textField2 = new JTextField();
				textField2.setColumns(10);
				textField2.setBackground(new Color(255, 245, 167));
				textField2.setBounds(222, 121, 142, 27);
				frame.getContentPane().add(textField2);
				
				textField5 = new JTextField();
				textField5.setColumns(10);
				textField5.setBackground(new Color(255, 245, 167));
				textField5.setBounds(222, 158, 142, 27);
				frame.getContentPane().add(textField5);
				
				textField8 = new JTextField();
				textField8.setColumns(10);
				textField8.setBackground(new Color(255, 245, 167));
				textField8.setBounds(222, 195, 142, 27);
				frame.getContentPane().add(textField8);
				
				JTextArea txtrPlaniranoVrijemeProlaska = new JTextArea();
				txtrPlaniranoVrijemeProlaska.setLineWrap(true);
				txtrPlaniranoVrijemeProlaska.setText("Planirano vrijeme prolaska");
				txtrPlaniranoVrijemeProlaska.setEditable(false);
				txtrPlaniranoVrijemeProlaska.setBackground(new Color(255, 245, 167));
				txtrPlaniranoVrijemeProlaska.setBounds(374, 73, 145, 41);
				frame.getContentPane().add(txtrPlaniranoVrijemeProlaska);
				
				textField3 = new JTextField();
				textField3.setColumns(10);
				textField3.setBackground(new Color(255, 245, 167));
				textField3.setBounds(374, 121, 145, 27);
				frame.getContentPane().add(textField3);
				
				textField6 = new JTextField();
				textField6.setColumns(10);
				textField6.setBackground(new Color(255, 245, 167));
				textField6.setBounds(374, 158, 145, 27);
				frame.getContentPane().add(textField6);
				
				textField9 = new JTextField();
				textField9.setColumns(10);
				textField9.setBackground(new Color(255, 245, 167));
				textField9.setBounds(374, 195, 145, 27);
				frame.getContentPane().add(textField9);
				
				JTextArea txtrVrijemeProlaska = new JTextArea();
				txtrVrijemeProlaska.setText("Vrijeme prolaska");
				txtrVrijemeProlaska.setEditable(false);
				txtrVrijemeProlaska.setBackground(new Color(255, 245, 167));
				txtrVrijemeProlaska.setBounds(529, 73, 145, 41);
				frame.getContentPane().add(txtrVrijemeProlaska);
				
				textField10 = new JTextField();
				textField10.setColumns(10);
				textField10.setBackground(new Color(255, 245, 167));
				textField10.setBounds(529, 121, 145, 27);
				frame.getContentPane().add(textField10);
				
				textField11 = new JTextField();
				textField11.setColumns(10);
				textField11.setBackground(new Color(255, 245, 167));
				textField11.setBounds(529, 158, 145, 27);
				frame.getContentPane().add(textField11);
				
				textField12 = new JTextField();
				textField12.setColumns(10);
				textField12.setBackground(new Color(255, 245, 167));
				textField12.setBounds(529, 195, 145, 27);
				frame.getContentPane().add(textField12);
				
				JTextArea txtrEvidentirajProlazakVoza = new JTextArea();
				txtrEvidentirajProlazakVoza.setLineWrap(true);
				txtrEvidentirajProlazakVoza.setText("Evidentiraj prolazak voza");
				txtrEvidentirajProlazakVoza.setEditable(false);
				txtrEvidentirajProlazakVoza.setBackground(new Color(255, 245, 167));
				txtrEvidentirajProlazakVoza.setBounds(685, 73, 145, 41);
				frame.getContentPane().add(txtrEvidentirajProlazakVoza);
				
				JCheckBox chckbxNewCheckBox1 = new JCheckBox("");
				chckbxNewCheckBox1.setBounds(695, 121, 135, 27);
				frame.getContentPane().add(chckbxNewCheckBox1);
				
				JCheckBox chckbxNewCheckBox2 = new JCheckBox("");
				chckbxNewCheckBox2.setBounds(695, 158, 135, 27);
				frame.getContentPane().add(chckbxNewCheckBox2);
				
				JCheckBox chckbxNewCheckBox3 = new JCheckBox("");
				chckbxNewCheckBox3.setBounds(695, 195, 135, 27);
				frame.getContentPane().add(chckbxNewCheckBox3);
				Line[] lines=RestMethods.method("GET", id);
				//int i=1;
				 //textField1.setText(lines[0].getSign());
				 //textField2.setText(lines[0].getStations().stream().filter(elem->elem.getStation().equals(id)).toString());
				//tetxFiled3.setText(line)
				frame.setVisible(true);
			}
}
