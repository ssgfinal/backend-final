package ssg.com.houssg.dto;

public class AccommodationParam {

    private String search;
    private String type;
    private String startDate;
    private String endDate;
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public AccommodationParam() {
		super();
	}
	public AccommodationParam(String search, String type, String startDate, String endDate) {
		super();
		this.search = search;
		this.type = type;
		this.startDate = startDate;
		this.endDate  = endDate;
	}
	@Override
	public String toString() {
		return "AccommodationParam [search=" + search + ", type=" + type + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
    
}