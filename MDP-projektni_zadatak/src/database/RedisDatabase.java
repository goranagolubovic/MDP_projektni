package database;

import java.util.ArrayList;


import model.User;
import model.XMLSerializer;
import redis.clients.jedis.Jedis;

public class RedisDatabase {
	public Jedis jedis;
	public XMLSerializer serializer;
	{
		 jedis=new Jedis("localhost");	
		 serializer=new XMLSerializer();
	}

	public static ArrayList<User> listOfUsers=new ArrayList<User>();
	public  void init() throws Exception{
		for(int i=1;i<=4;i++)
		listOfUsers.add(new User("korisnik"+i,"lozinka"+i,i));
		
		
		try {
		
			for(int i=1;i<5;i++) {
			//jedis.set(String.valueOf(i), serializer.serializeWithXML(listOfUsers.get(i-1)));
			}
			
		}
		catch(Exception e) {
		e.printStackTrace();
		}
		
	}
	public User getUserFromDatabase(String name) {
		for(int i=1;i<=listOfUsers.size();i++) {
			String value=jedis.get(String.valueOf(i));
			if(!value.isEmpty()) {
				User user=serializer.deserializeWithXML(value);
				if(user.getUsername().equals(name)) {
				return user;
			}
		}
		
	}
		return null;
	}
	}