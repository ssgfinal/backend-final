package ssg.com.houssg.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
import ssg.com.houssg.dto.AccommodationDto;
import ssg.com.houssg.dto.ResponseWrapper;
import ssg.com.houssg.dto.ReviewDto;
import ssg.com.houssg.dto.ReviewParam;
import ssg.com.houssg.service.ReservationService;
import ssg.com.houssg.service.ReviewService;

@RestController
public class ReviewController {

	@Value("${jwt.secret}")
	private String secretKey;

	@Autowired
	ReviewService service;

	@Autowired
	Cloudinary cloudinary;

	@Autowired
	ReservationService reservice;

	// 리뷰 추가
	@PostMapping(value = "review/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addReview(@RequestPart(value = "file", required = false) MultipartFile file,
			@RequestPart ReviewDto reviewDto, HttpServletRequest httpRequest) {
		System.out.println("리뷰 추가");

		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);

		if (service.reviewCheck(reviewDto) == 1) {
			System.out.println(service.reviewCheck(reviewDto));
			return ResponseEntity.badRequest().body("이미 해당 예약 번호에 대한 리뷰가 작성되었습니다.");
		}

		ReviewDto dto = new ReviewDto();
		try {
			// 파일 업로드 여부 확인
			if (file != null && !file.isEmpty()) {
				// Cloudinary를 사용하여 파일 업로드
				String cloudinaryImageUrl = uploadImage(file);
				// AccommodationDto 객체를 생성하여 숙소 정보 저장
				dto.setImg(cloudinaryImageUrl);
			}
			String reviewDtoContentType = httpRequest.getPart("reviewDto").getContentType();
			if (reviewDtoContentType == null || !reviewDtoContentType.equals("application/json")) {
				System.out.println(reviewDtoContentType);
				return ResponseEntity.badRequest().body("reviewDto의 타입이 잘못되었습니다.");
			}

			if (reviewDto.getReviewContent() == null) {
				return ResponseEntity.badRequest().body("리뷰내용x");
			}

			if (reviewDto.getReviewRating() == 0) {
				return ResponseEntity.badRequest().body("평점x");
			}

			if (reviewDto.getReservationNumber() == 0) {
				return ResponseEntity.badRequest().body("예약 번호가 유효x");
			}

			if (reviewDto.getRoomNumber() == 0) {
				return ResponseEntity.badRequest().body("룸번호x");
			}

			if (reviewDto.getAccomNumber() == 0) {
				return ResponseEntity.badRequest().body("숙소번호x");
			}
			dto.setReviewContent(reviewDto.getReviewContent());
			dto.setReviewRating(reviewDto.getReviewRating());
			dto.setReservationNumber(reviewDto.getReservationNumber());
			dto.setRoomNumber(reviewDto.getRoomNumber());
			dto.setAccomNumber(reviewDto.getAccomNumber());
			dto.setId(userId);

			service.addReview(dto);
			reservice.statusUpdate(reviewDto.getReservationNumber());

			// 리뷰 등록 성공 시
			return ResponseEntity.ok("리뷰 등록 성공");
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 등록 실패");
		}
	}

	// my 리뷰 보기
	@GetMapping("mypage/review")
	public ResponseEntity<List<ReviewDto>> getMyReview(HttpServletRequest httpRequest) {
		System.out.println("나의 리뷰 보기");
		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);

		List<ReviewDto> reviews = service.getMyReview(userId);

		if (reviews.isEmpty()) {
			// 리뷰가 없는 경우
			return new ResponseEntity<>(new ArrayList<ReviewDto>(), HttpStatus.OK);
		} else {
			// 리뷰를 찾은 경우
			return ResponseEntity.ok(reviews); // 리뷰 목록 반환
		}
	}

	@GetMapping("mypage/review/page")
	public ResponseEntity<ResponseWrapper<ReviewDto>> getMyReview(HttpServletRequest httpRequest,
			@RequestParam int pageSize, @RequestParam int page) {
		System.out.println("나의 리뷰 보기");

		// 페이지 크기와 현재 페이지를 고려하여 검색을 시작합니다.
		int start = (page - 1) * pageSize;
		int end = page * pageSize;

		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);
		ReviewParam param = new ReviewParam(userId, pageSize, page, start);
		// 데이터베이스 쿼리에 start와 end를 사용하여 데이터 범위를 지정
		List<ReviewDto> reviews = service.getMyReviewPage(param);
		int total = service.reviewCount(param);

		ResponseWrapper<ReviewDto> responseWrapper = new ResponseWrapper<>(reviews, total);
		if (reviews.isEmpty()) {
			// 리뷰가 없는 경우
			return new ResponseEntity<>(new ResponseWrapper<>(new ArrayList<>(), total), HttpStatus.OK);
		} else {
			// 리뷰를 찾은 경우
			return ResponseEntity.ok(responseWrapper);
		}
	}

	// 숙소에 관한 리뷰
	@GetMapping("review/all/accom")
	public ResponseEntity<?> getAllReview(@RequestParam int accomNumber) {
		System.out.println("숙소에 관한 리뷰 보기");
		if (accomNumber == 0) {
			return new ResponseEntity<>("객실번호x", HttpStatus.BAD_REQUEST);
		}

		List<ReviewDto> reviews = service.getAllReview(accomNumber);

		if (reviews.isEmpty()) {
			// 리뷰가 없는 경우
			return new ResponseEntity<>(new ArrayList<ReviewDto>(), HttpStatus.OK);
		} else {
			// 리뷰를 찾은 경우
			return ResponseEntity.ok(reviews); // 리뷰 목록 반환
		}
	}

	@GetMapping("review/all/accom/page")
	public ResponseEntity<?> getAllReviewPage(@RequestParam int accomNumber, @RequestParam int pageSize,
			@RequestParam int page) {
		System.out.println("숙소에 관한 리뷰 보기");
		int start = (page - 1) * pageSize;
		int end = page * pageSize;

		int total = 0;
		if (accomNumber == 0) {
			return ResponseEntity.badRequest().body("객실번호가 유효하지 않습니다.");
		}

		ReviewParam param = new ReviewParam(accomNumber, pageSize, page, start);
		List<ReviewParam> reviews = service.getAllReviewPage(param);
		System.out.println(reviews.toString());
		if (reviews.isEmpty()) {
			// 리뷰가 없는 경우
			ResponseWrapper<ReviewParam> responseWrapper = new ResponseWrapper<>(new ArrayList<>(), total);
			return ResponseEntity.ok(responseWrapper);
		} else {
			// 리뷰를 찾은 경우
			total = service.pageTotal(param);
			ResponseWrapper<ReviewParam> responseWrapper = new ResponseWrapper<>(reviews, total);
			return ResponseEntity.ok(responseWrapper); // 리뷰 목록 반환
		}
	}

	@PatchMapping("review/report")
	public ResponseEntity<String> updateReview(@RequestParam int reviewNumber, @RequestParam String reportMessage) {
		System.out.println("리뷰 신고하기");
		if (reviewNumber == 0) {
			return new ResponseEntity<>("리뷰번호x", HttpStatus.BAD_REQUEST);
		}
		if (reportMessage == null) {
			return new ResponseEntity<>("메세지x", HttpStatus.BAD_REQUEST);
		}

		int count = service.updateReview(reviewNumber, reportMessage);

		if (count == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰신고x");
		}

		return ResponseEntity.ok("신고완료");
	}

	@DeleteMapping("review")
	public ResponseEntity<String> deleteReview(@RequestParam int reviewNumber) {
		System.out.println("리뷰 삭제하기");
		if (reviewNumber == 0) {
			return new ResponseEntity<>("리뷰번호x", HttpStatus.BAD_REQUEST);
		}
		int count = service.deleteReview(reviewNumber);

		if (count == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰가 삭제되지 않았습니다");
		}

		return ResponseEntity.ok("리뷰가 삭제되었습니다.");
	}

	@GetMapping("auth/review")
	public ResponseEntity<List<ReviewDto>> getAuthReview() {
		System.out.println("신고받은 리뷰 보기");

		List<ReviewDto> reviews = service.getAuthReview();

		if (reviews != null && !reviews.isEmpty()) {
			// 리뷰 목록이 비어 있지 않으면 200 OK 응답과 함께 목록을 반환
			return ResponseEntity.ok(reviews);
		} else {
			return new ResponseEntity<>(new ArrayList<ReviewDto>(), HttpStatus.OK);
		}
	}

	// 답글 추가
	@PatchMapping("review/comment/add")
	public ResponseEntity<String> addComment(@RequestParam int reviewNumber, @RequestParam String reviewComment) {
		try {
			System.out.println("답글 추가 시작");
			if (reviewNumber == 0) {
				return new ResponseEntity<>("리뷰번호x", HttpStatus.BAD_REQUEST);
			}
			if (reviewComment == null) {
				return new ResponseEntity<>("내용x", HttpStatus.BAD_REQUEST);
			}
			// 댓글을 추가하는 비즈니스 로직 수행
			int count = service.addComment(reviewNumber, reviewComment);

			// 댓글 추가가 성공한 경우
			if (count == 1) {
				return new ResponseEntity<>("comment 등록 성공", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("comment 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// 예외 처리: 예외 발생 시 서버 오류 응답을 반환
			return new ResponseEntity<>("comment 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("review/comment")
	public ResponseEntity<String> updateComment(@RequestParam int reviewNumber, @RequestParam String reviewComment) {
		System.out.println("답글 수정");
		if (reviewNumber == 0) {
			return new ResponseEntity<>("리뷰번호x", HttpStatus.BAD_REQUEST);
		}
		if (reviewComment == null) {
			return new ResponseEntity<>("내용x", HttpStatus.BAD_REQUEST);
		}
		int count = service.updateComment(reviewNumber, reviewComment);

		if (count == 1) {
			return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Comment update failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("reservation/review")
	public ResponseEntity<?> reservationReview(@RequestParam Integer reservationNumber,
			HttpServletRequest httpRequest) {
		System.out.println("예약에서 나의 리뷰 보기");
		if (reservationNumber == null || reservationNumber <= 0) {
			return ResponseEntity.badRequest().body("번호 x");
		}
		// 토큰 및 사용자 ID를 추출하는 코드 (getTokenFromRequest 및 getUserIdFromToken 메서드 사용)
		String token = getTokenFromRequest(httpRequest);
		String userId = getUserIdFromToken(token);

		// 서비스 메서드를 호출하여 리뷰 목록을 가져옵니다.
		List<ReviewDto> list = service.reservationReview(userId, reservationNumber);

		// 리뷰 목록이 null인 경우 빈 배열로 반환하도록 처리
		if (list == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}

		// ResponseEntity를 사용하여 응답을 래핑하여 반환합니다.
		return ResponseEntity.ok(list);
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
