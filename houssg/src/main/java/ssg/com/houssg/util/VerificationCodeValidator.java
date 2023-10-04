package ssg.com.houssg.util;

import jakarta.servlet.http.HttpSession;
import ssg.com.houssg.dao.SmsCodeDao;
import ssg.com.houssg.dto.SmsCodeDto;

public class VerificationCodeValidator {
    
	  private SmsCodeDao smsCodeDao;
	
	  public VerificationCodeValidator(SmsCodeDao smsCodeDao) {
	        this.smsCodeDao = smsCodeDao;
	    }

	
    public  boolean isValidVerificationCode(String Code, HttpSession session) {
    	System.out.println(session.getId());
    	session.getAttribute(session.getId());
    	System.out.println("세션에 저장된 코드" + session.getAttribute(session.getId()));
    	System.out.println("사용자가 입력한 코드 "+ Code);
    	
    	
    	String sessionCode = (String) session.getAttribute(session.getId());
    	
    	if (sessionCode != null && sessionCode.equals(Code)) {
            // 세션에 저장된 코드와 사용자가 입력한 코드가 일치하면 true를 반환합니다.
            return true;
        } else {
            // 일치하지 않으면 false를 반환합니다.
            return false;
        }
    }
}