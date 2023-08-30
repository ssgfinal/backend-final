package ssg.com.houssg.dto;

public class RoomDto {
	
	private int room_number;
	private String room_name;
	private String room_category;
	private String room_details;
	private String inner_view;
	private int room_price;
	private int room_availability;
	private int accom_number;
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
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
	public String getInner_view() {
		return inner_view;
	}
	public void setInner_view(String inner_view) {
		this.inner_view = inner_view;
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
	public RoomDto(int room_number, String room_name, String room_category, String room_details, String inner_view,
			int room_price, int room_availability, int accom_number) {
		super();
		this.room_number = room_number;
		this.room_name = room_name;
		this.room_category = room_category;
		this.room_details = room_details;
		this.inner_view = inner_view;
		this.room_price = room_price;
		this.room_availability = room_availability;
		this.accom_number = accom_number;
	}
	@Override
	public String toString() {
		return "RoomDto [room_number=" + room_number + ", room_name=" + room_name + ", room_category=" + room_category
				+ ", room_details=" + room_details + ", inner_view=" + inner_view + ", room_price=" + room_price
				+ ", room_availability=" + room_availability + ", accom_number=" + accom_number + "]";
	}
	
	
	
}
