package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.FavoriteDto;

@Mapper
@Repository
public interface FavoriteDao {

	int addFavorite(String id, int accomNumber);
	
	int deleteFavorite(String id, int accomNumber);
	
	List<FavoriteDto> getMyFavorite(String id);
		
	Integer roomGet(int accomNumber, String id);
}
