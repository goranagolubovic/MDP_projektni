package model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	private String Username;
	private String password;
	private int idZSMDP;
	public String getUsername() {
		return Username;
	}
	public void setUsername(String Username) {
		this.Username = Username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User() {
		
	}
	public User(String Username, String password, int idZSMDP) {
		super();
		this.Username = Username;
		this.idZSMDP = idZSMDP;
		try {
			this.password = toHexString(getSHA(password));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public String toString() {
		return "User [Username=" + Username + ", password=" + password + ", idZSMDP=" + idZSMDP + "]";
	}
	public int getIdZSMDP() {
		return idZSMDP;
	}
	public void setUserID(int idZSMDP) {
		this.idZSMDP = idZSMDP;
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
