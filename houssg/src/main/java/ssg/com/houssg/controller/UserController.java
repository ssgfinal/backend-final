package ssg.com.houssg.controller;

import java.security.MessageDigest;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.UserDto;
import ssg.com.houssg.security.JwtTokenProvider;
import ssg.com.houssg.service.TokenService;
import ssg.com.houssg.service.UserService;
import ssg.com.houssg.util.InputValidator;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private TokenService tokenService;

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody UserDto user) {
		System.out.println("UserController login(UserDto user) " + new Date());
		System.out.println("Received data from client: " + user.toString());

		user.setPassword(hashPassword(user.getPassword())); // 사용자가 입력한 비밀번호를 DB에 있는 hashedPW로 변경한 후에 로그인 진행
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

	@PostMapping("idcheck")
	public String idcheck(String id) {
		System.out.println("UserController idcheck(String id) " + new Date());

		int count = service.idcheck(id);
		if (count == 0) {
			return "YES";
		}

		return "NO";
	}

	@PostMapping("nicknamecheck")
	public String nicknamecheck(String nickname) {
		System.out.println("UserController nicknamecheck(String nickname) " + new Date());

		int count = service.nicknamecheck(nickname);
		if (count == 0) {
			return "YES";
		}

		return "NO";
	}

	@PostMapping("adduser")
	public String addmember(UserDto user) {
		System.out.println("UserController adduser(UserDto dto) " + new Date());

		System.out.println("Received data from client: " + user.toString());
		// Not_Null 처리 된 컬럼 필수 입력 여부 확인
		if (user.getId() == null || user.getId().trim().isEmpty() || user.getPassword() == null
				|| user.getPassword().trim().isEmpty() || user.getNickname() == null
				|| user.getNickname().trim().isEmpty()) {
			System.out.println("회원가입 실패1");
			return "NO";
		}

		// 아이디 유효성 검사
		if (!InputValidator.isValidUserId(user.getId())) {
			System.out.println("회원가입 실패: 아이디가 유효하지 않습니다.");
			return "NO";
		}

		// 비밀번호 유효성 검사
		if (!InputValidator.isValidPassword(user.getPassword())) {
			System.out.println("회원가입 실패: 비밀번호가 유효하지 않습니다.");
			return "NO";
		}

		// 닉네임 유효성 검사
		if (!InputValidator.isValidNickname(user.getNickname())) {
			System.out.println("회원가입 실패: 닉네임이 유효하지 않습니다.");
			return "NO";
		}

		// 전화번호 유효성 검사
		if (!InputValidator.isValidPhoneNumber(user.getPhone_number())) {
			System.out.println("회원가입 실패: 전화번호가 유효하지 않습니다.");
			return "NO";
		}

		// 비밀번호 해시화
		String hashedPassword = hashPassword(user.getPassword());
		System.out.println("원본 비밀번호: " + user.getPassword());
		System.out.println("암호화된 비밀번호: " + hashedPassword);
		user.setPassword(hashedPassword);

		int count = service.adduser(user);
		if (count > 0) {
			return "YES";
		}
		System.out.println("회원가입 실패2");

		return "NO";
	}

	// 비밀번호 해쉬화 메서드
	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			return "";
		}
	}

}
