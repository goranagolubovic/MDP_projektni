package service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import control.XMLSerializer;
import model.User;
import properties.ConfigProperties;

public class LoginService {
	private final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static Logger log = Logger.getLogger(LoginService.class.getName());
	{
		for (int i = 1; i <= 4; i++) {
			User user = new User("korisnik" + i, "lozinka" + i, String.valueOf(alphabet.charAt(i)));
			XMLSerializer.serializeWithXML(user);
		}
	}

	public User login(String user, String password) {
		// 
		User u = XMLSerializer.deserializeWithXML(user);
		if (u != null) {
			try {
				if (user.equals(u.getUsername())
						&& User.toHexString(User.getSHA(String.valueOf(password))).equals(u.getPassword())) {
					return u;
				}
			} catch (NoSuchAlgorithmException e) {
				Logger.getLogger(LoginService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
		return null;
	}

	public String[] getStations() {
		List<String> stations = new ArrayList<>();
		User[] users = XMLSerializer.deserializeWithXML();
		int i = 0;
		while (i < users.length) {
			User u = users[i];
			if (stations.contains(u.getId())) {
				i++;
			} else {
				stations.add(users[i].getId());
				i++;
			}
		}
		return stations.toArray(new String[stations.size()]);
	}

	public String[] getUsersForSelectedStation(String station) {
		List<String> usersName = new ArrayList<>();
		User[] users = XMLSerializer.deserializeWithXML();
		for (int i = 0; i < users.length; i++) {
			if (users[i].getId().equals(station)) {
				usersName.add(users[i].getUsername());
			}
		}
		return usersName.toArray(new String[usersName.size()]);
	}

	public String[] users() {
		User[] users = XMLSerializer.deserializeWithXML();
		List<String> usernames = new ArrayList<>();
		for (int i = 0; i < users.length; i++) {
			usernames.add(users[i].getUsername());
		}
		return usernames.toArray(new String[usernames.size()]);
	}
}
