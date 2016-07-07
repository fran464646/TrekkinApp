package tfg.model.util;

public class CoordinateAux {

	private Double longitude;
	private Double latitude;
	
	public CoordinateAux(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public CoordinateAux() {
		super();
	}
	
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
}
