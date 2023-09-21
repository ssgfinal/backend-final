package ssg.com.houssg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	
	 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable()
				.addFilterBefore(new TokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests()
				.requestMatchers("/swagger-ui.html", "/v3/api-docs", "/swagger-resources/**", "/webjars/**").permitAll()
				.requestMatchers(new AntPathRequestMatcher ("/user/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
				.anyRequest().authenticated(); // 이외 모든 요청은 인증필요
				
		return http.build();
	}
}
