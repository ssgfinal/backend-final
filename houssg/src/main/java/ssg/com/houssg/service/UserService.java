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

	// ID + 폰번호 중복체크
	public int idPhoneNumberCheck(String id, String phone_number) {
		return dao.idPhoneNumberCheck(id, phone_number);
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

	// 휴대폰번호 중복체크
	public int phoneNumberCheck(String phone_number) {
		return dao.phoneNumberCheck(phone_number);
	}

	// 비밀번호 찾기
	public UserDto findUserByIdPhonNumber(String id, String phone_number) {
		return dao.findUserByIdPhonNumber(id, phone_number);
	}

	// 비밀번호 업데이트
	public int updatePassword(UserDto user) {
		return dao.updatePassword(user);
	}

	public UserDto findByUsername(String username) {
		return null;
	}
	
	// 마이페이지 비밀번호 찾기
    public String findPasswordById(String id) {
        return dao.findPasswordById(id);
    }
    
    // 전화번호 변경
 	public void changePhone(String id, String phone_number) {
 		 dao.changePhone(id, phone_number);
 	}
 	
 	// 닉네임 변경
 	public void changeNickname(String id, String nick_name) {
 		dao.changeNickname(id, nick_name);
 	}

 	// 카카오 로그인
 	public UserDto kakaoLogin(String id) {
 		return dao.kakaoLogin(id);
 	}
}