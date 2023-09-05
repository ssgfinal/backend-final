package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 블랙리스트에 토큰 추가
    public void blacklistToken(String token) {
        // Redis에 토큰 추가 (토큰 값은 여기서는 "BLACKLISTED"로 설정)
        redisTemplate.opsForValue().set(token, "BLACKLISTED");
        // 토큰의 만료 시간 설정 (예: 24시간)
        redisTemplate.expire(token, 24, TimeUnit.HOURS);
    }

    // 토큰이 블랙리스트에 있는지 확인
    public boolean isTokenBlacklisted(String token) {
        // Redis에서 토큰 조회
        String value = redisTemplate.opsForValue().get(token);
        // 토큰이 블랙리스트에 있는 경우 true 반환, 그렇지 않으면 false 반환
        return value != null && value.equals("BLACKLISTED");
    }
}
