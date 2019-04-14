package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Food;
import db.entity.User;


@Repository("foodDao")
public class FoodDao {

	@Resource
	private BaseDao<Food> baseDao;
	
	public void saveFood(Food food) {
		baseDao.save(food);
	}
	
	public void deleteFood(Food food) {
		baseDao.delete(food);
	}
	
	public List<Food> getFoodByShopName(String shop_name){
		return baseDao.find("select c from Food c where c.shop_name=?0", new Object[] {shop_name}, Food.class);
	}
	
	
	
	public Food getFoodById(Integer food_id) {
		return baseDao.get("select c from Food c where c.food_id=?0", new Object[] {food_id}, Food.class);

	}
	
	
}