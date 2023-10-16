package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.RoomServiceDao;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.RoomServiceDto;
@Service
@Transactional
public class RoomServiceService {

	@Autowired
	RoomServiceDao dao;

	public int insertRoomService(RoomServiceDto dto) {
		return dao.insertRoomService(dto);
	}

	public int updateRoomService(RoomServiceDto roomServiceDto) {
		return dao.updateRoomService(roomServiceDto);
	}
	
	public int deleteService(int roomNumber) {
		return dao.deleteService(roomNumber);
	}
}
