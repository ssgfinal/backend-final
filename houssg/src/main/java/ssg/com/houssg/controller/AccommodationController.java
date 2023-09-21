package ssg.com.houssg.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Provider.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.subst.Token.Type;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.AccommodationParam;
import ssg.com.houssg.dto.FacilityDto;

import ssg.com.houssg.service.AccommodationService;
import ssg.com.houssg.service.FacilityService;


@RestController
public class AccommodationController {

    @Autowired
    private AccommodationService service;
    
    @Autowired
    private FacilityService facservice;
    
    @GetMapping("search")
    public ResponseEntity<List<AccommodationDto>> getAddressSearch(@RequestParam String search) {
        System.out.println("주소별 검색");
        AccommodationParam param = new AccommodationParam();
        search = "%" + search.replace(" ", "%") + "%";
        param.setSearch(search);
        List<AccommodationDto> accommodations = service.getAddressSearch(param);
        
        // 검색 결과가 비어 있는 경우 NOT_FOUND 응답을 반환할 수 있습니다.
        if (accommodations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // 검색 결과가 비어 있지 않은 경우 OK 응답과 함께 검색 결과를 반환합니다.
        return ResponseEntity.ok(accommodations);
    }
    
    // (3) 숙소 등록이 진행됨
    @PostMapping(value = "accom/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addAccommodation(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("id") String id,
                                                   @RequestParam("accomName") String accomName,
                                                   @RequestParam("accomAddress") String accomAddress,
                                                   @RequestParam("teleNumber") String teleNumber,
                                                   @RequestParam("accomCategory") String accomCategory,
                                                   @RequestParam("accomDetails") String accomDetails,
                                                   @RequestParam("checkIn") String checkIn,
                                                   @RequestParam("checkOut") String checkOut,
                                                   @RequestParam("businessNumber") String businessNumber,
                                                   @RequestParam("zipCode") String zipCode,
                                                   @RequestParam("nearbySea") boolean nearbySea,
                                                   @RequestParam("oceanView") boolean oceanView,
                                                   @RequestParam("parkingAvailable") boolean parkingAvailable,
                                                   @RequestParam("pool") boolean pool,
                                                   @RequestParam("spa") boolean spa,
                                                   @RequestParam("couplePc") boolean couplePc,
                                                   @RequestParam("wifi") boolean wifi,
                                                   @RequestParam("family") boolean family,
                                                   @RequestParam("twinBed") boolean twinBed,
                                                   @RequestParam("barbecue") boolean barbecue,
                                                   @RequestParam("noSmoking") boolean noSmoking,
                                                   @RequestParam("luggageStorage") boolean luggageStorage,
                                                   @RequestParam("freeMovieOtt") boolean freeMovieOtt,
                                                   HttpServletRequest request) {
        System.out.println("숙소 추가");
        

        String path = request.getSession().getServletContext().getRealPath("/upload");
        String root = path + "\\" + "uploadFiles";
        String saveFileName = "";

        File fileCheck = new File(root);

        if (!fileCheck.exists()) fileCheck.mkdirs();

        try {
            if (file != null && !file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                saveFileName = UUID.randomUUID().toString() + extension;

                String filePath = root + "\\" + saveFileName;

                file.transferTo(new File(filePath));

                // AccommodationDto 객체를 생성하여 숙소 정보 저장
                AccommodationDto dto = new AccommodationDto();
                dto.setImg(filePath);
                dto.setId(id);
                dto.setAccomName(accomName);
                dto.setAccomAddress(accomAddress);
                dto.setTeleNumber(teleNumber);
                dto.setAccomCategory(accomCategory);
                dto.setAccomDetails(accomDetails);
                dto.setCheckIn(checkIn);
                dto.setCheckOut(checkOut);
                dto.setBusinessNumber(businessNumber);
                dto.setZipCode(zipCode);

                // FacilityDto 객체를 생성하여 시설 정보 저장
                FacilityDto facilityDto = new FacilityDto();
                facilityDto.setNearbySea(nearbySea);
                facilityDto.setOceanView(oceanView);
                facilityDto.setParkingAvailable(parkingAvailable);
                facilityDto.setPool(pool);
                facilityDto.setSpa(spa);
                facilityDto.setCouplePc(couplePc);
                facilityDto.setWifi(wifi);
                facilityDto.setFamily(family);
                facilityDto.setTwinBed(twinBed);
                facilityDto.setBarbecue(barbecue);
                facilityDto.setNoSmoking(noSmoking);
                facilityDto.setLuggageStorage(luggageStorage);
                facilityDto.setFreeMovieOtt(freeMovieOtt);

                // AccommodationService를 호출하여 숙소 정보 및 시설 정보 저장
                int insertedAccomNumber = service.addAccommodationAndFacility(dto, facilityDto);

                System.out.println(dto.toString());
                System.out.println(facilityDto.toString());
            } else {
                // 파일이 업로드되지 않은 경우 처리
                // AccommodationDto 객체를 생성하여 숙소 정보 저장
                AccommodationDto dto = new AccommodationDto();
                dto.setId(id);
                dto.setAccomName(accomName);
                dto.setAccomAddress(accomAddress);
                dto.setTeleNumber(teleNumber);
                dto.setAccomCategory(accomCategory);
                dto.setAccomDetails(accomDetails);
                dto.setCheckIn(checkIn);
                dto.setCheckOut(checkOut);
                dto.setBusinessNumber(businessNumber);
                dto.setZipCode(zipCode);

                // FacilityDto 객체를 생성하여 시설 정보 저장
                FacilityDto facilityDto = new FacilityDto();
                facilityDto.setNearbySea(nearbySea);
                facilityDto.setOceanView(oceanView);
                facilityDto.setParkingAvailable(parkingAvailable);
                facilityDto.setPool(pool);
                facilityDto.setSpa(spa);
                facilityDto.setCouplePc(couplePc);
                facilityDto.setWifi(wifi);
                facilityDto.setFamily(family);
                facilityDto.setTwinBed(twinBed);
                facilityDto.setBarbecue(barbecue);
                facilityDto.setNoSmoking(noSmoking);
                facilityDto.setLuggageStorage(luggageStorage);
                facilityDto.setFreeMovieOtt(freeMovieOtt);

                // AccommodationService를 호출하여 숙소 정보 및 시설 정보 저장
                int insertedAccomNumber = service.addAccommodationAndFacility(dto, facilityDto);

                System.out.println(dto.toString());
                System.out.println(facilityDto.toString());
            }

            // 숙소 등록 성공 시
            return ResponseEntity.ok("숙소 등록 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일 업로드 실패");

            // 업로드 실패 시 파일 삭제
            if (!saveFileName.isEmpty()) {
                new File(root + "\\" + saveFileName).delete();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("숙소 등록 실패");
        }
    }



    @GetMapping("mypage/accom")
    public ResponseEntity<List<AccommodationDto>> getMyAccom(@RequestParam String id) {
        System.out.println("내숙소 조회");
        List<AccommodationDto> myAccommodations = service.getMyAccom(id);
        
        // 내숙소 조회 결과가 비어 있는 경우 NOT_FOUND 응답을 반환할 수 있습니다.
        if (myAccommodations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // 내숙소 조회 결과가 비어 있지 않은 경우 OK 응답과 함께 조회 결과를 반환합니다.
        return ResponseEntity.ok(myAccommodations);
    }
    
    @PatchMapping(value = "accom/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAccommodation(@RequestParam(value = "file", required = false) MultipartFile file,
                                                     @RequestParam("accomNumber") int accomNumber,
                                                     @RequestParam("id") String id,
                                                     @RequestParam("accomName") String accomName,
                                                     @RequestParam("accomAddress") String accomAddress,
                                                     @RequestParam("teleNumber") String teleNumber,
                                                     @RequestParam("accomDetails") String accomDetails,
                                                     @RequestParam("checkIn") String checkIn,
                                                     @RequestParam("checkOut") String checkOut,
                                                     @RequestParam("nearbySea") boolean nearbySea,
                                                     @RequestParam("oceanView") boolean oceanView,
                                                     @RequestParam("parkingAvailable") boolean parkingAvailable,
                                                     @RequestParam("pool") boolean pool,
                                                     @RequestParam("spa") boolean spa,
                                                     @RequestParam("couplePc") boolean couplePc,
                                                     @RequestParam("wifi") boolean wifi,
                                                     @RequestParam("family") boolean family,
                                                     @RequestParam("twinBed") boolean twinBed,
                                                     @RequestParam("barbecue") boolean barbecue,
                                                     @RequestParam("noSmoking") boolean noSmoking,
                                                     @RequestParam("luggageStorage") boolean luggageStorage,
                                                     @RequestParam("freeMovieOtt") boolean freeMovieOtt,
                                                     HttpServletRequest request) {
        System.out.println("숙소 업데이트");

        String path = request.getSession().getServletContext().getRealPath("/upload");
        String root = path + "\\" + "uploadFiles";
        String saveFileName = "";

        File fileCheck = new File(root);

        if (!fileCheck.exists()) fileCheck.mkdirs();

        String filePath = "";
        try {
            // 이전 파일의 경로를 가져옵니다.
            AccommodationDto previousAccommodation = service.getAccom(accomNumber);
            String previousFilePath = previousAccommodation.getImg();

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
            AccommodationDto dto = new AccommodationDto();
            dto.setAccomNumber(accomNumber);
            dto.setId(id);
            dto.setAccomName(accomName);
            dto.setAccomAddress(accomAddress);
            dto.setTeleNumber(teleNumber);
            dto.setAccomDetails(accomDetails);
            dto.setCheckIn(checkIn);
            dto.setCheckOut(checkOut);

            // 파일을 업로드한 경우에만 이미지 경로 설정
            if (file != null && !file.isEmpty()) {
                dto.setImg(filePath);
            } else {
                // 새 파일이 업로드되지 않은 경우, 이전 파일의 경로를 사용합니다.
                dto.setImg(previousFilePath);
            }

            // FacilityDto 객체를 생성하여 시설 정보 업데이트
            FacilityDto facilityDto = new FacilityDto();
            facilityDto.setAccomNumber(accomNumber);
            facilityDto.setNearbySea(nearbySea);
            facilityDto.setOceanView(oceanView);
            facilityDto.setParkingAvailable(parkingAvailable);
            facilityDto.setPool(pool);
            facilityDto.setSpa(spa);
            facilityDto.setCouplePc(couplePc);
            facilityDto.setWifi(wifi);
            facilityDto.setFamily(family);
            facilityDto.setTwinBed(twinBed);
            facilityDto.setBarbecue(barbecue);
            facilityDto.setNoSmoking(noSmoking);
            facilityDto.setLuggageStorage(luggageStorage);
            facilityDto.setFreeMovieOtt(freeMovieOtt);

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
                new File(root + "\\" + saveFileName).delete();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("숙소 업데이트 실패");
        }
    }
    @PatchMapping("auth/accom")
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
    @GetMapping("get/accom")
    public ResponseEntity<AccommodationDto> getAccom(@RequestParam int accomNumber) {
        System.out.println("리스트에 접근합니다");
        AccommodationDto accommodation = service.getAccom(accomNumber);
        
        // accommodation이 null인 경우 NOT_FOUND 반환
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    }
    @GetMapping("get/all/accom")
    public ResponseEntity<List<AccommodationDto>> getAllAccom() {
        System.out.println("전체 숙소 리스트 보기");
        List<AccommodationDto> accommodationList = service.getAllAccom();
        
        // accommodationList가 null 또는 비어있을 경우 NOT_FOUND 반환
        if (accommodationList == null || accommodationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accommodationList, HttpStatus.OK);
    }
}
