package ssg.com.houssg.service;

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
    	return dao.getMyAccom(id);
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
    
    public AccommodationDto getAllAccom(int accomNumber){
    	return dao.getAllAccom(accomNumber);
    }
}
