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
	
	List<AccommodationDto> getMyAccom(String id);
	
	int updateAccom(AccommodationDto dto);
	
	int deleteAccommodationById(int accommodationId);

	void insertAccommodation(AccommodationDto dto);
	
	void updateAccommodation(AccommodationDto accommodationDto);
	
	AccommodationDto getAccom(int accomNumber);
	
	AccommodationDto getAccomid(int accomNumber, String id);
	
	int updateRequest(int accomNumber);
	
	int deleteRequest();
	
	List<AccommodationDto> getAllAccom();
	
	List<AccommodationDto> getAllAccomid(String id);
	
	int accomApproval(int accomNumber);
	
	int accomApprovalX(int accomNumber);
	
	List<AccommodationDto> getApprovalAccom();
	
	List<AccommodationDto> accomScore();
	
	List<AccommodationDto> accomScoreid(String id);
	
	List<AccommodationDto> accomScore20();
	
	List<AccommodationDto> accomScore20id(String id);
	
	List<AccommodationDto> newAccom20();
	
	List<AccommodationDto> newAccom20id(String id);
}
