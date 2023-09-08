package ssg.com.houssg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.houssg.dao.FavoriteDao;
import ssg.com.houssg.dto.FavoriteDto;

@Service
@Transactional
public class FavoriteService {

	@Autowired
	FavoriteDao dao;
	
	public int addFavorite(String id, int accomNumber){
		return dao.addFavorite(id, accomNumber);
	}
	public int deleteFavorite(String id, int accomNumber){
		return dao.deleteFavorite(id, accomNumber);
	}
	
	public List<FavoriteDto> getMyFavorite(String id){
		return dao.getMyFavorite(id);
	}
}
