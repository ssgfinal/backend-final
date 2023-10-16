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
		smsCodeDao.deleteExpiredVerificationCodes();
		System.out.println("인증번호 테이블 클리어");

	}

	public void deleteSuccessVerificationCodes(String sessionId) {
		smsCodeDao.deleteSuccessVerificationCodes(sessionId);
	}

	@Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시에 실행
	public void updateReservationStatus() {
		reservationDao.usedCheck();
		System.out.println("이용완료 체크");
	}

	@Scheduled(cron = "0 0 10 * * *") // 매일 오전 10시에 실행
	public void updatePatmentReward() {
		UserDto userDto = new UserDto();
		reservationDao.paymentRewards(userDto);
		System.out.println("포인트 적립 완료");
	}
	

	// 예약시간으로부터 20분이 지난 예약 내역
	@Scheduled(fixedRate = 600000)
	public void deleteUnpaidReservation() {
		reservationDao.deleteUnpaidReservation();
		System.out.println("결제안한 애들 캇투");
	}
	
}
