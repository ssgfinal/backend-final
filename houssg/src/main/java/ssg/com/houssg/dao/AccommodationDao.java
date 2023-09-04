package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.AccommodationParam;

@Mapper
@Repository
public interface AccommodationDao {

	List<AccommodationDto> getAllAccom();
	
	List<AccommodationDto> getAccomType(String type);
	
	List<AccommodationDto> getAddressSearch(String search);
	
	List<AccommodationDto> getAddressSearch(AccommodationParam param);
	
	int addAccom (AccommodationDto dto);

}
