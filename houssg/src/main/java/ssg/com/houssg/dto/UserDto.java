package ssg.com.houssg.dto;


public class UserDto {

	private String id;
	private String password;
	private String nickname;
	private String phonenumber;
	private int auth;
	private int point;
	private String newPassword;
	

	public UserDto() {

	}


	public UserDto(String id, String password, String nickname, String phonenumber, int auth, int point, String newPassword) {
		super();
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.phonenumber = phonenumber;
		this.auth = auth;
		this.point = point;
		this.newPassword = newPassword;
	}

	
	
	public int getPoint() {
		return point;
	}


	public void setPoint(int point) {
		this.point = point;
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

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
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
		return "UserDto [id=" + id + ", password=" + password + ", nickname=" + nickname + ", phonenumber="
				+ phonenumber + ", auth=" + auth + ", point=" + point + ", newPassword=" + newPassword + "]";
	}

	

	

}
