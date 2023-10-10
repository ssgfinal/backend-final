package ssg.com.houssg.dto;

import java.util.Arrays;
import java.util.List;

public class AccommodationRequest {

	private int accomNumber;
    private String accomName;
    private String accomAddress;
    private String teleNumber;
    private String accomCategory;
    private String accomDetails;
    private String checkIn;
    private String checkOut;
    private String businessNumber;
    // private FacilityDto facilityDto;
    private int facilityDto[];
    
    public AccommodationRequest() {
		// TODO Auto-generated constructor stub
	}

	public AccommodationRequest(int accomNumber, String accomName, String accomAddress, String teleNumber,
			String accomCategory, String accomDetails, String checkIn, String checkOut, String businessNumber,
			int[] facilityDto) {
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
		this.facilityDto = facilityDto;
	}

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

	public int[] getFacilityDto() {
		return facilityDto;
	}

	public void setFacilityDto(int[] facilityDto) {
		this.facilityDto = facilityDto;
	}

	@Override
	public String toString() {
		return "AccommodationRequest [accomNumber=" + accomNumber + ", accomName=" + accomName + ", accomAddress="
				+ accomAddress + ", teleNumber=" + teleNumber + ", accomCategory=" + accomCategory + ", accomDetails="
				+ accomDetails + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", businessNumber="
				+ businessNumber + ", facilityDto=" + Arrays.toString(facilityDto) + "]";
	}
    
    
    
    /*
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
		return "AccommodationRequest [accomNumber=" + accomNumber + ", accomName=" + accomName
				+ ", accomAddress=" + accomAddress + ", teleNumber=" + teleNumber + ", accomCategory=" + accomCategory
				+ ", accomDetails=" + accomDetails + ", checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", businessNumber=" + businessNumber + ", facilityDto=" + facilityDto + "]";
	}
	
	public AccommodationRequest(int accomNumber, String accomName, String accomAddress, String teleNumber,
			String accomCategory, String accomDetails, String checkIn, String checkOut, String businessNumber,
			FacilityDto facilityDto) {
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
		this.facilityDto = facilityDto;
	}*/
}
