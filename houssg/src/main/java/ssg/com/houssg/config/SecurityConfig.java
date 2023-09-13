package ssg.com.houssg.config;

//WebSecurityConfig.java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;


@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig {
	@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.anyRequest().permitAll();
			return http.build();
		}
}
