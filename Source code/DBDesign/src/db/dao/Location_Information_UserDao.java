package db.dao;


import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Location_Information_User;






@Repository("location_Information_UserDao")
public class Location_Information_UserDao {
	@Resource
	private BaseDao<Location_Information_User> baseDao;
	
	public Location_Information_User getLocationByid(Integer id) {
		return baseDao.get("select c from Location_Information_User c where c.location_information_id=?0", new Object[] {id}, Location_Information_User.class);
	}
	
	public void save_User_Location(Location_Information_User lis) {
		baseDao.save(lis);
	}
	
	public void saveOrUpdate_User_Location(Location_Information_User lis) {
		baseDao.saveOrUpdate(lis);
	}
	
	
}