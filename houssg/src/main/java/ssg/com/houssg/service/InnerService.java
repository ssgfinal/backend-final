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
	
	public void updateInnerView(InnerDto innerDto) {
        System.out.println("객실이미지업데이트");
		dao.updateInnerView(innerDto);
    }
	public int deleteImg(int roomNumber) {
		int imgCount =  dao.deleteImg(roomNumber);
		if(imgCount == 0) {
			System.out.println("이미지 삭제 실패");
			return 0;
		}
		return imgCount;
	}
}
