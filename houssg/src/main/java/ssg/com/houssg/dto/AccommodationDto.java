package ssg.com.houssg.dto;

import java.io.Serializable;
import java.util.List;

public class AccommodationDto implements Serializable {
	private int accomNumber;
	private String accomName;
	private String accomAddress;
	private String teleNumber;
	private String accomCategory;
	private String accomDetails;
	private String checkIn;
	private String checkOut;
	private String businessNumber;
	private String id;
	private int auth;
	private int approvalRequest;
	private int deletionRequest;
	private String img;
	private int addRequest;
	private int[] service;
	private double avgRating;
	private int maxPrice;

	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public String getAccomName() {
		return accomName;
	}
	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}
	public String getAccomAddress() {
		return accomAddress;
	}
	public void setAccomAddress(String accomAddress) {
		this.accomAddress = accomAddress;
	}
	public String getTeleNumber() {
		return teleNumber;
	}
	public void setTeleNumber(String teleNumber) {
		this.teleNumber = teleNumber;
	}
	public String getAccomCategory() {
		return accomCategory;
	}
	public void setAccomCategory(String accomCategory) {
		this.accomCategory = accomCategory;
	}
	public String getAccomDetails() {
		return accomDetails;
	}
	public void setAccomDetails(String accomDetails) {
		this.accomDetails = accomDetails;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public String getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public int getApprovalRequest() {
		return approvalRequest;
	}
	public void setApprovalRequest(int approvalRequest) {
		this.approvalRequest = approvalRequest;
	}
	public int getDeletionRequest() {
		return deletionRequest;
	}
	public void setDeletionRequest(int deletionRequest) {
		this.deletionRequest = deletionRequest;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getAddRequest() {
		return addRequest;
	}
	public void setAddRequest(int addRequest) {
		this.addRequest = addRequest;
	}
	public int[] getService() {
		return service;
	}
	public void setService(int[] service) {
		this.service = service;
	}
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public AccommodationDto() {
	}
	
	
	
	public AccommodationDto(int accomNumber, String accomName, String accomAddress, String teleNumber, String accomCategory,
		String accomDetails, String checkIn, String checkOut, String businessNumber, String id, int auth,
		int approvalRequest, int deletionRequest, String img, int addRequest, int[] service, double avgRating, int maxPrice) {
		super();
		this.accomNumber = accomNumber;
		this.accomName = accomName;
		this.accomAddress = accomAddress;
		this.teleNumber = teleNumber;
		this.accomCategory = accomCategory;
		this.accomDetails = accomDetails;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.businessNumber = businessNumber;
		this.id = id;
		this.auth = auth;
		this.approvalRequest = approvalRequest;
		this.deletionRequest = deletionRequest;
		this.img = img;
		this.addRequest = addRequest;
		this.service = service;
		this.avgRating = avgRating;
		this.maxPrice = maxPrice;
	}
	@Override
	public String toString() {
		return "AccommodationDto [accomNumber=" + accomNumber + ", accomName=" + accomName + ", accomAddress="
				+ accomAddress + ", teleNumber=" + teleNumber + ", accomCategory=" + accomCategory + ", accomDetails="
				+ accomDetails + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", businessNumber="
				+ businessNumber + ", id=" + id + ", auth=" + auth + ", approvalRequest=" + approvalRequest
				+ ", deletionRequest=" + deletionRequest + ", img=" + img + ", addRequest="
				+ addRequest + "avgRating" + avgRating + "maxPrice" + maxPrice + "]";
	}
}