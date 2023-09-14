package ssg.com.houssg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ssg.com.houssg.dto.UserDto;

import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// HTTP 요청 헤더에서 엑세스 토큰 추출
		String accessToken = extractAccessTokenFromRequest(request);

		if (accessToken != null && tokenProvider.isAccessTokenValid(accessToken)) {
			filterChain.doFilter(request, response); // 유효한 액세스 토큰이면 요청을 그대로 전달
		} else {
			// 액세스 토큰이 만료되었거나 유효하지 않으면 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
			String refreshToken = extractRefreshTokenFromRequest(request);

			if (refreshToken != null && tokenProvider.isRefreshTokenValid(refreshToken)) {
				// 리프레시 토큰에서 아이디 추출
				String userId = tokenProvider.getUserIdFromToken(refreshToken);
				UserDto user = new UserDto();
				user.setId(userId);
				String newAccessToken = tokenProvider.createAccessToken(user);

				// 새로운 액세스 토큰을 응답 헤더에 설정
				response.setHeader("Authorization", "Bearer " + newAccessToken);

				filterChain.doFilter(request, response);
			} else {
				// 리프레시 토큰도 만료되었거나 유효하지 않으면 인증 실패 응답을 반환
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

	private String extractAccessTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // "Bearer " 부분을 제외한 토큰 추출
		}

		return null;
	}

	private String extractRefreshTokenFromRequest(HttpServletRequest request) {
		Cookie refreshTokenCookie = WebUtils.getCookie(request, "refreshToken");

		if (refreshTokenCookie != null) {
			return refreshTokenCookie.getValue();
		}

		return null;
	}
}
