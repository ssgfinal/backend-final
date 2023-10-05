package ssg.com.houssg.util;

import ssg.com.houssg.dao.SmsCodeDao;
import ssg.com.houssg.dto.SmsCodeDto;

public class VerificationCodeValidator {
    
	  private SmsCodeDao smsCodeDao;
	
	  public VerificationCodeValidator(SmsCodeDao smsCodeDao) {
	        this.smsCodeDao = smsCodeDao;
	    }

    public  boolean isValidVerificationCode(String phoneNumber, String Code) {

    	// DB에서 해당 세션 아이디에 저장된 SMS 코드 정보를 가져옴
        SmsCodeDto smsCodeDto = smsCodeDao.getCodeByphoneNumber(phoneNumber);
        
        if (smsCodeDto == null) {
            // 해당 세션 아이디에 대한 정보가 DB에 없음
            return false;
        }

        // DB에 저장된 세션 아이디와 인증번호를 클라이언트에서 받은 값과 비교
        if (phoneNumber.equals(smsCodeDto.getPhoneNumber()) && Code.equals(smsCodeDto.getVerificationCode())) {
            // 세션 아이디와 인증번호가 일치

            // 유효 시간 체크
            long currentTime = System.currentTimeMillis();
            long expirationTime = smsCodeDto.getExpirationTime().getTime();

            if (currentTime <= expirationTime) {
                // 유효 시간 내에 검증됨
                return true;
            }
        }
        // 인증번호가 일치하지 않거나 유효 시간이 지났음
        return false;
    }
}