//package ssg.com.houssg.config;
//
////WebSecurityConfig.java
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import ssg.com.houssg.security.JwtTokenProvider;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig{
//
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests(auth -> auth.anyRequest().authenticated())
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // JWT 토큰 검증 필터 추가
//        http.addFilterBefore(new JwtAuthenticationFilter(JwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//
//        // CORS 설정 및 기타 보안 설정
//
//        return http.build();
//    }
//}
