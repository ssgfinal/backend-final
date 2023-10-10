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
	
	int updateRequest(int accomNumber);
	
	int deleteRequest();
	
	List<AccommodationDto> getAllAccom();
	
	int accomApproval(int accomNumber);
	
	int accomApprovalX(int accomNumber);
	
	List<AccommodationDto> getApprovalAccom();
	
	List<AccommodationDto> accomScore();
	
	List<AccommodationDto> accomScore20();
	
	List<AccommodationDto> newAccom20();

}
