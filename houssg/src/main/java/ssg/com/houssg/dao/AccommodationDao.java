package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.AccommodationDto;

@Mapper
@Repository
public interface AccommodationDao {

	List<AccommodationDto> getAllAccom();

	List<AccommodationDto> getRoomAva();


}
