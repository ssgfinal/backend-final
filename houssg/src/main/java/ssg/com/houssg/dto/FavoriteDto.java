package ssg.com.houssg.dto;

public class FavoriteDto {

	private String id;
	private int accomNumber;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public FavoriteDto() {
		super();
	}
	public FavoriteDto(String id, int accomNumber) {
		super();

		this.id = id;
		this.accomNumber = accomNumber;
	}
	

	@Override
	public String toString() {
		return "FavoriteDto [id=" + id + ", accomNumber=" + accomNumber + "]";
	}
	
	
}
