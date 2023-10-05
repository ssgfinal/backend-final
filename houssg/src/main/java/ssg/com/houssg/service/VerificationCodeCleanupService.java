package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ssg.com.houssg.dao.SmsCodeDao;

@Service
public class VerificationCodeCleanupService {
    
    @Autowired
    private SmsCodeDao smsCodeDao;
    

    // 3600000 = 1시간
    @Scheduled(fixedRate = 1800000) // 30분마다 실행 (시간 단위는 밀리초)
    public void cleanupExpiredVerificationCodes() {
        // 만료된 인증 코드를 삭제하는 로직 구현
        smsCodeDao.deleteExpiredVerificationCodes();
    	System.out.println("인증번호 테이블 클리어");

    }
    
    public void deleteSuccessVerificationCodes(String sessionId) {
    	smsCodeDao.deleteSuccessVerificationCodes(sessionId);
    }
}
