package ssg.com.houssg.util;

import java.util.Random;

public class CouponUtil {
    public static String generateRandomCouponNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder couponNumber = new StringBuilder();
        Random random = new Random(System.currentTimeMillis()); // 현재 시간을 시드로 사용

        for (int i = 0; i < 16; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            couponNumber.append(randomChar);
        }

        return couponNumber.toString();
    }
}
