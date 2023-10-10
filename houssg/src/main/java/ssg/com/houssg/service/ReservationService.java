package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.ReservationDao;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.ReservationBasicInfoDto;
import ssg.com.houssg.dto.ReservationDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.UserCouponDto;

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
	public ReservationBasicInfoDto getReservationBasicInfo(int accomNumber, int roomNumber, String userId) {
		ReservationBasicInfoDto basicInfo = new ReservationBasicInfoDto();

		// 숙소 정보 설정
		List<AccommodationDto> accommodationInfoList = getAccommodationInfo(accomNumber);
		basicInfo.setAccommodationInfoList(accommodationInfoList);

		// 객실 정보 설정
		List<RoomDto> roomInfoList = getRoomInfo(roomNumber);
		basicInfo.setRoomInfoList(roomInfoList);

		// 쿠폰 정보 설정
		List<UserCouponDto> couponInfoList = getCouponInfo(userId);
		basicInfo.setCouponInfoList(couponInfoList);

		// 보유 포인트 조회 (사용자 ID를 파라미터로 전달)
		int userPoints = getUserPoints(userId);
		basicInfo.setUserPoints(userPoints);

		// 유저 id 설정
		basicInfo.setUserId(userId);
		
		return basicInfo;
	}

	public void enrollReservation(ReservationDto reservationDto) {
		dao.enrollReservation(reservationDto);
	}

}
