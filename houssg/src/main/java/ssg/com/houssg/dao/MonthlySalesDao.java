package ssg.com.houssg.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.MonthlySalesDto;

@Mapper
@Repository
public interface MonthlySalesDao {
	
	// 매월 1일 정산내역 기록 가능한 인스턴스 생성
	void makeMonthlySales(MonthlySalesDto monthlySalesDto);
	
	// 이용완료 시 매출액 업데이트
	void updateMonthlySales();
	
	// 숙소별 매출액 조회
	List<MonthlySalesDto> checkMonthlySales(String id);
	
	List<String> havingAccom(String id);
}
