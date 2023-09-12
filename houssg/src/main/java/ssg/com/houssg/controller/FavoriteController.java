package ssg.com.houssg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.FavoriteDto;
import ssg.com.houssg.service.FavoriteService;

@RestController
public class FavoriteController {

	@Autowired
	FavoriteService service;
	
	@PostMapping("addFavorite")
	public String addFavorite(@RequestParam String id, @RequestParam int accomNumber){
		System.out.println("찜하기");
		int count = service.addFavorite(id, accomNumber);
		if(count==0) {
			return "NO";
		}
		return "YES";
	}
	
	@PostMapping("deleteFavorite")
	public String deleteFavorite(@RequestParam String id, @RequestParam int accomNumber){
		System.out.println("찜해제");
		int count = service.deleteFavorite(id, accomNumber);
		if(count==0) {
			return "NO";
		}
		return "YES";
	}
	
	@PostMapping("getMyFavorite")
	public List<FavoriteDto> getMyFavorite(@RequestParam String id){
		System.out.println("찜보기");
		return service.getMyFavorite(id);
	}
}
