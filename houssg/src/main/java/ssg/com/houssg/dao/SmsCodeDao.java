package ssg.com.houssg.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.SmsCodeDto;

@Mapper
@Repository
public interface SmsCodeDao {
	
	 // 인증번호 저장
	 void saveCode(SmsCodeDto smsCodeDto);
	 
	 // 세션 아이디로 저장된 정보 불러오기
	 SmsCodeDto getCodeByphoneNumber(String phoneNumber);
	
	 // 1시간 주기마다 만료기간이 지난 코드 삭제
	 void deleteExpiredVerificationCodes();
	 
	 // 인증완료된 인증번호 삭제
	 void deleteSuccessVerificationCodes(String phoneNumber);
	 
	 // 인증번호 업데이트
	 void updateCode(SmsCodeDto smsCodeDto);
}
