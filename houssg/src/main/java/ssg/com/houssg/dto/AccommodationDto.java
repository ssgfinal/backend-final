package ssg.com.houssg.dto;


import java.util.Arrays;


public class AccommodationDto {
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
	private int deletionRequest;
	private String img;
	private int addRequest;
	private int[] service;
	private double avgRating;
	private int minPrice;
	private int reviewCount;
	private String ownerId;
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
	public void setId(String id) {
		this.id = id;
	}
	public void setAuth(int auth) {
		this.auth = auth;
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
	public void setAddRequest(int addRequest) {
		this.addRequest = addRequest;
	}
	public int[] getService() {
		return service;
	}
	public void setService(int[] service) {
		this.service = service;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public AccommodationDto() {
		super();
	}
	public AccommodationDto(int accomNumber, String accomName, String accomAddress, String teleNumber,
			String accomCategory, String accomDetails, String checkIn, String checkOut, String businessNumber,
			String id, int auth, int deletionRequest, String img, int addRequest, int[] service,
			double avgRating, int minPrice, int reviewCount) {
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
		this.deletionRequest = deletionRequest;
		this.img = img;
		this.addRequest = addRequest;
		this.service = service;
		this.avgRating = avgRating;
		this.minPrice = minPrice;
		this.reviewCount = reviewCount;
	}
	@Override
	public String toString() {
		return "AccommodationDto [accomNumber=" + accomNumber + ", accomName=" + accomName + ", accomAddress="
				+ accomAddress + ", teleNumber=" + teleNumber + ", accomCategory=" + accomCategory + ", accomDetails="
				+ accomDetails + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", businessNumber="
				+ businessNumber + ", id=" + id + ", auth=" + auth + ", deletionRequest=" + deletionRequest + ", img="
				+ img + ", addRequest=" + addRequest + ", service=" + Arrays.toString(service) + ", avgRating="
				+ avgRating + ", minPrice=" + minPrice + ", reviewCount=" + reviewCount + "]";
	}

}