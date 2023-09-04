package ssg.com.houssg.dto;

public class RoomDto {
	
	private int room_number;
	private String room_category;
	private String room_details;
	private int room_price;
	private int room_availability;
	private int accom_number;
	private int room_use;
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	public String getRoom_category() {
		return room_category;
	}
	public void setRoom_category(String room_category) {
		this.room_category = room_category;
	}
	public String getRoom_details() {
		return room_details;
	}
	public void setRoom_details(String room_details) {
		this.room_details = room_details;
	}
	public int getRoom_price() {
		return room_price;
	}
	public void setRoom_price(int room_price) {
		this.room_price = room_price;
	}
	public int getRoom_availability() {
		return room_availability;
	}
	public void setRoom_availability(int room_availability) {
		this.room_availability = room_availability;
	}
	public int getAccom_number() {
		return accom_number;
	}
	public void setAccom_number(int accom_number) {
		this.accom_number = accom_number;
	}
	public int getRoom_use() {
		return room_use;
	}
	public void setRoom_use(int room_use) {
		this.room_use = room_use;
	}
	public RoomDto() {
		super();
	}
	public RoomDto(int room_number, String room_category, String room_details, int room_price, int room_availability,
			int accom_number, int room_use) {
		super();
		this.room_number = room_number;
		this.room_category = room_category;
		this.room_details = room_details;
		this.room_price = room_price;
		this.room_availability = room_availability;
		this.accom_number = accom_number;
		this.room_use = room_use;
	}
	@Override
	public String toString() {
		return "RoomDto [room_number=" + room_number + ", room_category=" + room_category + ", room_details="
				+ room_details + ", room_price=" + room_price + ", room_availability=" + room_availability
				+ ", accom_number=" + accom_number + ", room_use=" + room_use + "]";
	}
	
	
	
}
