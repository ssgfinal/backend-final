package ssg.com.houssg.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ssg.com.houssg.dao.OutdoorDao;
import ssg.com.houssg.dto.OutdoorDto;

@Service
@Transactional
public class OutdoorService {

	@Autowired
	OutdoorDao dao;
	
	public void insertOutdoorView(OutdoorDto dto) {
		System.out.println("숙소이미지등록");
        dao.insertOutdoorView(dto);            
    }
	

	
}
