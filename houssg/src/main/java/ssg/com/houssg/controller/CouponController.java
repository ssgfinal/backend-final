package ssg.com.houssg.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.CouponDto;
import ssg.com.houssg.dto.UserCouponDto;
import ssg.com.houssg.service.CouponService;
import ssg.com.houssg.util.CouponUtil;

@RestController
@RequestMapping("coupon")
public class CouponController {

	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	private CouponService service;

	// 관리자 쿠폰 발행
	@PostMapping("issued")
	public String couponIssued(@RequestParam String couponName, @RequestParam int discount,
			@RequestParam String expirationDate) {
		System.out.println("CouponController couponIssued " + new Date());

		// 16자리 랜덤 쿠폰 번호 생성
		String couponNumber = CouponUtil.generateRandomCouponNumber();

		// 클라이언트로부터 전달받은 유효 기간 문자열을 LocalDate로 파싱
		LocalDate parsedExpirationDate = LocalDate.parse(expirationDate);

		// DTO에 값을 설정
		CouponDto dto = new CouponDto();
		dto.setCouponNumber(couponNumber);
		dto.setCouponName(couponName);
		dto.setDiscount(discount);
		dto.setExpirationDate(parsedExpirationDate);

		// 서비스를 호출하여 쿠폰 발행 로직 실행
		int result = service.couponIssued(dto);

		if (result > 0) {
			return "success";
		} else {
			return "failed";
		}
	}

	// 쿠폰 가져오기(다운가능한 쿠폰 목록 출력)
	@GetMapping("/get-valid-coupons")
	public List<CouponDto> getValidCoupons() {
		return service.getCoupons();
	}

	// 유저 - 쿠폰번호로 쿠폰 정보 조회
	@GetMapping("/find-couponinfo")
	public CouponDto findCouponByNumber(@RequestParam String couponNumber, HttpServletRequest request) {
		return service.findCouponByNumber(couponNumber);
	}

	// 유저 - ID와 함께 쿠폰 다운로드(저장)
	@PostMapping("/enroll-usercoupon")
	public ResponseEntity<String> enrollUserCoupon(@RequestBody UserCouponDto dto, HttpServletRequest request) {
		String token = getTokenFromRequest(request);

		if (token != null) {
			String userId = getUserIdFromToken(token);
			if (userId != null) {
				// 사용자가 입력한 쿠폰번호로 쿠폰 정보를 조회
				CouponDto couponInfo = service.findCouponByNumber(dto.getCouponNumber());

				if (couponInfo != null) {
					// 중복 체크
					int couponExists = service.userCouponNumberCheck(dto.getCouponNumber());

					// 이미 쿠폰번호가 등록된 경우 중복 등록을 막음
					if (couponExists > 0) {
						return ResponseEntity.badRequest().body("중복");
					} else {
						// 사용자 쿠폰 정보와 사용자 ID를 함께 저장
						int result = service.enrollUserCoupon(userId, couponInfo, dto);
						if (result > 0) {
							return ResponseEntity.ok("success");
						} else {
							return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
						}
					}
				} else {
					return ResponseEntity.badRequest().body("쿠폰이 존재하지 않습니다."); // 해당 쿠폰이 없을 경우 처리
				}
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
	}

	// 유저 - 나의 쿠폰 조회
	@GetMapping("/mypage")
	public ResponseEntity<List<CouponDto>> myCoupon(HttpServletRequest request) {
		String token = getTokenFromRequest(request);
		String userId = getUserIdFromToken(token);

		List<CouponDto> coupons = service.myCoupon(userId);

		if (coupons != null && !coupons.isEmpty()) {
			return new ResponseEntity<>(coupons, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<CouponDto>(), HttpStatus.OK);
		}
	}

	// AccessToken 획득 및 파싱 Part
	private String getTokenFromRequest(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		if (accessToken != null && accessToken.startsWith("Bearer ")) {
			return accessToken.substring(7); // "Bearer " 부분을 제외한 엑세스 토큰 부분 추출
		}

		String refreshToken = request.getHeader("RefreshToken");
		if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
			return refreshToken.substring(7);
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
