package ssg.com.houssg.dto;

import java.time.LocalDate;

public class CouponDto {
	
	private String couponNumber;
	private String couponName;
	private int discount;
	private LocalDate expirationDate;
	private int expirationStatus;
	
	public int getExpirationStatus() {
		return expirationStatus;
	}

	public void setExpirationStatus(int expirationStatus) {
		this.expirationStatus = expirationStatus;
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
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "CouponDto [couponNumber=" + couponNumber + ", couponName=" + couponName + ", discount=" + discount
				+ ", expirationDate=" + expirationDate + ", expirationStatus=" + expirationStatus + "]";
	}
	  
	
}
