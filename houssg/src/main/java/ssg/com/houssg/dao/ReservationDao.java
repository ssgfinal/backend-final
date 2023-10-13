package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.ReservationRoomDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.UserCouponDto;
import ssg.com.houssg.dto.UserDto;

@Mapper
@Repository
public interface ReservationDao {

	// 숙소 정보 조회
	List<AccommodationDto> getAccommodationInfo(int accomNumber);

	// 객실 정보 조회
	List<RoomDto> getRoomInfo(int roomNumber);

	// 쿠폰 정보 조회
	List<UserCouponDto> getCouponInfo(String Id);

	// room_number를 받아 객실 별 예약 현황 불러옴
	List<ReservationRoomDto> getReservationStatus(int roomNumber);
	
	// 연도+월 받아와 해당 연,월에 해당하는 객실 별 예약 현황 불러옴
	List<ReservationRoomDto> getReservationStatusForYearMonth(int roomNumber, String yearMonth);

	// 객실 번호로 숙소 번호 조회
	int getAccomNumberByRoomNumber(int roomNumber);

	// 예약 등록(생성)
	void enrollReservation(ReservationDto reservationDto);

	// 예약 취소
	void cancelReservation();

	// 이용 완료
	void completeUsage();

	// 사용한 쿠폰 is_used 변경
	void usedCoupon(String couponNumber);

	// 사용한 포인트 차감
	void usedPoint(String Id, int usePoint);

	// 포인트 적립
	void accumulatePoints(UserDto userDto);
	
	// 유저 -ID로 예약내역정보 조회
	List<ReservationDto> findRerservationById(String Id);
	
	// "lastCheck" 쿼리를 실행하고 결과를 반환
    List<ReservationRoomDto> lastCheck(ReservationDto reservationDto);
}

