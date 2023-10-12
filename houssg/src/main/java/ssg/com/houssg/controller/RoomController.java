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

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

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
	
	@Autowired
	private Cloudinary cloudinary;

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

	    // 나머지 로직은 그대로 두겠습니다.

	    // 'RoomDto' 객체 생성
	    roomDto.setRoomCategory(request.getRoomCategory());
	    roomDto.setRoomPrice(request.getRoomPrice());
	    roomDto.setRoomAvailability(request.getRoomAvailability());
	    roomDto.setAccomNumber(request.getAccomNumber());
	    System.out.println(roomDto.toString());
	    // 방 추가 로직

	    RoomServiceDto roomServiceDto = new RoomServiceDto();
	    int[] roomServiceDtoList = request.getRoomServiceDto();
	    roomServiceDto.setOceanView(roomServiceDtoList[0]);
	    roomServiceDto.setPc(roomServiceDtoList[1]);
	    roomServiceDto.setNoSmoking(roomServiceDtoList[2]);
	    roomServiceDto.setDoubleBed(roomServiceDtoList[3]);
	    roomServiceDto.setQueenBed(roomServiceDtoList[4]);
	    roomServiceDto.setKingBed(roomServiceDtoList[5]);
	    System.out.println(roomServiceDto.toString());

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

	         List<String> changeFileList = new ArrayList();

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

	         System.out.println(multiFileList);

	         RoomDto roomDto = new RoomDto();
	         roomDto.setRoomNumber(request.getRoomNumber());
	         roomDto.setRoomCategory(request.getRoomCategory());
	         roomDto.setRoomPrice(request.getRoomPrice());

	         // "roomAvailability:"에서 콜론(예상치 못한 문자) 제거
	         // 이 부분이 예기치 않은 문자 오류의 원인이었습니다.
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

	         InnerDto innerDto = new InnerDto();
	         innerDto.setRoomNumber(roomDto.getRoomNumber());

	         // 파일을 업로드한 경우에만 이미지 경로 설정
	         if (!changeFileList.isEmpty()) {
	             for (int i = 0; i < changeFileList.size() && i < 10; i++) {
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
