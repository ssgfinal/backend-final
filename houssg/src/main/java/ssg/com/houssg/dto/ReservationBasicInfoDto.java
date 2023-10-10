package ssg.com.houssg.dto;

import java.util.List;

public class ReservationBasicInfoDto {
	
	private List<AccommodationDto> accommodationInfoList;
    private List<RoomDto> roomInfoList;
    private List<UserCouponDto> couponInfoList;
    private int userPoints;
    private String userId;
    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<AccommodationDto> getAccommodationInfoList() {
		return accommodationInfoList;
	}
	public void setAccommodationInfoList(List<AccommodationDto> accommodationInfoList) {
		this.accommodationInfoList = accommodationInfoList;
	}
	public List<RoomDto> getRoomInfoList() {
		return roomInfoList;
	}
	public void setRoomInfoList(List<RoomDto> roomInfoList) {
		this.roomInfoList = roomInfoList;
	}
	public List<UserCouponDto> getCouponInfoList() {
		return couponInfoList;
	}
	public void setCouponInfoList(List<UserCouponDto> couponInfoList) {
		this.couponInfoList = couponInfoList;
	}
	public int getUserPoints() {
		return userPoints;
	}
	public void setUserPoints(int userPoints) {
		this.userPoints = userPoints;
	}
    
    
}
