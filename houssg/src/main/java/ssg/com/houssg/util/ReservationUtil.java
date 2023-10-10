package ssg.com.houssg.util;

import java.util.Random;

public class ReservationUtil {
	public static int generateRandomReservationNumber() {
		 Random random = new Random(System.currentTimeMillis());
	        int reservationNumber = 0;

	        for (int i = 0; i < 9; i++) {
	            // 0부터 9까지의 난수를 생성하여 reservationNumber에 추가
	            int digit = random.nextInt(10);
	            reservationNumber = reservationNumber * 10 + digit;
	        }

        return Math.abs(reservationNumber);
    }
}
