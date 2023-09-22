package ssg.com.houssg.controller;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.CouponDto;
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

	// 쿠폰번호 중복체크
	@PostMapping("couponNumberCheck")
	public String couponNumberCheck(String couponNumber) {
		System.out.println("CouponController couponNumberCheck(int couponNumber)" + new Date());

		int count = service.couponNumberCheck(couponNumber);
		if (count == 0) {
			return "success";
		}
		return "failed";

	}

	
}
