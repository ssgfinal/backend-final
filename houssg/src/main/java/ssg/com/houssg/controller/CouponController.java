package ssg.com.houssg.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.CouponDto;
import ssg.com.houssg.dto.UserCouponDto;
import ssg.com.houssg.service.CouponService;
import ssg.com.houssg.util.CouponUtil;

@RestController
@RequestMapping("coupon")
public class CouponController {

	@Autowired
	private CouponService service;

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

	// 쿠폰 가져오기
	@GetMapping("/getValidCoupons")
	public List<CouponDto> getValidCoupons() {
		return service.getCoupons();
	}

	// 유저 - 쿠폰번호로 쿠폰 정보 조회
	@GetMapping("/findByCouponNumber")
	public CouponDto findByCouponNumber(@RequestParam String couponNumber) {
		return service.findByCouponNumber(couponNumber);
	}

//	// 유저 - ID와 함께 쿠폰 다운로드(저장)
//	@PostMapping("/enrollUserCoupon")
//	public ResponseEntity<String> enrollUserCoupon(@RequestBody UserCouponDto dto) {
//	    // 쿠폰번호로 중복 체크
//	    int couponExists = service.userCouponNumberCheck(dto.getCouponNumber());
//
//	    // 이미 쿠폰번호가 등록된 경우 중복 등록을 막음
//	    if (couponExists > 0) {
//	        return ResponseEntity.badRequest().body("중복");
//	    } else {
//	        String result = service.enrollUserCoupon(id, dto);
//	        if ("success".equals(result)) {
//	            return ResponseEntity.ok("success");
//	        } else {
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
//	        }
//	    }
//	}

}
