package ssg.com.houssg.dto;

import java.util.Arrays;

public class RoomRequest {

	private int roomNumber;
	private String roomCategory;
	private int roomPrice;
	private int roomAvailability;
	private int accomNumber;
	private int roomServiceDto[];
	private String[] resistImage;
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
	public int[] getRoomServiceDto() {
		return roomServiceDto;
	}
	public void setRoomServiceDto(int[] roomServiceDto) {
		this.roomServiceDto = roomServiceDto;
	}
	public String[] getResistImage() {
		return resistImage;
	}
	public void setResistImage(String[] resistImage) {
		this.resistImage = resistImage;
	}
	public RoomRequest() {
		super();
	}
	public RoomRequest(int roomNumber, String roomCategory, int roomPrice, int roomAvailability, int accomNumber,
			int[] roomServiceDto, String[] resistImage) {
		super();
		this.roomNumber = roomNumber;
		this.roomCategory = roomCategory;
		this.roomPrice = roomPrice;
		this.roomAvailability = roomAvailability;
		this.accomNumber = accomNumber;
		this.roomServiceDto = roomServiceDto;
		this.resistImage = resistImage;
	}
	@Override
	public String toString() {
		return "RoomRequest [roomNumber=" + roomNumber + ", roomCategory=" + roomCategory + ", roomPrice=" + roomPrice
				+ ", roomAvailability=" + roomAvailability + ", accomNumber=" + accomNumber + ", roomServiceDto="
				+ Arrays.toString(roomServiceDto) + ", resistImage=" + Arrays.toString(resistImage) + "]";
	}
}
