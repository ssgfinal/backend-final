package ssg.com.houssg;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 접속허가 설정
@Configuration
public class WebConfigurer implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
//		registry.addMapping("/**").allowedOrigins("*");
//		registry.addMapping("/**").allowedHeaders("*");
		
		 registry.addMapping("/**")
         .allowedOrigins("*") // 모든 출처 허용
         .allowedHeaders("*") // 모든 헤더 허용
         .exposedHeaders("Access-Control-Allow-Origin"); // 노출할 헤더 추가
         
	
		
		
	}
}

