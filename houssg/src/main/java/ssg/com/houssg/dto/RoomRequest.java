package ssg.com.houssg.dto;

public class RoomRequest {

	private int roomNumber;
	private String roomCategory;
	private String roomDetails;
	private int roomPrice;
	private int roomAvailability;
	private int accomNumber;
	private int roomUse;
	private RoomServiceDto roomServiceDto;
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
	public RoomServiceDto getRoomServiceDto() {
		return roomServiceDto;
	}
	public void setRoomServiceDto(RoomServiceDto roomServiceDto) {
		this.roomServiceDto = roomServiceDto;
	}
	public RoomRequest() {
		super();
	}
	public RoomRequest(int roomNumber, String roomCategory, String roomDetails, int roomPrice, int roomAvailability,
			int accomNumber, int roomUse, RoomServiceDto roomServiceDto) {
		super();
		this.roomNumber = roomNumber;
		this.roomCategory = roomCategory;
		this.roomDetails = roomDetails;
		this.roomPrice = roomPrice;
		this.roomAvailability = roomAvailability;
		this.accomNumber = accomNumber;
		this.roomUse = roomUse;
		this.roomServiceDto = roomServiceDto;
	}
	
}
