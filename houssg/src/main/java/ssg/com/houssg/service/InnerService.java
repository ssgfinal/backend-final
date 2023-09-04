package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.InnerDao;
import ssg.com.houssg.dto.InnerDto;




@Service
@Transactional
public class InnerService {

	@Autowired
	InnerDao dao;
	
	public void insertInnerView(InnerDto dto) {
		System.out.println("객실이미지등록");
        dao.insertInnerView(dto);            
    }
}
