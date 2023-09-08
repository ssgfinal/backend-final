package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.AccommodationDao;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.AccommodationParam;

@Service
@Transactional
public class AccommodationService {

	@Autowired
	AccommodationDao dao;
	
	public List<AccommodationDto> getAllAccom() {
        return dao.getAllAccom();
    }
	
	public List<AccommodationDto> getAccomType(String type){
		return dao.getAccomType(type);
	}
	
	public List<AccommodationDto> getAddressSearch(String search){
		return dao.getAddressSearch(search);
	}
	
    public List<AccommodationDto> getAddressSearch(AccommodationParam param) {
        String search = param.getSearch();
        return dao.getAddressSearch(param);
    }
    
    public int addAccom(AccommodationDto dto) {
        return dao.addAccom(dto);
    }
    
    public List<AccommodationDto> getMyAccom(String id){
    	return dao.getMyAccom(id);
    }
    public int updateAccom(AccommodationDto dto) {
    	return dao.updateAccom(dto);
    }
}
