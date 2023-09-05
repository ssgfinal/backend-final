package ssg.com.houssg.util;

import java.util.regex.Pattern;

public class InputValidator {
	// 아이디 검사
	public static boolean isValidUserId(String userId) {
		// 아이디 길이가 5자리 이상 10자리 이하인지 확인
		if (userId.length() < 5 || userId.length() > 10) {
			return false;
		}

		// 아이디에 특수문자로 언더스코어(_)만 허용되는지 확인
		String regex = "^[a-zA-Z0-9_]+$";
		return Pattern.matches(regex, userId);
	}

	// 비밀번호 검사
	public static boolean isValidPassword(String password) {
		// 비밀번호 길이가 8자리에서 20자리인지 확인
		if (password.length() < 8 || password.length() > 20) {
			return false;
		}

		// 비밀번호에 영문소문자, 숫자, 특수기호(!,@,#,_) 조합이 있는지 확인
		String regex = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#_])[A-Za-z\\d!@#_]+$";
		return Pattern.matches(regex, password);
	}

	// 닉네임 검사
	public static boolean isValidNickname(String nickname) {
		// 닉네임 길이가 2자에서 8자 사이이며, 닉네임에 특수문자 및 한글 자음과 모음만으로 구성된 경우를 제한
		String regex = "^[가-힣a-zA-Z0-9]{2,8}$";
		return Pattern.matches(regex, nickname);
	}

	// 전화번호 검사
	public static boolean isValidPhoneNumber(String phoneNumber) {
		// 전화번호는 하이픈 없이 숫자로만 입력된 것으로 가정
		String regex = "^[0-9]+$";
		return Pattern.matches(regex, phoneNumber);
	}
}
