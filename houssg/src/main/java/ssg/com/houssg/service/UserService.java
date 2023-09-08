package ssg.com.houssg.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dto.UserDto;
import ssg.com.houssg.dao.UserDao;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserDao dao;
	
	// 아이디 중복체크
	public int idCheck(String id) {
		return dao.idCheck(id);
	}
	
	// 회원가입
	public int signUp(UserDto dto) {
		return dao.signUp(dto);
	}	
	
	// 닉네임 중복체크
	public int nicknameCheck(String nickname) {
		return dao.nicknameCheck(nickname);
	}
	
	// 로그인
	public UserDto login(UserDto dto) {
		return dao.login(dto);
	}
	
	public UserDto findUserById(String id) {
        return dao.findUserById(id);
    }
	
	// 아이디 찾기
	public UserDto findIdByPhoneNumber(String phone_number) {
		return dao.findIdByPhoneNumber(phone_number);
	}
	
	// 닉네임 중복체크
	public int phoneNumberCheck(String phone_number) {
		return dao.phoneNumberCheck(phone_number);
	}
	
//	// 비밀번호 찾기
//	UserDto findUserByNicknameAndUserId(String nickname, String id);
//
//	// 비밀번호 업데이트
//	void updatePassword(UserDto dto);
	
}
	






