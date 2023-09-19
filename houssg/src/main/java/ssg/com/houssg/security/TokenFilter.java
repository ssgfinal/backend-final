package ssg.com.houssg.security;

import org.springframework.beans.factory.annotation.Autowired;
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
		// HTTP 요청 헤더에서 엑세스 토큰 추출
		String accessToken = extractAccessTokenFromRequest(request);

		if (accessToken != null && tokenProvider.isAccessTokenValid(accessToken)) {

			// 권한 부여 --- 추가한거 !!

//			String userId = tokenProvider.getUserIdFromToken(accessToken);
//
//			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,
//					null, List.of(new SimpleGrantedAuthority("직접 정하기")));
//			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

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
				String newRefreshToken = tokenProvider.createRefreshToken(user);

				// 새로운 액세스 토큰과 리프레시 토큰을 응답 헤더에 설정
				response.setHeader("Authorization", "Bearer " + newAccessToken);
				response.setHeader("Refresh-Token", newRefreshToken);

				// 기존 리프레시 토큰 삭제 및 새로운 리프레시 토큰 저장
				// TokenSaveService tokenService = new TokenSaveService(redisTemplate);
				tokenService.removeRefreshToken(refreshToken);
				tokenService.storeRefreshToken(newRefreshToken, user);

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
		String refreshTokenHeader = request.getHeader("Refresh-Token");

		if (refreshTokenHeader != null) {
			return refreshTokenHeader;
		}

		return null;
	}
}
