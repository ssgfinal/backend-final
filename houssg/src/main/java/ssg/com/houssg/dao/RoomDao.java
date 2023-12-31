package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.RoomServiceDto;



@Mapper
@Repository
public interface RoomDao {

	
	void addRoom (RoomDto dto);
	
	List<RoomDto> choiceAccom(int roomNumber);
	
	int getRoomNumberFromDatabase();
	
	int updateRoom(RoomDto roomDto);
	
	int deleteRequest(int roomNumber);

	int isCategoryDuplicate(int accomNumber,String roomCategory);
	
	int delRequest(int roomNumber);
	
	int choiceRoom(int roomNumber);
}
