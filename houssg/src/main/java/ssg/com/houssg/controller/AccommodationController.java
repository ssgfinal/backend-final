package ssg.com.houssg.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.directory.SearchResult;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import ch.qos.logback.core.subst.Token.Type;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.AccommodationOcrDto;
import ssg.com.houssg.dto.AccommodationParam;
import ssg.com.houssg.dto.AccommodationRequest;
import ssg.com.houssg.dto.FacilityDto;
import ssg.com.houssg.dto.ResponseWrapper;
import ssg.com.houssg.service.AccommodationService;
import ssg.com.houssg.service.FacilityService;
import ssg.com.houssg.util.NaverOcrService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


@RestController
public class AccommodationController {

	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
    private NaverOcrService naverOcrService;
	
    @Autowired
    private AccommodationService service;
    
    @Autowired
    private FacilityService facservice;

    @Autowired
    private Cloudinary  cloudinary;
    

    @PostMapping(value = "naverOcr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> ocr(@RequestPart MultipartFile file) {
        try {
            AccommodationOcrDto result = naverOcrService.callNaverCloudOcr(file);
            if (result.getAccomName() != null && result.getBusinessNumber() != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                // 파일 업로드 실패 메시지 반환
                return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // 서버에서 예외 발생한 경우
            e.printStackTrace();
            return new ResponseEntity<>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<AccommodationDto>> getAddressSearch(@RequestParam(name = "search", required = false) String search,
                                                                   @RequestParam(name = "type", required = false) String type,
                                                                   @RequestParam(name = "select", required = false) String select,
                                                                   @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
    	List<AccommodationDto> searchResults;
    	
    	
    	// 페이지 크기와 현재 페이지를 고려하여 검색을 시작합니다.
    	int pageSize = 24;
        int start = (page - 1) * pageSize;
        int end = page * pageSize;
        
        AccommodationParam param = new AccommodationParam(search, type, select, pageSize, page, start);
        
        int total = 0;
        
        if (type != null && search != null && select != null) {
        	System.out.println(param.toString());
            searchResults = service.search(param);
            total = service.searchTotal(param);
            System.out.println("총 갯수 : "+total);
        } else if (type != null && select != null) {
        	System.out.println(param.toString());
            searchResults = service.typeSearch(param);
            total = service.typeTotal(param);
            System.out.println("총갯수 : "+total);
        } else if (search != null && select != null) {
        	System.out.println(param.toString());
        	total = service.addressTotal(param);
        	System.out.println("총 갯수: "+total);	
            searchResults = service.getAddressSearch(param);
        } else {
        	return new ResponseEntity<>(new ResponseWrapper<>(new ArrayList<>(), total), HttpStatus.OK);
        }
        
        ResponseWrapper<AccommodationDto> responseWrapper = new ResponseWrapper<>(searchResults,total);
        return ResponseEntity.ok(responseWrapper);
    }


    @PostMapping(value = "accom/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAccommodation(@RequestPart("file") MultipartFile file,
                                                                @RequestPart AccommodationRequest request,
                                                                HttpServletRequest httpRequest) {
        System.out.println("숙소 추가 신청");
        System.out.println(request.toString());
        
        AccommodationDto dto = new AccommodationDto();
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        
        try {
            if (file != null && !file.isEmpty()) {
                // Cloudinary를 사용하여 파일 업로드
                String cloudinaryImageUrl = uploadImage(file);
                // AccommodationDto 객체를 생성하여 숙소 정보 저장
                dto.setImg(cloudinaryImageUrl);
                
                if (request.getTeleNumber() == null || request.getAccomDetails() == null
                        || request.getCheckIn() == null || request.getCheckOut() == null || request.getFacilityDto() == null) {
                	return new ResponseEntity<>("정보가 빠졌음", HttpStatus.BAD_REQUEST);
                }
                // 나머지 Request에서 매핑한 값을 설정
                dto.setId(userId);
                dto.setAccomName(request.getAccomName());
                dto.setAccomAddress(request.getAccomAddress());
                dto.setTeleNumber(request.getTeleNumber());
                dto.setAccomCategory(request.getAccomCategory());
                dto.setAccomDetails(request.getAccomDetails());
                dto.setCheckIn(request.getCheckIn());
                dto.setCheckOut(request.getCheckOut());
                dto.setBusinessNumber(request.getBusinessNumber());

                // FacilityDto 객체를 생성하여 시설 정보 저장
                FacilityDto facilityDto = new FacilityDto();
                int[] facilityDtoList = request.getFacilityDto();
                facilityDto.setNearbySea(facilityDtoList[0]);
                facilityDto.setParkingAvailable(facilityDtoList[1]);
                facilityDto.setPool(facilityDtoList[2]);
                facilityDto.setSpa(facilityDtoList[3]);
                facilityDto.setWifi(facilityDtoList[4]);
                facilityDto.setTwinBed(facilityDtoList[5]);
                facilityDto.setBarbecue(facilityDtoList[6]);
                facilityDto.setNoSmoking(facilityDtoList[7]);
                facilityDto.setLuggageStorage(facilityDtoList[8]);
                facilityDto.setFreeMovieOtt(facilityDtoList[9]);

                // AccommodationService를 호출하여 숙소 정보 및 시설 정보 저장
                int insertedAccomNumber = service.addAccommodationAndFacility(dto, facilityDto);

                System.out.println(dto.toString());
                System.out.println(facilityDto.toString());

                // 성공한 정보를 JSON 형식으로 클라이언트에 반환
                Map<String, String> response = new HashMap<>();
                response.put("message", "숙소 등록 성공");
                response.put("accommodationId", String.valueOf(insertedAccomNumber)); // 등록된 숙소 ID

                return ResponseEntity.ok(response);
            }

            // 파일 업로드 실패 시
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 업로드 및 숙소 등록 실패");

            // 실패한 정보를 JSON 형식으로 클라이언트에 반환
            Map<String, String> response = new HashMap<>();
            response.put("message", "숙소 등록 실패");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("mypage/accom")
    public ResponseEntity<List<AccommodationDto>> getMyAccom(HttpServletRequest httpRequest) {
        System.out.println("내 숙소 조회");
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        List<AccommodationDto> myAccommodations = service.getMyAccom(userId);
        
        // 내숙소 조회 결과가 비어 있는 경우 빈 응답을 반환할 수 있습니다.
        if (myAccommodations.isEmpty()) {
        	return new ResponseEntity<>(new ArrayList<AccommodationDto>(),HttpStatus.OK);
        }
        
        // 내숙소 조회 결과가 비어 있지 않은 경우 OK 응답과 함께 조회 결과를 반환합니다.
        return ResponseEntity.ok(myAccommodations);
    }
    

    @PatchMapping(value = "accom", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAccommodation(@RequestParam(value = "file", required = false) MultipartFile file,
                                                      @RequestPart AccommodationRequest request,
                                                      HttpServletRequest httpRequest) {
        System.out.println("숙소 업데이트");
    
        AccommodationDto dto = new AccommodationDto();
        try {
            // 이전 파일의 경로를 가져옵니다.
            AccommodationDto previousAccommodation = service.choiceAccom(request.getAccomNumber());
            System.out.println(previousAccommodation);
            String previousFilePath = "";
            if (previousAccommodation != null) {
                previousFilePath = previousAccommodation.getImg();
                System.out.println(previousFilePath);
            }

            if (file != null && !file.isEmpty()) {
            	// Cloudinary를 사용하여 파일 업로드
                String cloudinaryImageUrl = uploadImage(file);
                // AccommodationDto 객체를 생성하여 숙소 정보 저장
                dto.setImg(cloudinaryImageUrl);
            } else {
                // 새 파일이 업로드되지 않은 경우, 이전 파일의 경로를 사용합니다.
                dto.setImg(previousFilePath);
            }
            if (request.getAccomNumber() == 0 || request.getTeleNumber() == null || request.getAccomDetails() == null
                    || request.getCheckIn() == null || request.getCheckOut() == null || request.getFacilityDto() == null) {
                return ResponseEntity.badRequest().body("잘못된 요청입니다.");
            }
            // AccommodationDto 객체를 생성하여 숙소 정보 업데이트
            dto.setAccomNumber(request.getAccomNumber());                 
            dto.setTeleNumber(request.getTeleNumber());   
            dto.setAccomDetails(request.getAccomDetails());
            dto.setCheckIn(request.getCheckIn());
            dto.setCheckOut(request.getCheckOut());

            // FacilityDto 객체를 생성하여 시설 정보 업데이트
            FacilityDto facilityDto = new FacilityDto();
            int[] facilityDtoList = request.getFacilityDto();
            facilityDto.setNearbySea(facilityDtoList[0]);
            facilityDto.setParkingAvailable(facilityDtoList[1]);
            facilityDto.setPool(facilityDtoList[2]);
            facilityDto.setSpa(facilityDtoList[3]);
            facilityDto.setWifi(facilityDtoList[4]);
            facilityDto.setTwinBed(facilityDtoList[5]);
            facilityDto.setBarbecue(facilityDtoList[6]);
            facilityDto.setNoSmoking(facilityDtoList[7]);
            facilityDto.setLuggageStorage(facilityDtoList[8]);
            facilityDto.setFreeMovieOtt(facilityDtoList[9]);

            // AccommodationService를 호출하여 숙소 정보 및 시설 정보 업데이트
            service.updateAccommodationAndFacility(dto, facilityDto);

            System.out.println(dto.toString());
            System.out.println(facilityDto.toString());

            // 숙소 업데이트 성공 시
            return ResponseEntity.ok("숙소 업데이트 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 업로드 실패");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("숙소 업데이트 실패");
        }
    }

    @PatchMapping("accom/del/request")
    public ResponseEntity<String> updateRequest(@RequestParam int accomNumber) {
        System.out.println("삭제요청합니다");
        if (accomNumber==0) {
        	return new ResponseEntity<>("숙소번호x",HttpStatus.BAD_REQUEST);
        }
        int count = service.updateRequest(accomNumber);

        if (count > 0) {
            // 업데이트가 성공했을 경우 200 OK와 응답 메시지 반환
            String responseMessage = "숙소 삭제 요청이 수락되었습니다.";
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } else {
            // 업데이트가 실패했을 경우 400 Bad Request와 응답 메시지 반환
            String responseMessage = "숙소 삭제 요청을 처리하는 동안 문제가 발생했습니다.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
    @PatchMapping("auth/accom/del")
    public ResponseEntity<String> deleteRequest(@RequestParam int accomNumber) {
        System.out.println("삭제 요청 처리 완료");
        if (accomNumber==0) {
        	return new ResponseEntity<>("숙소번호x",HttpStatus.BAD_REQUEST);
        }
        int count = service.deleteRequest();

        if (count > 0) {
            // 삭제가 성공한 경우 200 OK와 응답 메시지 반환
            String responseMessage = "요청된 항목이 삭제되었습니다.";
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } else {
            // 삭제가 실패한 경우 400 Bad Request와 응답 메시지 반환
            String responseMessage = "요청된 항목 삭제 중에 문제가 발생했습니다.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
    @GetMapping("accom/detail")
    public ResponseEntity<?> getAccom(@RequestParam Integer accomNumber) {
        System.out.println("리스트에 접근합니다");
    try {
        if (accomNumber==null||accomNumber==0) {
        	return new ResponseEntity<>("숙소번호x",HttpStatus.BAD_REQUEST);
        }
        AccommodationDto accommodation;
        accommodation = service.getAccom(accomNumber);

        // accommodation이 null인 경우 NOT_FOUND 반환
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    	}catch(Exception e) {
    		System.out.println(e);
    		return new ResponseEntity<>("기타에러",HttpStatus.BAD_REQUEST);
    	}
    }

    @GetMapping("accom/all")
    public ResponseEntity<List<AccommodationDto>> getAllAccom() {
        System.out.println("전체 숙소 리스트 보기");
        List<AccommodationDto> accommodationList;
        accommodationList = service.getAllAccom();
        // accommodationList가 null 또는 비어있을 경우 NOT_FOUND 반환
        if (accommodationList == null || accommodationList.isEmpty()) {
        	return new ResponseEntity<>(new ArrayList<AccommodationDto>(),HttpStatus.OK);
        }

        return new ResponseEntity<>(accommodationList, HttpStatus.OK);
    }
    
    @PatchMapping("accom/approval")
    public ResponseEntity<String> accomApproval(@RequestParam int accomNumber) {
        System.out.println("숙소등록신청허가/재신청");
        if (accomNumber==0) {
        	return new ResponseEntity<>("숙소번호x",HttpStatus.BAD_REQUEST);
        }
        
        int result = service.accomApproval(accomNumber);
        
        if (result == 1) {
            return new ResponseEntity<>("Approval successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Approval failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("accom/approvalX")	
    public ResponseEntity<String> accomApprovalX(@RequestParam int accomNumber){
    	System.out.println("숙소등록거절");
    	if (accomNumber==0) {
        	return new ResponseEntity<>("숙소번호x",HttpStatus.BAD_REQUEST);
        }
    	
    	int result = service.accomApprovalX(accomNumber);
    	
    	if (result == 1) {
    		return new ResponseEntity<>("승인 거절", HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>("승인 실패",HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    @GetMapping("auth/accom/add/request")
    public ResponseEntity<List<AccommodationDto>> getApprovalAccom() {
        System.out.println("숙소등록신청목록보기");
        
        List<AccommodationDto> approvalAccomList = service.getApprovalAccom();
        
        if (approvalAccomList != null && !approvalAccomList.isEmpty()) {
            return ResponseEntity.ok(approvalAccomList); // 성공한 경우 숙소 목록 반환
        } else {
        	return new ResponseEntity<>(new ArrayList<AccommodationDto>(),HttpStatus.OK);
        }
    }
    @GetMapping("auth/accom/del/request")
    public ResponseEntity<?> getDeletionAccom() {
        System.out.println("숙소삭제요청목록보기");
        
        List<AccommodationDto> deletionAccomList = service.getDeletionAccom();
        
        if(deletionAccomList != null && !deletionAccomList.isEmpty()) {
        	return ResponseEntity.ok(deletionAccomList);
        } else {
        	return new ResponseEntity<>(new ArrayList<AccommodationDto>(),HttpStatus.OK);
        }
    }
    
    @GetMapping("accom/score")
    public ResponseEntity<List<AccommodationDto>> accomScore(){
        System.out.println("평점 높은 순으로 숙소 보기");
        List<AccommodationDto> accommodationDtoList;
        accommodationDtoList = service.accomScore();
        if (accommodationDtoList.isEmpty()) {
            // 숙소 목록이 비어있는 경우, NO_CONTENT 상태 코드와 함께 빈 목록을 반환합니다.
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // 숙소 목록이 비어있지 않은 경우, OK 상태 코드와 함께 숙소 목록을 반환합니다.
        return new ResponseEntity<>(accommodationDtoList, HttpStatus.OK);
    }
    
    @GetMapping("accom/20/date")
    public ResponseEntity<List<AccommodationDto>> newAccom20() {
        System.out.println("전체 숙소 리스트 날짜순 20개 보기");
        List<AccommodationDto> accommodationList;
        accommodationList = service.newAccom20();   
        // accommodationList가 null 또는 비어있을 경우 NOT_FOUND 반환
        if (accommodationList == null || accommodationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accommodationList, HttpStatus.OK);
    }
    
    @GetMapping("accom/20/score")
    public ResponseEntity<List<AccommodationDto>> accomScore20(){
        System.out.println("평점 높은 순으로 숙소 20개 보기");
        List<AccommodationDto> accommodationDtoList;
        accommodationDtoList = service.accomScore20();
        if (accommodationDtoList.isEmpty()) {
            // 숙소 목록이 비어있는 경우, NO_CONTENT 상태 코드와 함께 빈 목록을 반환합니다.
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // 숙소 목록이 비어있지 않은 경우, OK 상태 코드와 함께 숙소 목록을 반환합니다.
        return new ResponseEntity<>(accommodationDtoList, HttpStatus.OK);
    }
    
 // AccessToken 획득 및 파싱 Part
 	private String getTokenFromRequest(HttpServletRequest request) {
 	    String accessToken = request.getHeader("Authorization");
 	    if (accessToken != null && accessToken.startsWith("Bearer ")) {
 	        return accessToken.substring(7); // "Bearer " 부분을 제외한 엑세스 토큰 부분 추출
 	    }

 	    String refreshToken = request.getHeader("RefreshToken");
 	    if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
 	        return refreshToken.substring(7);
 	    }

 	    return null;
 	}
    
    private String getUserIdFromToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
					.parseClaimsJws(token).getBody();
			return claims.get("id", String.class); // "id" 클레임 추출
		} catch (Exception e) {
			// 토큰 파싱 실패
			e.printStackTrace();
			return null;
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
