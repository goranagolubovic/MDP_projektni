package views;

import java.awt.EventQueue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
class ImagePanel extends JComponent {
    private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}

// elsewhere

public class ZSMDPWindow {


	private JFrame frame;
	private String idZSMDP;


	/**
	 * Create the application.
	 */
	

	public ZSMDPWindow(String idZSMDP) {
		this.idZSMDP=idZSMDP;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setTitle(idZSMDP);
		System.out.print(idZSMDP);
		Image img = Toolkit.getDefaultToolkit().getImage("C:\\Users\\goran\\Desktop\\MDP-projektni\\ZSMDP\\images\\template1.jpg");
		frame.setBounds(100, 100, 577, 603);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(205,116,63));
		frame.setVisible(true);
		JLabel lbl1 = new JLabel();
		lbl1.addMouseMotionListener(new MouseAdapter() {
		            public void mouseEntered(MouseEvent me) {
		                startTimer();
		            }
		        });
		lbl1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println(true);
				EventQueue.invokeLater(()-> new TrainPassingRecording(idZSMDP));
				}
		});
		lbl1.setBounds(43, 64, 57, 50);
		lbl1.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\goran\\Desktop\\MDP-projektni\\ZSMDP\\images\\add.png").getImage().getScaledInstance(lbl1.getWidth(), lbl1.getHeight(), Image.SCALE_SMOOTH)));
		frame.getContentPane().add(lbl1);
		
		JLabel lbl2 = new JLabel("New label");
		lbl2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				EventQueue.invokeLater(()-> new ViewTimeTable(idZSMDP));
			}
		});
		lbl2.setBounds(43, 137, 57, 50);
		lbl2.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\goran\\Desktop\\MDP-projektni\\ZSMDP\\images\\driving.png").getImage().getScaledInstance(lbl2.getWidth(), lbl2.getHeight(), Image.SCALE_SMOOTH)));
		frame.getContentPane().add(lbl2);;
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
