package ssg.com.houssg.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.FavoriteDto;
import ssg.com.houssg.service.FavoriteService;

@RestController
public class FavoriteController {

	@Value("${jwt.secret}")
	private String secretKey;
	
	@Autowired
	FavoriteService service;
	
	@PostMapping("favorite/add")
	public ResponseEntity<String> addFavorite(@RequestParam int accomNumber,HttpServletRequest httpRequest) {
		String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
		// 찜하기 요청 처리
	    int count = service.addFavorite(userId, accomNumber);

	    // 찜하기 결과에 따라 응답 설정
	    if (count == 0) {
	        // 찜하기가 실패하면 404 Not Found 응답 반환
	        return new ResponseEntity<>("NO", HttpStatus.NOT_FOUND);
	    }

	    // 찜하기가 성공하면 200 OK 응답 반환
	    return new ResponseEntity<>("YES", HttpStatus.OK);
	}

	@DeleteMapping("favorite")
	public ResponseEntity<String> deleteFavorite(@RequestParam int accomNumber,HttpServletRequest httpRequest) {
		String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
		// 찜해제 요청 처리
	    int count = service.deleteFavorite(userId, accomNumber);

	    // 찜해제 결과에 따라 응답 설정
	    if (count == 0) {
	        // 찜해제가 실패하면 404 Not Found 응답 반환
	        return new ResponseEntity<>("NO", HttpStatus.NOT_FOUND);
	    }

	    // 찜해제가 성공하면 200 OK 응답 반환
	    return new ResponseEntity<>("YES", HttpStatus.OK);
	}
	
	 @PostMapping("mypage/favorite")
	 public ResponseEntity<List<FavoriteDto>> getMyFavorite(HttpServletRequest httpRequest) {
	     System.out.println("찜보기");
	     String token = getTokenFromRequest(httpRequest);
	     String userId = getUserIdFromToken(token);
	     List<FavoriteDto> favoriteList = service.getMyFavorite(userId);
	     
	     if (favoriteList != null && !favoriteList.isEmpty()) {
	         // 찜 목록이 비어 있지 않으면 200 OK 응답과 함께 목록을 반환
	         return ResponseEntity.ok(favoriteList);
	     } else {
	         // 찜 목록이 비어 있으면 404 Not Found와 함께 빈 목록을 반환
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
	     }
	 }
	 @GetMapping("/favorite")
	    public ResponseEntity<Integer> roomGet(@RequestParam int accomNumber, HttpServletRequest httpRequest) {
	        String token = getTokenFromRequest(httpRequest);
	        String userId = getUserIdFromToken(token);

	        int count = service.roomGet(accomNumber, userId);
	        return ResponseEntity.ok(count);
	    }
	 
	    private String getTokenFromRequest(HttpServletRequest request) {
			String token = request.getHeader("Authorization");

			if (token != null && token.startsWith("Bearer ")) {
				return token.substring(7);
			}

			return null;
		}
	    
	    private String getUserIdFromToken(String token) {
			try {
				Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
						.parseClaimsJws(token).getBody();
				return claims.get("id", String.class); // "id" 클레임 추출
			} catch (Exception e) {
				// 토큰 파싱 실패
				e.printStackTrace();
				return null;
			}
		}
}
