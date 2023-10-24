package ssg.com.houssg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.FavoriteDto;
import ssg.com.houssg.dto.InnerDto;
import ssg.com.houssg.dto.RoomDto;
import ssg.com.houssg.dto.RoomRequest;
import ssg.com.houssg.dto.RoomServiceDto;
import ssg.com.houssg.service.InnerService;
import ssg.com.houssg.service.RoomService;
import ssg.com.houssg.util.RoomUtil;

@RestController
public class RoomController {
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	private RoomService service;

	@Autowired
	private InnerService innerService;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private RoomUtil roomUtil;

	@PostMapping(value = "room/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addroom(@RequestPart(value = "multiFile", required = false) List<MultipartFile> multiFile,
	        							  @RequestPart RoomRequest request, HttpServletRequest httprequest) {
	    System.out.println("객실 추가");
	    RoomDto roomDto = new RoomDto();
	    
	    

	    // 이미지 URL을 저장할 리스트 선언
	    List<String> imageUrls = new ArrayList<>();

	    try {
	        for (int i = 0; i < Math.min(multiFile.size(), 10); i++) {
	            MultipartFile file = multiFile.get(i);

	            // Cloudinary를 사용하여 파일 업로드
	            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

	            // 업로드 결과에서 파일의 고유 URL을 가져옴
	            String cloudinaryImageUrl = (String) uploadResult.get("url");

	            // 이미지 URL을 리스트에 추가
	            imageUrls.add(cloudinaryImageUrl);
	            System.out.println("업로드 성공: " + cloudinaryImageUrl);
	        }
	        System.out.println("다중 파일 업로드 성공");
	    } catch (Exception e) {
	        System.out.println("다중 파일 업로드 실패");
	        e.printStackTrace();
	        // 업로드 실패 시 500 Internal Server Error 반환
	        return new ResponseEntity<>("파일 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // 'RoomDto' 객체 생성
	    
	    roomDto.setRoomCategory(request.getRoomCategory());
	    roomDto.setRoomPrice(request.getRoomPrice());
	    roomDto.setRoomAvailability(request.getRoomAvailability());
	    roomDto.setAccomNumber(request.getAccomNumber());
	    // 방 추가 로직

	    ResponseEntity<String> validationResponse = roomUtil.isValidRoom(request,true);
	    if (validationResponse.getStatusCode() != HttpStatus.OK) {
	        // 유효성 검사 실패 시 오류 응답 반환
	        return validationResponse;
	    }
	    
	    RoomServiceDto roomServiceDto = new RoomServiceDto();
	    int[] roomServiceDtoList = request.getRoomServiceDto();
	    roomServiceDto.setOceanView(roomServiceDtoList[0]);
	    roomServiceDto.setPc(roomServiceDtoList[1]);
	    roomServiceDto.setNoSmoking(roomServiceDtoList[2]);
	    roomServiceDto.setDoubleBed(roomServiceDtoList[3]);
	    roomServiceDto.setQueenBed(roomServiceDtoList[4]);
	    roomServiceDto.setKingBed(roomServiceDtoList[5]);

	    // 'rooms' 테이블에 방 정보 추가
	    int roomCount = service.addRoom(roomDto, roomServiceDto);
	    if (roomCount == 0) {
	        return new ResponseEntity<>("방 추가 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // 'innerview' 테이블에 이미지 정보 추가
	    InnerDto innerDto = new InnerDto();
	    innerDto.setRoomNumber(roomDto.getRoomNumber());

	    if (imageUrls != null && !imageUrls.isEmpty()) {
	        for (int i = 0; i < Math.min(imageUrls.size(), 10); i++) {
	            switch (i) {
	                case 0:
	                    innerDto.setImg1(imageUrls.get(i));
	                    break;
	                case 1:
	                    innerDto.setImg2(imageUrls.get(i));
	                    break;
	                case 2:
	                    innerDto.setImg3(imageUrls.get(i));
	                    break;
	                case 3:
	                    innerDto.setImg4(imageUrls.get(i));
	                    break;
	                case 4:
	                    innerDto.setImg5(imageUrls.get(i));
	                    break;
	                case 5:
	                    innerDto.setImg6(imageUrls.get(i));
	                    break;
	                case 6:
	                    innerDto.setImg7(imageUrls.get(i));
	                    break;
	                case 7:
	                    innerDto.setImg8(imageUrls.get(i));
	                    break;
	                case 8:
	                    innerDto.setImg9(imageUrls.get(i));
	                    break;
	                case 9:
	                    innerDto.setImg10(imageUrls.get(i));
	                    break;
	            }
	        }
	    }
	    System.out.println(innerDto.toString());
	    innerService.insertInnerView(innerDto);

	    return new ResponseEntity<>("방 추가 성공", HttpStatus.OK);
	}
	@GetMapping("room/detail")
	public ResponseEntity<?> choiceAccom(@RequestParam int accomNumber) {
	    System.out.println("숙소 상세로 들어갑니다");
	    if (accomNumber <= 0) {
	        return new ResponseEntity<>("객실 번호x", HttpStatus.BAD_REQUEST);
	    }
	    
	    List<RoomDto> list = service.choiceAccom(accomNumber);
	    
	    if (list != null && !list.isEmpty()) {
	        return new ResponseEntity<>(list, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(new ArrayList<RoomDto>(), HttpStatus.OK);
	    }
	}

	@PatchMapping(value = "room", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> updateRoom(@RequestPart(value = "multiFile", required = false) List<MultipartFile> multiFileList,
											 @RequestPart RoomRequest request,
											 HttpServletRequest httprequest) {
	    try {
	        System.out.println("객실 업데이트 시작");
	        List<String> changeFileList = new ArrayList();
	        // 필수 필드인 roomNumber 검사
	        ResponseEntity<String> validationResponse = roomUtil.isValidRoom(request,false);
		    if (validationResponse.getStatusCode() != HttpStatus.OK) {
		        // 유효성 검사 실패 시 오류 응답 반환
		        return validationResponse;
		    }
		    if (request.getRoomNumber()==0) {
		    	return new ResponseEntity<>("룸 번호 입력x",HttpStatus.BAD_REQUEST);
		    }
	        if (multiFileList != null && !multiFileList.isEmpty()) {
	            for (int i = 0; i < multiFileList.size(); i++) {
	                MultipartFile file = multiFileList.get(i);
	                String originalFileName = file.getOriginalFilename();
	                // 파일 이름이 null 또는 빈 문자열인 경우 스킵
	                if (originalFileName == null || originalFileName.isEmpty()) {
	                    continue;
	                }
	                // Cloudinary를 사용하여 파일 업로드
	                String cloudinaryImageUrl = uploadImage(file);
	                changeFileList.add(cloudinaryImageUrl);
	                System.out.println("업로드 성공: " + cloudinaryImageUrl);
	            }
	        }
	        RoomDto roomDto = new RoomDto();
	        roomDto.setRoomNumber(request.getRoomNumber());
	        roomDto.setRoomCategory(request.getRoomCategory());
	        roomDto.setRoomPrice(request.getRoomPrice());
	        roomDto.setRoomAvailability(request.getRoomAvailability());
	        roomDto.setAccomNumber(request.getAccomNumber());
	        System.out.println(roomDto.toString());

	        RoomServiceDto roomServiceDto = new RoomServiceDto();
	        roomServiceDto.setRoomNumber(request.getRoomNumber());
	        int[] roomServiceDtoList = request.getRoomServiceDto();
	        roomServiceDto.setOceanView(roomServiceDtoList[0]);
	        roomServiceDto.setPc(roomServiceDtoList[1]);
	        roomServiceDto.setNoSmoking(roomServiceDtoList[2]);
	        roomServiceDto.setDoubleBed(roomServiceDtoList[3]);
	        roomServiceDto.setQueenBed(roomServiceDtoList[4]);
	        roomServiceDto.setKingBed(roomServiceDtoList[5]);
	        System.out.println(roomServiceDto.toString());
	        service.updateRoom(roomDto, roomServiceDto);
	        // 이미지를 수정할 때 기존 이미지를 유지하고 새 이미지를 추가
	        InnerDto innerDto = new InnerDto();
	        innerDto.setRoomNumber(request.getRoomNumber());
	        List<String> resistFileList = new ArrayList<>(Arrays.asList(request.getResistImage()));

	        if (resistFileList != null && !resistFileList.isEmpty()) {
	            // 기존 이미지 유지
	            for (int i = 0; i < 10; i++) {
	                if (i < resistFileList.size()) {
	                    innerDto.setImg(i, resistFileList.get(i));                    
	                }
	            }
	            // 새 이미지 추가
	            for (int i = resistFileList.size(); i < resistFileList.size() + changeFileList.size() && i < 10; i++) {
	                innerDto.setImg(i, changeFileList.get(i - resistFileList.size()));	                
	            }
	            // 나머지 이미지 초기화
	            for (int i = resistFileList.size() + changeFileList.size(); i < 10; i++) {
	                innerDto.setImg(i, null);
	            }	          
	        innerService.updateInnerView(innerDto);
	        } else {
	            // 기존 이미지 유지하지 않고 모든 이미지를 새 이미지로 설정
	            for (int i = 0; i < changeFileList.size() && i < 10; i++) {
	                innerDto.setImg(i, changeFileList.get(i));
	            }
	        }
	        System.out.println(innerDto.toString());
	        innerService.updateInnerView(innerDto);
	        System.out.println("객실 업데이트 완료");
	        return new ResponseEntity<>("방 업데이트 성공", HttpStatus.OK);
	    } catch (Exception e) {
	        System.out.println("객실 업데이트 실패");
	        e.printStackTrace();
	        return new ResponseEntity<>("객실 업데이트 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PatchMapping("room/request")
	public ResponseEntity<String> deleteRoom(@RequestParam int roomNumber) {
	    System.out.println("방 삭제 요청");

	    // 방 삭제 요청
	    if (roomNumber == 0) {
	        return new ResponseEntity<>("룸 번호 입력x", HttpStatus.BAD_REQUEST);
	    }
	    int count = service.delRequest(roomNumber);
	    if (count == 1) {
	    	return new ResponseEntity<>("예약 중입니다", HttpStatus.OK);
	    } 
	    int isRoom = service.choiceRoom(roomNumber);
	    if(isRoom == 0) {
	    	return new ResponseEntity<>("객실 존재x", HttpStatus.BAD_REQUEST);
	    }
	    int roomCount = service.deleteRequest(roomNumber);
	    if (roomCount <= 0) {
	        // 삭제 요청 실패
	        return new ResponseEntity<>("예약된 방이 남아있습니다.",HttpStatus.BAD_REQUEST);
	    }
	    
	    return ResponseEntity.ok(roomNumber + "방은 삭제 요청이 되었습니다.");
	}

	private String uploadImage(MultipartFile imageFile) throws Exception {
	    try {
	        Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
	        return uploadResult.get("secure_url").toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
}
