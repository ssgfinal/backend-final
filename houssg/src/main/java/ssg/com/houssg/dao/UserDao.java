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
	
	UserDto login(UserDto dto);
	
	// 유저 조회
	UserDto findUserById(String id);
	
	// 아이디 찾기
	UserDto findIdByPhoneNumber(String phone_number);
	
	// 휴대폰번호 중복
	int phoneNumberCheck(String phone_number);
	
	// 패스워드 업데이트
		
	// 비밀번호 찾기
	UserDto findUserByNicknameAndUserId(String nickname, String id);

}
