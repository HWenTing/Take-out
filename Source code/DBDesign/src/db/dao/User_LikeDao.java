package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.User;
import db.entity.UserLike;





@Repository("user_LikeDao")
public class User_LikeDao {

	@Resource
	private BaseDao<UserLike> baseDao;
	
	public List<UserLike> getByUserName(String user_name){
		return baseDao.find("select c from UserLike c where c.user_name=?0", new Object[] {user_name}, UserLike.class);

	}
	
	public void addUserLikeShop(UserLike userLike) {
		baseDao.save(userLike);
	}
	
}
