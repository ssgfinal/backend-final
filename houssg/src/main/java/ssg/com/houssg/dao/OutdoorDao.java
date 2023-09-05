package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import ssg.com.houssg.dto.OutdoorDto;

@Mapper
@Repository
public interface OutdoorDao {

	void insertOutdoorView(OutdoorDto dto);

	void storeFile(MultipartFile file);
	

}
