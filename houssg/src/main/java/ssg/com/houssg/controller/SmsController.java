package ssg.com.houssg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.SmsResponseDto;
import ssg.com.houssg.dto.UserDto;
import ssg.com.houssg.service.UserService;
import ssg.com.houssg.util.SmsUtil;
import ssg.com.houssg.util.VerificationCodeValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("message")
public class SmsController {

	@Autowired
	private UserService userService;

	@Autowired
	private SmsUtil smsUtil;

	@PostMapping("/sms")
	public ResponseEntity<SmsResponseDto> sendSms(@RequestBody RequestDto request, HttpSession session) {
		return smsUtil.sendSms(request, session);
	}

	@PostMapping("/sms-check") // 인증번호 비교 확인
	public ResponseEntity<String> checkVerificationCode(HttpSession session,
			@RequestParam("verificationCode") String Code) {
		boolean isValid = VerificationCodeValidator.isValidVerificationCode(session, Code);

		if (isValid) {
			return ResponseEntity.ok("인증되었습니다.");
		} else {
			return ResponseEntity.badRequest().body("유효하지 않는 인증번호입니다.");
		}
	}

	@PostMapping("/sms-check-findid") // 인증번호 비교 확인
	public ResponseEntity<Object> findId(HttpSession session,
			@RequestParam("verificationCode") String code, @RequestParam("phone_number") String phoneNumber) {
		boolean isValid = VerificationCodeValidator.isValidVerificationCode(session, code);

		if (!isValid) {
			return ResponseEntity.badRequest().body("유효하지 않는 인증번호입니다.");
		}

		// 인증번호가 유효한 경우 아이디를 찾기
		int count = userService.phoneNumberCheck(phoneNumber);

		if (count == 0) {
			// 해당 전화번호로 가입한 사용자가 없음
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 휴대폰번호로 가입한 아이디가 없습니다.");
		}

		UserDto foundId = userService.findIdByPhoneNumber(phoneNumber);

		if (foundId != null) {
			return ResponseEntity.ok(foundId);
		} else {
			// 해당 휴대폰번호로 가입한 아이디가 없습니다
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 휴대폰번호로 가입한 아이디가 없습니다.");
		}
	}


}