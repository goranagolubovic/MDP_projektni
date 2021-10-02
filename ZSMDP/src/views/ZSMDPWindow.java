package views;

import java.awt.EventQueue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class ZSMDPWindow {


	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZSMDPWindow window = new ZSMDPWindow();
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
	public ZSMDPWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	
		frame.setBounds(100, 100, 594, 439);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);;
		
		JLabel lbl1 = new JLabel();
		lbl1.addMouseMotionListener(new MouseAdapter() {
			

		            
		            public void mouseEntered(MouseEvent me) {
		                startTimer();
		            }
		        });

		       
		    
	
		   
		
		lbl1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				TimeTableRecording ttr=new TimeTableRecording();
				ttr.main(null);
				/*
				try {
					Properties prop=new Properties();
				
					prop.load(new FileInputStream("resources"+File.separator+"config.properties"));
					
					/
					// priprema i otvaranje HTTP zahtjeva
					URL url = new URL(prop.getProperty("BASE_URL"));
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST"); 
					conn.setRequestProperty("Content-Type", "application/json");
					// podaci za body dio zahtjeva
					JSONObject input = new JSONObject(new Line());
					// slanje body dijela
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
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			*/}
		});
		lbl1.setBounds(30, 42, 57, 50);
		lbl1.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\goran\\Desktop\\MDP-projektni\\ZSMDP\\images\\add.png").getImage().getScaledInstance(lbl1.getWidth(), lbl1.getHeight(), Image.SCALE_SMOOTH)));
		frame.getContentPane().add(lbl1);
	}
private void startTimer() {
    TimerTask task = new TimerTask() {

    
        public void run() {
            SwingUtilities.invokeLater(new Runnable() {

              
                public void run() {
                    JOptionPane.showMessageDialog(frame, "Test");
                }
            });
        }
    };

    Timer timer = new Timer(true);
    timer.schedule(task, 30);



}
}
