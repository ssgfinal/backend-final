package ssg.com.houssg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ssg.com.houssg.dto.RoomRequest;
import ssg.com.houssg.service.RoomService;

@Component
public class RoomUtil {
    @Autowired
    private RoomService service;

    public ResponseEntity<String> isValidRoom(RoomRequest request, boolean checkCategoryDuplicate) {
        if (request.getRoomCategory() == null) {
            return new ResponseEntity<>("카테고리 입력x", HttpStatus.BAD_REQUEST);
        }

        if (request.getRoomPrice() <= 0) {
            return new ResponseEntity<>("가격 입력x", HttpStatus.BAD_REQUEST);
        }

        if (request.getRoomAvailability() == 0) {
            return new ResponseEntity<>("방 갯수x", HttpStatus.BAD_REQUEST);
        }

        if (request.getAccomNumber() == 0) {
            return new ResponseEntity<>("숙소번호x", HttpStatus.BAD_REQUEST);
        }

        if (checkCategoryDuplicate && service.isCategoryDuplicate(request.getAccomNumber(), request.getRoomCategory())) {
            return new ResponseEntity<>("같은 객실명을 사용할 수 없습니다", HttpStatus.BAD_REQUEST);
        }

        // 모든 조건을 통과하면 유효한 방 정보
        return new ResponseEntity<>("유효한 방 정보입니다", HttpStatus.OK);
    }
}

