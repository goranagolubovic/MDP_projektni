package model;
import java.sql.Time;
import java.time.LocalTime;

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
	public StationTime() {
		
	}
	@Override
	public String toString() {
		String isTrainArrived="";
		Time currentTime=Time.valueOf(LocalTime.now());
		Time temp=Time.valueOf(time);
		isTrainArrived=(temp.compareTo(currentTime)>0) ? "Voz je prošao kroz stanicu":"Voz nije prošao kroz stanicu";
		return station+"  "+time+"  "+isTrainArrived;
	}
}