package ssg.com.houssg.dto;

import java.util.Date;

public class SmsCodeDto {
	
	private String sessionId;
	private String verificationCode;
	private Date expirationTime;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getVerificationCode() {
		return verificationCode;
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
		return "SmsCodeDto [sessionId=" + sessionId + ", verificationCode=" + verificationCode + ", expirationTime="
				+ expirationTime + "]";
	}
	
}
