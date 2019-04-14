package db.dao;


import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Location_Information_Shop;





@Repository("location_Information_ShopDao")
public class Location_Information_ShopDao {
	@Resource
	private BaseDao<Location_Information_Shop> baseDao;
	
	public Location_Information_Shop getLocationByid(Integer id) {
		return baseDao.get("select c from Location_Information_Shop c where c.location_information_id=?0", new Object[] {id}, Location_Information_Shop.class);
	}
	
	public void save_Shop_Location(Location_Information_Shop lis) {
		baseDao.save(lis);
	}
	
	public void saveOrUpdate_Shop_Location(Location_Information_Shop lis) {
		baseDao.saveOrUpdate(lis);
	}
	
	
}
