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
	public int idcheck(String id) {
		return dao.idcheck(id);
	}
	
	// 회원가입
	public int adduser(UserDto dto) {
	
		return dao.adduser(dto);
	}	
	
	// 닉네임 중복체크
	public int nicknamecheck(String nickname) {
		return dao.nicknamecheck(nickname);
	}
	
	// 로그인
	public UserDto login(UserDto dto) {
		
		return dao.login(dto);
	}
	
	public UserDto findUserById(String id) {
        return dao.findUserById(id);
    }
	
	
//	 // 사용자 정보 가져오기
//    public UserDetails loadUserByUser(String userId) {
//        UserDto user = findUserById(userId);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with id: " + userId);
//        }
//
//        return UserPrincipal.create(user);
//    }
}
	
//	// 아이디 찾기
//	UserDto findUserByNicknameAndPhoneNumber(String nickname, String user_phone_number);
//	 
//	// 비밀번호 찾기
//	UserDto findUserByNicknameAndUserId(String nickname, String id);
//
//	// 비밀번호 업데이트
//	void updatePassword(UserDto dto);




