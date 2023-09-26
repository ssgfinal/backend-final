package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public CouponDto findByCouponNumber(String couponNumber) {
		return dao.findBycouponNumber(couponNumber);
	}
	
	// 유저 - ID와 함께 쿠폰 다운로드(저장)
    public String enrollUserCoupon(String Id, UserCouponDto dto) {
        // 쿠폰번호로 중복 체크
        int couponExists = dao.userCouponNumberCheck(dto.getCouponNumber());

        // 이미 쿠폰번호가 등록된 경우 중복 등록을 막음
        if (couponExists > 0) {
            return "중복";
        } else {
            int result = dao.enrollUserCoupon(Id, dto);
            if (result > 0) {
                return "success";
            } else {
                return "failed";
            }
        }
    }

	public int userCouponNumberCheck(String couponNumber) {	
		return dao.userCouponNumberCheck(couponNumber);
	}

}
