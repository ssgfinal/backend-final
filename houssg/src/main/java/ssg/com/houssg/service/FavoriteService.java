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
	public int roomGet(int accomNumber, String id) {
        Integer result = dao.roomGet(accomNumber, id);
        return (result != null) ? result : 0;
    }
	public boolean isIdDuplicate(int accomNumber, String id) {
		int count = dao.isIdDuplicate(accomNumber, id);
		return count>0?true:false;
	}
}
