package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
