package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.ReservationDao;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.CouponDto;
import ssg.com.houssg.dto.ReservationInfoDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.ReservationRoomDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.UserCouponDto;
import ssg.com.houssg.dto.UserDto;

@Service
@Transactional
public class ReservationService {

	@Autowired
	private ReservationDao dao;

	// 숙소 정보 조회
	public List<AccommodationDto> getAccommodationInfo(int accomNumber) {
		return dao.getAccommodationInfo(accomNumber);
	}

	// 객실 정보 조회
	public List<RoomDto> getRoomInfo(int roomNumber) {
		return dao.getRoomInfo(roomNumber);
	}

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

	// 보유 포인트 조회
	public int getUserPoints(String Id) {
		Integer points = dao.getUserPoints(Id);
		return points != null ? points : 0;
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

		// 보유 포인트 조회 (사용자 ID를 파라미터로 전달)
		int userPoints = getUserPoints(userId);
		basicInfo.setUserPoint(userPoints);

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

	public void enrollReservation(ReservationDto reservationDto) {
		dao.enrollReservation(reservationDto);
	}

	@Transactional
	public void usedCoupon(String couponNumber) {
		dao.usedCoupon(couponNumber);
		System.out.println("쿠폰 사용 여부 체크함");
	}

	@Transactional
	public void usedPoint(String Id, int usePoint) {
		dao.usedPoint(Id, usePoint);
		System.out.println(usePoint + "포인트 차감함");
	}

	@Transactional
	public void accumulatePoints(UserDto userDto, double paymentAmount) {
		dao.accumulatePoints(userDto);
	}

	// 유저 - Id로 예약내역 조회
	public List<ReservationDto> findRerservationById(String Id) {
		return dao.findRerservationById(Id);
	}

}
