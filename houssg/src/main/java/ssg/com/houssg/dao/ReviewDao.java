package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.ReviewDto;

@Mapper
@Repository
public interface ReviewDao {

	int addReview(ReviewDto dto);
	
	List<ReviewDto> getMyReview(String id);
	
	List<ReviewDto> getAllReview(int roomNumber, int accomNumber);
	
	int updateReview(ReviewDto dto);
	
	int deleteReview(int reviewNumber);
	
	List<ReviewDto> getAuthReview();
	
	int addComment(int reviewNumber, int reservationNumber, String reviewComment);
	
	int updateComment(int reviewNumber, int reservationNumber, String reviewComment);
}
