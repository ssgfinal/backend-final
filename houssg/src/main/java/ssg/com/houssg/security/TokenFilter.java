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
import java.util.ArrayList;
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

		final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshTokenHeader = request.getHeader("RefreshToken");
		System.out.println("어세스 토큰 : " + authorization);
		System.out.println("리프레시 토큰: " + refreshTokenHeader);

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			if (refreshTokenHeader == null || !refreshTokenHeader.startsWith("Bearer ")) {
				System.out.println("authorization 및 refreshToken 토큰이 없거나 형식이 잘못됨");
				filterChain.doFilter(request, response);
				return;
			}
		}

		// HTTP 요청 헤더에서 엑세스 토큰 추출
		String accessToken = extractAccessTokenFromRequest(request);

		// 어세스
		if (accessToken != null) {
			if(!(tokenProvider.isAccessTokenValid(accessToken))) {
				// 어세스 토큰이 만료됐을 경우
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			
			System.out.println("어세스 토큰 추출 완료");
			String userId = tokenProvider.getUserIdFromToken(accessToken);
			performAuthorization(userId, "access");
			System.out.println(userId);

			System.out.println("어세스로 인증 완료");
			filterChain.doFilter(request, response); // 유효한 액세스 토큰이면 요청을 그대로 전달
		} 

		else if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
			// 액세스 토큰이 만료되었거나 유효하지 않으면 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
			String refreshToken = extractRefreshTokenFromRequest(request);
			System.out.println("리프레시 토큰 추출 완료");

			if (refreshToken != null && tokenProvider.isRefreshTokenValid(refreshToken)) {
				// 리프레시 토큰에서 아이디 추출
				String userId = tokenProvider.getUserIdFromToken(refreshToken);

				if (tokenService.isRefreshTokenValidForAccessToken(userId, refreshToken)) {
					System.out.println("리프레시 토큰이 유효함");

					// 권한 부여
					performAuthorization(userId, "refresh");
					UserDto user = new UserDto();
					user.setId(userId);
					String newAccessToken = tokenProvider.createAccessToken(user);
					String newRefreshToken = tokenProvider.createRefreshToken(user);

					// 새로운 액세스 토큰과 리프레시 토큰을 응답 헤더에 설정
					response.setHeader("Authorization", "Bearer " + newAccessToken);
					response.setHeader("RefreshToken", "Bearer " + newRefreshToken);

					// 기존 리프레시 토큰 삭제 및 새로운 리프레시 토큰 저장
					tokenService.removeRefreshToken(userId);
					tokenService.storeRefreshToken(userId, newRefreshToken);
					System.out.println("새로운 어세스 : " + newAccessToken);
					System.out.println("새로운 리프레시 : " + newRefreshToken);
					System.out.println("어세스 만료 >> 리프레시로 재발급 완료");
					filterChain.doFilter(request, response);
				} else {
					// 리프레시 토큰도 만료되었거나 유효하지 않으면 인증 실패 응답을 반환
					System.out.println("리프레시도 만료 or 유효x");
					response.getWriter().write("Relogin");
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}

		}
	}

	private void performAuthorization(String userId, String tokenType) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		// accessToken인 경우
		if ("access".equals(tokenType)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		// refreshToken인 경우
		else if ("refresh".equals(tokenType)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_REFRESH_TOKEN"));
		}

		// 권한 설정
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null,
				authorities);
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
		String refreshTokenHeader = request.getHeader("RefreshToken");

		if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
			return refreshTokenHeader.substring(7); // "Bearer " 부분을 제외한 토큰 추출
		}

		return null;
	}
}
