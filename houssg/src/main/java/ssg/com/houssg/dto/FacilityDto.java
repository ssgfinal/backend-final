package ssg.com.houssg.dto;

import java.io.Serializable;

public class FacilityDto implements Serializable{

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
    
	public FacilityDto() {
	}

	public int getAccomNumber() {
		return accomNumber;
	}
	public void setAccomNumber(int accomNumber) {
		this.accomNumber = accomNumber;
	}
	public int getNearbySea() {
		return nearbySea;
	}
	public void setNearbySea(int nearbySea) {
		this.nearbySea = nearbySea;
	}
	public int getOceanView() {
		return oceanView;
	}
	public void setOceanView(int oceanView) {
		this.oceanView = oceanView;
	}
	public int getParkingAvailable() {
		return parkingAvailable;
	}
	public void setParkingAvailable(int parkingAvailable) {
		this.parkingAvailable = parkingAvailable;
	}
	public int getPool() {
		return pool;
	}
	public void setPool(int pool) {
		this.pool = pool;
	}
	public int getSpa() {
		return spa;
	}
	public void setSpa(int spa) {
		this.spa = spa;
	}
	public int getCouplePc() {
		return couplePc;
	}
	public void setCouplePc(int couplePc) {
		this.couplePc = couplePc;
	}
	public int getWifi() {
		return wifi;
	}
	public void setWifi(int wifi) {
		this.wifi = wifi;
	}
	public int getFamily() {
		return family;
	}
	public void setFamily(int family) {
		this.family = family;
	}
	public int getTwinBed() {
		return twinBed;
	}
	public void setTwinBed(int twinBed) {
		this.twinBed = twinBed;
	}
	public int getBarbecue() {
		return barbecue;
	}
	public void setBarbecue(int barbecue) {
		this.barbecue = barbecue;
	}
	public int getNoSmoking() {
		return noSmoking;
	}
	public void setNoSmoking(int noSmoking) {
		this.noSmoking = noSmoking;
	}
	public int getLuggageStorage() {
		return luggageStorage;
	}
	public void setLuggageStorage(int luggageStorage) {
		this.luggageStorage = luggageStorage;
	}
	public int getFreeMovieOtt() {
		return freeMovieOtt;
	}
	public void setFreeMovieOtt(int freeMovieOtt) {
		this.freeMovieOtt = freeMovieOtt;
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
