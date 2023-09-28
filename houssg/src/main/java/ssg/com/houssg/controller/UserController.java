package ssg.com.houssg.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.SmsResponseDto;
import ssg.com.houssg.dto.UserDto;
import ssg.com.houssg.security.JwtTokenProvider;
import ssg.com.houssg.service.TokenSaveService;
import ssg.com.houssg.service.UserService;
import ssg.com.houssg.util.SmsUtil;
import ssg.com.houssg.util.UserUtil;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private TokenSaveService tokenService;

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private SmsUtil smsUtil;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto user) {
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
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + token);
	        headers.add("Refresh-Token" , refreshToken);
	        tokenService.storeRefreshToken(refreshToken, user);
	        
	        System.out.println("로그인 성공" + new Date());
	        
			return ResponseEntity.ok().headers(headers)
		            .body("로그인 성공"); // 토큰 반환
		}		

		return ResponseEntity.notFound().build();
	}

	// 로그아웃
	@PostMapping("logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.replace("Bearer ", "");

		// 리프레시 토큰을 Redis에서 삭제
	    tokenService.removeRefreshToken(token);
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
	@PostMapping("/signup")
	public String signUp(@RequestBody UserDto user) {
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

	// 비밀번호 찾기
	@PostMapping("findpw")
	public ResponseEntity<UserDto> findPw(@RequestParam("id") String id,
			@RequestParam("phone_number") String phone_number, HttpSession session) {

		// 휴대폰 번호 중복 검사
		int count = service.idPhoneNumberCheck(id, phone_number);

		if (count == 0) {
			// 해당 전화번호로 가입한 사용자가 없음(코드 400)
			System.out.println("해당 정보를 가진 사용자가 없습니다.");
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

	// 비밀번호 변경
	@PostMapping("updatePassword")
	public ResponseEntity<String> updatePassword(@RequestParam("id") String id, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
	    UserUtil userUtil = new UserUtil();

	    if (userUtil.isNullOrEmpty(id) || userUtil.isNullOrEmpty(newPassword) || userUtil.isNullOrEmpty(confirmPassword)) {
	        System.out.println("ID, 패스워드 입력 오류");
	        return ResponseEntity.badRequest().body("아이디, 비밀번호, 비밀번호 확인 란이 공백입니다.");
	    }

	    if (!newPassword.equals(confirmPassword)) {
	        System.out.println("비밀번호, 비밀번호 확인 값 불일치");
	        return ResponseEntity.badRequest().body("새로운 비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
	    }

	    // 비밀번호 유효성 검사
	    if (!userUtil.isValidPassword(newPassword)) {
	        System.out.println("비밀번호 유효성 검사 실패");
	        return ResponseEntity.badRequest().body("비밀번호가 유효하지 않습니다.");
	    }

	    // 아이디를 사용하여 비밀번호 변경 대상 사용자를 검색
	    UserDto foundUser = service.findUserById(id);

	    if (foundUser == null) {
	        System.out.println("사용자 없음");
	        return ResponseEntity.badRequest().body("해당 아이디를 가진 사용자를 찾을 수 없습니다.");
	    }

	    // 비밀번호 해싱
	    String hashedPassword = userUtil.hashPassword(newPassword);

	    // UserDto에 해싱된 새로운 비밀번호 설정
	    foundUser.setPassword(hashedPassword);

	    // 비밀번호 업데이트 메서드 호출
	    int rowsAffected = service.updatePassword(foundUser);

	    if (rowsAffected > 0) {
	        return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
	    } else {
	        return ResponseEntity.badRequest().body("비밀번호 재설정에 실패했습니다.");
	    }
	}

}
