package ssg.com.houssg.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.ReservationBasicInfoDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.service.ReservationService;
import ssg.com.houssg.util.ReservationUtil;

@RestController
@RequestMapping("reservation")
public class ReservationController {

	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private RestTemplate restTemplate;

	// 예약 페이지 기본 정보 조회
	@GetMapping("/basic-info")
	public ResponseEntity<String> getReservationBasicInfo(HttpServletRequest request,
			@RequestParam(name = "roomNumber") int roomNumber) {
		try {
			// HTTP 요청 헤더에서 토큰 추출
			String token = getTokenFromRequest(request);

			// 토큰에서 사용자 ID 추출
			String userId = getUserIdFromToken(token);

			// roomNumber를 사용하여 accomNumber를 조회
			int accomNumber = reservationService.getAccomNumberByRoomNumber(roomNumber);

			// 사용자 ID를 파라미터로 전달하여 예약 기본 정보 조회
			ReservationBasicInfoDto basicInfo = reservationService.getReservationBasicInfo(accomNumber, roomNumber,
					userId);

			// 필요한 필드만 선택하여 JSON 문자열로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode responseJson = objectMapper.createObjectNode();

			// Accommodation 정보 선택
			ObjectNode accommodationJson = objectMapper.createObjectNode();
			accommodationJson.put("accomNumber", basicInfo.getAccommodationInfoList().get(0).getAccomNumber());
			accommodationJson.put("accomName", basicInfo.getAccommodationInfoList().get(0).getAccomName());
			responseJson.set("accommodationInfoList", accommodationJson);

			// Room 정보 선택
			ObjectNode roomJson = objectMapper.createObjectNode();
			roomJson.put("roomNumber", basicInfo.getRoomInfoList().get(0).getRoomNumber());
			roomJson.put("roomCategory", basicInfo.getRoomInfoList().get(0).getRoomCategory());
			roomJson.put("roomPrice", basicInfo.getRoomInfoList().get(0).getRoomPrice());
			responseJson.set("roomInfoList", roomJson);

			// Coupon 정보 선택
			ObjectNode couponJson = objectMapper.createObjectNode();
			couponJson.put("couponNumber", basicInfo.getCouponInfoList().get(0).getCouponNumber());
			couponJson.put("couponName", basicInfo.getCouponInfoList().get(0).getCouponName());
			couponJson.put("discount", basicInfo.getCouponInfoList().get(0).getDiscount());
			responseJson.set("couponInfoList", couponJson);

			// UserPoints와 userId
			responseJson.put("userPoints", basicInfo.getUserPoints());
			responseJson.put("userId", basicInfo.getUserId());

			// return ResponseEntity.ok(basicInfo);

			// JSON 문자열 반환
			System.out.println(responseJson.toString());
			return ResponseEntity.ok(responseJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).build();
		}
	}

	// 예약 등록 API
	@PostMapping("/enroll")
	public ResponseEntity<String> enrollReservation(@RequestBody ReservationDto reservationDto) {
		try {
			// 예약번호 생성
			int reservationNumber = ReservationUtil.generateRandomReservationNumber();

			// 현재 날짜와 시각을 얻어옴
			LocalDateTime reservationTime = LocalDateTime.now();

			reservationDto.setReservationNumber(reservationNumber);
			reservationDto.setReservationTime(reservationTime);
			System.out.println(reservationTime);

			// /basic-info 엔드포인트로 GET 요청을 보냄
			ResponseEntity<String> basicInfoResponse = restTemplate
					.getForEntity("/basic-info?roomNumber=" + reservationDto.getRoomNumber(), String.class);
			
			// basicInfoResponse에서 필요한 정보를 추출하고 사용
			String basicInfoJson = basicInfoResponse.getBody();
			
			// JSON 문자열을 ObjectMapper를 사용하여 JSON 객체로 파싱
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(basicInfoJson);

			// JSON에서 원하는 필드 추출
			String accomNumber = jsonNode.get("accommodationInfoList").get("accomNumber").asText();
			String accomName = jsonNode.get("accommodationInfoList").get("accomName").asText();
			String roomNumber = jsonNode.get("roomInfoList").get("roomNumber").asText();
			String roomCategory = jsonNode.get("roomInfoList").get("roomCategory").asText();
			int roomPrice = jsonNode.get("roomInfoList").get("roomPrice").asInt();
			String couponNumber = jsonNode.get("couponInfoList").get("couponNumber").asText();
			String couponName = jsonNode.get("couponInfoList").get("couponName").asText();
			int discount = jsonNode.get("couponInfoList").get("discount").asInt();
			
			reservationDto.setAccomNumber(accomNumber);
			reservationDto.setAccomName(accomName);
			reservationDto.setRoomNumber(roomNumber);
			reservationDto.setRoomCategory(roomCategory);
			reservationDto.setRoomPrice(roomPrice);
			reservationDto.setCouponNumber(couponNumber);
			reservationDto.setCouponName(couponName);
			reservationDto.setDiscount(discount);


			// 예약 정보를 서비스로 전달하여 등록
			reservationService.enrollReservation(reservationDto);

			// 예약 등록 성공 시 클라이언트에게 성공 응답 반환
			return ResponseEntity.ok("예약성공");
		} catch (Exception e) {
			// 예약 등록 중에 예외 발생 시 에러 응답 반환
			e.printStackTrace();
			return ResponseEntity.status(400).body("예약실패");
		}
	}

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
