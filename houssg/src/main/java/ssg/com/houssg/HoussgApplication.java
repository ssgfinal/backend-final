package ssg.com.houssg;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("ssg.com.houssg")
@EnableScheduling
public class HoussgApplication {


	public static void main(String[] args) {
		SpringApplication.run(HoussgApplication.class, args);
		
	}

}
