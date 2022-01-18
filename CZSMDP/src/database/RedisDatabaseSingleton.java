package database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import model.Line;
import model.StationTime;
import redis.clients.jedis.Jedis;

public class RedisDatabaseSingleton {
	private static Jedis jedis;
	private static Gson gson;
	private static List<Line> lines = new ArrayList<>();
	private static RedisDatabaseSingleton instance = null;

	{
		jedis = new Jedis("localhost");
		gson = new Gson();

		lines.add(new Line("linija1",
				Arrays.asList(new StationTime("A", "16:30:00", "", 0), new StationTime("B", "16:45:00", "", 0))));
		lines.add(new Line("linija2",
				Arrays.asList(new StationTime("G", "17:30:00", "", 0), new StationTime("D", "17:45:00", "", 0))));
		lines.add(new Line("linija3",
				Arrays.asList(new StationTime("A", "16:30:00", "", 0), new StationTime("D", "16:45:00", "", 0))));
		lines.add(new Line("linija4",
				Arrays.asList(new StationTime("C", "13:30:00", "", 0), new StationTime("B", "13:45:00", "", 0))));

		for (int i = 0; i < lines.size(); i++) {
			if (!jedis.exists(lines.get(i).getSign())) {
				System.out.println("Prvo dodavanje");
				jedis.set(((Line) lines.get(i)).getSign(), gson.toJson((Line) lines.get(i)));
			}
		}
	}
	private RedisDatabaseSingleton() {
	}
	public static Jedis getJedis() {
		return jedis;
	}

	public static void setJedis(Jedis jedis) {
		RedisDatabaseSingleton.jedis = jedis;
	}

	public static Gson getGson() {
		return gson;
	}

	public static void setGson(Gson gson) {
		RedisDatabaseSingleton.gson = gson;
	}


	public static RedisDatabaseSingleton getInstance() {
		// To ensure only one instance is created
		if (instance == null) {
			instance = new RedisDatabaseSingleton();
		}
		return instance;
	}
}
