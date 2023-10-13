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
	
	public int updateReview(ReviewDto dto) {
		return dao.updateReview(dto);
	}
	public int deleteReview(int reviewNumber) {
		return dao.deleteReview(reviewNumber);
	}
	
	public List<ReviewDto> getAuthReview(){
		return dao.getAuthReview();
	}
	public int addComment(int reviewNumber, int reservationNumber, String reviewComment) {
		return dao.addComment(reviewNumber, reservationNumber, reviewComment);
	}
	public int updateComment(int reviewNumber, int reservationNumber, String reviewComment) {
		return dao.updateComment(reviewNumber, reservationNumber, reviewComment);
	}
}
