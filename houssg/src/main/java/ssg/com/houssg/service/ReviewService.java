package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.ReviewDao;
import ssg.com.houssg.dto.ReviewDto;

@Service
@Transactional
public class ReviewService {

	@Autowired
	ReviewDao dao;
	
	public int addReview(ReviewDto dto) {
		return dao.addReview(dto);
	}
	public List<ReviewDto> getMyReview(String id){
		return dao.getMyReview(id);
	}
	
	public List<ReviewDto> getAllReview(int accomNumber){
		return dao.getAllReview(accomNumber);
	}
	
	public int updateReview(int reviewNumber, String reportMessage) {
		return dao.updateReview(reviewNumber, reportMessage);
	}
	public int deleteReview(int reviewNumber) {
		return dao.deleteReview(reviewNumber);
	}
	
	public List<ReviewDto> getAuthReview(){
		return dao.getAuthReview();
	}
	public int addComment(int reviewNumber, String reviewComment) {
		return dao.addComment(reviewNumber, reviewComment);
	}
	public int updateComment(int reviewNumber, String reviewComment) {
		return dao.updateComment(reviewNumber, reviewComment);
	}
}
