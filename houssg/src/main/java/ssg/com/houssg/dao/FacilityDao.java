package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.FacilityDto;

@Mapper
@Repository
public interface FacilityDao  {

	 List<FacilityDto> addFicility(FacilityDto dto2);
}
