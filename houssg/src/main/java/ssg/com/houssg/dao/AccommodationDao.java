package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.AccommodationParam;

@Mapper
@Repository
public interface AccommodationDao {

	List<AccommodationDto> getAddressSearch(AccommodationParam param);
	
	boolean addAccom (AccommodationDto dto);
	
	List<AccommodationDto> getMyAccom(String id);
	
	int updateAccom(AccommodationDto dto);

}
