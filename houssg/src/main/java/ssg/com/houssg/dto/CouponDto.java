package ssg.com.houssg.dto;

import java.time.LocalDate;

public class CouponDto {
	
	private String couponNumber;
	private String couponName;
	private int discount;
	private LocalDate expirationDate;
	
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
				+ ", expirationDate=" + expirationDate + "]";
	}
}
