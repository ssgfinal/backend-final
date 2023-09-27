package ssg.com.houssg.dto;

import java.time.LocalDate;

public class UserCouponDto {
	
	private String id;
	private String couponNumber;
	private String couponName;
	private int discount;
	private LocalDate expirationDate;
	private int isUsed;
	
	public String getCouponNumber() {
		return couponNumber;
	}
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}
	
	@Override
	public String toString() {
		return "UserCouponDto [id=" + id + ", couponNumber=" + couponNumber + ", couponName=" + couponName
				+ ", discount=" + discount + ", expirationDate=" + expirationDate + ", isUsed=" + isUsed + "]";
	}
	

}
