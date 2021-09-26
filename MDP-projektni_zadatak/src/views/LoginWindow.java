package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.xml.rpc.ServiceException;

import control.XMLDeserializer;
import control.XMLDeserializerService;
import control.XMLDeserializerServiceLocator;
import database.RedisDatabase;
import model.User;
import model.XMLSerializer;

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

	private JFrame frame;
	private JTextField txtIme;
	private JTextField textField;
	private JPasswordField passwordField;
	//private static RedisDatabase redis;
	private static XMLSerializer xmlSerializer=new XMLSerializer();;
	/**
	 * Launch the application.
	 */
	{
		for(int i=1;i<=4;i++) {
			User user=new User("korisnik"+i,"lozinka"+i,i);
			xmlSerializer.serializeWithXML(user);
		}
	}
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
				XMLDeserializerServiceLocator locator=new XMLDeserializerServiceLocator();
				try {
					XMLDeserializer deserializer=locator.getXMLDeserializer();
				    user=deserializer.deserializeWithXML(textField.getText());
				}
				catch(ServiceException e1) {
					e1.printStackTrace();
				}
				catch(RemoteException e2) {
					e2.printStackTrace();
				}
				try {
					if(user!=null && user.getPassword().equals(User.toHexString(User.getSHA(String.valueOf(passwordField.getPassword()))))){
					System.out.println("Uspjesno prijavljen!");
					}
					else {
						System.out.println("Neuspjesno prijavljen!");
					}
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(310, 219, 96, 21);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(10, 10, 485, 367);
		lblNewLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\goran\\Desktop\\MDP-projektni\\MDP-projektni_zadatak\\images\\train.jpg").getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_DEFAULT)));
		
		frame.getContentPane().add(lblNewLabel);
		
		
		
	}
}