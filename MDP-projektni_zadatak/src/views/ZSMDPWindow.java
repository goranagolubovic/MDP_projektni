package views;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(26, 25, 83, 47);
		btnNewButton.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\goran\\Desktop\\MDP-projektni\\MDP-projektni_zadatak\\images\\add.png").getImage().getScaledInstance(btnNewButton.getWidth(), btnNewButton.getHeight(), Image.SCALE_DEFAULT)));
		frame.getContentPane().add(btnNewButton);
	}

}
