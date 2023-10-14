package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ssg.com.houssg.dao.ReservationDao;
import ssg.com.houssg.dao.SmsCodeDao;
import ssg.com.houssg.dto.UserDto;

@Service
public class AutoProcessService {
    
    @Autowired
    private SmsCodeDao smsCodeDao;
    
    @Autowired
    private ReservationDao reservationDao;
    
   
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
    
    // @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    @Scheduled(fixedRate = 120000)
    public void updateReservationStatusAndReward() {
    	
    	reservationDao.usedCheck();
    	UserDto userDto =  new UserDto();
    	reservationDao.accumulatePoints(userDto);
        System.out.println("이용완료 및 포인트 적립 완료");
    }
}
