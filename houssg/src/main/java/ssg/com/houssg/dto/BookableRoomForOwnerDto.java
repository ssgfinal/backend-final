package ssg.com.houssg.dto;

import java.util.List;

public class BookableRoomForOwnerDto {
	 private int roomNumber;
	 private String roomCategory;
	 private List<ReservationRoomDto> availabilityInfo;
	 
	 
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
	public List<ReservationRoomDto> getAvailabilityInfo() {
		return availabilityInfo;
	}
	public void setAvailabilityInfo(List<ReservationRoomDto> availabilityInfo) {
		this.availabilityInfo = availabilityInfo;
	}
	 
	 
}
