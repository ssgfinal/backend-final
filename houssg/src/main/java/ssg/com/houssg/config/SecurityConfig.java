package ssg.com.houssg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;
import ssg.com.houssg.security.TokenFilter;


@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig {
	
	@Autowired
	 private  TokenFilter tokenFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable()
				//.cors(Customizer.withDefaults())
				//.cors().disable()
				.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests()
				.requestMatchers("/","/swagger-ui/**", "/v3/api-docs/**","/user/**","/message/**").permitAll()
				.requestMatchers("/room/get/**","/review/get/all/**","/search/**","/get/all/accom/**","/get/accom/**","/healthcheck/**").permitAll()
				.anyRequest().authenticated(); // 이외 모든 요청은 인증필요
				
		return http.build();
	}
}
