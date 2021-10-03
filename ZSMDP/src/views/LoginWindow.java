package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.xml.rpc.ServiceException;


import model.User;

import service.LoginService;
import service.LoginServiceServiceLocator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Canvas;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class LoginWindow {

	private static final String ZSMDP_IMAGES_TRAIN_JPG = "C:\\Users\\goran\\Desktop\\MDP-projektni\\ZSMDP\\images\\train.jpg";
	private JFrame frame;
	private JTextField txtIme;
	private JTextField textField;
	private JPasswordField passwordField;
	//private static RedisDatabase redis;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			  // redis=new RedisDatabase();
				
				try {
					//redis.init();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					LoginWindow window = new LoginWindow();
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
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 10));
		frame.getContentPane().setBackground(new Color(205, 133, 63));
		frame.setBounds(100, 100, 548, 424);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea txtrIme = new JTextArea();
		txtrIme.setEditable(false);
		txtrIme.setColumns(1);
		txtrIme.setBounds(259, 91, 40, 37);
		txtrIme.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtrIme.setBackground(new Color(205, 133, 63));
		txtrIme.setText("Ime");
		frame.getContentPane().add(txtrIme);
		
		textField = new JTextField();
		textField.setBackground(new Color(192, 192, 192));
		textField.setBounds(310, 83, 96, 37);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextArea txtrLozinka = new JTextArea();
		txtrLozinka.setEditable(false);
		txtrLozinka.setText("Lozinka");
		txtrLozinka.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtrLozinka.setBackground(new Color(205, 133, 63));
		txtrLozinka.setBounds(259, 158, 51, 37);
		frame.getContentPane().add(txtrLozinka);
		
		JTextArea txtrPrijavaNaSistem = new JTextArea();
		txtrPrijavaNaSistem.setEditable(false);
		txtrPrijavaNaSistem.setText("Prijava na sistem");
		txtrPrijavaNaSistem.setFont(new Font("Tahoma", Font.BOLD, 24));
		txtrPrijavaNaSistem.setBackground(new Color(205, 133, 63));
		txtrPrijavaNaSistem.setBounds(147, 24, 246, 37);
		frame.getContentPane().add(txtrPrijavaNaSistem);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(192, 192, 192));
		passwordField.setBounds(310, 150, 96, 37);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("Prijavi se");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user=null;
				LoginServiceServiceLocator locator=new LoginServiceServiceLocator();
				try {
					LoginService log=locator.getLoginService();
				    user=log.login(textField.getText(),String.valueOf(passwordField.getPassword()));
				}
				catch(ServiceException e1) {
					e1.printStackTrace();
				}
				catch(RemoteException e2) {
					e2.printStackTrace();
				}
					if(user!=null) {
						
					System.out.println("Uspjesno prijavljen!");
					System.out.println(user.getId());
					final User finalUser = user;
					EventQueue.invokeLater(()-> new ZSMDPWindow(finalUser.getId()));
					
					}
					else {
						System.out.println("Neuspjesno prijavljen!");
					}
			}
		});
		btnNewButton.setBounds(310, 219, 96, 21);
		frame.getContentPane().add(btnNewButton);
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(10, 10, 485, 367);
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon(ZSMDP_IMAGES_TRAIN_JPG).getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_DEFAULT)));
		
		frame.getContentPane().add(lblNewLabel);
		
		
		
	}
}
