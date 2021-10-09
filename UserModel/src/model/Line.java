package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line {
	private String sign;
	private List<String> stations;
	
	public String getSign() {
		return sign;
	}
	public Line() {
		
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public List<String> getStations() {
		return stations;
	}
	public void setStations(List<String> stations) {
		this.stations = stations;
	}
	public Line(String sign, List<String> stations) {
		super();
		this.sign = sign;
		this.stations = stations;
	}
	@Override
	public String toString() {
		return "Line [sign=" + sign + ", stations=" + stations + "]";
	}
}


