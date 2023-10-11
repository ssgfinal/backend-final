package ssg.com.houssg.dto;

public class RoomServiceDto {
	
    public int roomNumber;
    public int oceanView;
    public int pc;
    public int noSmoking;
    public int doubleBed;
    public int queenBed;
    public int kingBed;
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getOceanView() {
		return oceanView;
	}
	public void setOceanView(int oceanView) {
		this.oceanView = oceanView;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getNoSmoking() {
		return noSmoking;
	}
	public void setNoSmoking(int noSmoking) {
		this.noSmoking = noSmoking;
	}
	public int getDoubleBed() {
		return doubleBed;
	}
	public void setDoubleBed(int doubleBed) {
		this.doubleBed = doubleBed;
	}
	public int getQueenBed() {
		return queenBed;
	}
	public void setQueenBed(int queenBed) {
		this.queenBed = queenBed;
	}
	public int getKingBed() {
		return kingBed;
	}
	public void setKingBed(int kingBed) {
		this.kingBed = kingBed;
	}
	public RoomServiceDto() {
		super();
	}
	public RoomServiceDto(int roomNumber, int oceanView, int pc, int noSmoking, int doubleBed,
			int queenBed, int kingBed) {
		super();
		this.roomNumber = roomNumber;
		this.oceanView = oceanView;
		this.pc = pc;
		this.noSmoking = noSmoking;
		this.doubleBed = doubleBed;
		this.queenBed = queenBed;
		this.kingBed = kingBed;
	}
	@Override
	public String toString() {
		return "RoomServiceDto [roomNumber=" + roomNumber +  ", oceanView=" + oceanView + ", pc=" + pc + 
				", noSmoking=" + noSmoking + ", doubleBed=" + doubleBed + ", queenBed=" + queenBed
				+ ", kingBed=" + kingBed + "]";
	}
	
}
