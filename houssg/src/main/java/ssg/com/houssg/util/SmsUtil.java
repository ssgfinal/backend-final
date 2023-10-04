package ssg.com.houssg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpSession;
import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.SmsResponseDto;
import ssg.com.houssg.service.SmsService;

@Component
public class SmsUtil {

	@Autowired
	private SmsService smsService;

	public ResponseEntity<SmsResponseDto> sendSms(RequestDto request, HttpSession session) {
		try {

			SmsResponseDto data = smsService.sendSms(request.getRecipientPhoneNumber(), request.getContent(), session);
			return ResponseEntity.ok().body(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 예외 발생 시 500 에러 반환
		}
	}
}
