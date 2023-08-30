package ssg.com.houssg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.service.AccommodationService;

@RestController
public class AccommodationController {

    @Autowired
    private AccommodationService service;

    @GetMapping("getAllAccom")
    public List<AccommodationDto> getAllAccom() {
    	System.out.println("리스트에 접근합니다");
        return service.getAllAccom();
    }
    
    @GetMapping("getRoomAva")
    public List<AccommodationDto> getRoomAva() {
    	System.out.println("예약한 방의 값이 0인것(빈방)을 조회합니다");
        return service.getRoomAva();
    }
}
