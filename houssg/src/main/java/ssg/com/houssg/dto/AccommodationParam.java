package ssg.com.houssg.dto;

public class AccommodationParam {

    private String search;
    private String type;
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

	public AccommodationParam() {
		super();
	}
	public AccommodationParam(String search, String type) {
		super();
		this.search = search;
		this.type = type;

	}
	@Override
	public String toString() {
		return "AccommodationParam [search=" + search + ", type=" + type + "]";
	}
    
}