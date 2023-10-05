package ssg.com.houssg.dto;

public class FacilityDto {

	public int accomNumber;
	public int nearbySea;
	public int oceanView;
	public int parkingAvailable;
	public int pool;
	public int spa;
	public int couplePc;
	public int wifi;
	public int family;
	public int twinBed;
	public int barbecue;
	public int noSmoking;
	public int luggageStorage;
	public int freeMovieOtt;
    
	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public int isNearbySea() {
		return nearbySea;
	}
	public void setNearbySea(int nearbySea) {
		this.nearbySea = nearbySea;
	}
	public int isOceanView() {
		return oceanView;
	}
	public void setOceanView(int oceanView) {
		this.oceanView = oceanView;
	}
	public int isParkingAvailable() {
		return parkingAvailable;
	}
	public void setParkingAvailable(int parkingAvailable) {
		this.parkingAvailable = parkingAvailable;
	}
	public int isPool() {
		return pool;
	}
	public void setPool(int pool) {
		this.pool = pool;
	}
	public int isSpa() {
		return spa;
	}
	public void setSpa(int spa) {
		this.spa = spa;
	}
	public int isCouplePc() {
		return couplePc;
	}
	public void setCouplePc(int couplePc) {
		this.couplePc = couplePc;
	}
	public int isWifi() {
		return wifi;
	}
	public void setWifi(int wifi) {
		this.wifi = wifi;
	}
	public int isFamily() {
		return family;
	}
	public void setFamily(int family) {
		this.family = family;
	}
	public int isTwinBed() {
		return twinBed;
	}
	public void setTwinBed(int twinBed) {
		this.twinBed = twinBed;
	}
	public int isBarbecue() {
		return barbecue;
	}
	public void setBarbecue(int barbecue) {
		this.barbecue = barbecue;
	}
	public int isNoSmoking() {
		return noSmoking;
	}
	public void setNoSmoking(int noSmoking) {
		this.noSmoking = noSmoking;
	}
	public int isLuggageStorage() {
		return luggageStorage;
	}
	public void setLuggageStorage(int luggageStorage) {
		this.luggageStorage = luggageStorage;
	}
	public int isFreeMovieOtt() {
		return freeMovieOtt;
	}
	public void setFreeMovieOtt(int freeMovieOtt) {
		this.freeMovieOtt = freeMovieOtt;
	}
	public FacilityDto() {
		super();
	}
	
	
	public int getNearbySea() {
		return nearbySea;
	}
	public int getOceanView() {
		return oceanView;
	}
	public int getParkingAvailable() {
		return parkingAvailable;
	}
	public int getPool() {
		return pool;
	}
	public int getSpa() {
		return spa;
	}
	public int getCouplePc() {
		return couplePc;
	}
	public int getWifi() {
		return wifi;
	}
	public int getFamily() {
		return family;
	}
	public int getTwinBed() {
		return twinBed;
	}
	public int getBarbecue() {
		return barbecue;
	}
	public int getNoSmoking() {
		return noSmoking;
	}
	public int getLuggageStorage() {
		return luggageStorage;
	}
	public int getFreeMovieOtt() {
		return freeMovieOtt;
	}
	public FacilityDto(int accomNumber, int nearbySea, int oceanView, int parkingAvailable, int pool,
			int spa, int couplePc, int wifi, int family, int twinBed, int barbecue,
			int noSmoking, int luggageStorage, int freeMovieOtt) {
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
