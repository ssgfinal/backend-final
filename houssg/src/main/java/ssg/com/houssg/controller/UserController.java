package ssg.com.houssg.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.SmsResponseDto;
import ssg.com.houssg.dto.UserDto;
import ssg.com.houssg.security.JwtTokenProvider;
import ssg.com.houssg.service.SmsService;
import ssg.com.houssg.service.TokenService;
import ssg.com.houssg.service.UserService;
import ssg.com.houssg.util.SmsUtil;
import ssg.com.houssg.util.UserUtil;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
    private SmsUtil smsUtil;


	// 로그인
	@PostMapping("login")
	public ResponseEntity<?> login(UserDto user) {
		System.out.println("UserController login(UserDto user) " + new Date());
		System.out.println("클라이언트로 부터 받은 데이터 : " + user.toString());

		user.setPassword(userUtil.hashPassword(user.getPassword())); // 사용자가 입력한 비밀번호를 DB에 있는 hashedPW로 변경한 후에 로그인 진행
		UserDto dto = service.login(user);

		System.out.println(dto);
		if (dto != null) {
			System.out.println("dto != null");
			// 로그인 성공 시 JWT 토큰 생성 및 반환
			String token = jwtTokenProvider.createAccessToken(dto); // UserDto 객체를 createToken 메서드에 전달
			System.out.println("생성된 토큰: " + token);
			String refreshToken = jwtTokenProvider.createRefreshToken(dto);
			System.out.println("생성된 리프레시 토큰: " + refreshToken);

			return ResponseEntity.ok(token); // 토큰 반환
		}

		return ResponseEntity.notFound().build();
	}

	// 로그아웃
	@PostMapping("logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.replace("Bearer ", "");

		if (tokenService.isTokenBlacklisted(token)) {
			// 이미 블랙리스트에 있는 토큰인 경우 처리
			return ResponseEntity.badRequest().body("이미 로그아웃된 토큰입니다.");
		}

		// 토큰을 블랙리스트에 추가
		tokenService.blacklistToken(token);

		return ResponseEntity.ok("로그아웃되었습니다.");
	}

	// 아이디 중복확인
	@PostMapping("idcheck")
	public String idCheck(String id) {
		System.out.println("UserController idCheck(String id) " + new Date());

		int count = service.idCheck(id);
		if (count == 0) {
			return "YES";
		}

		return "NO";
	}
	
	// 닉네임 중복확인
	@PostMapping("nicknamecheck")
	public String nicknameCheck(String nickname) {
		System.out.println("UserController nicknameCheck(String nickname) " + new Date());

		int count = service.nicknameCheck(nickname);
		if (count == 0) {
			return "YES";
		}

		return "NO";
	}
	
	// 회원가입
	@PostMapping("signup")
	public String signUp(UserDto user) {
	    System.out.println("UserController signUp(UserDto dto) " + new Date());

	    System.out.println("클라이언트로 부터 받은 데이터 : " + user.toString());

	    // UserDto 객체를 UserUtil을 사용하여 검사
	    UserUtil userUtil = new UserUtil();
	    if (!userUtil.isValidUser(user)) {
	        System.out.println("회원가입 실패");
	        return "NO";
	    }
	    int count = service.signUp(user);
	    if (count > 0) {
	        return "YES";
	    }
	    System.out.println("회원가입 실패2");

	    return "NO";
	}

	// 아이디 찾기
	@PostMapping("findid")
	public ResponseEntity<UserDto> findId(@RequestParam("phone_number") String phone_number, HttpSession session) {
	    
	    // 휴대폰 번호 중복 검사
	    int count = service.phoneNumberCheck(phone_number);
	    
	    if (count == 0) {
	        // 해당 전화번호로 가입한 사용자가 없음(코드 400)
	        return ResponseEntity.badRequest().build();
	    }
	    
	    // SMS 전송
	    // RequestDto에 phone_number 설정
	    RequestDto requestDto = new RequestDto();
	    requestDto.setRecipientPhoneNumber(phone_number);
	    ResponseEntity<SmsResponseDto> smsResponse = smsUtil.sendSms(requestDto, session);

	    if (smsResponse.getStatusCode() != HttpStatus.OK) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	    
	    return ResponseEntity.ok().build();
	}

}

	


