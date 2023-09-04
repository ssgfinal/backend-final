package ssg.com.houssg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.UserDto;

import ssg.com.houssg.service.UserService;

@RestController
public class UserController {
	
	@Autowired
    private UserService service;
	

}
