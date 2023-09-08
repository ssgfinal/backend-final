package ssg.com.houssg.dto;

public class FavoriteDto {

	private int isFavorite;
	private String id;
	private int accomNumber;
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}
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
	public FavoriteDto(int isFavorite, String id, int accomNumber) {
		super();
		this.isFavorite = isFavorite;
		this.id = id;
		this.accomNumber = accomNumber;
	}
	
	
	
	public FavoriteDto(String id, int accomNumber) {
		super();
		this.id = id;
		this.accomNumber = accomNumber;
	}
	@Override
	public String toString() {
		return "FavoriteDto [isFavorite=" + isFavorite + ", id=" + id + ", accomNumber=" + accomNumber + "]";
	}
	
	
}
