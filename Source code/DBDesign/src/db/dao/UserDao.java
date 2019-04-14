package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.User;


@Repository("userDao")
public class UserDao {

	@Resource
	private BaseDao<User> baseDao;
	
	public User getUserByUsername(String username) {
		return baseDao.get("select c from User c where c.user_name=?0", new Object[] {username}, User.class);
	}

	public void saveUser(User user) {
		baseDao.save(user);
	}
	
	public void saveOrUpdateUser(User user) {
		baseDao.saveOrUpdate(user);
	}
	
}
