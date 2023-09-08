package ssg.com.houssg.dto;

public class RoomDto {
	
	private int roomNumber;
	private String roomCategory;
	private String roomDetails;
	private int roomPrice;
	private int roomAvailability;
	private int accomNumber;
	private int roomUse;
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}
	public String getRoomDetails() {
		return roomDetails;
	}
	public void setRoomDetails(String roomDetails) {
		this.roomDetails = roomDetails;
	}
	public int getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}
	public int getRoomAvailability() {
		return roomAvailability;
	}
	public void setRoomAvailability(int roomAvailability) {
		this.roomAvailability = roomAvailability;
	}
	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public int getRoomUse() {
		return roomUse;
	}
	public void setRoomUse(int roomUse) {
		this.roomUse = roomUse;
	}
	public RoomDto() {
		super();
	}
	public RoomDto(int roomNumber, String roomCategory, String roomDetails, int roomPrice, int roomAvailability,
			int accomNumber, int roomUse) {
		super();
		this.roomNumber = roomNumber;
		this.roomCategory = roomCategory;
		this.roomDetails = roomDetails;
		this.roomPrice = roomPrice;
		this.roomAvailability = roomAvailability;
		this.accomNumber = accomNumber;
		this.roomUse = roomUse;
	}
	@Override
	public String toString() {
		return "RoomDto [roomNumber=" + roomNumber + ", roomCategory=" + roomCategory + ", roomDetails=" + roomDetails
				+ ", roomPrice=" + roomPrice + ", roomAvailability=" + roomAvailability + ", accomNumber=" + accomNumber
				+ ", roomUse=" + roomUse + "]";
	}
	
	
	
	
}
