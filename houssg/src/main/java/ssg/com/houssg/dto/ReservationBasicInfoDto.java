package ssg.com.houssg.dto;

import java.util.List;

public class ReservationBasicInfoDto {
	
	private List<ReservationRoomDto> bookableRoomList;
    private List<UserCouponDto> couponList;
    private int userPoint;
	
	public List<ReservationRoomDto> getBookableRoomList() {
		return bookableRoomList;
	}
	public void setBookableRoomList(List<ReservationRoomDto> bookableRoomList) {
		this.bookableRoomList = bookableRoomList;
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
