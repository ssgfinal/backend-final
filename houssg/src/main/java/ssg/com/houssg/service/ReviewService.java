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
	
	public List<ReviewDto> getAllReview(int roomNumber, int accomNumber){
		return dao.getAllReview(roomNumber, accomNumber);
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
}
