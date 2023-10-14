package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

import ssg.com.houssg.dto.CouponDto;
import ssg.com.houssg.dto.UserCouponDto;

@Mapper
@Repository
public interface CouponDao {

	// 쿠폰 발행
	int couponIssued(CouponDto dto);
		
	// 유효기간이 지난 쿠폰을 expirationStatus를 1로 업데이트
    void updateExpirationStatus();
    
    // 유효기간이 남은 쿠폰만을 조회
    List<CouponDto> getValidCoupons();

    // 유저 - 쿠폰번호로 쿠폰 정보 조회
    CouponDto findCouponByNumber(@Param("couponNumber") String couponNumber);
	
	// 유저 - ID와 함꼐 쿠폰 다운로드(저장)
	int enrollUserCoupon(String id, UserCouponDto dto);
	
	// 유저 - 쿠폰번호 중복 조회
	int userCouponNumberCheck(String couponNumber);
	
	// 유저 - 나의 쿠폰 조회
	List<CouponDto> myCoupon(String id);
}
