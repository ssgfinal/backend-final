package ssg.com.houssg.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import ssg.com.houssg.dto.UserDto;

import org.springframework.beans.factory.annotation.Value;
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

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        return header;
    }

    private Map<String, Object> createClaims(UserDto user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("nickname", user.getNickname());
        claims.put("user_phone_number", user.getPhonenumber());
        claims.put("auth", user.getAuth());
        return claims;
    }

    public String createAccessToken(UserDto user) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiration = Date.from(now.plus(accessTokenValidityInMilliseconds, ChronoUnit.MILLIS));

        Map<String, Object> header = createHeader();
        Map<String, Object> claims = createClaims(user);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setSubject(user.getId())
                .setIssuer(issuer) // issuer 정보 설정
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(UserDto user) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiration = Date.from(now.plus(refreshTokenValidityInMilliseconds, ChronoUnit.MILLIS));

        Map<String, Object> header = createHeader();
        Map<String, Object> claims = createClaims(user);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setSubject(user.getId())
                .setIssuer(issuer) // issuer 정보 설정
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    
    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // InvalidTokenException을 사용하여 예외 처리
            throw new RuntimeException("효력이 없는 토큰입니다.");
        }
    }

    public String refreshAccessToken(String refreshToken) {
        if (validateToken(refreshToken)) {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
            
            // Refresh 토큰의 클레임 정보로 사용자 객체 생성
            UserDto user = new UserDto();
            user.setId(claims.get("id", String.class));
            // 다른 필요한 클레임 정보를 설정
            
            // 새로운 Access 토큰 생성
            String newAccessToken = createAccessToken(user);

            return newAccessToken;
        } else {
            // Refresh 토큰이 유효하지 않을 경우 InvalidTokenException을 사용하여 예외 처리
            throw new RuntimeException("Invalid Refresh Token");
        }
    }
}
