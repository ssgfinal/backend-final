package ssg.com.houssg.dto;

public class MonthlySalesDto {
	
	private int accomNumber;
	private String accomName; 
	private String yearMonth;
	private String id;
	private int sales;
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
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	 
	
}
