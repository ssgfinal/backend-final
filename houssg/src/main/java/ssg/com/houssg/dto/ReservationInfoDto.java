package ssg.com.houssg.dto;

import java.util.List;

public class ReservationInfoDto {
	
	private List<ReservationRoomDto> bookableRoomList;
    private List<UserCouponDto> couponList;
	
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
    
    
}
