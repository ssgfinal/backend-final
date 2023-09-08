package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.RoomDto;



@Mapper
@Repository
public interface RoomDao {

	
	int addRoom (RoomDto dto);
	
	List<RoomDto> choiceAccom(int roomNumber);
}
