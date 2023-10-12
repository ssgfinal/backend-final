package ssg.com.houssg.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import ssg.com.houssg.dto.ReviewDto;
import ssg.com.houssg.service.ReviewService;

@RestController
public class ReviewController {
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	ReviewService service;
	
	@Autowired
	Cloudinary cloudinary;
	
	// 리뷰 추가
	@PostMapping(value = "review/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addReview(@RequestPart(value = "file", required = false) MultipartFile file,
	                                        @RequestPart ReviewDto reviewDto,
	                                        HttpServletRequest httpRequest) {
	    System.out.println("리뷰 추가");

	    
	    String token = getTokenFromRequest(httpRequest);
	    String userId = getUserIdFromToken(token);

	    ReviewDto dto = new ReviewDto();
	    try {
	        // 파일 업로드 여부 확인
	        if (file != null && !file.isEmpty()) {
	        	// Cloudinary를 사용하여 파일 업로드
                String cloudinaryImageUrl = uploadImage(file);
                // AccommodationDto 객체를 생성하여 숙소 정보 저장
                dto.setImg(cloudinaryImageUrl);   
	        }
	        // 리뷰 등록 로직 추가
	        
	        dto.setReviewContent(reviewDto.getReviewContent());
	        dto.setReviewRating(reviewDto.getReviewRating());
	        dto.setReservationNumber(reviewDto.getReservationNumber());
	        dto.setRoomNumber(reviewDto.getRoomNumber());
	        dto.setAccomNumber(reviewDto.getAccomNumber());
	        dto.setId(userId);

	        service.addReview(dto);

	        // 리뷰 등록 성공 시
	        return ResponseEntity.ok("리뷰 등록 성공");
	    } catch (Exception e) {

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 등록 실패"); // 400 에러
	    }
	}

	private String saveUploadedFile(MultipartFile file, String uploadDir) throws IOException {
	    String originalFileName = file.getOriginalFilename();
	    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	    String saveFileName = UUID.randomUUID().toString() + extension;
	    String filePath = uploadDir + File.separator + saveFileName;
	    file.transferTo(new File(filePath));
	    return saveFileName;
	}


	// my 리뷰 보기
	@PostMapping("mypage/review")
	public ResponseEntity<List<ReviewDto>> getMyReview(HttpServletRequest httpRequest) {
	    System.out.println("나의 리뷰 보기");
	    String token = getTokenFromRequest(httpRequest);
        String userId = getUserIdFromToken(token);
	    List<ReviewDto> reviews = service.getMyReview(userId);

	    if (reviews.isEmpty()) {
	        // 리뷰가 없는 경우
	        return ResponseEntity.noContent().build(); // 204 No Content 반환
	    } else {
	        // 리뷰를 찾은 경우
	        return ResponseEntity.ok(reviews); // 리뷰 목록 반환
	    }
	}
	// 숙소에 관한 리뷰
	@GetMapping("review/all/accom")
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
	@PatchMapping("review/report")
	public ResponseEntity<String> updateReview(@RequestParam int reviewNumber, @RequestParam int roomNumber, @RequestParam int accomNumber) {
		    System.out.println("리뷰 신고하기");
		    
		    // ReviewDto 객체 생성 및 필드 설정
		    ReviewDto dto = new ReviewDto();
		    dto.setReviewNumber(reviewNumber);
		    dto.setRoomNumber(roomNumber);
		    dto.setAccomNumber(accomNumber);
		    
		    int count = service.updateReview(dto);
		    
		    if (count == 0) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO");
		    }
		    
		    return ResponseEntity.ok("YES");
		}
	@DeleteMapping("review")
	public ResponseEntity<String> deleteReview(@RequestParam int reviewNumber) {
		    System.out.println("리뷰 삭제하기");
		    
		    int count = service.deleteReview(reviewNumber);
		    
		    if (count == 0) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO");
		    }
		    
		    return ResponseEntity.ok("YES");
		}
	@PostMapping("auth/review")
	public ResponseEntity<List<ReviewDto>> getAuthReview() {
		    System.out.println("신고받은 리뷰 보기");
		    
		    List<ReviewDto> reviews = service.getAuthReview();
		    
		    if (reviews != null && !reviews.isEmpty()) {
		        // 리뷰 목록이 비어 있지 않으면 200 OK 응답과 함께 목록을 반환
		        return ResponseEntity.ok(reviews);
		    } else {
		        // 리뷰 목록이 비어 있으면 404 Not Found 응답 반환
		        return ResponseEntity.notFound().build();
		    }
		}
	// 답글 추가
	@PatchMapping("review/comment/add")
	public ResponseEntity<List<ReviewDto>> addComment(@RequestParam int review_number,
											          @RequestParam int reservation_number,
											          @RequestParam String review_comment) {
	    try {
	        System.out.println("답글 추가");
	        
	        // 요청 매개변수를 사용하여 ReviewDto 객체 생성
	        ReviewDto dto = new ReviewDto();
	        dto.setReviewNumber(review_number);
	        dto.setReservationNumber(reservation_number);
	        dto.setReviewComment(review_comment);
	        
	        int count = service.addComment(review_number, reservation_number, review_comment); // updateComment 메서드를 호출하여 댓글을 추가합니다.
	        if (count > 0) {
	            List<ReviewDto> updatedReviews = service.getAllReview(dto.getRoomNumber(), dto.getAccomNumber()); // 업데이트된 리뷰 목록을 가져옵니다.
	            return ResponseEntity.ok(updatedReviews); // 성공한 경우 업데이트된 리뷰 목록을 반환합니다.
	        } else {
	            return ResponseEntity.badRequest().build(); // 실패한 경우 Bad Request를 반환합니다.
	        }
	    } catch (Exception e) {
	        // 예외 처리: 예외 발생 시 클라이언트에 오류 응답을 반환합니다.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@PatchMapping("review/comment")
	public ResponseEntity<String> updateComment(
			@RequestParam int review_number,
	        @RequestParam int reservation_number,
	        @RequestParam String review_comment
	) {
	    System.out.println("답글 수정");
	    
	    int result = service.updateComment(review_number, reservation_number, review_comment);
	    
	    if (result == 1) {
	        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Comment update failed", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
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
