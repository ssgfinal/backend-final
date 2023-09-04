package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.UserDao;
import ssg.com.houssg.dto.UserDto;

@Service
@Transactional
public class UserService {

	@Autowired
	UserDao dao;
	
	
}
