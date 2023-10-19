package ssg.com.houssg.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.ReservationDao;
import ssg.com.houssg.dto.AccomListDto;
import ssg.com.houssg.dto.AccomReservationListDto;
import ssg.com.houssg.dto.OffLineReservationDto;
import ssg.com.houssg.dto.ReservationInfoDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.ReservationForLmsDto;
import ssg.com.houssg.dto.ReservationHistoryDto;
import ssg.com.houssg.dto.ReservationRoomDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.UserCouponDto;

@Service
@Transactional
public class ReservationService {

	@Autowired
	private ReservationDao dao;

	// 쿠폰 정보 조회
	public List<UserCouponDto> getCouponInfo(String Id) {
		return dao.getCouponInfo(Id);
	}

	// 객실별 예약현황 조회
	public List<ReservationRoomDto> getReservationStatus(int roomNumber) {
		return dao.getReservationStatus(roomNumber);
	}

	// 연월 받아서 객실별 예약현황 조회
	public List<ReservationRoomDto> getReservationStatusForYearMonth(int roomNumber, String yearMonth) {
		return dao.getReservationStatusForYearMonth(roomNumber, yearMonth);
	}

	// 객실 번호로 숙소 번호 조회
	public int getAccomNumberByRoomNumber(int roomNumber) {
		return dao.getAccomNumberByRoomNumber(roomNumber);
	}

	// 예약 페이지 기본 정보 조회
	public ReservationInfoDto getReservationBasicInfo(int roomNumber, String userId) {
		ReservationInfoDto basicInfo = new ReservationInfoDto();

		// 예약가능한 객실 정보
		List<ReservationRoomDto> bookableRoomList = getReservationStatus(roomNumber);
		basicInfo.setBookableRoomList(bookableRoomList);

		// 쿠폰 정보 설정
		List<UserCouponDto> couponList = getCouponInfo(userId);
		basicInfo.setCouponList(couponList);

		return basicInfo;
	}

	// 예약 페이지 - 월별 예약가능한 객실 정보 조회
	public ReservationInfoDto getAvailableRoom(int roomNumber, String yearMonth) {
		ReservationInfoDto Info = new ReservationInfoDto();

		// 예약가능한 객실 정보
		List<ReservationRoomDto> bookableRoomList = getReservationStatusForYearMonth(roomNumber, yearMonth);
		Info.setBookableRoomList(bookableRoomList);

		return Info;
	}

	// 예약등록
	public void enrollReservation(ReservationDto reservationDto) {
		dao.enrollReservation(reservationDto);
	}

	// 결제완료 >> 예약완료
	public void paymentCheck(int reservationNumber) {
		dao.paymentCheck(reservationNumber);
	}

	// 예약취소
	public void cancelReservationByUser(int reservationNumber) {
		dao.cancelReservationByOwner(reservationNumber);
	}

	// 쿠폰사용 여부 체크
	@Transactional
	public void usedCoupon(String couponNumber) {
		dao.usedCoupon(couponNumber);
		System.out.println("쿠폰 사용 여부 체크함");
	}

	// 사용한 포인트 차감
	@Transactional
	public void usedPoint(String Id, int usePoint) {
		dao.usedPoint(Id, usePoint);
		System.out.println(usePoint + "포인트 차감함");
	}

	// 유저 - Id로 예약내역 조회
	public List<ReservationHistoryDto> findRerservationById(String Id) {
		return dao.findRerservationById(Id);
	}

	// 예약 가능 여부 확인
	public boolean isReservationAvailable(ReservationDto reservationDto) {
		List<ReservationRoomDto> availableRooms = dao.lastCheck(reservationDto);
		System.out.println(availableRooms);

		if (availableRooms.stream().anyMatch(reservedRoom -> reservedRoom.getAvailableRooms() == 0)) {
			return false; // 하나라도 0인 경우 false 반환
		}

		return true; // 모든 방이 예약 가능하면 true 반환
	}

	// 예약 가능 여부 확인 for offline
	public boolean isReservationAvailableForOffLine(OffLineReservationDto offLineReservationDto) {
		List<ReservationRoomDto> availableRooms = dao.lastCheckForOffLine(offLineReservationDto);
		System.out.println(availableRooms);

		if (availableRooms.stream().anyMatch(reservedRoom -> reservedRoom.getAvailableRooms() == 0)) {
			return false; // 하나라도 0인 경우 false 반환
		}

		return true; // 모든 방이 예약 가능하면 true 반환
	}

	// 사업자 ID로 가지고있는 숙소번호, 이름 가져옴
	public List<AccomListDto> getAccommodationByOwnerId(String Id) {
		return dao.getAccommodationByOwnerId(Id);
	}

	// 숙소번호, 날짜로 예약상태가 1(예약완료)인 예약정보 가져옴
	public List<AccomReservationListDto> getHistoryForOwner(int accomNumber, String yearMonth) {
		return dao.getHistoryForOwner(accomNumber, yearMonth);
	}

	// 숙소번호로 객실 정보 조회
	public List<RoomDto> getRoomInfoByAccommodationNumber(int accomNumber) {
		return dao.getRoomInfoByAccommodationNumber(accomNumber);
	}

	// 0. 사업자 - 예약번호로 취소에 필요한 정보 조회
	public ReservationDto getReservationDetails(int reservationNumber) {
		return dao.getReservationDetails(reservationNumber);
	}

	// 1. 사업자 - 예약취소
	public void cancelReservationByOwner(int reservationNumber) {
		dao.cancelReservationByOwner(reservationNumber);
	}

	// 2. 예약취소 - 포인트 반환
	public void returnUsePoint(String Id, int usePoint) {
		dao.returnUsePoint(Id, usePoint);
	}

	// 3. 예약취소 - 쿠폰 반환
	public void returnUseCoupon(String couponNumber) {
		dao.returnUseCoupon(couponNumber);
	}

	// 4. 사업자 - 예약취소 - 취소리워드 계산
	public void pointRewardsForCancel(int reservationNumber, int paymentAmount) {
		dao.pointRewardsForCancel(reservationNumber, paymentAmount);
	}

	// 사업자 - 오프라인 예약추가
	public void offLineEnrollByOwner(ReservationDto reservationDto) {
		dao.offLineEnrollByOwner(reservationDto);
	}

	// 예약 삭제
	public boolean deleteReservation(int reservationNumber) {
		int rowsAffected = dao.deleteReservation(reservationNumber);
		return rowsAffected > 0;
	}

	// 예약번호로 예약정보 조회
	public ReservationForLmsDto getReservationInfoForGuest(int reservationNumber) {
		return dao.getReservationInfoForGuest(reservationNumber);
	}
	
	
	// 수수료 계산
	public int calculateCancellationFee(int reservationNumber, LocalDate startDate, int paymentAmount) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(currentDate, startDate);
        int daysUntilCheckIn = period.getDays();

        if (daysUntilCheckIn >= 7) {
            return 0; // 일주일 전
        } else if (daysUntilCheckIn >= 5) {
            return (int) (paymentAmount * 0.3); // 5, 6일 전
        } else if (daysUntilCheckIn >= 2) {
            return (int) (paymentAmount * 0.5); // 2, 3, 4일 전
        } else {
        	return -1; // 하루 전, 당일 (취소불가)
        }
    }

}
