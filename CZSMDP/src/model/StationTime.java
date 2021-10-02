package model;
import java.sql.Time;

public class StationTime {
	private String station;
	private String time;
	public StationTime(String station, String time) {
		super();
		this.station = station;
		this.time = time;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}