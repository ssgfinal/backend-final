package ssg.com.houssg.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.houssg.dto.FavoriteDto;
import ssg.com.houssg.service.FavoriteService;

@RestController
public class FavoriteController {

	@Autowired
	FavoriteService service;
	
	@PostMapping("favorite/add")
	public ResponseEntity<String> addFavorite(@RequestParam String id, @RequestParam int accomNumber) {
	    // 찜하기 요청 처리
	    int count = service.addFavorite(id, accomNumber);

	    // 찜하기 결과에 따라 응답 설정
	    if (count == 0) {
	        // 찜하기가 실패하면 404 Not Found 응답 반환
	        return new ResponseEntity<>("NO", HttpStatus.NOT_FOUND);
	    }

	    // 찜하기가 성공하면 200 OK 응답 반환
	    return new ResponseEntity<>("YES", HttpStatus.OK);
	}

	@DeleteMapping("favorite")
	public ResponseEntity<String> deleteFavorite(@RequestParam String id, @RequestParam int accomNumber) {
	    // 찜해제 요청 처리
	    int count = service.deleteFavorite(id, accomNumber);

	    // 찜해제 결과에 따라 응답 설정
	    if (count == 0) {
	        // 찜해제가 실패하면 404 Not Found 응답 반환
	        return new ResponseEntity<>("NO", HttpStatus.NOT_FOUND);
	    }

	    // 찜해제가 성공하면 200 OK 응답 반환
	    return new ResponseEntity<>("YES", HttpStatus.OK);
	}
	
	 @PostMapping("mypage/favorite")
	 public ResponseEntity<List<FavoriteDto>> getMyFavorite(@RequestParam String id) {
	     System.out.println("찜보기");
	     List<FavoriteDto> favoriteList = service.getMyFavorite(id);
	     
	     if (favoriteList != null && !favoriteList.isEmpty()) {
	         // 찜 목록이 비어 있지 않으면 200 OK 응답과 함께 목록을 반환
	         return ResponseEntity.ok(favoriteList);
	     } else {
	         // 찜 목록이 비어 있으면 404 Not Found와 함께 빈 목록을 반환
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
	     }
	 }

}
