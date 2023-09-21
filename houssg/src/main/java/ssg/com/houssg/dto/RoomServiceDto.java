package ssg.com.houssg.dto;

public class RoomServiceDto {
	
    private int roomNumber;
    private boolean cityView;
    private boolean oceanView;
    private boolean pc;
    private boolean noSmoking;
    private boolean doubleBed;
    private boolean queenBed;
    private boolean kingBed;
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public boolean isCityView() {
		return cityView;
	}
	public void setCityView(boolean cityView) {
		this.cityView = cityView;
	}
	public boolean isOceanView() {
		return oceanView;
	}
	public void setOceanView(boolean oceanView) {
		this.oceanView = oceanView;
	}
	public boolean isPc() {
		return pc;
	}
	public void setPc(boolean pc) {
		this.pc = pc;
	}
	public boolean isNoSmoking() {
		return noSmoking;
	}
	public void setNoSmoking(boolean noSmoking) {
		this.noSmoking = noSmoking;
	}
	public boolean isDoubleBed() {
		return doubleBed;
	}
	public void setDoubleBed(boolean doubleBed) {
		this.doubleBed = doubleBed;
	}
	public boolean isQueenBed() {
		return queenBed;
	}
	public void setQueenBed(boolean queenBed) {
		this.queenBed = queenBed;
	}
	public boolean isKingBed() {
		return kingBed;
	}
	public void setKingBed(boolean kingBed) {
		this.kingBed = kingBed;
	}
	public RoomServiceDto() {
		super();
	}
	public RoomServiceDto(int roomNumber, boolean cityView, boolean oceanView, boolean pc,
			boolean noSmoking, boolean doubleBed, boolean queenBed, boolean kingBed) {
		super();
		this.roomNumber = roomNumber;
		this.cityView = cityView;
		this.oceanView = oceanView;
		this.pc = pc;
		this.noSmoking = noSmoking;
		this.doubleBed = doubleBed;
		this.queenBed = queenBed;
		this.kingBed = kingBed;
	}
	@Override
	public String toString() {
		return "RoomServiceDto [roomNumber=" + roomNumber + ", cityView=" + cityView + ", oceanView=" + oceanView
				+ ", pc=" + pc + ", noSmoking=" + noSmoking + ", doubleBed=" + doubleBed
				+ ", queenBed=" + queenBed + ", kingBed=" + kingBed+"]";
	}
    
}
