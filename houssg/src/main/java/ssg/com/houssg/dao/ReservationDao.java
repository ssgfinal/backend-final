package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.AccomListDto;
import ssg.com.houssg.dto.AccomReservationListDto;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.ReservationRoomDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.UserCouponDto;
import ssg.com.houssg.dto.UserDto;

@Mapper
@Repository
public interface ReservationDao {

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

	// 유저 - 예약 취소
	void cancelReservation();

	// 이용 완료
	void usedCheck();
	
	// 포인트 적립
	void paymentRewards(UserDto userDto);
	
	// 사용한 쿠폰 is_used 변경
	void usedCoupon(String couponNumber);

	// 사용한 포인트 차감
	void usedPoint(String Id, int usePoint);

	// 유저 -ID로 예약내역정보 조회
	List<ReservationDto> findRerservationById(String Id);
	
	// "lastCheck" 쿼리를 실행하고 결과를 반환
    List<ReservationRoomDto> lastCheck(ReservationDto reservationDto);
    
    // 사업자 ID로 가지고있는 숙소번호, 이름 가져옴
    List<AccomListDto> getAccommodationByOwnerId(String Id);
    
    // 숙소번호, 날짜로 예약상태가 2(예약완료)인 예약정보 가져옴
    List<AccomReservationListDto> getHistoryForOwner(int accomNumber, String yearMonth);
    
    // 숙소번호로 객실정보 조회
    List<RoomDto> getRoomInfoByAccommodationNumber(int accomNumber);
    
    // 0. 사업자 - 예약번호로 취소에 필요한 정보 조회
    ReservationDto getReservationDetails(int reservationNumber);
    
    // 1. 사업자 - 예약취소
    void cancelReservationByOwner(int reservationNumber);
    
    // 2. 사업자 - 예약취소 - 포인트 반환
    void returnUsePoint(String Id, int usePoint);
    
    // 3. 사업자 - 예약취소 - 쿠폰 반환
    void returnUseCoupon(String couponNumber);
    
    // 4. 사업자 - 예약취소 - 취소 포인트 리워드 지급
    void pointRewardsForCancel(int reservationNumber, int paymentAmount);
}

