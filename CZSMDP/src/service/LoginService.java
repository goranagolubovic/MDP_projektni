package service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import control.XMLSerializer;
import model.User;

public class LoginService {
		private final static String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		{
		for(int i=1;i<=4;i++) {
		User user=new User("korisnik"+i,"lozinka"+i,String.valueOf(alphabet.charAt(i)));
		XMLSerializer.serializeWithXML(user);
		}
		}
	public User login(String user,String password) {
		User u=XMLSerializer.deserializeWithXML(user);
		if(u!=null) {
			try {
				if(user.equals(u.getUsername()) && 
						User.toHexString(User.getSHA(String.valueOf(password))).equals(u.getPassword())) {
				return u;
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public String[] getStations(){
		int dim=XMLSerializer.deserializeWithXML().length;
		List<String>stations=new ArrayList<>();
		User[] users=XMLSerializer.deserializeWithXML();
		int i=0;
		while(i<users.length) {
			User u=users[i];
			 if(stations.contains(u.getId())) {
					i++;
				}
				else {
					stations.add(users[i].getId());
					i++;
				}
			}
		return stations.toArray(new String[stations.size()]);
	}
	public String[] getUsersForSelectedStation(String station) {
		List<String>usersName=new ArrayList<>();
		User[] users=XMLSerializer.deserializeWithXML();
		for(int i=0;i<users.length;i++) {
			if(users[i].getId().equals(station)) {
						usersName.add(users[i].getUsername());
				}
			}
		return usersName.toArray(new String[usersName.size()]);
	}
}
