package ssg.com.houssg.dto;

public class RoomServiceDto {
	
    private int roomNumber;
    private boolean cityView;
    private boolean oceanView;
    private boolean spa;
    private boolean pc;
    private boolean nonSmoking;
    private boolean doubleBed;
    private boolean queenBed;
    private boolean kingBed;
    private boolean netflix;
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
	public boolean isSpa() {
		return spa;
	}
	public void setSpa(boolean spa) {
		this.spa = spa;
	}
	public boolean isPc() {
		return pc;
	}
	public void setPc(boolean pc) {
		this.pc = pc;
	}
	public boolean isNonSmoking() {
		return nonSmoking;
	}
	public void setNonSmoking(boolean nonSmoking) {
		this.nonSmoking = nonSmoking;
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
	public boolean isNetflix() {
		return netflix;
	}
	public void setNetflix(boolean netflix) {
		this.netflix = netflix;
	}
	public RoomServiceDto() {
		super();
	}
	public RoomServiceDto(int roomNumber, boolean cityView, boolean oceanView, boolean spa, boolean pc,
			boolean nonSmoking, boolean doubleBed, boolean queenBed, boolean kingBed, boolean netflix) {
		super();
		this.roomNumber = roomNumber;
		this.cityView = cityView;
		this.oceanView = oceanView;
		this.spa = spa;
		this.pc = pc;
		this.nonSmoking = nonSmoking;
		this.doubleBed = doubleBed;
		this.queenBed = queenBed;
		this.kingBed = kingBed;
		this.netflix = netflix;
	}
	@Override
	public String toString() {
		return "RoomServiceDto [roomNumber=" + roomNumber + ", cityView=" + cityView + ", oceanView=" + oceanView
				+ ", spa=" + spa + ", pc=" + pc + ", nonSmoking=" + nonSmoking + ", doubleBed=" + doubleBed
				+ ", queenBed=" + queenBed + ", kingBed=" + kingBed + ", netflix=" + netflix + "]";
	}
    
}
