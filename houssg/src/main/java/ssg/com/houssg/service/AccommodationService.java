package ssg.com.houssg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.AccommodationDao;
import ssg.com.houssg.dao.FacilityDao;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.AccommodationParam;
import ssg.com.houssg.dto.FacilityDto;

@Service
@Transactional
public class AccommodationService {

	@Autowired
	AccommodationDao dao;
	
	@Autowired
    FacilityDao facdao;
	
    public List<AccommodationDto> getAddressSearch(AccommodationParam param) {
        String search = param.getSearch();
        return dao.getAddressSearch(param);
    }
    
    public List<AccommodationDto> getMyAccom(String id){
    	List<AccommodationDto> accommodationDtoList = dao.getMyAccom(id);
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            FacilityDto facilityDto = facdao.getFacility(accommodationDto.getAccomNumber());
            int[] serviceList = {
                facilityDto.getNearbySea(),
                facilityDto.getParkingAvailable(),
                facilityDto.getPool(),
                facilityDto.getSpa(),
                facilityDto.getWifi(),
                facilityDto.getTwinBed(),
                facilityDto.getBarbecue(),
                facilityDto.getNoSmoking(),
                facilityDto.getLuggageStorage(),
                facilityDto.getFreeMovieOtt()
            };
            accommodationDto.setService(serviceList);
        }
        return accommodationDtoList;
    }
    public int updateAccom(AccommodationDto dto) {
    	return dao.updateAccom(dto);
    }
    public int deleteAccommodationById(int accommodationId) {
        return dao.deleteAccommodationById(accommodationId);
    }
    
    @Transactional
    public int addAccommodationAndFacility(AccommodationDto dto, FacilityDto facilityDto) {
        // AccommodationDto를 accommodation 테이블에 삽입
        dao.insertAccommodation(dto);

        // 삽입한 숙소의 accomNumber 가져오기
        int insertedAccomNumber = dto.getAccomNumber();

        // FacilityDto에 accomNumber 설정
        facilityDto.setAccomNumber(insertedAccomNumber);

        // FacilityDto를 facility 테이블에 삽입
        facdao.insertFacility(facilityDto);

        return insertedAccomNumber;
    }
    public void updateAccommodationAndFacility(AccommodationDto accommodationDto, FacilityDto facilityDto) {
        dao.updateAccommodation(accommodationDto);
        facilityDto.setAccomNumber(accommodationDto.getAccomNumber());
        facdao.updateFacility(facilityDto);
    }
    
    public AccommodationDto getAccom(int accomNumber){
    	AccommodationDto accommodationDto = dao.getAccom(accomNumber);
    	FacilityDto facilityDto = facdao.getFacility(accomNumber);
    	if (facilityDto != null) {
	    	int[] serviceList = {
	    			facilityDto.getNearbySea(),
	                facilityDto.getParkingAvailable(),
	                facilityDto.getPool(),
	                facilityDto.getSpa(),
	                facilityDto.getWifi(),
	                facilityDto.getTwinBed(),
	                facilityDto.getBarbecue(),
	                facilityDto.getNoSmoking(),
	                facilityDto.getLuggageStorage(),
	                facilityDto.getFreeMovieOtt()
	    	};
    	accommodationDto.setService(serviceList);
    	}
    	//accommodationDto.setFacilityDto(facilityDto);
    	return accommodationDto;
    }
    public AccommodationDto getAccomid(int accomNumber, String id){
    	AccommodationDto accommodationDto = dao.getAccomid(accomNumber,id);
    	FacilityDto facilityDto = facdao.getFacility(accomNumber);
    	if (facilityDto != null) {
	    	int[] serviceList = {
	    			facilityDto.getNearbySea(),
	                facilityDto.getParkingAvailable(),
	                facilityDto.getPool(),
	                facilityDto.getSpa(),
	                facilityDto.getWifi(),
	                facilityDto.getTwinBed(),
	                facilityDto.getBarbecue(),
	                facilityDto.getNoSmoking(),
	                facilityDto.getLuggageStorage(),
	                facilityDto.getFreeMovieOtt()
	    	};
    	accommodationDto.setService(serviceList);
    	}
    	//accommodationDto.setFacilityDto(facilityDto);
    	return accommodationDto;
    }
    
    public int updateRequest(int accomNumber) {
    	return dao.updateRequest(accomNumber);
    }
    public int deleteRequest() {
    	return dao.deleteRequest();
    }
    public List<AccommodationDto> getAllAccom(){
    	List<AccommodationDto> accommodationDtoList = dao.getAllAccom();

        // 각 숙소에 대한 시설 정보 설정
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    public List<AccommodationDto> getAllAccomid(String id){
    	List<AccommodationDto> accommodationDtoList = dao.getAllAccomid(id);

        // 각 숙소에 대한 시설 정보 설정
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    public int accomApproval(int accomNumber) {
    	return dao.accomApproval(accomNumber);
    }
    public int accomApprovalX(int accomNumber) {
    	return dao.accomApprovalX(accomNumber);
    }
    public List<AccommodationDto> getApprovalAccom(){
    	List<AccommodationDto> accommodationDtoList = dao.getApprovalAccom();

        // 각 승인된 숙소에 대한 시설 정보 설정
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    public List<AccommodationDto> accomScore(){
    	List<AccommodationDto> accommodationDtoList =  dao.accomScore();
    	 // 각 승인된 숙소에 대한 시설 정보 설정
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    public List<AccommodationDto> accomScoreid(String id){
    	List<AccommodationDto> accommodationDtoList =  dao.accomScoreid(id);
    	 // 각 승인된 숙소에 대한 시설 정보 설정
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    public List<AccommodationDto> newAccom20id(String id) {
        List<AccommodationDto> accommodationDtoList = dao.newAccom20id(id);

        // 각 숙소에 대한 시설 정보 설정
        for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }

    public List<AccommodationDto> newAccom20() {
        List<AccommodationDto> accommodationDtoList = dao.newAccom20();

        // 각 숙소에 대한 시설 정보 설정
        for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    public List<AccommodationDto> accomScore20(){
    	List<AccommodationDto> accommodationDtoList =  dao.accomScore20();
    	 // 각 승인된 숙소에 대한 시설 정보 설정
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    public List<AccommodationDto> accomScore20id(String id){
    	List<AccommodationDto> accommodationDtoList =  dao.accomScore20id(id);
    	 // 각 승인된 숙소에 대한 시설 정보 설정
    	for (AccommodationDto accommodationDto : accommodationDtoList) {
            setFacilityData(accommodationDto);
        }

        return accommodationDtoList;
    }
    private void setFacilityData(AccommodationDto accommodationDto) {
        FacilityDto facilityDto = facdao.getFacility(accommodationDto.getAccomNumber());

        // facilityDto가 null이 아닌 경우에만 시설 정보 설정
        if (facilityDto != null) {
            int[] serviceList = {
                facilityDto.getNearbySea(),
                facilityDto.getParkingAvailable(),
                facilityDto.getPool(),
                facilityDto.getSpa(),
                facilityDto.getWifi(),
                facilityDto.getTwinBed(),
                facilityDto.getBarbecue(),
                facilityDto.getNoSmoking(),
                facilityDto.getLuggageStorage(),
                facilityDto.getFreeMovieOtt()
            };
            accommodationDto.setService(serviceList);
        }
    }
}
