//package ssg.com.houssg.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//	
//	private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/login", "/addmember"};
//    
//	@Autowired
//    private TokenProvider tokenProvider;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .authorizeHttpRequests(requests ->
//                        requests.requestMatchers("/", "/swagger-ui/**", "/v3/**").permitAll()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
//                            //    .requestMatchers(PathRequest.toH2Console()).permitAll()	// H2 콘솔 접속은 모두에게 허용
//                                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )	// 세션을 사용하지 않으므로 STATELESS 설정
//                .build();
//    }
//}



