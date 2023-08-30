package ssg.com.houssg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ssg.com.houssg.dao")
public class HoussgApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoussgApplication.class, args);
	}

}
