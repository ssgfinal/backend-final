package ssg.com.houssg.dto;




public class UserDto {

	private String id;
	private String password;
	private String nickname;
	private String user_phone_number;
	private int auth;
	private String bank_name;
	private String account_number;
	
	
	
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



	public String getUser_phone_number() {
		return user_phone_number;
	}



	public void setUser_phone_number(String user_phone_number) {
		this.user_phone_number = user_phone_number;
	}



	public int getAuth() {
		return auth;
	}



	public void setAuth(int auth) {
		this.auth = auth;
	}



	public String getBank_name() {
		return bank_name;
	}



	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}



	public String getAccount_number() {
		return account_number;
	}



	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}



	public UserDto(String id, String password, String nickname, String user_phone_number, int auth, String bank_name,
			String account_number) {
		super();
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.user_phone_number = user_phone_number;
		this.auth = auth;
		this.bank_name = bank_name;
		this.account_number = account_number;
	}



	@Override
	public String toString() {
		return "UserDto [id=" + id + ", password=" + password + ", nickname=" + nickname + ", user_phone_number="
				+ user_phone_number + ", auth=" + auth + ", bank_name=" + bank_name + ", account_number="
				+ account_number + "]";
	}
	
	
}
