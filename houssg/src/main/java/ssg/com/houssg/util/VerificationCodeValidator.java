package ssg.com.houssg.util;

import jakarta.servlet.http.HttpSession;

public class VerificationCodeValidator {
    
    public static boolean isValidVerificationCode(HttpSession session, String Code) {
        // 세션에서 저장된 인증번호를 가져옴.
        String storedCode = (String) session.getAttribute("verificationCode");
        System.out.println(storedCode);
        // 세션에 저장된 인증번호와 사용자가 입력한 인증번호를 비교
        if (storedCode != null && storedCode.equals(Code)) {
            // 인증번호가 일치
            return true;
        }
        // 인증번호 불일치
        return false;
    }
}
