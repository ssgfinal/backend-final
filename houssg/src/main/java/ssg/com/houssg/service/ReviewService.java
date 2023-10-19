package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.ReviewDao;
import ssg.com.houssg.dto.ReviewDto;
import ssg.com.houssg.dto.ReviewParam;

@Service
@Transactional
public class ReviewService {

	@Autowired
	ReviewDao dao;
	// 리뷰 추가
	public int addReview(ReviewDto dto) {
		return dao.addReview(dto);
	}
	// 마이 리뷰 보기
	public List<ReviewDto> getMyReview(ReviewParam param){
		return dao.getMyReview(param);
	}
	// 마이 리뷰 총합
	public int reviewCount(ReviewParam param) {
		return dao.reviewCount(param);
	}
	// 한 숙소 리뷰 보기
	public List<ReviewDto> getAllReview(int accomNumber){
		return dao.getAllReview(accomNumber);
	}
	// 리뷰 신고하기
	public int updateReview(int reviewNumber, String reportMessage) {
		return dao.updateReview(reviewNumber, reportMessage);
	}
	// 리뷰 삭제하기
	public int deleteReview(int reviewNumber) {
		return dao.deleteReview(reviewNumber);
	}
	// 신고한 리뷰보기
	public List<ReviewDto> getAuthReview(){
		return dao.getAuthReview();
	}
	// 답글 추가
	public int addComment(int reviewNumber, String reviewComment) {
		return dao.addComment(reviewNumber, reviewComment);
	}
	// 답글 수정
	public int updateComment(int reviewNumber, String reviewComment) {
		return dao.updateComment(reviewNumber, reviewComment);
	}
}
