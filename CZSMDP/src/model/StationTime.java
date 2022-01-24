package model;

public class StationTime {
	private String station;
	private String time;
	private String actualTimeOfPassing;
	private int isTrainArrived;
	public String getActualTimeOfPassing() {
		return actualTimeOfPassing;
	}
	public void setActualTimeOfPassing(String actualTimeOfPassing) {
		this.actualTimeOfPassing = actualTimeOfPassing;
	}
	public int getIsTrainArrived() {
		return isTrainArrived;
	}
	public void setIsTrainArrived(int isTrainArrived) {
		this.isTrainArrived = isTrainArrived;
	}
	public StationTime(String station, String time,String actualTimeOfPassing,int isTrainArrived) {
		super();
		this.station = station;
		this.time = time;
		this.actualTimeOfPassing=actualTimeOfPassing;
		this.isTrainArrived=isTrainArrived;
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
		String trainArrived=(isTrainArrived==1) ? "Voz je prošao kroz stanicu":"Voz nije prošao kroz stanicu";
		return "Stanica: "+station+"  "+"Planirano vrijeme prolaska: "+time+"  "+"Stvarno vrijeme prolaska: "+actualTimeOfPassing+" "+trainArrived;
	}

}