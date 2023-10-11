package ssg.com.houssg.dto;

import java.util.List;

public class ReservationBasicInfoDto {
	
	private List<ReservationRoomDto> ReservedRoomList;
    private List<UserCouponDto> couponList;
    private int userPoint;

    
    
    
	public List<ReservationRoomDto> getReservedRoomList() {
		return ReservedRoomList;
	}
	public void setReservedRoomList(List<ReservationRoomDto> reservedRoomList) {
		ReservedRoomList = reservedRoomList;
	}
	
	public List<UserCouponDto> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<UserCouponDto> couponList) {
		this.couponList = couponList;
	}
	public int getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(int userPoint) {
		this.userPoint = userPoint;
	}
    
    
}
