package ssg.com.houssg.dto;

import java.util.Arrays;

public class RoomDto {
	
	private int roomNumber;
	private String roomCategory;
	private int roomPrice;
	private int roomAvailability;
	private int accomNumber;
	private int[] service;
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
	public int[] getService() {
		return service;
	}
	public void setService(int[] service) {
		this.service = service;
	}
	public RoomDto() {
		super();
	}
	public RoomDto(int roomNumber, String roomCategory, int roomPrice, int roomAvailability,
			int accomNumber, int[] service) {
		super();
		this.roomNumber = roomNumber;
		this.roomCategory = roomCategory;
		this.roomPrice = roomPrice;
		this.roomAvailability = roomAvailability;
		this.accomNumber = accomNumber;
		this.service = service;
	}
	@Override
	public String toString() {
		return "RoomDto [roomNumber=" + roomNumber + ", roomCategory=" + roomCategory + ", roomPrice=" +
				roomPrice + ", roomAvailability=" + roomAvailability + ", accomNumber=" + accomNumber
				+ ", service=" + Arrays.toString(service) + "]";
	}	
}
