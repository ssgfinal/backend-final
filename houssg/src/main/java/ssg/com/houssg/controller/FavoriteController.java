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
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.FavoriteDto;
import ssg.com.houssg.dto.FavoriteParam;
import ssg.com.houssg.service.FavoriteService;

@RestController
public class FavoriteController {

	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	FavoriteService service;

	@PostMapping("favorite")
	public ResponseEntity<String> addFavorite(@RequestParam int accomNumber, HttpServletRequest httpRequest) {
		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);
		// 찜하기 요청 처리

		if (accomNumber == 0) {
			return new ResponseEntity<>("숙소번호x", HttpStatus.BAD_REQUEST);
		}
		if (service.isIdDuplicate(accomNumber, userId)) {
			return new ResponseEntity<>("찜 중복x", HttpStatus.BAD_REQUEST);
		}
		// 찜하기 결과에 따라 응답 설정
		int count = service.addFavorite(userId, accomNumber);
		if (count == 0) {
			// 찜하기가 실패하면 404 Not Found 응답 반환
			return new ResponseEntity<>("등록 실패", HttpStatus.BAD_REQUEST);
		}

		// 찜하기가 성공하면 200 OK 응답 반환
		return new ResponseEntity<>("찜 등록", HttpStatus.OK);
	}

	@DeleteMapping("favorite")
	public ResponseEntity<String> deleteFavorite(@RequestParam int accomNumber, HttpServletRequest httpRequest) {
		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);
		// 찜해제 요청 처리
		if (accomNumber == 0) {
			return new ResponseEntity<>("숙소번호x", HttpStatus.BAD_REQUEST);
		}
		int count = service.deleteFavorite(userId, accomNumber);

		// 찜해제 결과에 따라 응답 설정
		if (count == 0) {
			// 찜해제가 실패하면 400 BAD_REQUEST 응답 반환
			return new ResponseEntity<>("삭제 할거 x", HttpStatus.BAD_REQUEST);
		}

		// 찜해제가 성공하면 200 OK 응답 반환
		return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
	}

	@GetMapping("mypage/favorite")
	public ResponseEntity<List<FavoriteParam>> getMyFavorite(HttpServletRequest httpRequest) {
		System.out.println("찜보기");
		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);
		List<FavoriteParam> favoriteList = service.getMyFavorite(userId);
		System.out.println(favoriteList);
		if (favoriteList != null && !favoriteList.isEmpty()) {
			// 찜 목록이 비어 있지 않으면 200 OK 응답과 함께 목록을 반환
			return ResponseEntity.ok(favoriteList);
		} else {
			return new ResponseEntity<>(new ArrayList<FavoriteParam>(), HttpStatus.OK);
		}
	}

	@GetMapping("favorite")
	public ResponseEntity<String> roomGet(@RequestParam int accomNumber, HttpServletRequest httpRequest) {
		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);

		if (accomNumber == 0) {
			return new ResponseEntity<>("객실 번호x", HttpStatus.BAD_REQUEST);
		}
		int count = service.roomGet(accomNumber, userId);
		if (count == 0) {
			return ResponseEntity.ok(String.valueOf(count));
		}
		return ResponseEntity.ok(String.valueOf(count));
	}

	// AccessToken 획득 및 파싱 Part
	private String getTokenFromRequest(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		if (accessToken != null && accessToken.startsWith("Bearer ")) {
			return accessToken.substring(7); // "Bearer " 부분을 제외한 엑세스 토큰 부분 추출
		}

		String refreshToken = request.getHeader("RefreshToken");
		if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
			return refreshToken.substring(7);
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
