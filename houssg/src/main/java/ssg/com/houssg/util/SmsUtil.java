package ssg.com.houssg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpSession;
import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.SmsResponseDto;
import ssg.com.houssg.service.BookingNotificationService;
import ssg.com.houssg.service.SmsService;

@Component
@Service
public class SmsUtil {

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private BookingNotificationService bookingNotificationService;
	
	  @Autowired
	    public SmsUtil(SmsService smsService, BookingNotificationService bookingNotificationService) {
	        this.smsService = smsService;
	        this.bookingNotificationService = bookingNotificationService;
	    }

	public ResponseEntity<SmsResponseDto> sendSms(RequestDto request, HttpSession session) {
		try {

			SmsResponseDto data = smsService.sendSms(request.getRecipientPhoneNumber(), request.getContent(), session);
			return ResponseEntity.ok().body(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 예외 발생 시 500 에러 반환
		}
	}
	
	public ResponseEntity<SmsResponseDto> sendLMS(RequestDto request) {
		try {

			SmsResponseDto data = bookingNotificationService.sendLms(request.getRecipientPhoneNumber(), request.getContent());
			return ResponseEntity.ok().body(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 예외 발생 시 500 에러 반환
		}
	}
}
