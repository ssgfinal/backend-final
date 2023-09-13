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
	FacilityDao dao;
	
	public List<FacilityDto> addFicility(FacilityDto dto2) {
		return dao.addFicility(dto2);
	}
}
