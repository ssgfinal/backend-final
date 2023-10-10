package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.UserCouponDto;


@Mapper
@Repository
public interface ReservationDao {
	
	// 숙소 정보 조회
    List<AccommodationDto> getAccommodationInfo(int accomNumber);

    // 객실 정보 조회
    List<RoomDto> getRoomInfo(int roomNumber);

    // 쿠폰 정보 조회
    List<UserCouponDto> getCouponInfo(String Id);

    // 보유 포인트 조회
    int getUserPoints(String Id);
    
    // 객실 번호로 숙소 번호 조회
    int getAccomNumberByRoomNumber(int roomNumber);
	
	// 예약 등록(생성)
	void enrollReservation(ReservationDto reservationDto);
	
	// 예약 취소
	void cancelReservation();
	
	// 이용 완료
	void completeUsage();

}