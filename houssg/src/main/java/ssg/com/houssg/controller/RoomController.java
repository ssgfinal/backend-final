package ssg.com.houssg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.InnerDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.RoomRequest;
import ssg.com.houssg.dto.RoomServiceDto;
import ssg.com.houssg.service.InnerService;
import ssg.com.houssg.service.RoomService;

@RestController
public class RoomController {
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	private RoomService service;

	@Autowired
	private InnerService innerService;
	

	@PostMapping(value = "room/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addroom(@RequestPart("multiFile") List<MultipartFile> multiFile,
							   	          @RequestPart RoomRequest request,
								          HttpServletRequest httprequest) {
	    System.out.println("객실 추가");

	    String path = httprequest.getSession().getServletContext().getRealPath("/upload");
	    String root = path + File.separator + "uploadFiles";

	    File fileCheck = new File(root);

	    if (!fileCheck.exists()) fileCheck.mkdirs();

	    List<Map<String, String>> fileList = new ArrayList<>();
	    System.out.println(request.toString());
	    for (int i = 0; i < multiFile.size(); i++) {
	        String originFile = multiFile.get(i).getOriginalFilename();
	        String ext = originFile.substring(originFile.lastIndexOf("."));
	        String changeFile = UUID.randomUUID().toString() + ext;
	        Map<String, String> map = new HashMap<>();
	        map.put("originFile", originFile);
	        map.put("changeFile", changeFile);
	        fileList.add(map);
	    }

	    try {
	        for (int i = 0; i < multiFile.size(); i++) {
	            File uploadFile = new File(root + File.separator + fileList.get(i).get("changeFile"));
	            multiFile.get(i).transferTo(uploadFile);
	        }
	        System.out.println("다중 파일 업로드 성공");
	    } catch (Exception e) {
	        System.out.println("다중 파일 업로드 실패");
	        // 만약 업로드 실패하면 파일 삭제
	        for (int i = 0; i < multiFile.size(); i++) {
	            new File(root + File.separator + fileList.get(i).get("changeFile")).delete();
	        }
	        e.printStackTrace();
	        // 업로드 실패 시 500 Internal Server Error 반환
	        return new ResponseEntity<>("파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    System.out.println(fileList);
	    List<String> changeFileList = new ArrayList<>();
	    for (Map<String, String> fileName : fileList) {
	        changeFileList.add(fileName.get("changeFile"));
	    }

	    // 'RoomDto' 객체 생성
	    RoomDto roomDto = new RoomDto();
	    roomDto.setRoomCategory(request.getRoomCategory());
	    roomDto.setRoomDetails(request.getRoomDetails());
	    roomDto.setRoomPrice(request.getRoomPrice());
	    roomDto.setRoomAvailability(request.getRoomAvailability());
	    roomDto.setAccomNumber(request.getAccomNumber());
	    System.out.println(roomDto.toString());
	    // 방 추가 로직
	    
	    
	    RoomServiceDto roomServiceDto = new RoomServiceDto();
	    int[] roomServiceDtoList = request.getRoomServiceDto();
	    roomServiceDto.setCityView(roomServiceDtoList[0]);
	    roomServiceDto.setOceanView(roomServiceDtoList[1]);
	    roomServiceDto.setPc(roomServiceDtoList[2]);
	    roomServiceDto.setNoSmoking(roomServiceDtoList[3]);
	    roomServiceDto.setDoubleBed(roomServiceDtoList[4]);
	    roomServiceDto.setQueenBed(roomServiceDtoList[5]);
	    roomServiceDto.setKingBed(roomServiceDtoList[6]);
	    System.out.println(roomServiceDto.toString());
	    // 'rooms' 테이블에 방 정보 추가
	    int roomCount = service.addRoom(roomDto, roomServiceDto);
	    if (roomCount == 0) {
	        return new ResponseEntity<>("방 추가 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // 'innerview' 테이블에 이미지 정보 추가
	    InnerDto innerDto = new InnerDto();
	    innerDto.setRoomNumber(roomDto.getRoomNumber());
	    
	    if (multiFile != null && !multiFile.isEmpty()) {
		    for (int i = 0; i < changeFileList.size(); i++) {
		        if (i < 10) { // 최대 10개까지 이미지 정보 저장
		            switch (i) {
		                case 0:
		                    innerDto.setImg1(changeFileList.get(i));
		                    break;
		                case 1:
		                    innerDto.setImg2(changeFileList.get(i));
		                    break;
		                case 2:
		                    innerDto.setImg3(changeFileList.get(i));
		                    break;
		                case 3:
		                    innerDto.setImg4(changeFileList.get(i));
		                    break;
		                case 4:
		                    innerDto.setImg5(changeFileList.get(i));
		                    break;
		                case 5:
		                    innerDto.setImg6(changeFileList.get(i));
		                    break;
		                case 6:
		                    innerDto.setImg7(changeFileList.get(i));
		                    break;
		                case 7:
		                    innerDto.setImg8(changeFileList.get(i));
		                    break;
		                case 8:
		                    innerDto.setImg9(changeFileList.get(i));
		                    break;
		                case 9:
		                    innerDto.setImg10(changeFileList.get(i));
		                    break;
		            }
		        }
		    }
	    }
	    System.out.println(innerDto.toString());
	    innerService.insertInnerView(innerDto);
	    
	    return new ResponseEntity<>("방 추가 성공", HttpStatus.OK);
	}


	
	 @GetMapping("room/detail")
	    public ResponseEntity<List<RoomDto>> choiceAccom(@RequestParam int accomNumber) {
	        System.out.println(accomNumber);
	        System.out.println("숙소상세로 들어갑니다");
	        List<RoomDto> list = service.choiceAccom(accomNumber);

	        if (list != null && !list.isEmpty()) {
	            // 숙소 정보가 존재할 경우 200 OK 응답과 데이터 반환
	            return new ResponseEntity<>(list, HttpStatus.OK);
	        } else {
	            // 숙소 정보가 없을 경우 404 NO_CONTENT 응답 반환
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	    }
	 
	 @PatchMapping(value = "room", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<String> updateRoom(@RequestPart(value = "multiFile", required = false) List<MultipartFile> multiFileList,
	            								 @RequestPart RoomRequest request,
	            								 HttpServletRequest httprequest) {
	        try {
	            System.out.println("객실 업데이트 시작");

	            // 파일 업로드 경로 설정
	            String path = httprequest.getSession().getServletContext().getRealPath("/upload");
	            String root = path + File.separator + "uploadFiles";
	            System.out.println(root);
	            // 업로드 경로가 없으면 생성
	            File fileCheck = new File(root);
	            if (!fileCheck.exists()) {
	                fileCheck.mkdirs();
	            }

	            // 이미지 파일 업로드 및 기존 이미지 주소 유지 처리
	            List<Map<String, String>> fileList = new ArrayList<>();
	            List<String> changeFileList = new ArrayList<>();

	            if (multiFileList != null && !multiFileList.isEmpty()) {
	                for (int i = 0; i < multiFileList.size(); i++) {
	                    String originalFileName = multiFileList.get(i).getOriginalFilename();
	                    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	                    String saveFileName = UUID.randomUUID().toString() + extension;
	                    Map<String, String> map = new HashMap<>();
	                    map.put("originalFile", originalFileName);
	                    map.put("changeFile", saveFileName);
	                    fileList.add(map);

	                    File uploadFile = new File(root + File.separator + saveFileName);
	                    multiFileList.get(i).transferTo(uploadFile);

	                    changeFileList.add(saveFileName);
	                }
	            }
	            System.out.println(multiFileList);

	            // 'RoomDto' 객체 생성
	            RoomDto roomDto = new RoomDto();
	            roomDto.setRoomNumber(request.getRoomNumber());
	    	    roomDto.setRoomCategory(request.getRoomCategory());
	    	    roomDto.setRoomDetails(request.getRoomDetails());
	    	    roomDto.setRoomPrice(request.getRoomPrice());
	    	    roomDto.setRoomAvailability(request.getRoomAvailability());
	    	    roomDto.setAccomNumber(request.getAccomNumber());
	            System.out.println(roomDto.toString());
	    	    RoomServiceDto roomServiceDto = new RoomServiceDto();
	    	    roomServiceDto.setRoomNumber(request.getRoomNumber());
	    	    int[] roomServiceDtoList = request.getRoomServiceDto();
	    	    roomServiceDto.setCityView(roomServiceDtoList[0]);
	    	    roomServiceDto.setOceanView(roomServiceDtoList[1]);
	    	    roomServiceDto.setPc(roomServiceDtoList[2]);
	    	    roomServiceDto.setNoSmoking(roomServiceDtoList[3]);
	    	    roomServiceDto.setDoubleBed(roomServiceDtoList[4]);
	    	    roomServiceDto.setQueenBed(roomServiceDtoList[5]);
	    	    roomServiceDto.setKingBed(roomServiceDtoList[6]);
	    	    
	    	    System.out.println(roomServiceDto.toString());
	    	    
	    	    service.updateRoom(roomDto, roomServiceDto);

	            InnerDto innerDto = new InnerDto();
	            innerDto.setRoomNumber(roomDto.getRoomNumber());
	            // 파일을 업로드한 경우에만 이미지 경로 설정
	            	if (multiFileList != null && !multiFileList.isEmpty()) {
	            	    for (int i = 0; i < multiFileList.size() && i < 10; i++) {
	            	        // 동적으로 InnerDto의 이미지 필드를 설정합니다.
	            	        switch (i) {
	            	            case 0:
	            	                innerDto.setImg1(changeFileList.get(i));
	            	                break;
	            	            case 1:
	            	                innerDto.setImg2(changeFileList.get(i));
	            	                break;
	            	            case 2:
	            	                innerDto.setImg3(changeFileList.get(i));
	            	                break;
	            	            case 3:
	            	                innerDto.setImg4(changeFileList.get(i));
	            	                break;
	            	            case 4:
	            	                innerDto.setImg5(changeFileList.get(i));
	            	                break;
	            	            case 5:
	            	                innerDto.setImg6(changeFileList.get(i));
	            	                break;
	            	            case 6:
	            	                innerDto.setImg7(changeFileList.get(i));
	            	                break;
	            	            case 7:
	            	                innerDto.setImg8(changeFileList.get(i));
	            	                break;
	            	            case 8:
	            	                innerDto.setImg9(changeFileList.get(i));
	            	                break;
	            	            case 9:
	            	                innerDto.setImg10(changeFileList.get(i));
	            	                break;
	            	        }
	            	    }
	            	    System.out.println(innerDto.toString());
	            	    innerService.updateInnerView(innerDto);
	            	}
	            System.out.println("객실 업데이트 완료");
	            
	            return new ResponseEntity<>("방 업데이트 성공", HttpStatus.OK);
	        } catch (Exception e) {
	            System.out.println("객실 업데이트 실패");
	            e.printStackTrace();
	            
	            return new ResponseEntity<>("객실 업데이트 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	        } 
	 }
}
