package model;
import java.util.List;

public class Line {
	private String sign;
	private List<StationTime> stations;
	
	public String getSign() {
		return sign;
	}
	public Line() {
		
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public List<StationTime> getStations() {
		return stations;
	}
	public void setStations(List<StationTime> stations) {
		this.stations = stations;
	}
	public Line(String sign, List<StationTime> stations) {
		super();
		this.sign = sign;
		this.stations = stations;
	}
	@Override
	public String toString() {
		String s="";
		s+=sign +"\n";
		for(StationTime st:stations) {
			s+="      ";
			s+=st+"\n";
		}
		return s;
	}
}


