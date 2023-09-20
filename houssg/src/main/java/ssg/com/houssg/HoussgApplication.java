package ssg.com.houssg;

import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import ssg.com.houssg.security.TokenFilter;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("ssg.com.houssg")
public class HoussgApplication {

//	@Bean
//	public FilterRegistrationBean<TokenFilter> jwttokenFilter() {
//		FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
//		registrationBean.setFilter(new TokenFilter());
//		registrationBean.addUrlPatterns("/user/*");
//
//		// 로그인, 회원가입, 아이디 찾기, 비밀번호 찾기 엔드포인트를 제외
//	   
//        registrationBean.setUrlPatterns(Arrays.asList("/user/login")); // 필터에서 제외할 엔드포인트 패턴
////	    registrationBean.addUrlPatterns("/signup");
////	    registrationBean.addUrlPatterns("/findid");
////	    registrationBean.addUrlPatterns("/findpw");
//        
//		return registrationBean;
//	}

	public static void main(String[] args) {
		SpringApplication.run(HoussgApplication.class, args);
	}

}
