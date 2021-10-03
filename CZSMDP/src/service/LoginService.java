package service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
						User.toHexString(User.getSHA(String.valueOf(password))).equals(u.getPassword()))
				return u;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
