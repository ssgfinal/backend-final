package ssg.com.houssg.dto;

public class AccommodationParam {

    private String search;
    private String type;
    private String startDate;
    private String lastDate;
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
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public AccommodationParam() {
		super();
	}
	public AccommodationParam(String search, String type, String startDate, String lastDate) {
		super();
		this.search = search;
		this.type = type;
		this.startDate = startDate;
		this.lastDate = lastDate;
	}
    
    

    
}