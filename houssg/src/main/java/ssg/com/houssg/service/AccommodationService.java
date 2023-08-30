package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.AccommodationDao;
import ssg.com.houssg.dto.AccommodationDto;

@Service
@Transactional
public class AccommodationService {

	@Autowired
	AccommodationDao dao;
	
	public List<AccommodationDto> getAllAccom() {
        return dao.getAllAccom();
    }
	
	public List<AccommodationDto> getRoomAva() {
        return dao.getRoomAva();
    }
}
