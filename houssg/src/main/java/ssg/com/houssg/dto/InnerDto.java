package ssg.com.houssg.dto;

public class InnerDto {

	private int roomNumber;
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	private String img5;
	private String img6;
	private String img7;
	private String img8;
	private String img9;
	private String img10;
	

	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	public String getImg6() {
		return img6;
	}
	public void setImg6(String img6) {
		this.img6 = img6;
	}
	public String getImg7() {
		return img7;
	}
	public void setImg7(String img7) {
		this.img7 = img7;
	}
	public String getImg8() {
		return img8;
	}
	public void setImg8(String img8) {
		this.img8 = img8;
	}
	public String getImg9() {
		return img9;
	}
	public void setImg9(String img9) {
		this.img9 = img9;
	}
	public String getImg10() {
		return img10;
	}
	public void setImg10(String img10) {
		this.img10 = img10;
	}
	public InnerDto() {
		super();
	}
	public InnerDto(int roomNumber, String img1, String img2, String img3, String img4, String img5, String img6,
			String img7, String img8, String img9, String img10) {
		super();
		this.roomNumber = roomNumber;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.img4 = img4;
		this.img5 = img5;
		this.img6 = img6;
		this.img7 = img7;
		this.img8 = img8;
		this.img9 = img9;
		this.img10 = img10;
	}
	@Override
	public String toString() {
		return "InnerDto [roomNumber=" + roomNumber + ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3 + ", img4="
				+ img4 + ", img5=" + img5 + ", img6=" + img6 + ", img7=" + img7 + ", img8=" + img8 + ", img9=" + img9
				+ ", img10=" + img10 + "]";
	}
	
}
