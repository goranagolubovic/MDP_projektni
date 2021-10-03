package database;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		
//		 lines.add(new Line("linija#1", Arrays.asList(new StationTime("A", "16:30:00"),new StationTime("B", "16:45:00"))));
//		 lines.add(new Line("linija#2", Arrays.asList(new StationTime("G", "17:30:00"),new StationTime("D", "17:45:00"))));
//		 lines.add(new Line("linija#3", Arrays.asList(new StationTime("A", "16:30:00"),new StationTime("D", "16:45:00"))));
//		 lines.add(new Line("linija#4", Arrays.asList(new StationTime("C", "13:30:00"),new StationTime("B", "13:45:00"))));
		 
//		 for(int i=0;i<lines.size();i++) {
//		 jedis.set(((Line) lines.get(i)).getSign(),gson.toJson((Line) lines.get(i)));
//		 }
		 
	}
public static void main(String [] args) {
	System.out.println(jedis.get("linija#5"));
}
	
	public  boolean addLineInDatabase(Line line) {
		//Line l=gson.fromJson(line.toString(),Line.class);
		if(jedis.set(line.getSign(),line.toString()) != null) {
			return true;
		}
		return false;
	}
	public List<Line> getLineFromDatbase(String signOfStation) {
		List<Line> lines=new ArrayList<>();
		Set<String>keys=jedis.keys("linija#*");
		Iterator<String> it = keys.iterator();
	    while (it.hasNext()) {
	        String key = it.next();
	        
	        Line value=gson.fromJson(jedis.get(key),Line.class);
	        List<StationTime> stations=value.getStations();
	        for(StationTime st:stations) {
	        	if(st.getStation().equals(signOfStation))
	        		lines.add(new Line(key,stations));
	        }
	        
	    }
		return lines;
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
