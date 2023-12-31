package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.ReviewDto;
import ssg.com.houssg.dto.ReviewParam;

@Mapper
@Repository
public interface ReviewDao {

	// 리뷰 추가
	int addReview(ReviewDto dto);
	// 마이 리뷰 보기
	List<ReviewDto> getMyReview(String param);
	// 마이 리뷰 보기
	List<ReviewDto> getMyReviewPage(ReviewParam param);
	// 마이 리뷰 총합
	int reviewCount(ReviewParam param);
	// 한 숙소 리뷰 보기
	List<ReviewDto> getAllReview(int accomNumber);
	// 한 숙소 리뷰 보기(페이지)
	List<ReviewParam> getAllReviewPage(ReviewParam param);
	// 페이지 토탈
	int pageTotal(ReviewParam param);
	// 리뷰 신고하기
	int updateReview(int reviewNumber, String reportMessage);
	// 리뷰 삭제하기
	int deleteReview(int reviewNumber);
	// 신고한 리뷰보기
	List<ReviewDto> getAuthReview();
	// 답글 추가
	int addComment(int reviewNumber, String reviewComment);
	// 답글 수정
	int updateComment(int reviewNumber, String reviewComment);
	// 예약에서 나의리뷰보기
	List<ReviewDto> reservationReview(String id, int reservationNumber);
	
	int reviewCheck(ReviewDto dto);
}
