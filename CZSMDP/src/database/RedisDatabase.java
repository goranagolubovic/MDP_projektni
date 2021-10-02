package database;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import model.Line;
import model.StationTime;
import model.User;
import redis.clients.jedis.Jedis;

public class RedisDatabase {
	private static List<Line> lines=new ArrayList<>() ;
	private static Jedis jedis;
	private static Gson gson;
	static
	{
		 jedis=new Jedis("localhost");
		 gson=new Gson();
		 lines.add(new Line("linija#1", Arrays.asList(new StationTime("A", "16:30:00"),new StationTime("B", "16:45:00"))));
		 lines.add(new Line("linija#2", Arrays.asList(new StationTime("G", "17:30:00"),new StationTime("D", "17:45:00"))));
		 lines.add(new Line("linija#3", Arrays.asList(new StationTime("A", "16:30:00"),new StationTime("D", "16:45:00"))));
		 lines.add(new Line("linija#4", Arrays.asList(new StationTime("C", "13:30:00"),new StationTime("B", "13:45:00"))));
		 
		 for(int i=0;i<lines.size();i++) {
		 jedis.set(((Line) lines.get(i)).getSign(),gson.toJson((Line) lines.get(i)));
		 }
		 
	}
public static void main(String [] args) {
	System.out.println(jedis.get("linija#5"));
}
	
	public  boolean addLineInDatabase(String line) {
		Line l=gson.fromJson(line, Line.class);
		if(jedis.set(l.getSign(),gson.toJson(l.getStations())) != null) {
			return true;
		}
		return false;
	}
	public String getLineFromDatbase(String signOfStation) {
		String line="";
		Set<String>keys=jedis.keys("linija#*");
		Iterator<String> it = keys.iterator();
	    while (it.hasNext()) {
	        String key = it.next();
	        
	        String value=gson.fromJson(jedis.get(key),String.class);
	        if(value.contains(signOfStation)) {
	        	line+=key+";"+value+";";
	        }
	    }
		return line;
	}
	
	public List<Line> getAllLines() {
		return jedis.keys("linija#*")
					.stream()
					.sorted()
					.map(jedis::get)
					.map(elem -> gson.fromJson(elem, Line.class))
					.collect(Collectors.toList());
	}
	
}
