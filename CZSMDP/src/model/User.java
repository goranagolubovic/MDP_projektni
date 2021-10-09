package model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	private String username;
	private String password;
	private String id;
	
	public User() {
		
	}
	
	
	 public User(String username, String password, String idZSMDP) {
		super();
		this.username = username;
		try {
			this.password = User.toHexString(User.getSHA(password));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.id = idZSMDP;
		
		
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", idZSMDP=" + id + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String idZSMDP) {
		this.id = idZSMDP;
	}

	public static byte[] getSHA(String input) throws NoSuchAlgorithmException  
	    {  
	        /* MessageDigest instance for hashing using SHA256 */  
	        MessageDigest md = MessageDigest.getInstance("SHA-256");  
	  
	        /* digest() method called to calculate message digest of an input and return array of byte */  
	        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
	    }  
	      
	    public static String toHexString(byte[] hash)  
	    {  
	        /* Convert byte array of hash into digest */  
	        BigInteger number = new BigInteger(1, hash);  
	  
	        /* Convert the digest into hex value */  
	        StringBuilder hexString = new StringBuilder(number.toString(16));  
	  
	        /* Pad with leading zeros */  
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');  
	        }  
	  
	        return hexString.toString();  
	    }  

}
