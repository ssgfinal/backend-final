package ssg.com.houssg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.AccommodationDto;
@RestController
public class HelloController {

	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@GetMapping("hello")
	public String hello(HttpServletRequest httpRequest) {
		
		String token = getTokenFromRequest(httpRequest);
		System.out.println("토큰 : " + token);
	    String userId = getUserIdFromToken(token);
	    System.out.println(userId);
	    
		return "Hello test입니다.";
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
