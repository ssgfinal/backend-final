package ssg.com.houssg.dto;

public class AccommodationParam {

    private String search;
    private String type;
    private String select;
    private int pageSize;
    private int page;
    private int start;
    private int end;
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
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public AccommodationParam() {
		super();
	}
	public AccommodationParam(String search, String type, String select, int pageSize, int page, int start) {
		super();
		this.search = search;
		this.type = type;
		this.select = select;
		this.pageSize = pageSize;
		this.page = page;
		this.start = start;
	}
}