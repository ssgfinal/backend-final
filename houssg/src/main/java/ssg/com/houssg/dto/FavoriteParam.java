package ssg.com.houssg.dto;

public class FavoriteParam {

	private String id;
	private int accomNumber;
	private String accomAddress;
	private double avgRating;
	private String accomName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public String getAccomAddress() {
		return accomAddress;
	}
	public void setAccomAddress(String accomAddress) {
		this.accomAddress = accomAddress;
	}
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public String getAccomName() {
		return accomName;
	}
	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}
	public FavoriteParam() {
		super();
	}
	public FavoriteParam(String id, int accomNumber, String accomAddress, double avgRating, String accomName) {
		super();
		this.id = id;
		this.accomNumber = accomNumber;
		this.accomAddress = accomAddress;
		this.avgRating = avgRating;
		this.accomName = accomName;
	}
	
}
