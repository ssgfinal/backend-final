package ssg.com.houssg.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssg.com.houssg.dao.CouponDao;
import ssg.com.houssg.dto.CouponDto;

@Service
public class CouponService {
	
	@Autowired
	CouponDao dao;
	
	// 쿠폰 발행
	public int couponIssued(CouponDto dto) {
	        return dao.couponIssued(dto);
	}
	
	
	// 쿠폰번호 중복 체크
	public int couponNumberCheck(String couponNumber) {
		return dao.couponNumberCheck(couponNumber);
	}
}
