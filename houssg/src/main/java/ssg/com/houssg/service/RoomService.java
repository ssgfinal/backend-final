package ssg.com.houssg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.InnerDao;
import ssg.com.houssg.dao.RoomDao;
import ssg.com.houssg.dao.RoomServiceDao;
import ssg.com.houssg.dto.InnerDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.RoomServiceDto;

@Service
@Transactional
public class RoomService {

	@Autowired
	RoomDao dao;
	
	@Autowired
	RoomServiceDao serdao;
	
	@Autowired
	InnerDao indao;
	
	@Transactional
	public int addRoom(RoomDto dto, RoomServiceDto roomServiceDto) {
	    dao.addRoom(dto);
	    int insertedRoomNumber = dto.getRoomNumber();

	    roomServiceDto.setRoomNumber(insertedRoomNumber);

	    serdao.insertRoomService(roomServiceDto);

	    return insertedRoomNumber;
	}

	
	public List<RoomDto> choiceAccom(int accomNumber) {
	    List<RoomDto> roomDtoList = dao.choiceAccom(accomNumber);
	    System.out.println(roomDtoList);
	    
	    for (RoomDto roomDto : roomDtoList) {
	        RoomServiceDto roomServiceDto = serdao.getService(roomDto.getRoomNumber());
	        InnerDto innerDto = indao.getImgs(roomDto.getRoomNumber());
	        if (roomServiceDto != null) {
	            System.out.println(roomDto.getRoomNumber());
	            int[] serviceList = {
	                roomServiceDto.getOceanView(),
	                roomServiceDto.getPc(),
	                roomServiceDto.getNoSmoking(),
	                roomServiceDto.getDoubleBed(),
	                roomServiceDto.getQueenBed(),
	                roomServiceDto.getKingBed()
	            };
	            roomDto.setService(serviceList);
	            
	            String[] imgList = {
	            	    innerDto.getImg1(),
	            	    innerDto.getImg2(),
	            	    innerDto.getImg3(),
	            	    innerDto.getImg4(),
	            	    innerDto.getImg5(),
	            	    innerDto.getImg6(),
	            	    innerDto.getImg7(),
	            	    innerDto.getImg8(),
	            	    innerDto.getImg9(),
	            	    innerDto.getImg10()
	            	};

	            	List<String> validImgs = new ArrayList<>();

	            	for (String img : imgList) {
	            	    if (img != null) {
	            	        validImgs.add(img);
	            	    }
	            	}

	            	roomDto.setImgs(validImgs.toArray(new String[0]));
	        } else {
	            // roomServiceDto가 null인 경우, 필드 값을 null로 설정
	            roomDto.setService(null);
	        }
	    }
	    
	    return roomDtoList;
	}
	
	public int getRoomNumberFromDatabase() {
        return dao.getRoomNumberFromDatabase();
    }
	
	@Transactional
	public int updateRoom(RoomDto dto, RoomServiceDto roomServiceDto) {
		dao.updateRoom(dto);
		int updatedRoomCount = dao.updateRoom(dto);

        if (updatedRoomCount == 0) {
            // 방 정보 업데이트 실패 시 0을 반환
            return 0;
        }

        // 방 서비스 정보 업데이트
        int updatedRoomServiceCount = serdao.updateRoomService(roomServiceDto);
        
        return updatedRoomServiceCount;
    }
	public int deleteRequest(int roomNumber) {
	    
	    // 방 삭제 시도
	    int deleteRoomCount = dao.deleteRequest(roomNumber);

	    if (deleteRoomCount <= 0) {
	        // 방 삭제 실패 시 오류 메시지 출력
	        System.out.println("예약이 존재합니다");
	        return 0;
	    }

	    // 방 삭제 성공 시 1 반환 (또는 다른 성공 코드)
	    return 1;
	}
	public boolean isCategoryDuplicate(int accomNumber,String roomCategory) {
		int count = dao.isCategoryDuplicate(accomNumber,roomCategory);
		return count > 0?true:false;
	}
	public int delRequest(int roomNumber) {
		return dao.delRequest(roomNumber);
	}
	public int choiceRoom(int roomNumber) {
		return dao.choiceRoom(roomNumber);
	}
}
