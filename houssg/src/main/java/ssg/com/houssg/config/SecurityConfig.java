package ssg.com.houssg.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

				.requestMatchers("/","/swagger-ui/**", "/v3/api-docs/**","/user/**","/sms/**").permitAll()
				.requestMatchers("/room/detail/**","/review/all/accom/**","/search/**","/accom/all/**","/accom/detail/**","/accom/score/**","/accom/20/**","/healthcheck/**").permitAll()
				.anyRequest().authenticated(); // 이외 모든 요청은 인증필요
				
		http.cors(Customizer.withDefaults());
				
		return http.build();
		
		
	}
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));  
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("RefreshToken");
        configuration.addExposedHeader("Total-Count");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
