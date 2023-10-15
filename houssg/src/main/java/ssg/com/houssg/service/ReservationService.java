package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.ReservationDao;
import ssg.com.houssg.dto.AccomListDto;
import ssg.com.houssg.dto.AccomReservationListDto;
import ssg.com.houssg.dto.AccommodationDto;
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

//	// 숙소 정보 조회
//	public List<AccommodationDto> getAccommodationInfo(int accomNumber) {
//		return dao.getAccommodationInfo(accomNumber);
//	}
//
//	// 객실 정보 조회
//	public List<RoomDto> getRoomInfo(int roomNumber) {
//		return dao.getRoomInfo(roomNumber);
//	}

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
	public List<ReservationDto> findRerservationById(String Id) {
		return dao.findRerservationById(Id);
	}

	// 예약 가능 여부 확인
	public boolean isReservationAvailable(ReservationDto reservationDto) {
		List<ReservationRoomDto> availableRooms = dao.lastCheck(reservationDto);

		for (ReservationRoomDto room : availableRooms) {
			if (room.getAvailableRooms() == 0) {
				return false; // 하나라도 0인 경우 false 반환
			}
		}

		return true; // 모든 방이 예약 가능하면 true 반환
	}
	
    // 사업자 ID로 가지고있는 숙소번호, 이름 가져옴
	public List<AccomListDto> getAccommodationByOwnerId(String id) {
        return dao.getAccommodationByOwnerId(id);
    }

    // 숙소번호, 날짜로 예약상태가 2(예약완료)인 예약정보 가져옴
    public List<AccomReservationListDto> getHistoryForOwner(String accomNumber, String yearMonth) {
        return dao.getHistoryForOwner(accomNumber, yearMonth);
    }
}
