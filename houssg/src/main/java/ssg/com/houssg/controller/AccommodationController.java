package ssg.com.houssg.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import ssg.com.houssg.service.AccommodationService;


@RestController
public class AccommodationController {

    @Autowired
    private AccommodationService service;
    
    @GetMapping("/getAddressSearch")
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
    @PostMapping("accom/add")
    public ResponseEntity<String> addAccommodation(@RequestParam(value = "file", required = false) MultipartFile file,
            AccommodationDto dto,
            HttpServletRequest request) {
        try {
            if (file != null && !file.isEmpty()) {
                // 파일 업로드가 올바로 수행되는 경우에만 img 필드 설정
                String path = request.getSession().getServletContext().getRealPath("/upload");
                String root = path + "\\" + "uploadFiles";

                File fileCheck = new File(root);

                if (!fileCheck.exists()) {
                    fileCheck.mkdirs();
                }

                // 업로드된 파일을 저장할 파일명 생성
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String saveFileName = UUID.randomUUID().toString() + extension;

                // 파일 저장 경로 설정
                String filePath = root + "\\" + saveFileName;

                // 파일 저장
                file.transferTo(new File(filePath));

                // img 필드에 파일 경로 설정
                dto.setImg(filePath);
            }

            // 숙소 정보를 데이터베이스에 저장하는 서비스 호출
            boolean isSaved = service.addAccom(dto);

            if (isSaved) {
                // 숙소 정보가 성공적으로 저장되면 201 Created 상태 코드와 숙소 번호를 반환
                return ResponseEntity.status(HttpStatus.CREATED).body("Accommodation added with ID: " + dto.getId());
            } else {
                // 숙소 정보 저장에 실패하면 500 Internal Server Error 상태 코드 반환
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add accommodation.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 파일 업로드 또는 저장 중에 예외가 발생한 경우 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add accommodation.");
        }
    }

    
    @GetMapping("/getMyAccom")
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
    
    @PostMapping("updateAccom")
    public ResponseEntity<String> updateAccom(@RequestParam(value = "file", required = false) MultipartFile file,
            AccommodationDto dto,
            HttpServletRequest request) {
        try {
            if (file != null && !file.isEmpty()) {
                // 새 파일을 업로드하면 이전 파일을 대체합니다.
                String path = request.getSession().getServletContext().getRealPath("/upload");
                String root = path + "\\" + "uploadFiles";

                File fileCheck = new File(root);

                if (!fileCheck.exists()) {
                    fileCheck.mkdirs();
                }

                // 업로드된 파일을 저장할 파일명 생성
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String saveFileName = UUID.randomUUID().toString() + extension;

                // 파일 저장 경로 설정
                String filePath = root + "\\" + saveFileName;

                // 파일 저장
                file.transferTo(new File(filePath));

                // 숙소 정보의 img 필드를 새 파일 경로로 업데이트
                dto.setImg(filePath);
            }

            // 숙소 정보를 데이터베이스에서 업데이트하는 서비스 호출
            int count = service.updateAccom(dto);

            if (count > 0) {
                // 숙소 정보가 성공적으로 업데이트되면 200 OK 상태 코드와 메시지 반환
                return ResponseEntity.ok("Accommodation updated successfully!");
            } else {
                // 숙소 정보 업데이트에 실패하면 500 Internal Server Error 상태 코드와 메시지 반환
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update accommodation.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 파일 업로드 또는 업데이트 중에 예외가 발생한 경우 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update accommodation.");
        }
    }
}
