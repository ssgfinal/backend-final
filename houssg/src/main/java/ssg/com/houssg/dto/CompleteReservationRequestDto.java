package ssg.com.houssg.dto;

public class CompleteReservationRequestDto {
	
	private int reservationNumber;
    private String sign;

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
