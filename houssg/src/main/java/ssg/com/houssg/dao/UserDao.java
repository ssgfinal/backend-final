package ssg.com.houssg.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.UserDto;

@Mapper
@Repository
public interface UserDao {
	
	int idcheck(String id);
	int nicknamecheck(String nick_name);
	int adduser(UserDto dto);
	
	UserDto login(UserDto dto);
	
	// 유저 조회
	UserDto findUserById(String id);
	
	// 아이디 찾기
	
	UserDto findUserByNicknameAndPhoneNumber(String nickname,String user_phone_number);
	
	// 패스워드 업데이트

		
	// 비밀번호 찾기
	UserDto findUserByNicknameAndUserId(String nickname, String id);

}
