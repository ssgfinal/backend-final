package ssg.com.houssg.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.RoomDto;



@Mapper
@Repository
public interface RoomDao {

	
	int addRoom (RoomDto dto);
}
