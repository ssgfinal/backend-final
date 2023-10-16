package ssg.com.houssg.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import ssg.com.houssg.dto.InnerDto;



@Mapper
@Repository
public interface InnerDao {

	void insertInnerView(InnerDto dto);

	void storeFile(MultipartFile file);
	
	void updateInnerView(InnerDto innerDto);
	
	InnerDto getImgs(int roomNumber);
	
	int deleteImg(int roomNumber);
	
}
