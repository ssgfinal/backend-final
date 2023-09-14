package ssg.com.houssg.dto;

public class FacilityDto {

	private int accomNumber;
	private boolean nearbySea;
	private boolean oceanView;
	private boolean parkingAvailable;
	private boolean pool;
	private boolean spa;
	private boolean couplePc;
	private boolean wifi;
	private boolean family;
	private boolean twinBed;
	private boolean barbecue;
	private boolean noSmoking;
	private boolean luggageStorage;
	private boolean freeMovieOtt;
    
	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public boolean isNearbySea() {
		return nearbySea;
	}
	public void setNearbySea(boolean nearbySea) {
		this.nearbySea = nearbySea;
	}
	public boolean isOceanView() {
		return oceanView;
	}
	public void setOceanView(boolean oceanView) {
		this.oceanView = oceanView;
	}
	public boolean isParkingAvailable() {
		return parkingAvailable;
	}
	public void setParkingAvailable(boolean parkingAvailable) {
		this.parkingAvailable = parkingAvailable;
	}
	public boolean isPool() {
		return pool;
	}
	public void setPool(boolean pool) {
		this.pool = pool;
	}
	public boolean isSpa() {
		return spa;
	}
	public void setSpa(boolean spa) {
		this.spa = spa;
	}
	public boolean isCouplePc() {
		return couplePc;
	}
	public void setCouplePc(boolean couplePc) {
		this.couplePc = couplePc;
	}
	public boolean isWifi() {
		return wifi;
	}
	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}
	public boolean isFamily() {
		return family;
	}
	public void setFamily(boolean family) {
		this.family = family;
	}
	public boolean isTwinBed() {
		return twinBed;
	}
	public void setTwinBed(boolean twinBed) {
		this.twinBed = twinBed;
	}
	public boolean isBarbecue() {
		return barbecue;
	}
	public void setBarbecue(boolean barbecue) {
		this.barbecue = barbecue;
	}
	public boolean isNoSmoking() {
		return noSmoking;
	}
	public void setNoSmoking(boolean noSmoking) {
		this.noSmoking = noSmoking;
	}
	public boolean isLuggageStorage() {
		return luggageStorage;
	}
	public void setLuggageStorage(boolean luggageStorage) {
		this.luggageStorage = luggageStorage;
	}
	public boolean isFreeMovieOtt() {
		return freeMovieOtt;
	}
	public void setFreeMovieOtt(boolean freeMovieOtt) {
		this.freeMovieOtt = freeMovieOtt;
	}
	public FacilityDto() {
		super();
	}
	public FacilityDto(int accomNumber, boolean nearbySea, boolean oceanView, boolean parkingAvailable, boolean pool,
			boolean spa, boolean couplePc, boolean wifi, boolean family, boolean twinBed, boolean barbecue,
			boolean noSmoking, boolean luggageStorage, boolean freeMovieOtt) {
		super();
		this.accomNumber = accomNumber;
		this.nearbySea = nearbySea;
		this.oceanView = oceanView;
		this.parkingAvailable = parkingAvailable;
		this.pool = pool;
		this.spa = spa;
		this.couplePc = couplePc;
		this.wifi = wifi;
		this.family = family;
		this.twinBed = twinBed;
		this.barbecue = barbecue;
		this.noSmoking = noSmoking;
		this.luggageStorage = luggageStorage;
		this.freeMovieOtt = freeMovieOtt;
	}
	@Override
	public String toString() {
		return "FacilityDto [accomNumber=" + accomNumber + ", nearbySea=" + nearbySea + ", oceanView=" + oceanView
				+ ", parkingAvailable=" + parkingAvailable + ", pool=" + pool + ", spa=" + spa + ", couplePc="
				+ couplePc + ", wifi=" + wifi + ", family=" + family + ", twinBed=" + twinBed + ", barbecue=" + barbecue
				+ ", noSmoking=" + noSmoking + ", luggageStorage=" + luggageStorage + ", freeMovieOtt=" + freeMovieOtt
				+ "]";
	}
	
}
