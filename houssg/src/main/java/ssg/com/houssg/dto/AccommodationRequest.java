package ssg.com.houssg.dto;

import java.util.Arrays;
import java.util.List;

public class AccommodationRequest {

	private int accomNumber;
	private String id;
    private String accomName;
    private String accomAddress;
    private String teleNumber;
    private String accomCategory;
    private String accomDetails;
    private String checkIn;
    private String checkOut;
    private String businessNumber;
    private String zipCode;
    private FacilityDto facilityDto;
	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public FacilityDto getFacilityDto() {
        return facilityDto;
    }
    public void setFacilityDto(FacilityDto facilityDto) {
        this.facilityDto = facilityDto;
    }
	public AccommodationRequest() {
		super();
	}
	
	@Override
	public String toString() {
		return "AccommodationRequest [accomNumber=" + accomNumber + ", id=" + id + ", accomName=" + accomName
				+ ", accomAddress=" + accomAddress + ", teleNumber=" + teleNumber + ", accomCategory=" + accomCategory
				+ ", accomDetails=" + accomDetails + ", checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", businessNumber=" + businessNumber + ", zipCode=" + zipCode + ", facilityDto=" + facilityDto + "]";
	}
	
	public AccommodationRequest(int accomNumber, String id, String accomName, String accomAddress, String teleNumber,
			String accomCategory, String accomDetails, String checkIn, String checkOut, String businessNumber,
			String zipCode, FacilityDto facilityDto) {
		super();
		this.accomNumber = accomNumber;
		this.id = id;
		this.accomName = accomName;
		this.accomAddress = accomAddress;
		this.teleNumber = teleNumber;
		this.accomCategory = accomCategory;
		this.accomDetails = accomDetails;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.businessNumber = businessNumber;
		this.zipCode = zipCode;
		this.facilityDto = facilityDto;
	}
}
