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
import ssg.com.houssg.dao.SmsCodeDao;
import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.SmsResponseDto;
import ssg.com.houssg.dto.UserDto;
import ssg.com.houssg.service.UserService;
import ssg.com.houssg.service.AutoProcessService;
import ssg.com.houssg.util.SmsUtil;
import ssg.com.houssg.util.VerificationCodeValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("sms")
public class SmsController {

	@Autowired
	private UserService userService;

	@Autowired
	private SmsUtil smsUtil;
	
	@Autowired
	private SmsCodeDao smsCodeDao;
	
	@Autowired
    private AutoProcessService verificationCodeCleanupService;
	

	@PostMapping("/sign-up")
	public ResponseEntity<SmsResponseDto> sendSms(@RequestBody RequestDto request, HttpSession session) {
		String phoneNumber = request.getRecipientPhoneNumber();
		
		// 전화번호 중복 확인
		int count = userService.phoneNumberCheck(phoneNumber);
		
        if (count > 0) {
            // 중복된 전화번호인 경우 오류 응답 반환
            return ResponseEntity.badRequest().build();
        }

        return smsUtil.sendSms(request, session);
    }

	

	@PostMapping("/check") // 인증번호 비교 확인
	public ResponseEntity<String> checkVerificationCode(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("verificationCode") String Code) {
		System.out.println(Code);
		
		// VerificationCodeValidator 클래스의 인스턴스 생성
	    VerificationCodeValidator validator = new VerificationCodeValidator(smsCodeDao);

	    // 생성한 인스턴스를 사용하여 isValidVerificationCode 메서드 호출
	    boolean isValid = validator.isValidVerificationCode(phoneNumber ,Code);
		System.out.println("입력받은 값 : "  + Code);
		if (isValid) {
			System.out.println(isValid);
			
			// 인증이 완료된 후에 삭제
			verificationCodeCleanupService.deleteSuccessVerificationCodes(phoneNumber);

			return ResponseEntity.ok("인증되었습니다.");
		} else {
			System.out.println(isValid);
			return ResponseEntity.badRequest().body("유효하지 않는 인증번호입니다.");
		}
	}

	@PostMapping("/check-findid") // 인증번호 비교 확인
	public ResponseEntity<Object> findId(@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("verificationCode") String Code) {
		// VerificationCodeValidator 클래스의 인스턴스 생성
	    VerificationCodeValidator validator = new VerificationCodeValidator(smsCodeDao); 

	    // 생성한 인스턴스를 사용하여 isValidVerificationCode 메서드 호출
	    boolean isValid = validator.isValidVerificationCode(phoneNumber, Code);

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
			// 인증이 완료된 후에 삭제
			verificationCodeCleanupService.deleteSuccessVerificationCodes(phoneNumber);
			return ResponseEntity.ok(foundId);
		} else {
			// 해당 휴대폰번호로 가입한 아이디가 없습니다
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 휴대폰번호로 가입한 아이디가 없습니다.");
		}
	}


}