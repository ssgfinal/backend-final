package ssg.com.houssg.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.ReservationBasicInfoDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.ReservationRoomDto;
import ssg.com.houssg.dto.UserCouponDto;
import ssg.com.houssg.service.ReservationService;
import ssg.com.houssg.util.ReservationUtil;

@RestController
@RequestMapping("reservation")
public class ReservationController {

	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	private ReservationService reservationService;

	// 예약 페이지 기본 정보 조회
	@GetMapping("/basic-info")
	public ResponseEntity<String> getReservationBasicInfo(int roomNumber, HttpServletRequest request) {
		try {
			// HTTP 요청 헤더에서 토큰 추출
			String token = getTokenFromRequest(request);

			// 토큰에서 사용자 ID 추출
			String userId = getUserIdFromToken(token);

			// 사용자 ID를 파라미터로 전달하여 예약 기본 정보 조회
			ReservationBasicInfoDto basicInfo = reservationService.getReservationBasicInfo(roomNumber, userId);

			// 필요한 필드만 선택하여 JSON 문자열로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode responseJson = objectMapper.createObjectNode();

			// 예약된 객실 정보
			if (basicInfo.getBookableRoomList() != null) {
				ArrayNode bookableRoomArray = objectMapper.createArrayNode();
				for (ReservationRoomDto bookableRoom : basicInfo.getBookableRoomList()) {
					ObjectNode bookableRoomJson = objectMapper.createObjectNode();
					bookableRoomJson.put("date", bookableRoom.getDate());
					bookableRoomJson.put("availableRooms", bookableRoom.getAvailableRooms());
					bookableRoomArray.add(bookableRoomJson);
				}
				responseJson.set("bookalbeRoomList", bookableRoomArray);
			} else {
				responseJson.set("bookalbeRoomList", null);
			}

			// Coupon 정보
			if (basicInfo.getCouponList() != null && !basicInfo.getCouponList().isEmpty()) {
				ArrayNode couponArray = objectMapper.createArrayNode();
				for (UserCouponDto couponInfo : basicInfo.getCouponList()) {
					ObjectNode couponJson = objectMapper.createObjectNode();
					couponJson.put("couponNumber", couponInfo.getCouponNumber());
					couponJson.put("couponName", couponInfo.getCouponName());
					couponJson.put("discount", couponInfo.getDiscount());
					couponArray.add(couponJson);
				}
				responseJson.set("couponInfoList", couponArray);
			} else {
				responseJson.set("couponInfoList", null);
			}

			// 유저 포인트 정보
			responseJson.put("userPoint", basicInfo.getUserPoint());

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
	public ResponseEntity<String> enrollReservation(HttpServletRequest request,
			@RequestBody ReservationDto reservationDto) {
		try {

			// HTTP 요청 헤더에서 토큰 추출
			String token = getTokenFromRequest(request);
			String userId = getUserIdFromToken(token);

			// 예약번호 생성
			int reservationNumber = ReservationUtil.generateRandomReservationNumber();

			// 현재 날짜와 시각을 얻어옴
			LocalDateTime reservationTime = LocalDateTime.now();

			reservationDto.setReservationNumber(reservationNumber);
			reservationDto.setReservationTime(reservationTime);
			System.out.println(reservationTime);


			reservationDto.setId(userId);

			// 예약 정보를 서비스로 전달하여 등록
			reservationService.enrollReservation(reservationDto);

			// 쿠폰 사용여부 컬럼 변경
			// reservationService.usedCoupon(couponNumber);

			// 포인트 차감
			int usePoint = reservationDto.getUsePoint();
			reservationService.usedPoint(userId, usePoint);

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
