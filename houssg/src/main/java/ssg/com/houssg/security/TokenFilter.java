package ssg.com.houssg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ssg.com.houssg.dto.UserDto;
import ssg.com.houssg.service.TokenSaveService;

import java.io.IOException;
import java.util.List;

@Component
public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private TokenSaveService tokenService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	    System.out.println("1. 필터 시작");
	    
	    final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println("Authorization : " + authorization);
		
		// accessToken 안보내거나 형식 안맞으면 커트
		if(authorization == null || !authorization.startsWith("Bearer ")) {
			System.out.println("authorization이 없거나 잘못보냄");
			filterChain.doFilter(request, response);
			return;
		}

		// HTTP 요청 헤더에서 엑세스 토큰 추출
		String accessToken = extractAccessTokenFromRequest(request);
		System.out.println(accessToken + "어세스 토큰 테스트");
		// 여기까지는 잘 됨
		
		// 근데 if문에서 걸러짐
		if (accessToken != null && tokenProvider.isAccessTokenValid(accessToken)) {
			
			System.out.println("어세스 토큰 추출 완료");
			
			// 권한 부여 --- 추가한거 !!
			String userId = tokenProvider.getUserIdFromToken(accessToken);
	        performAuthorization(userId);
	        System.out.println(userId);
//			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,
//					null, List.of(new SimpleGrantedAuthority("직접 정하기")));
//			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	        System.out.println("어세스로 인증 완료");
	       
			filterChain.doFilter(request, response); // 유효한 액세스 토큰이면 요청을 그대로 전달
		} 
		else {
			// Refresh를 응답 >> 클라에서 리프레시 토큰 전송
	        response.getWriter().write("Refresh");
			// 액세스 토큰이 만료되었거나 유효하지 않으면 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
			String refreshToken = extractRefreshTokenFromRequest(request);
			System.out.println("어세스 안돼서 리프레시 뽑아냄");
			
			if (refreshToken != null && tokenProvider.isRefreshTokenValid(refreshToken)) {
				System.out.println("어세스 만료 or 유효x라 리프레시로 재발급하는중");

				// 리프레시 토큰에서 아이디 추출
				String userId = tokenProvider.getUserIdFromToken(refreshToken);
				UserDto user = new UserDto();
				user.setId(userId);
				String newAccessToken = tokenProvider.createAccessToken(user);
				String newRefreshToken = tokenProvider.createRefreshToken(user);

				// 새로운 액세스 토큰과 리프레시 토큰을 응답 헤더에 설정
				response.setHeader("Authorization", "Bearer " + newAccessToken);
				response.setHeader("Refresh-Token", newRefreshToken);
				
				// 기존 리프레시 토큰 삭제 및 새로운 리프레시 토큰 저장
				// TokenSaveService tokenService = new TokenSaveService(redisTemplate);
				tokenService.removeRefreshToken(refreshToken);
				tokenService.storeRefreshToken(newRefreshToken, user);
				System.out.println("어세스 만료 >> 리프레시로 재발급 완료");
				filterChain.doFilter(request, response);
			} 
			else {
				// 리프레시 토큰도 만료되었거나 유효하지 않으면 인증 실패 응답을 반환
				System.out.println("리프레시도 만료 or 유효x");
			    response.getWriter().write("Relogin");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}

	}
	
	private void performAuthorization(String userId) {
	    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,
	            null, List.of(new SimpleGrantedAuthority("권한")));
	    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	private String extractAccessTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // "Bearer " 부분을 제외한 토큰 추출
		}

		return null;
	}

	private String extractRefreshTokenFromRequest(HttpServletRequest request) {
		String refreshTokenHeader = request.getHeader("Refresh-Token");

		if (refreshTokenHeader != null) {
			return refreshTokenHeader;
		}

		return null;
	}
}
