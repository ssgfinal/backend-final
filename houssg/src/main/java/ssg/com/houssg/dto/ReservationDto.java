package ssg.com.houssg.dto;

import java.time.LocalDateTime;


public class ReservationDto{
	
	// 주석에 'O' 적힌건 예약내역에서 보여줘야할 항목
	
	private int reservationNumber; // 예약번호 O
	private LocalDateTime reservationTime; // 예약시간 O
	private String id; // 유저 id
	
	private String startDate; // 숙박시작날짜 O
	private String endDate; // 숙박시작날짜 O
	
	private int status; // 예약상태 0:예약완료 / 1: 유저취소 / 2: 업주취소 /3:이용완료 O

	private String nickname; // 예약자 닉네임 
	private String phoneNumber; //예약자 전화번호  
	
	private String gusetName; // 이용자 명 O
	private String gusetPhone; // 이용자 전화번호 O
	
	private String accomNumber; // 숙소번호
	private String accomName; // 숙소이름 O
	
	private String roomNumber; // 객실번호
	private String roomCategory; // 객실종류 O
	private int roomPrice; // 1박당 객실가격 O
	
	public String couponNumber; // 쿠폰 번호 O
	private String couponName; // 쿠폰 이름 O
	private int discount; // 쿠폰할인가 O
	
	private int usePoint; // 사용한 포인트 O
	
	private int totalPrice; // 총 금액 O
	private int paymentAmount; // 실제 결제금액 O
	
	public int getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public LocalDateTime getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(LocalDateTime reservationTime) {
		this.reservationTime = reservationTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGusetName() {
		return gusetName;
	}
	public void setGusetName(String gusetName) {
		this.gusetName = gusetName;
	}
	public String getGusetPhone() {
		return gusetPhone;
	}
	public void setGusetPhone(String gusetPhone) {
		this.gusetPhone = gusetPhone;
	}
	public String getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(String accomNumber) {
		this.accomNumber = accomNumber;
	}
	public String getAccomName() {
		return accomName;
	}
	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
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
	public String getCouponNumber() {
		return couponNumber;
	}
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getUsePoint() {
		return usePoint;
	}
	public void setUsePoint(int usePoint) {
		this.usePoint = usePoint;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	@Override
	public String toString() {
		return "ReservationDto [reservationNumber=" + reservationNumber + ", reservationTime=" + reservationTime
				+ ", id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status
				+ ", nickname=" + nickname + ", phoneNumber=" + phoneNumber + ", gusetName=" + gusetName
				+ ", gusetPhone=" + gusetPhone + ", accomNumber=" + accomNumber + ", accomName=" + accomName
				+ ", roomNumber=" + roomNumber + ", roomCategory=" + roomCategory + ", roomPrice=" + roomPrice
				+ ", couponNumber=" + couponNumber + ", couponName=" + couponName + ", discount=" + discount
				+ ", usePoint=" + usePoint + ", totalPrice=" + totalPrice + ", paymentAmount=" + paymentAmount + "]";
	}
	
	
	 
}
 