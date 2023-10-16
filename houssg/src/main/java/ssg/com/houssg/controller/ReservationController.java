package ssg.com.houssg.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.ReservationInfoDto;
import ssg.com.houssg.dto.AccomListDto;
import ssg.com.houssg.dto.AccomReservationListDto;
import ssg.com.houssg.dto.BookableRoomForOwnerDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.ReservationRoomDto;
import ssg.com.houssg.dto.RoomDto;
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
			ReservationInfoDto basicInfo = reservationService.getReservationBasicInfo(roomNumber, userId);

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
				responseJson.set("bookableRoomList", bookableRoomArray);
			} else {
				responseJson.set("bookableRoomList", null);
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
				ArrayNode emptyArray = objectMapper.createArrayNode();
				responseJson.set("couponInfoList", emptyArray);
			}

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
	public ResponseEntity<?> enrollReservation(HttpServletRequest request, @RequestBody ReservationDto reservationDto) {
		try {

			// HTTP 요청 헤더에서 토큰 추출
			String token = getTokenFromRequest(request);
			String userId = getUserIdFromToken(token);

			// 예약 가능 여부 확인
			boolean isAvailable = reservationService.isReservationAvailable(reservationDto);

			if (isAvailable) {
				// 예약번호 생성
				System.out.println("예약 가능 여부 " + isAvailable);
				int reservationNumber = ReservationUtil.generateRandomReservationNumber();

				// 현재 날짜와 시각을 얻어옴
				LocalDateTime reservationTime = LocalDateTime.now();

				reservationDto.setReservationNumber(reservationNumber);
				reservationDto.setReservationTime(reservationTime);
				System.out.println("예약번호 " + reservationNumber + "  예약시간 " + reservationTime);
				reservationDto.setId(userId);

				// 예약 정보를 서비스로 전달하여 등록
				System.out.println("등록합니다.");
				reservationService.enrollReservation(reservationDto);

				// 쿠폰 사용여부 컬럼 변경
				System.out.println("쿠폰 변겅합니다");
				reservationService.usedCoupon(reservationDto.couponNumber);

				// 포인트 차감
				int usePoint = reservationDto.getUsePoint();
				reservationService.usedPoint(userId, usePoint);

				// 예약 등록 성공 시 클라이언트에게 성공 응답 반환
				return ResponseEntity.ok(reservationNumber);
			} else {
				// 예약 불가능한 경우 에러 응답 반환
				System.out.println("예약가능 방 갯수 초과로 예약 불가능");
				return ResponseEntity.status(400).body("예약 불가능");
			}
		} catch (Exception e) {
			// 예약 등록 중에 예외 발생 시 에러 응답 반환
			e.printStackTrace();
			return ResponseEntity.status(400).body("예약 실패");
		}
	}

	// 연도 + 월 기준 객실 별 예약 현황 조회
	@GetMapping("/available-room")
	public ResponseEntity<String> getAvailableRoom(int roomNumber, String yearMonth) {
		try {

			// 사용자 ID를 파라미터로 전달하여 예약 기본 정보 조회
			ReservationInfoDto info = reservationService.getAvailableRoom(roomNumber, yearMonth);

			// 필요한 필드만 선택하여 JSON 문자열로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode responseJson = objectMapper.createObjectNode();

			// 예약된 객실 정보
			if (info.getBookableRoomList() != null) {
				ArrayNode bookableRoomArray = objectMapper.createArrayNode();
				for (ReservationRoomDto bookableRoom : info.getBookableRoomList()) {
					ObjectNode bookableRoomJson = objectMapper.createObjectNode();
					bookableRoomJson.put("date", bookableRoom.getDate());
					bookableRoomJson.put("availableRooms", bookableRoom.getAvailableRooms());
					bookableRoomArray.add(bookableRoomJson);
				}
				responseJson.set("bookableRoomList", bookableRoomArray);
			} else {
				responseJson.set("bookableRoomList", null);
			}

			// JSON 문자열 반환
			System.out.println(responseJson.toString());
			return ResponseEntity.ok(responseJson.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).build();
		}
	}

	// 유저 - Id로 예약내역 정보 조회
	@GetMapping("/history")
	public List<ReservationDto> findRerservationById(HttpServletRequest request) {

		// HTTP 요청 헤더에서 토큰 추출
		String token = getTokenFromRequest(request);
		String userId = getUserIdFromToken(token);

		return reservationService.findRerservationById(userId);
	}

	// 사업자 - 네비바 - 예약확인 버튼
	@GetMapping("/owner/check")
	public ResponseEntity<Map<String, Object>> getOwnerReservation(HttpServletRequest request,
			@RequestParam String yearMonth) {
		try {
			String token = getTokenFromRequest(request);
			String ownerId = getUserIdFromToken(token);

			List<AccomListDto> accommodationList = reservationService.getAccommodationByOwnerId(ownerId);

			Map<String, Object> response = new HashMap<>();
			response.put("accommodationList", accommodationList);

			if (accommodationList != null && !accommodationList.isEmpty()) {
				int accomNumber = accommodationList.get(0).getAccomNumber();
				System.out.println(accomNumber);
				List<AccomReservationListDto> reservations = reservationService.getHistoryForOwner(accomNumber,
						yearMonth);
				System.out.println(reservations);
				response.put("reservations", reservations);
			} else {
				// 예약이 없는 경우에 빈배열 출력
				response.put("reservations", Collections.emptyList());
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).build();
		}
	}

	// 사업자 - 예약확인 - 캘린더 월 이동, 숙소종류변경, 예약내역보기버튼
	@GetMapping("/owner/check-other")
	public List<AccomReservationListDto> getOwnerReservationForOther(@RequestParam int accomNumber,
			@RequestParam String yearMonth) {
		List<AccomReservationListDto> reservations = reservationService.getHistoryForOwner(accomNumber, yearMonth);
		return reservations;
	}

	@GetMapping("/owner/available-room")
	public ResponseEntity<List<BookableRoomForOwnerDto>> getAvailableRooms(@RequestParam int accomNumber, @RequestParam String yearMonth) { 
		
		// accomNumber로 해당 숙소의 모든 객실 정보를 가져옴
		List<RoomDto> rooms = reservationService.getRoomInfoByAccommodationNumber(accomNumber);

		// 사용 가능한 객실 정보를 담을 목록을 생성
		List<BookableRoomForOwnerDto> roomAvailabilityList = new ArrayList<>();

		
		for (RoomDto room : rooms) {
			
			// 객실 번호와 객실 타입을 가져옴
			int roomNumber = room.getRoomNumber();
			String roomCategory = room.getRoomCategory();

			// 날짜와 예약 가능 갯수를 가져와서 AvailabilityInfoDto 목록을 만듦
			List<ReservationRoomDto> availabilityInfo = reservationService.getReservationStatusForYearMonth(roomNumber, yearMonth);

			// RoomAvailabilityDto 객체를 만들어 결과 목록에 추가
			BookableRoomForOwnerDto roomAvailability = new BookableRoomForOwnerDto();
			roomAvailability.setRoomNumber(roomNumber);
			roomAvailability.setRoomCategory(roomCategory);
			roomAvailability.setAvailabilityInfo(availabilityInfo);

			roomAvailabilityList.add(roomAvailability);
		}

		// 사용 가능한 객실 정보 목록을 반환
		return ResponseEntity.ok(roomAvailabilityList);
	}
	
	// 사업자 - 예약 취소, 사용한 포인트, 쿠폰 반환, 취소리워드 지급
    @PostMapping("/owner-cancel")
    public ResponseEntity<String> cancelAndProcessRewards(@RequestParam("reservationNumber") int reservationNumber) {
        // 1. 예약 정보 조회: reservationNumber를 사용하여 필요한 데이터 가져오기
        ReservationDto reservation = reservationService.getReservationDetails(reservationNumber);
        String id = reservation.getId();
        int usePoint = reservation.getUsePoint();
        String couponNumber = reservation.getCouponNumber();
        int paymentAmount = reservation.getPaymentAmount();
        
        // 2. 사업자 - 예약 취소
        reservationService.cancelReservationByOwner(reservationNumber);
        
        // 3. 사업자 - 예약 취소 - 포인트 반환
        reservationService.returnUsePoint(id, usePoint);

        // 4. 사업자 - 예약 취소 - 쿠폰 반환
        reservationService.returnUseCoupon(couponNumber);

        // 5. 사업자 - 예약 취소 - 취소 리워드 계산
        reservationService.pointRewardsForCancel(reservationNumber, paymentAmount);

        return ResponseEntity.ok("Reservation canceled and rewards processed.");
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
