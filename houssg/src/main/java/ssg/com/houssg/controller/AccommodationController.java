package ssg.com.houssg.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Provider.Service;
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
import ssg.com.houssg.dto.AccommodationParam;
import ssg.com.houssg.dto.AccommodationRequest;
import ssg.com.houssg.dto.FacilityDto;

import ssg.com.houssg.service.AccommodationService;
import ssg.com.houssg.service.FacilityService;


@RestController
public class AccommodationController {
	
	@Value("${jwt.secret}")
	private String secretKey;

    @Autowired
    private AccommodationService service;
    
    @Autowired
    private FacilityService facservice;
    

    
    @GetMapping("search")
    public ResponseEntity<List<AccommodationDto>> getAddressSearch(
    		@RequestParam(value = "search", required = false) String search,
    	    @RequestParam(value = "type", required = false) String type,
    	    @RequestParam(value = "startDate", required = false) String startDate,
    	    @RequestParam(value = "endDate", required = false) String endDate) {

    AccommodationParam param = new AccommodationParam();

        if (search != null && !search.equals("")) {
            search = "%" + search.replace(" ", "%") + "%";
            System.out.println(search);
            param.setSearch(search);
        }

        if (type != null && !type.equals("")) {
        	System.out.println(type);
            param.setType(type);
        }

        if (startDate != null && !startDate.equals("") && endDate != null && !endDate.equals("")) {
            // 시작 날짜와 종료 날짜가 모두 제공된 경우에만 설정
        	System.out.println(startDate+endDate);
            param.setStartDate(startDate);
            param.setEndDate(endDate);
        }

        List<AccommodationDto> accommodations = service.getAddressSearch(param);
        System.out.println(param.toString());
        System.out.println(accommodations);
        // 검색 결과가 비어 있는 경우 NOT_FOUND 응답을 반환할 수 있습니다.
        if (accommodations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 검색 결과가 비어 있지 않은 경우 OK 응답과 함께 검색 결과를 반환합니다.
        return ResponseEntity.ok(accommodations);
    }
    

    @PostMapping(value = "accom/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> addAccommodation(@RequestPart("file") MultipartFile file,
                                                                @RequestPart AccommodationRequest request,
                                                                HttpServletRequest httpRequest) {
        System.out.println("숙소 추가 신청");
        System.out.println(request.toString());
        String path = httpRequest.getSession().getServletContext().getRealPath("/upload");
        String root = path + File.separator + "uploadFiles";
        String saveFileName = "";

        File fileCheck = new File(root);

        if (!fileCheck.exists()) fileCheck.mkdirs();
        AccommodationDto dto = new AccommodationDto();
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        try {
            if (file != null && !file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                saveFileName = UUID.randomUUID().toString() + extension;

                String filePath = root + File.separator + saveFileName;
                file.transferTo(new File(filePath));

                // AccommodationDto 객체를 생성하여 숙소 정보 저장

                dto.setImg(filePath);

                // DTO에 Request에서 매핑한 값을 설정
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
                System.out.println(request.toString());
                FacilityDto facilityDto = request.getFacilityDto();

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
            System.out.println("파일 업로드 실패");

            // 업로드 실패 시 파일 삭제
            if (!saveFileName.isEmpty()) {
                new File(root + File.separator + saveFileName).delete();
            }

            // 실패한 정보를 JSON 형식으로 클라이언트에 반환
            Map<String, String> response = new HashMap<>();
            response.put("message", "숙소 등록 실패");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PostMapping("mypage/accom")
    public ResponseEntity<List<AccommodationDto>> getMyAccom(HttpServletRequest httpRequest) {
        System.out.println("내 숙소 조회");
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        List<AccommodationDto> myAccommodations = service.getMyAccom(userId);
        
        // 내숙소 조회 결과가 비어 있는 경우 빈 응답을 반환할 수 있습니다.
        if (myAccommodations.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // 내숙소 조회 결과가 비어 있지 않은 경우 OK 응답과 함께 조회 결과를 반환합니다.
        return ResponseEntity.ok(myAccommodations);
    }
    
    
    @PatchMapping(value = "accom", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAccommodation(@RequestParam(value = "file", required = false) MultipartFile file,
    												  @RequestPart AccommodationRequest request,
                                                      HttpServletRequest httpRequest) {
        System.out.println("숙소 업데이트");

        String path = httpRequest.getSession().getServletContext().getRealPath("/upload");
        String root = path + File.separator + "uploadFiles";
        String saveFileName = "";

        File fileCheck = new File(root);

        if (!fileCheck.exists()) fileCheck.mkdirs();

        String filePath = "";
        AccommodationDto dto = new AccommodationDto();
        try {
            // 이전 파일의 경로를 가져옵니다.
            AccommodationDto previousAccommodation = service.getAccom(request.getAccomNumber());
            String previousFilePath = "";

            if (previousAccommodation != null) {
                previousFilePath = previousAccommodation.getImg();
            }

            if (file != null && !file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                saveFileName = UUID.randomUUID().toString() + extension;

                filePath = root + "\\" + saveFileName;
                file.transferTo(new File(filePath));
                
            } else {
                // 새 파일이 업로드되지 않은 경우, 이전 파일의 경로를 사용합니다.
                filePath = previousFilePath;
            }

            // AccommodationDto 객체를 생성하여 숙소 정보 업데이트
            
            dto.setAccomName(request.getAccomName());
            dto.setAccomAddress(request.getAccomAddress());
            dto.setTeleNumber(request.getTeleNumber());
            dto.setAccomCategory(request.getAccomCategory());
            dto.setAccomDetails(request.getAccomDetails());
            dto.setCheckIn(request.getCheckIn());
            dto.setCheckOut(request.getCheckOut());
            dto.setBusinessNumber(request.getBusinessNumber());

            // 파일을 업로드한 경우에만 이미지 경로 설정
            if (file != null && !file.isEmpty()) {
                dto.setImg(filePath);
            } else {
                // 새 파일이 업로드되지 않은 경우, 이전 파일의 경로를 사용합니다.
                dto.setImg(previousFilePath);
            }

            // FacilityDto 객체를 생성하여 시설 정보 업데이트
            FacilityDto facilityDto = request.getFacilityDto();

            // AccommodationService를 호출하여 숙소 정보 및 시설 정보 업데이트
            service.updateAccommodationAndFacility(dto, facilityDto);

            System.out.println(dto.toString());
            System.out.println(facilityDto.toString());

            // 숙소 업데이트 성공 시
            return ResponseEntity.ok("숙소 업데이트 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 업로드 실패");

            // 업로드 실패 시 파일 삭제
            if (!saveFileName.isEmpty()) {
                new File(root + File.separator + saveFileName).delete();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("숙소 업데이트 실패");
        }
    }
    @PatchMapping("accom/del/request")
    public ResponseEntity<String> updateRequest(@RequestParam int accomNumber) {
        System.out.println("삭제요청합니다");
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
    @PostMapping("accom/detail")
    public ResponseEntity<AccommodationDto> getAccom(@RequestParam int accomNumber) {
        System.out.println("리스트에 접근합니다");
        AccommodationDto accommodation;
        accommodation = service.getAccom(accomNumber);

        // accommodation이 null인 경우 NOT_FOUND 반환
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    }
    @PostMapping("accom/detail/id")
    public ResponseEntity<AccommodationDto> getAccomid(@RequestParam int accomNumber, HttpServletRequest httpRequest) {
        System.out.println("리스트에 접근합니다");
        String token = getTokenFromRequest(httpRequest);
        AccommodationDto accommodation;
        String userId = getUserIdFromToken(token);
        accommodation = service.getAccomid(accomNumber, userId);
   
        // accommodation이 null인 경우 NOT_FOUND 반환
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    } 

    @PostMapping("accom/all")
    public ResponseEntity<List<AccommodationDto>> getAllAccom() {
        System.out.println("전체 숙소 리스트 보기");
        List<AccommodationDto> accommodationList;
        accommodationList = service.getAllAccom();
        // accommodationList가 null 또는 비어있을 경우 NOT_FOUND 반환
        if (accommodationList == null || accommodationList.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accommodationList, HttpStatus.OK);
    }
    @PostMapping("accom/all/id")
    public ResponseEntity<List<AccommodationDto>> getAllAccomid(HttpServletRequest httpRequest) {
        System.out.println("전체 숙소 리스트 보기");
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        List<AccommodationDto> accommodationList;
        accommodationList = service.getAllAccomid(userId);
        // accommodationList가 null 또는 비어있을 경우 NOT_FOUND 반환
        if (accommodationList == null || accommodationList.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accommodationList, HttpStatus.OK);
    }
    
    @PatchMapping("accom/approval")
    public ResponseEntity<String> accomApproval(@RequestParam int accomNumber) {
        System.out.println("숙소등록신청허가/재신청");
        
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
    	
    	int result = service.accomApprovalX(accomNumber);
    	
    	if (result == 1) {
    		return new ResponseEntity<>("승인 거절", HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>("승인 실패",HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    @PostMapping("auth/accom/add/request")
    public ResponseEntity<List<AccommodationDto>> getApprovalAccom() {
        System.out.println("숙소등록신청목록보기");
        
        List<AccommodationDto> approvalAccomList = service.getApprovalAccom();
        
        if (approvalAccomList != null && !approvalAccomList.isEmpty()) {
            return ResponseEntity.ok(approvalAccomList); // 성공한 경우 숙소 목록 반환
        } else {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 숙소 목록이 없는 경우 No Content(204) 반환
        }
    }
    @PostMapping("accom/score")
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
    @PostMapping("accom/score/id")
    public ResponseEntity<List<AccommodationDto>> accomScoreid(HttpServletRequest httpRequest){
        System.out.println("평점 높은 순으로 숙소 보기");
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        List<AccommodationDto> accommodationDtoList;
        accommodationDtoList = service.accomScoreid(userId);
        if (accommodationDtoList.isEmpty()) {
            // 숙소 목록이 비어있는 경우, NO_CONTENT 상태 코드와 함께 빈 목록을 반환합니다.
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // 숙소 목록이 비어있지 않은 경우, OK 상태 코드와 함께 숙소 목록을 반환합니다.
        return new ResponseEntity<>(accommodationDtoList, HttpStatus.OK);
    }
    @PostMapping("accom/20/date")
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
    @PostMapping("accom/20/date/id")
    public ResponseEntity<List<AccommodationDto>> newAccom20id(HttpServletRequest httpRequest) {
        System.out.println("전체 숙소 리스트 날짜순 20개 보기");
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        List<AccommodationDto> accommodationList;
        accommodationList = service.newAccom20id(userId);
        // accommodationList가 null 또는 비어있을 경우 NOT_FOUND 반환
        if (accommodationList == null || accommodationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accommodationList, HttpStatus.OK);
    }
    @PostMapping("accom/20/score")
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
    @PostMapping("accom/20/score/id")
    public ResponseEntity<List<AccommodationDto>> accomScore20id(HttpServletRequest httpRequest){
        System.out.println("평점 높은 순으로 숙소 20개 보기");
        String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
        List<AccommodationDto> accommodationDtoList;;
        accommodationDtoList = service.accomScore20id(userId);
        if (accommodationDtoList.isEmpty()) {
            // 숙소 목록이 비어있는 경우, NO_CONTENT 상태 코드와 함께 빈 목록을 반환합니다.
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        // 숙소 목록이 비어있지 않은 경우, OK 상태 코드와 함께 숙소 목록을 반환합니다.
        return new ResponseEntity<>(accommodationDtoList, HttpStatus.OK);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token != null && token.startsWith("Bearer ")) {
			return token.substring(7);
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
}
