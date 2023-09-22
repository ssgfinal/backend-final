package ssg.com.houssg.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.CouponDto;

@Mapper
@Repository
public interface CouponDao {

	// 쿠폰 발행
	int couponIssued(CouponDto dto);
	
	// 쿠폰번호 중복확인
	int couponNumberCheck(String couponNumber);
}
