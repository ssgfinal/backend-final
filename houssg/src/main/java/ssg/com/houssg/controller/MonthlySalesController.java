package ssg.com.houssg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import ssg.com.houssg.dto.MonthlySalesSummaryDto;
import ssg.com.houssg.service.MonthlySalesService;


@RestController
@RequestMapping("monthly-sales")
public class MonthlySalesController {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Autowired
	private MonthlySalesService monthlySalesService;
	
	
	@GetMapping("/check")
    public List<MonthlySalesSummaryDto> getMonthlySales(HttpServletRequest request) {
		
		String token = getTokenFromRequest(request);
		String ownerId = getUserIdFromToken(token); 
		
		List<MonthlySalesSummaryDto> salesList = monthlySalesService.getMonthlySales(ownerId);
        return salesList;
    }
	
	// AccessToken 획득 및 파싱 Part
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


