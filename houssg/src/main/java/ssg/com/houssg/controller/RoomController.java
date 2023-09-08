package ssg.com.houssg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	private RoomService service;
	
	@PostMapping("addRoom")
	public String addRoom(RoomDto dto) {
		System.out.println(dto.toString());
		int count = service.addRoom(dto);
		if(count == 0) {
			return "NO";
		}
		return Integer.toString(dto.getRoomNumber());
	}
	
	@GetMapping("choiceAccom")
	public List<RoomDto> choiceAccom(@RequestParam int roomNumber){
		System.out.println(roomNumber);
		System.out.println("숙소상세로 들어갑니다");
		List<RoomDto> list = service.choiceAccom(roomNumber);		
		return list;
	}
}
