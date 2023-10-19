package ssg.com.houssg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ssg.com.houssg.dto.RequestDto;
import ssg.com.houssg.dto.ReservationForLmsDto;

@Component
public class LmsUtil {
	
	@Autowired
	private SmsUtil smsUtil;

    public void sendLmsForComplete(ReservationForLmsDto lmsInfo, String reservationNumber) {
        if (lmsInfo == null) {
            throw new IllegalArgumentException("예약 정보를 찾을 수 없습니다.");
        }

        String guestPhone = lmsInfo.getGuestPhone();
        String guestName = lmsInfo.getGuestName();
        String startDate = lmsInfo.getStartDate();
        String endDate = lmsInfo.getEndDate();
        String accomName = lmsInfo.getAccomName();
        String roomCategory = lmsInfo.getRoomCategory();
        String checkIn = lmsInfo.getCheckIn();
        String checkOut = lmsInfo.getCheckOut();

        // SMS 메시지 생성
        RequestDto requestDto = new RequestDto();
        requestDto.setTitle("HOUS-SG 예약확정");
        requestDto.setRecipientPhoneNumber(guestPhone);
        String content = "[HOUS-SG 예약 확정]" + "\n아래 예약정보를 확인해 주세요." 
                + "\n\n■ 예약번호 : " + reservationNumber 
                + "\n■ 예약자명 : " + guestName
                + "\n■ 숙소이름 : " + accomName 
                + "\n■ 객실타입 : " + roomCategory 
                + "\n■ 입실일 : " + startDate + " " + checkIn 
                + "\n■ 퇴실일 : " + endDate + " " + checkOut
                + "\n\n※ 상세내용은 홈페이지의 예약내역에서 확인해주세요."
                + "\n\n※ 좋은 숙소에서 행복한 시간 되세요. \n- HOUS-SG -";
        requestDto.setContent(content);

        // SMS 전송
        smsUtil.sendLMS(requestDto);
    }
    
    
    public void sendLmsForOwnerCancel(ReservationForLmsDto lmsInfo, String reservationNumber) {
        if (lmsInfo == null) {
            throw new IllegalArgumentException("예약 정보를 찾을 수 없습니다.");
        }

        String guestPhone = lmsInfo.getGuestPhone();
        String guestName = lmsInfo.getGuestName();
        String startDate = lmsInfo.getStartDate();
        String endDate = lmsInfo.getEndDate();
        String accomName = lmsInfo.getAccomName();
        String roomCategory = lmsInfo.getRoomCategory();
        String checkIn = lmsInfo.getCheckIn();
        String checkOut = lmsInfo.getCheckOut();

        // SMS 메시지 생성
        RequestDto requestDto = new RequestDto();
        requestDto.setTitle("HOUS-SG 예약취소(숙소)");
        requestDto.setRecipientPhoneNumber(guestPhone);
        String content = "[HOUS-SG 숙소사정 예약취소]" 
        		+ "\n고객님의 예약이 [숙소사정]으로 취소되었습니다. 이용에 불편을 드려 죄송합니다." 
                + "\n\n■ 예약번호 : " + reservationNumber 
                + "\n■ 예약자명 : " + guestName
                + "\n■ 숙소이름 : " + accomName 
                + "\n■ 객실타입 : " + roomCategory 
                + "\n■ 입실일 : " + startDate + " " + checkIn 
                + "\n■ 퇴실일 : " + endDate + " " + checkOut
                + "\n\n※ 결제하신 금액의 10%가 취소 리워드로 지급되었습니다."
                + "\n\n※ 상세한 내용은 해당 숙소를 통해 문의 바랍니다. \n- HOUS-SG -";
        requestDto.setContent(content);

        // SMS 전송
        smsUtil.sendLMS(requestDto);
    }
    
    public void sendLmsForUserCancel(ReservationForLmsDto lmsInfo, String reservationNumber, int refundAmount,
    		String bankName, String account) {
        if (lmsInfo == null) {
            throw new IllegalArgumentException("예약 정보를 찾을 수 없습니다.");
        }

        String guestPhone = lmsInfo.getGuestPhone();

        // SMS 메시지 생성
        RequestDto requestDto = new RequestDto();
        requestDto.setTitle("HOUS-SG 예약취소(고객)");
        requestDto.setRecipientPhoneNumber(guestPhone);
        String content = "[HOUS-SG 예약취소]" 
        		+ "\n고객님의 취소 요청이 정상적으로 완료되었습니다." 
        		+ "\n\n■ 은행명 : " + bankName
        		+ "\n■ 계좌번호 : " + account
        		+ "\n■ 환불금액 : " + refundAmount
        		+ "\n\n※ 취소 완료일로부터 영업일 기준 3일~5일 이내 입력하신 계좌로 자동 입금됩니다."
                + "\n\n※ 이용해 주셔서 감사합니다. \n - HOUS-SG -";
        requestDto.setContent(content);

        // SMS 전송
        smsUtil.sendLMS(requestDto);
    }
    
    public void sendLmsForOneDayAgo(ReservationForLmsDto lmsInfo) {
        if (lmsInfo == null) {
            throw new IllegalArgumentException("예약 정보를 찾을 수 없습니다.");
        }
        
        int reservationNumber = lmsInfo.getReservationNumber();
        String guestPhone = lmsInfo.getGuestPhone();
        String guestName = lmsInfo.getGuestName();
        String startDate = lmsInfo.getStartDate();
        String endDate = lmsInfo.getEndDate();
        String accomName = lmsInfo.getAccomName();
        String roomCategory = lmsInfo.getRoomCategory();
        String checkIn = lmsInfo.getCheckIn();
        String checkOut = lmsInfo.getCheckOut();

        // SMS 메시지 생성
        RequestDto requestDto = new RequestDto();
        requestDto.setTitle("HOUS-SG 예약일 하루전");
        requestDto.setRecipientPhoneNumber(guestPhone);
        String content = "[HOUS-SG 예약 알림]" 
        		+ "\n♬ 예약하신 숙소 방문 하루 전입니다. ♬" 
        		+"\n\n■ 예약번호 : " + reservationNumber 
        		+ "\n■ 예약자명 : " + guestName
        		+ "\n■ 숙소이름 : " + accomName 
        		+ "\n■ 객실타입 : " + roomCategory 
        		+"\n■ 입실일 : " + startDate + " " + checkIn 
        		+ "\n■ 퇴실일 : " + endDate + " " + checkOut
        		+ "\n\n※ 입실 시간에 늦지 않게 도착해주세요!"
        		+ "\n\n※ 좋은 숙소에서 행복한 시간 되세요. \n- HOUS-SG -";
        requestDto.setContent(content);

        // SMS 전송
        smsUtil.sendLMS(requestDto);
    }
}
