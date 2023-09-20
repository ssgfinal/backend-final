package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ssg.com.houssg.dto.UserDto;

import java.util.concurrent.TimeUnit;

@Service
public class TokenSaveService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TokenSaveService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    // 리프레시 토큰을 Redis에 저장
    public void storeRefreshToken(String refreshToken, UserDto user) {
        // Redis에 리프레시 토큰 저장
        redisTemplate.opsForValue().set(refreshToken, user.getId());
        // 리프레시 토큰의 만료 시간 설정 (예: 30일)
        redisTemplate.expire(refreshToken, 30, TimeUnit.DAYS);
    }

    // 리프레시 토큰의 유효성 검사
    public boolean isRefreshTokenValid(String refreshToken) {
        return redisTemplate.hasKey(refreshToken);
    }

    // 리프레시 토큰 제거
    public void removeRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}
