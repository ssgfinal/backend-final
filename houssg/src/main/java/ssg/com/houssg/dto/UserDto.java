package ssg.com.houssg.dto;

import java.util.List;


public class UserDto {

	private String id;
	private String password;
	private String nickname;
	private String phone_number;
	private int auth;
	private String newPassword;
	

	public UserDto() {

	}


	public UserDto(String id, String password, String nickname, String phone_number, int auth, String newPassword) {
		super();
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.phone_number = phone_number;
		this.auth = auth;
		this.newPassword = newPassword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", password=" + password + ", nickname=" + nickname + ", phone_number="
				+ phone_number + ", auth=" + auth +  ", newPassword=" + newPassword + "]";
	}

	

}
