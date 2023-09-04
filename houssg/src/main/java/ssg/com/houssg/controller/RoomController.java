package ssg.com.houssg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.service.InnerService;
import ssg.com.houssg.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	private RoomService service;
	
	@Autowired
	private InnerService inner;
	
	@PostMapping("addRoom")
	public String addRoom(RoomDto dto) {
		System.out.println(dto.toString());
		int count = service.addRoom(dto);
		if(count == 0) {
			return "NO";
		}
		return Integer.toString(dto.getRoom_number());
	}
}
