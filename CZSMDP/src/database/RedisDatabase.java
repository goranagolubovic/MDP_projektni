package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import model.Line;
import model.StationTime;
import redis.clients.jedis.Jedis;

public class RedisDatabase {
	
	private RedisDatabaseSingleton singleton;
	private Jedis jedis;
	public Jedis getJedis() {
		return jedis;
	}
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	public Gson getGson() {
		return gson;
	}
	public void setGson(Gson gson) {
		this.gson = gson;
	}
	private Gson gson;
	public RedisDatabase() {
		singleton=RedisDatabaseSingleton.getInstance();
		jedis=singleton.getJedis();
		gson=singleton.getGson();
	}
	public  boolean addLineInDatabase(Line line) {
		if(jedis.set(line.getSign(),line.toString()) != null) {
			return true;
		}
		return false;
	}
	public List<Line> getLineFromDatbase(String signOfStation) {
		List<Line> lines=new ArrayList<>();
		Set<String>keys=jedis.keys("linija*");
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
		return jedis.keys("linija*")
					.stream()
					.sorted()
					.map(jedis::get)
					.map(elem -> gson.fromJson(elem, Line.class))
					.collect(Collectors.toList());
	}

	public boolean update(String id,Line line) {
		String res = jedis.set(id, gson.toJson(line));
		System.out.println(res);
		if ("OK".equals(res)) {
			return true;
		} else
			return false;
	}
	public boolean deleteLine(String id) {
		if(jedis.del(id) == 1) {
			return true;
		}
		return false;
	}
	
}
