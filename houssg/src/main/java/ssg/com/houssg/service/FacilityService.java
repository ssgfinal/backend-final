package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.FacilityDao;
import ssg.com.houssg.dto.FacilityDto;

@Service
@Transactional
public class FacilityService {

	@Autowired
    private FacilityDao dao;

    public void insertFacility(FacilityDto facilityDto) {
        dao.insertFacility(facilityDto);
    }
    
    public void updateFacility(FacilityDto facilityDto) {
    	dao.updateFacility(facilityDto);
    }
}
