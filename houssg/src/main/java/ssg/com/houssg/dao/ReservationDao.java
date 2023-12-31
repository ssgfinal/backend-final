package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.AccomListDto;
import ssg.com.houssg.dto.AccomReservationListDto;
import ssg.com.houssg.dto.OffLineReservationDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.ReservationForLmsDto;
import ssg.com.houssg.dto.ReservationHistoryDto;
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
	
	// 유저포인트 가져옴
	int getUserPoints(String Id);
	
	// 객실 번호로 숙소 번호 조회
	int getAccomNumberByRoomNumber(int roomNumber);

	// 예약 등록(생성)
	void enrollReservation(ReservationDto reservationDto);
	
	// 결제완료 >> 예약완료
	void paymentCheck(int reservationNumber);

	// 유저 - 예약 취소
	void cancelReservationByUser(int reservationNumber);

	// 이용 완료
	void usedCheck();
	
	// 예약 삭제
    int deleteReservation(int reservationNumber);
	
	// 포인트 적립
	void paymentRewards(UserDto userDto);
	
	// 사용한 쿠폰 is_used 변경
	void usedCoupon(String couponNumber);

	// 사용한 포인트 차감
	void usedPoint(String Id, int usePoint);

	// 유저 -ID로 예약내역정보 조회
	List<ReservationHistoryDto> findRerservationById(String Id);
	
	// "lastCheck" 쿼리를 실행하고 결과를 반환
    List<ReservationRoomDto> lastCheck(ReservationDto reservationDto);
    
    // "lastCheck" 쿼리를 실행하고 결과를 반환
    List<ReservationRoomDto> lastCheckForOffLine(OffLineReservationDto offLineReservationDto);
    
    // "ownerModalCheck" 쿼리를 실행하고 결과를 반환
    List<ReservationRoomDto> ownerModalCheck(int roomNumber, String startDate);
    
    // 사업자 ID로 가지고있는 숙소번호, 이름 가져옴
    List<AccomListDto> getAccommodationByOwnerId(String Id);
    
    // 숙소번호, 날짜로 예약상태가 1(예약완료)인 예약정보 가져옴
    List<AccomReservationListDto> getHistoryForOwner(int accomNumber, String yearMonth);
    
    // 숙소번호로 객실정보 조회
    List<RoomDto> getRoomInfoByAccommodationNumber(int accomNumber);
    
    // 0. 사업자 - 예약번호로 취소에 필요한 정보 조회
    ReservationDto getReservationDetails(int reservationNumber);
    
    // 1. 사업자 - 예약취소
    void cancelReservationByOwner(int reservationNumber);
    
    // 2. 예약취소 - 포인트 반환
    void returnUsePoint(String Id, int usePoint);
    
    // 3. 예약취소 - 쿠폰 반환
    void returnUseCoupon(String couponNumber);
    
    // 4. 사업자 - 예약취소 - 취소 포인트 리워드 지급
    void pointRewardsForCancel(int reservationNumber, int paymentAmount);
    
    // 예약등록(status 0 - 예약중) 후 20분 내로 결제되지않은 예약 내역 삭제
    void deleteUnpaidReservation();
    
    // 사업자 - 오프라인 예약 추가
    void offLineEnrollByOwner(ReservationDto reservationDto);
    
    // 예약번호로 게스트 전화번호 조회
    ReservationForLmsDto getReservationInfoForGuest(int reservationNumber);
    
    // 방문 하루 전 예약정보 조회
    ReservationForLmsDto getOndDayAgoReservation();
    
    // 리뷰 작성 시 review_status = 1
    int statusUpdate(int reservationNumber);
}

