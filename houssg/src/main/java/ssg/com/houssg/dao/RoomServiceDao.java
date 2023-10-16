package ssg.com.houssg.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.RoomServiceDto;

@Mapper
@Repository
public interface RoomServiceDao {

	int insertRoomService(RoomServiceDto dto);
	
	int updateRoomService(RoomServiceDto roomServiceDto);
	
	RoomServiceDto getService(int roomNumber);
	
	int deleteService(int roomNumber);
}
