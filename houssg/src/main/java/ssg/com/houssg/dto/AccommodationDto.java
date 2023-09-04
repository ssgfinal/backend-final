package ssg.com.houssg.dto;

public class AccommodationDto {

	private int accom_number;
	private String accom_name;
	private String accom_address;
	private String tele_number;
	private String accom_category;
	private String accom_details;
	private String check_in;
	private String check_out;
	private int business_number;
	private String id;
	private int auth;
	private int approval_request;
	private int deletion_request;
	private String zip_code;
	
	public int getAccom_number() {
		return accom_number;
	}
	public void setAccom_number(int accom_number) {
		this.accom_number = accom_number;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}
	public String getAccom_address() {
		return accom_address;
	}
	public void setAccom_address(String accom_address) {
		this.accom_address = accom_address;
	}
	public String getTele_number() {
		return tele_number;
	}
	public void setTele_number(String tele_number) {
		this.tele_number = tele_number;
	}
	public String getAccom_category() {
		return accom_category;
	}
	public void setAccom_category(String accom_category) {
		this.accom_category = accom_category;
	}
	public String getAccom_details() {
		return accom_details;
	}
	public void setAccom_details(String accom_details) {
		this.accom_details = accom_details;
	}
	public String getCheck_in() {
		return check_in;
	}
	public void setCheck_in(String check_in) {
		this.check_in = check_in;
	}
	public String getCheck_out() {
		return check_out;
	}
	public void setCheck_out(String check_out) {
		this.check_out = check_out;
	}
	public int getBusiness_number() {
		return business_number;
	}
	public void setBusiness_number(int business_number) {
		this.business_number = business_number;
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
	public int getApproval_request() {
		return approval_request;
	}
	public void setApproval_request(int approval_request) {
		this.approval_request = approval_request;
	}
	public int getDeletion_request() {
		return deletion_request;
	}
	public void setDeletion_request(int deletion_request) {
		this.deletion_request = deletion_request;
	}
	
	public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
    
	public AccommodationDto() {
		super();
	}
	public AccommodationDto(int accom_number, String accom_name, String accom_address, String tele_number,
			String accom_category, String accom_details, String check_in, String check_out, int business_number,
			String id, int auth, int approval_request, int deletion_request) {
		super();
		this.accom_number = accom_number;
		this.accom_name = accom_name;
		this.accom_address = accom_address;
		this.tele_number = tele_number;
		this.accom_category = accom_category;
		this.accom_details = accom_details;
		this.check_in = check_in;
		this.check_out = check_out;
		this.business_number = business_number;
		this.id = id;
		this.auth = auth;
		this.approval_request = approval_request;
		this.deletion_request = deletion_request;
	}
	@Override
	public String toString() {
		return "AccommodationDto [accom_number=" + accom_number + ", accom_name=" + accom_name + ", accom_address="
				+ accom_address + ", tele_number=" + tele_number + ", accom_category=" + accom_category
				+ ", accom_details=" + accom_details + ", check_in=" + check_in + ", check_out=" + check_out
				+ ", business_number=" + business_number + ", id=" + id + ", auth=" + auth + ", approval_request="
				+ approval_request + ", deletion_request=" + deletion_request + ", zip_code=" + zip_code + "]";
	}
	
	
	
}