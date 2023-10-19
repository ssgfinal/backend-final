package ssg.com.houssg.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.UserDto;

@Mapper
@Repository
public interface UserDao {
	
	int idCheck(String id);
	int nicknameCheck(String nick_name);
	int signUp(UserDto dto);
	int idPhoneNumberCheck(String id, String phone_number);
	UserDto login(UserDto dto);
	
	// 유저 조회
	UserDto findUserById(String id);
	 
	// 아이디 찾기
	UserDto findIdByPhoneNumber(String phone_number);
	
	// 휴대폰번호 중복
	int phoneNumberCheck(String phone_number);
	
	// 패스워드 업데이트
	int updatePassword(UserDto user);
	
	// 비밀번호 찾기
	UserDto findUserByIdPhonNumber(String id, String phone_number);
	
	// 마이페이지 비밀번호 찾기
	String findPasswordById(String id);
	
	// 마이페이지 전화번호 변경
	void changePhone(String id, String phone_number);
	
	// 마이페이지 닉네임 변경
	void changeNickname(String id, String nick_name);
	
	// 카카오 로그인
	UserDto kakaoLogin(String id);
	
	
}
