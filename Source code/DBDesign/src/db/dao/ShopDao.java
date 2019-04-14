package db.dao;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Shop;




@Repository("shopDao")
public class ShopDao {
	
	@Resource
	private BaseDao<Shop> baseDao;


	public Shop getShopById(Long id) {
		return baseDao.get(Shop.class, id);
	}
	
	public List<Shop> geShopByFuzzyName(String fuzzyName) {
		return baseDao.find("select r from Restaurant r where r.restaurantName like ?0", 
				new Object[] {"%"+fuzzyName+"%"}, Shop.class);
	}
	
	public Shop getShopByShopname(String username) {
		return baseDao.get("select r from Shop r where r.shop_name=?0", new Object[] {username}, Shop.class);
	}
		
	public List<Shop> getShopByShopTag(String Tagname) {
		return baseDao.find("select r from Shop r where r.shop_tag=?0 ", new Object[] {Tagname}, Shop.class);
	}
	
	public List<Shop> getAllShop(){
		return baseDao.find("select  r from Shop r",Shop.class);
	}
	
	public void saveShop(Shop shop) {
		baseDao.save(shop);
	}
	
	public void saveOrUpdateShop(Shop shop) {
		baseDao.saveOrUpdate(shop);
	}
}