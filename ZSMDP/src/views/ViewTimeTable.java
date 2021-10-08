package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.google.gson.Gson;

import control.RestMethods;
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
		String content="";
		Line[] line=RestMethods.method("GET",id);
		for(Line l:line) {
			content+=line.toString()+"\n";
		}
		textArea.setText(content);
		textArea.setEditable(false);
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
