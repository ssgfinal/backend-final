package ssg.com.houssg.dto;

import java.util.Date;

public class SmsCodeDto {

	private String phoneNumber;
	private String verificationCode;
	private Date expirationTime;
	private int requestCount;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public int getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public String toString() {
		return "SmsCodeDto [phoneNumber=" + phoneNumber + ", verificationCode=" + verificationCode + ", expirationTime="
				+ expirationTime + ", requestCount=" + requestCount + "]";
	}

}
