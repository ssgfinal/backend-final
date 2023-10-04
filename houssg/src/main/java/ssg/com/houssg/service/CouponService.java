package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.ibatis.annotations.Param;

import ssg.com.houssg.dao.CouponDao;
import ssg.com.houssg.dto.CouponDto;
import ssg.com.houssg.dto.UserCouponDto;

@Service
public class CouponService {

	@Autowired
	CouponDao dao;

	// 쿠폰 발행
	public int couponIssued(CouponDto dto) {
		return dao.couponIssued(dto);
	}

	public List<CouponDto> getCoupons() {
		// 유효기간이 지난 쿠폰을 처리 (expirationStatus를 1로 변경)
		dao.updateExpirationStatus();
		// 유효기간이 남은 쿠폰만 조회
		return dao.getValidCoupons();
	}

	// 유저 - 쿠폰번호로 쿠폰 정보 조회
	public CouponDto findCouponByNumber(String couponNumber) {
		return dao.findCouponByNumber(couponNumber);
	}
	
	// 유저 - ID와 함께 쿠폰 다운로드(저장)
	public int enrollUserCoupon(String userId, CouponDto couponInfo, UserCouponDto dto) {
	    // 사용자 쿠폰 정보와 사용자 ID를 함께 저장
	    dto.setId(userId);
	    dto.setCouponNumber(couponInfo.getCouponNumber());
	    dto.setCouponName(couponInfo.getCouponName());
	    dto.setDiscount(couponInfo.getDiscount());
	    dto.setExpirationDate(couponInfo.getExpirationDate());

	    // user_coupon 테이블에 저장
	    return dao.enrollUserCoupon(userId, dto);
	}

    
	public int userCouponNumberCheck(String couponNumber) {	
		return dao.userCouponNumberCheck(couponNumber);
	}

}
