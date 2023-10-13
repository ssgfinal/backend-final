package ssg.com.houssg.dto;

public class AccommodationOcrDto {

	private String accomName;
	private String businessNumber;
	
	
	public String getAccomName() {
		return accomName;
	}
	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}
	public String getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	public AccommodationOcrDto(String accomName, String businessNumber) {
		super();
		this.accomName = accomName;
		this.businessNumber = businessNumber;
	}
	public AccommodationOcrDto() {
		super();
	}
	@Override
	public String toString() {
		return "AccommodationOcrDto [accomName=" + accomName + ", businessNumber=" + businessNumber + "]";
	}
	
}
