package ssg.com.houssg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Service
public class TokenSaveService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TokenSaveService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    

    // 리프레시 토큰을 Redis에 저장
    public void storeRefreshToken(String userId, String refreshToken) {
        // Redis에 리프레시 토큰 저장
        redisTemplate.opsForValue().set(userId,refreshToken);
        // 리프레시 토큰의 만료 시간 설정 (7일)
        redisTemplate.expire(refreshToken, 7, TimeUnit.DAYS);
    }
    
    
    public boolean isRefreshTokenValidForAccessToken(String userId, String refreshToken) {
        // Redis에서 어세스 토큰을 사용하여 리프레시 토큰을 검색
        String storedRefreshToken = redisTemplate.opsForValue().get(userId);
        
        // 리프레시 토큰을 찾았고, 주어진 리프레시 토큰과 일치하는지 확인
        if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
            // 리프레시 토큰의 유효성을 확인
            return true;
        }
        
        // 어세스 토큰과 연결된 리프레시 토큰을 찾을 수 없거나, 일치하지 않으면 유효하지 않음
        return false;
    }

    
    // 리프레시 토큰 제거
    public void removeRefreshToken(String userId) {
        redisTemplate.delete(userId);
    }
}
