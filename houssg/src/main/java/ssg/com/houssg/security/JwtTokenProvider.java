package ssg.com.houssg.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import ssg.com.houssg.dto.UserDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long accessTokenValidityInMilliseconds;

	@Value("${jwt.refreshExpiration}")
	private long refreshTokenValidityInMilliseconds;

	@Value("${jwt.issuer}")
	private String issuer;

	// 헤더 설정
	private Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();
		header.put("typ", "JWT");
		return header;
	}

	// 클레임 설정 (내용물)
	private Map<String, Object> createClaims(UserDto user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("auth", user.getAuth());
		return claims;
	}

	// 어세스토큰 생성
	public String createAccessToken(UserDto user) {
		Instant now = Instant.now();
		// 발급시간
		Date issuedAt = Date.from(now);
		System.out.println(issuedAt);
		// 만료시간
		Date expiration = Date.from(now.plus(accessTokenValidityInMilliseconds, ChronoUnit.MILLIS));
		System.out.println(expiration);
		Map<String, Object> header = createHeader();
		Map<String, Object> claims = createClaims(user);

//		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//		System.out.println(key);
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
		
		return Jwts.builder().setHeader(header).setClaims(claims).setSubject(user.getId()).setIssuer(issuer) // issuer
																												// 정보 설정
				.setIssuedAt(issuedAt).setExpiration(expiration).signWith(key, SignatureAlgorithm.HS512).compact();
	}

	// 리프레시 토큰 생성
	public String createRefreshToken(UserDto user) {
		Instant now = Instant.now();
		Date issuedAt = Date.from(now);
		Date expiration = Date.from(now.plus(refreshTokenValidityInMilliseconds, ChronoUnit.MILLIS));

		Map<String, Object> header = createHeader();
		Map<String, Object> claims = createClaims(user);

		Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

		return Jwts.builder().setHeader(header).setClaims(claims).setSubject(user.getId()).setIssuer(issuer) // issuer
																												// 정보 설정
				.setIssuedAt(issuedAt).setExpiration(expiration).signWith(key, SignatureAlgorithm.HS512).compact();
	}

	// 토큰에서 ID 추출
	public String getUserIdFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
				.parseClaimsJws(token).getBody();
		return claims.get("id", String.class); // "id" 클레임 추출
	}

	// 토큰에서 권한정보 추출
	public int getAuthFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
				.parseClaimsJws(token).getBody();
		return claims.get("auth", Integer.class); // "auth" 클레임 추출
	}

	// 엑세스 토큰의 유효성을 검증하고 만료 여부를 판단
	public boolean isAccessTokenValid(String token) {
		try {
			// 엑세스 토큰 파싱
			Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
					.parseClaimsJws(token).getBody();

			// 토큰의 만료 일시 가져오기
			Date expiration = claims.getExpiration();

			// 현재 시간과 비교하여 만료 여부 판단
			Date now = new Date();
			System.out.println("만료시간 여부 : " + expiration != null);
			System.out.println("만료 여부 : " + !expiration.before(now));
			return expiration != null && !expiration.before(now);
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			return false; // 토큰 파싱 오류 또는 유효하지 않은 토큰인 경우
		}
	}

	// 리프레시 토큰 유효성 검증 및 만료 확인
	public boolean isRefreshTokenValid(String token) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
					.parseClaimsJws(token);
			Date expiration = claims.getBody().getExpiration();

			return expiration.after(new Date()); // 리프레시 토큰이 만료되었는지 확인
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

//	public String refreshAccessToken(String refreshToken) {
//		if (isRefreshTokenValid(refreshToken)) {
//			Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
//					.parseClaimsJws(refreshToken).getBody();
//
//			// Refresh 토큰의 클레임 정보로 사용자 객체 생성
//			UserDto user = new UserDto();
//			user.setId(claims.get("id", String.class));
//			// 다른 필요한 클레임 정보를 설정
//
//			// 새로운 Access 토큰 생성
//			String newAccessToken = createAccessToken(user);
//
//			return newAccessToken;
//		} else {
//			// Refresh 토큰이 유효하지 않을 경우 InvalidTokenException을 사용하여 예외 처리
//			throw new RuntimeException("Invalid Refresh Token");
//		}
//	}
}
