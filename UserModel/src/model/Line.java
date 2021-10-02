package model;

import java.util.Arrays;

public class Line {
	private String sign;
	private String  [] stations=new String [100];
	@Override
	public String toString() {
		return "Linija [sign=" + sign + ", stations=" + Arrays.toString(stations) + "]";
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String[] getStations() {
		return stations;
	}
	public void setStations(String[] stations) {
		this.stations = stations;
	}
	public Line(String sign, String[] stations) {
		super();
		this.sign = sign;
		this.stations = stations;
	}
	

}
