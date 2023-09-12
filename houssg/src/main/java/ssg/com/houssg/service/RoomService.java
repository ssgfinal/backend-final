package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.RoomDao;
import ssg.com.houssg.dto.RoomDto;

@Service
@Transactional
public class RoomService {

	@Autowired
	RoomDao dao;
	
	public int addRoom(RoomDto dto) {
		return dao.addRoom(dto);
	}
	
	public List<RoomDto> choiceAccom(int roomNumber){
		return dao.choiceAccom(roomNumber);
	}
	
}
