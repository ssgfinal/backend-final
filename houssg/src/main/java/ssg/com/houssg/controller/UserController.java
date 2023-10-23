package ssg.com.houssg.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.cloudinary.Url;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.RequestDispatcher;
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
	
	@Value("${kakao.client_id}")
	private String clientId;
	
	@Value("${kakao.client_secret}")
	private String clientSecret;

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
			String userId = dto.getId();
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("message", "로그인 성공");
			responseMap.put("nickname", dto.getNickname()); // 닉네임 추가
			responseMap.put("phone", dto.getPhonenumber()); // 휴대폰 번호 추가
			responseMap.put("point", dto.getPoint());

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + token);
			headers.add("RefreshToken", "Bearer " +refreshToken);
			tokenService.storeRefreshToken(userId, refreshToken);

			System.out.println("로그인 성공" + new Date());

			return ResponseEntity.ok().headers(headers).body(responseMap); // 토큰 반환
		}

		return ResponseEntity.badRequest().build();
	}

	// 로그아웃
	@PostMapping("log-out")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		// HTTP 요청 헤더에서 토큰 추출
		String token = getTokenFromRequest(request);

		// 토큰에서 사용자 ID 추출
		String userId = getUserIdFromToken(token);

		// 리프레시 토큰을 Redis에서 삭제
		tokenService.removeRefreshToken(userId);
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
	public ResponseEntity<String> updateMypageNickname(HttpServletRequest request,
			@RequestParam("nickname") String nickname) {
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


	
	@PostMapping("/kakao/log-in")
	public ResponseEntity<?> kakao(@RequestParam("code") String authorizationCode, HttpServletRequest http) {
	    String apiUrl = "https://kauth.kakao.com/oauth/token";
	    System.out.println("카카오톡 로그인");
	    try {
	    	ResponseEntity<String> responseEntity ;
		    HttpHeaders headers = new HttpHeaders();

	    	try {
	        RestTemplate restTemplate = new RestTemplate();
	        
	        // 요청 헤더 설정
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        String baseUrl = http.getRequestURL().toString();
	        
	        if (baseUrl.startsWith("http://localhost")) {
	        	baseUrl = "http://localhost:8080";
	        } else {
	        	baseUrl = "http://52.79.147.124";
	        }
	        // base URL 출력 또는 사용
	        System.out.println("Base URL: " + baseUrl);
	        // 요청 파라미터 설정
	        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
	        parameters.add("grant_type", "authorization_code");
	        parameters.add("client_id", clientId);
	        parameters.add("client_secret", clientSecret);
	        parameters.add("redirect_uri", "http://localhost:8080"); 
	        parameters.add("code", authorizationCode); // 실제 Authorization Code 사용
	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);
	        // 토큰 요청을 보냅니다.
	          responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
	          
	    	} catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복 코드가 감지되었습니다.");
	    	}
	    	
	        // 성공적인 경우, 액세스 토큰을 클라이언트에게 반환합니다.
	        if (responseEntity.getStatusCode().is2xxSuccessful()) {
	            String responseBody = responseEntity.getBody();
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(responseBody);
	            if (jsonNode.has("access_token")) {
	                String accessToken = jsonNode.get("access_token").asText();
	                System.out.println("'access_token' : '" + accessToken+"'");
	                String kakaoAccessToken = accessToken;

	                apiUrl = "https://kapi.kakao.com/v2/user/me";

	                    URL url = new URL(apiUrl);
	                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	                    conn.setRequestMethod("GET");
	                    conn.setRequestProperty("Authorization", "Bearer " + kakaoAccessToken);

	                    int responseCode = conn.getResponseCode();
	                    System.out.println("카카오 응답 : " + responseCode);

	                    if (responseCode == 200) {
	                        ObjectMapper mapper = new ObjectMapper();
	                        jsonNode = mapper.readTree(conn.getInputStream());
	                        String memberId = jsonNode.get("id").asText();
	                        

	                        // 이 부분에서 UserDto에 값을 설정합니다.
	                        UserDto userDto = new UserDto();
	                        userDto.setId(memberId);
	                        
	                        String token = jwtTokenProvider.createAccessToken(userDto); // UserDto 객체를 createToken 메서드에 전달
	             			System.out.println("생성된 토큰: " + token);
	             			String refreshToken = jwtTokenProvider.createRefreshToken(userDto);
	             			System.out.println("생성된 리프레시 토큰: " + refreshToken);
	             			
	             			headers = new HttpHeaders();
	            			headers.add("Authorization", "Bearer " + token);
	            			headers.add("RefreshToken", "Bearer " + refreshToken);
	            			tokenService.storeRefreshToken(memberId, refreshToken);
	                        
	                        int check = service.idCheck(memberId);

	                        if(check != 0) {
	                        	
	                        	
	                        	UserDto login = service.kakaoLogin(memberId);
	                			
	                            if(login==null||login.equals("")) {
	                            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
	                            } else {     			
	                     			Map<String, Object> responseMap = new HashMap<>();
	                    			responseMap.put("message", "로그인 성공");
	                    			responseMap.put("phonenumber", login.getPhonenumber());
	                    			responseMap.put("nickname", login.getNickname()); 
	                    			responseMap.put("point", login.getPoint());
	                    			
	                    			System.out.println("로그인 성공" + new Date());
	                    			return ResponseEntity.ok().headers(headers).body(responseMap); // 토큰 반환
	                            }
	                        } else {
	                        	userDto.setNickname(null);
	                        	return ResponseEntity.ok().headers(headers).body("nickname : "+userDto.getNickname());
	                        }
	                    } else {
	                        System.out.println("카카오 응답 : " + responseCode);
	                        System.out.println("카카오 로그인 실패");
	                        
	                        // 실패할 경우 적절한 응답 반환
	                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카카오 로그인 실패");
	                    }
	            } else {
	            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("액세스 토큰이 없습니다.");
	            }
	        } else {
	            // 실패한 응답 처리
	            return ResponseEntity.status(responseEntity.getStatusCode()).body("Failed");
	        }
	    } catch (Exception e) {
	        // 예외 처리
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
	    }
	}

    @PostMapping("/kakao/sign-up")
    public ResponseEntity<?> kakaoSignUp(HttpServletRequest httpRequest, @RequestParam String phonenumber, @RequestParam String nickname) {
        // 클라이언트가 회원가입 요청을 보냈을 때, 새로운 사용자로 간주
    	String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
    	
        UserDto userDto = new UserDto();
        userDto.setId(userId); // 아이디 설정

        int check = service.idCheck(userId);

        if (check == 0) {
            System.out.println("회원가입합니다");
            userDto.setPassword("Abcd123@");
            userDto.setPhonenumber(phonenumber);
            userDto.setNickname(nickname);
            UserUtil userUtil = new UserUtil();
            
            if (!userUtil.isValidUser(userDto)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패, 전화번호를 입력해 주세요");
            }
            
            int count = service.signUp(userDto);
            
            if (count > 0) {

                // 클라이언트에게 토큰을 반환하고, 회원가입 성공 메시지를 함께 반환
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "회원가입 및 로그인 성공");
                responseMap.put("phonenumber", userDto.getPhonenumber());
                responseMap.put("nickname", userDto.getNickname());
                responseMap.put("point", userDto.getPoint());

                return ResponseEntity.ok().body(responseMap);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패2");
            }
        }

        // 이미 존재하는 사용자인 경우에 대한 처리
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 사용자입니다.");
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
