package ssg.com.houssg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	RoomService service;
	

}
