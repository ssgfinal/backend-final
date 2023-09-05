package ssg.com.houssg.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.dynamic.annotation.Value;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        ClientOptions clientOptions = ClientOptions.builder()
//                .socketOptions(SocketOptions.builder().keepAlive(true).build())
//                .build();
//
//        return new LettuceConnectionFactory("localhost", 6379, clientOptions);
//    }
//    

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }
}
