package ssg.com.houssg.dto;

public class ReservationRoomDto {
	
	 	private String date;
	    private int availableRooms;
	    private String yearMonth;
	    
	    
		public String getYearMonth() {
			return yearMonth;
		}
		public void setYearMonth(String yearMonth) {
			this.yearMonth = yearMonth;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public int getAvailableRooms() {
			return availableRooms;
		}
		public void setAvailableRooms(int availableRooms) {
			this.availableRooms = availableRooms;
		}
		@Override
		public String toString() {
			return "ReservationRoomDto [date=" + date + ", availableRooms=" + availableRooms + ", yearMonth="
					+ yearMonth + "]";
		}
	    
}
