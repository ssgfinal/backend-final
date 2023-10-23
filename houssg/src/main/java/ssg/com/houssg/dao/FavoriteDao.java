package ssg.com.houssg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.houssg.dto.FavoriteDto;
import ssg.com.houssg.dto.FavoriteParam;

@Mapper
@Repository
public interface FavoriteDao {

	int addFavorite(String id, int accomNumber);
	
	int deleteFavorite(String id, int accomNumber);
	
	List<FavoriteParam> getMyFavorite(String id);
		
	Integer roomGet(int accomNumber, String id);
	
	int isIdDuplicate(int accomNumber, String id);
}
