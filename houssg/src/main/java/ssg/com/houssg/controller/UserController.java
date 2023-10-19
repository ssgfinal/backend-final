package ssg.com.houssg.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
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

	@Value("${jwt.secret}")
	private String secretKey;

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
	@PostMapping("/log-in")
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

			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("message", "로그인 성공");
			responseMap.put("nickname", dto.getNickname()); // 닉네임 추가
			responseMap.put("phone", dto.getPhonenumber()); // 휴대폰 번호 추가
			responseMap.put("point", dto.getPoint());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + token);
			headers.add("RefreshToken", refreshToken);
			tokenService.storeRefreshToken(refreshToken, user);

			System.out.println("로그인 성공" + new Date());

			return ResponseEntity.ok().headers(headers).body(responseMap); // 토큰 반환
		}

		return ResponseEntity.badRequest().build();
	}

	// 로그아웃
	@PostMapping("log-out")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
		String token = authorizationHeader.replace("Bearer ", "");

		// 리프레시 토큰을 Redis에서 삭제
		tokenService.removeRefreshToken(token);
		return ResponseEntity.ok("로그아웃되었습니다.");
	}

	// 아이디 중복확인
	@PostMapping("id-check")
	public String idCheck(String id) {
		System.out.println("UserController idCheck(String id) " + new Date());

		int count = service.idCheck(id);
		if (count == 0) {
			return "YES";
		}

		return "NO";
	}

	// 닉네임 중복확인
	@PostMapping("nickname-check")
	public String nicknameCheck(String nickname) {
		System.out.println("UserController nicknameCheck(String nickname) " + new Date());

		int count = service.nicknameCheck(nickname);
		if (count == 0) {
			return "YES";
		}

		return "NO";
	}

	// 회원가입
	@PostMapping("/sign-up")
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

	// 아이디 찾기 및 전화번호 변경
	@PostMapping("find-id")
	public ResponseEntity<SmsResponseDto> findId(@RequestParam("phone_number") String phone_number,
			HttpSession session) {

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

		return ResponseEntity.ok().body(smsResponse.getBody());
	}

	// 아이디 찾기 및 전화번호 변경
	@PostMapping("change-phone")
	public ResponseEntity<SmsResponseDto> findㅇ(@RequestParam("phone_number") String phone_number,
			HttpSession session) {

		// 휴대폰 번호 중복 검사
		int count = service.phoneNumberCheck(phone_number);

		if (count > 0) {
			// 이미 존재하는 번호인 경우
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

		return ResponseEntity.ok().body(smsResponse.getBody());
	}

	// 비밀번호 찾기
	@PostMapping("find-pw")
	public ResponseEntity<SmsResponseDto> findPw(@RequestParam("id") String id,
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

		return ResponseEntity.ok().body(smsResponse.getBody());
	}

	// 비밀번호 변경
	@PostMapping("update-pw")
	public ResponseEntity<String> updatePassword(@RequestParam("id") String id,
			@RequestParam("newPassword") String newPassword) {
		UserUtil userUtil = new UserUtil();

		if (userUtil.isNullOrEmpty(id) || userUtil.isNullOrEmpty(newPassword)) {
			System.out.println("ID, 패스워드 입력 오류");
			return ResponseEntity.badRequest().body("아이디, 비밀번호 란이 공백입니다.");
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

	// 마이페이지 비밀번호 변경
	@PostMapping("/mypage-pw")
	public ResponseEntity<String> updateMypagePassword(HttpServletRequest request,
			@RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {

		// HTTP 요청 헤더에서 토큰 추출
		String token = getTokenFromRequest(request);

		// 토큰에서 사용자 ID 추출
		String userId = getUserIdFromToken(token);

		UserUtil userUtil = new UserUtil();

		// 아이디를 사용하여 비밀번호 변경 대상 사용자를 검색
		UserDto foundUser = service.findUserById(userId);

		if (foundUser == null) {
			System.out.println("사용자 없음");
			return ResponseEntity.badRequest().body("해당 아이디를 가진 사용자를 찾을 수 없습니다.");
		}

		// 데이터베이스에서 현재 비밀번호 가져오기
		String PasswordFromDB = service.findPasswordById(userId);

		// 현재 비밀번호를 검증
		if (!userUtil.verifyCurrentPassword(password, PasswordFromDB)) {
			return ResponseEntity.badRequest().body("현재 비밀번호가 일치하지 않습니다");
		}

		// 추가적인 비밀번호 유효성 검사 수행 (예: 복잡성 요구사항)
		if (!userUtil.isValidPassword(newPassword)) {
			return ResponseEntity.badRequest().body("유효하지 않은 새 비밀번호");
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
	
	// 마이페이지 닉네임 변경
	@PostMapping("/mypage-nickname")
	public ResponseEntity<String> updateMypageNickname(HttpServletRequest request, @RequestParam("nickname") String nickname) {
	    // HTTP 요청 헤더에서 토큰 추출
	    String token = getTokenFromRequest(request);

	    // 토큰에서 사용자 ID 추출
	    String userId = getUserIdFromToken(token);

	    // 닉네임 변경 전에 새 닉네임 유효성 검사
	    UserUtil userUtil = new UserUtil();
	    if (!userUtil.isValidNickname(nickname)) {
	        return ResponseEntity.badRequest().body("유효하지 않은 닉네임");
	    }

	    // 닉네임 중복 체크
	    int count = service.nicknameCheck(nickname);
	    if (count > 0) {
	        return ResponseEntity.badRequest().body("중복 닉네임");
	    }

	    // 해당하는 아이디의 닉네임 변경
	    try {
	    	service.changeNickname(userId, nickname);
	        return ResponseEntity.ok("닉네임 변경 성공");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("닉네임 변경 실패");
	    }
	}
	
    @PostMapping("/kakaoLogin")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> requestBody,
    									@RequestParam(value="phonenumber", required=false) String phonenumber) {
        System.out.println("MemberController 카카오 로그인 " + new Date());

        String kakaoAccessToken = requestBody.get("access_token");

        String apiUrl = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + kakaoAccessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("카카오 응답 : " + responseCode);

            if (responseCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(conn.getInputStream());
                String memberId = jsonNode.get("id").asText();
                String memberName = jsonNode.get("properties").get("nickname").asText();

                // 이 부분에서 UserDto에 값을 설정합니다.
                UserDto userDto = new UserDto();
                userDto.setId(memberId); // 아이디 설정
                userDto.setNickname(memberName); // 닉네임 설정
                // 이후 UserDto를 이용하여 로그인 또는 회원가입 처리를 수행합니다.
                int check = service.idCheck(memberId);

                if(check != 0) {
                	UserDto login = service.kakaoLogin(memberId);
                	System.out.println(login);
                    if(login==null||login.equals("")) {
                    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
                    } else {
                    	String token = jwtTokenProvider.createAccessToken(userDto); // UserDto 객체를 createToken 메서드에 전달
             			System.out.println("생성된 토큰: " + token);
             			String refreshToken = jwtTokenProvider.createRefreshToken(userDto);
             			System.out.println("생성된 리프레시 토큰: " + refreshToken);
             			
             			Map<String, Object> responseMap = new HashMap<>();
            			responseMap.put("message", "로그인 성공");
            			responseMap.put("nickname", userDto.getNickname()); 
            			responseMap.put("point", userDto.getPoint());

            			HttpHeaders headers = new HttpHeaders();
            			headers.add("Authorization", "Bearer " + token);
            			headers.add("RefreshToken", refreshToken);
            			tokenService.storeRefreshToken(refreshToken, userDto);
            			System.out.println("로그인 성공" + new Date());

            			return ResponseEntity.ok().headers(headers).body(responseMap); // 토큰 반환
                    }
                } else {
                	HttpHeaders headers = new HttpHeaders();
                	headers.add("Authorization","Bearer +kakako+");
                	
                	
                	System.out.println("회원가입합니다");
                    userDto.setPassword("Abcd123@");
                    userDto.setPhonenumber(phonenumber);
                    UserUtil userUtil = new UserUtil();
                    if (!userUtil.isValidUser(userDto)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body("회원가입 실패, 전화번호를 입력해 주세요");
                    }
                    int count = service.signUp(userDto);
                    if (count > 0) {
                        return ResponseEntity.ok().body("회원가입 성공");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패2");
                    }
                }
            } else {
                System.out.println("카카오 응답 : " + responseCode);
                System.out.println("카카오 로그인 실패");
                
                // 실패할 경우 적절한 응답 반환
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카카오 로그인 실패");
            }
        } catch (IOException e) {
            e.printStackTrace();
            
            // 내부 서버 오류가 발생할 경우 500 상태 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("내부 서버 오류");
        }
    }


	// AccessToken 획득 및 파싱 Part
	private String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token != null && token.startsWith("Bearer ")) {
			return token.substring(7);
		}

		return null;
	}

	private String getUserIdFromToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
					.parseClaimsJws(token).getBody();
			return claims.get("id", String.class); // "id" 클레임 추출
		} catch (Exception e) {
			// 토큰 파싱 실패
			e.printStackTrace();
			return null;
		}
	}

}
