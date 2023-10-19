package ssg.com.houssg.dto;

public class ReservationForLmsDto {
	private int reservationNumber;
	private String guestPhone;
    private String guestName;
    private String startDate;
    private String endDate;
    private String accomName;
    private String roomCategory;
    private String checkIn;
	private String checkOut;
    private int paymentAmount;
    
    
   
	public int getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public String getGuestPhone() {
		return guestPhone;
	}
	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}
	public String getAccomName() {
		return accomName;
	}
	public void setAccomName(String accomName) {
		this.accomName = accomName;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
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
	
	public String getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	@Override
	public String toString() {
		return "ReservationForLmsDto [reservationNumber=" + reservationNumber + ", guestPhone=" + guestPhone
				+ ", guestName=" + guestName + ", startDate=" + startDate + ", endDate=" + endDate + ", accomName="
				+ accomName + ", roomCategory=" + roomCategory + ", checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", paymentAmount=" + paymentAmount + "]";
	}
	
	
	
    
}
