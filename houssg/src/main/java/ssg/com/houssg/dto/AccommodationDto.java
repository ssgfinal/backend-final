package ssg.com.houssg.dto;

public class AccommodationDto {

	private int accomNumber;
	private String accomName;
	private String accomAddress;
	private String teleNumber;
	private String accomCategory;
	private String accomDetails;
	private String checkIn;
	private String checkOut;
	private int businessNumber;
	private String id;
	private int auth;
	private int approvalRequest;
	private int deletionRequest;
	private String zipCode;
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
	public int getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(int businessNumber) {
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
	public String getZip_code() {
		return zipCode;
	}
	public void setZip_code(String zip_code) {
		this.zipCode = zip_code;
	}
	public AccommodationDto() {
		super();
	}
	public AccommodationDto(int accomNumber, String accomName, String accomAddress, String teleNumber,
			String accomCategory, String accomDetails, String checkIn, String checkOut, int businessNumber, String id,
			int auth, int approvalRequest, int deletionRequest, String zip_code) {
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
		this.zipCode = zip_code;
	}
	
	
}