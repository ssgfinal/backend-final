package ssg.com.houssg.dto;

public class ReviewParam {

	private String id;
	private int pageSize;
    private int page;
    private int start;
    private int end;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public ReviewParam() {
		super();
	}
	public ReviewParam(String id, int pageSize, int page, int start) {
		super();
		this.id = id;
		this.pageSize = pageSize;
		this.page = page;
		this.start = start;
	}
	@Override
	public String toString() {
		return "ReviewParam [id=" + id + ", pageSize=" + pageSize + ", page=" + page + ", start=" + start + ", end="
				+ end + "]";
	}
	
}
