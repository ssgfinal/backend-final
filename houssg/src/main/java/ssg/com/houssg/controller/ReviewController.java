package ssg.com.houssg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import ssg.com.houssg.dto.ReviewDto;
import ssg.com.houssg.service.ReviewService;

@RestController
public class ReviewController {

	@Autowired
	ReviewService service;
	
	// 리뷰 추가
	@PostMapping(value = "review/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addReview(
	    @RequestParam(value = "file", required = false) MultipartFile file,
	    @RequestParam("reviewContent") String reviewContent,
	    @RequestParam("reviewRating") double reviewRating,
	    @RequestParam("reservationNumber") int reservationNumber,
	    @RequestParam("roomNumber") int roomNumber,
	    @RequestParam("accomNumber") int accomNumber,
	    @RequestParam("id") String id,
	    HttpServletRequest request
	) {
	    System.out.println("리뷰 추가");

	    String path = request.getSession().getServletContext().getRealPath("/upload");
	    String root = path + "\\" + "uploadFiles";
	    String saveFileName = ""; // 변수를 미리 초기화

	    File fileCheck = new File(root);

	    if (!fileCheck.exists()) fileCheck.mkdirs();

	    try {
	        // 파일 업로드 여부 확인
	        if (file != null && !file.isEmpty()) {
	            // 파일 업로드된 경우 처리
	            // 업로드된 파일을 저장할 파일명 생성
	            String originalFileName = file.getOriginalFilename();
	            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	            saveFileName = UUID.randomUUID().toString() + extension; // 변수 초기화

	            // 파일 저장 경로 설정
	            String filePath = root + "\\" + saveFileName;

	            // 파일 저장
	            file.transferTo(new File(filePath));

	            // 리뷰 등록 로직 추가
	            ReviewDto dto = new ReviewDto();
	            dto.setImg(filePath); // 파일 경로 설정
	            dto.setReviewContent(reviewContent); // 다른 리뷰 관련 필드 설정
	            dto.setReviewRating(reviewRating);
	            dto.setReservationNumber(reservationNumber);
	            dto.setRoomNumber(roomNumber);
	            dto.setAccomNumber(accomNumber);
	            dto.setId(id);

	            service.addReview(dto);
	        } else {
	            // 파일이 업로드되지 않은 경우 처리
	            // 리뷰 등록 로직 추가 (파일 관련 필드를 제외한 나머지 필드만 사용)
	            ReviewDto dto = new ReviewDto();
	            dto.setReviewContent(reviewContent);
	            dto.setReviewRating(reviewRating);
	            dto.setReservationNumber(reservationNumber);
	            dto.setRoomNumber(roomNumber);
	            dto.setAccomNumber(accomNumber);
	            dto.setId(id);

	            service.addReview(dto);
	        }

	        // 리뷰 등록 성공 시
	        return ResponseEntity.ok("리뷰 등록 성공");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("파일 업로드 실패");

	        // 업로드 실패 시 파일 삭제
	        if (!saveFileName.isEmpty()) {
	            new File(root + "\\" + saveFileName).delete();
	        }

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 등록 실패"); // 400 에러
	    }
	}

	// my 리뷰 보기
	@PostMapping("mypage/review")
	public ResponseEntity<List<ReviewDto>> getMyReview(@RequestParam String nickname) {
	    System.out.println("나의 리뷰 보기");

	    List<ReviewDto> reviews = service.getMyReview(nickname);

	    if (reviews.isEmpty()) {
	        // 리뷰가 없는 경우
	        return ResponseEntity.noContent().build(); // 204 No Content 반환
	    } else {
	        // 리뷰를 찾은 경우
	        return ResponseEntity.ok(reviews); // 리뷰 목록 반환
	    }
	}
	// 숙소에 관한 리뷰
	@PostMapping("review/get/all")
	public ResponseEntity<List<ReviewDto>> getAllReview(@RequestParam int roomNumber, @RequestParam int accomNumber) {
	    System.out.println("숙소에 관한 리뷰 보기");
	     
	    List<ReviewDto> reviews = service.getAllReview(roomNumber, accomNumber);
	     
	    if (reviews.isEmpty()) {
	        // 리뷰가 없는 경우
	        return ResponseEntity.noContent().build(); // HTTP 상태 코드 204 No Content 반환
	    } else {
	        // 리뷰를 찾은 경우
	        return ResponseEntity.ok(reviews); // 리뷰 목록 반환
	    }
	}
	
	@PostMapping("review/report")
	public String updateReview(ReviewDto dto) {
		System.out.println("리뷰 신고하기");
		int count = service.updateReview(dto);
		if(count==0) {
			return "NO";
		}
		return "YES";
	}
	
	@PostMapping("deleteReview")
	public String deleteReview(int reviewNumber) {
		System.out.println("리뷰 삭제하기");
		int count = service.deleteReview(reviewNumber);
		if(count==0) {
			return "NO";
		}
		return "YES";
	}
}
