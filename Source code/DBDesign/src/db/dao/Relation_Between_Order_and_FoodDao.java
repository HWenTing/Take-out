package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Food;
import db.entity.Relation_Between_Order_and_Food;

@Repository("relation_Between_Order_and_FoodDao")

public class Relation_Between_Order_and_FoodDao {

	@Resource
	private BaseDao<Relation_Between_Order_and_Food> baseDao;
	
	
	public List<Relation_Between_Order_and_Food> getByOrderId(Integer orderId) {
		return baseDao.find("select c from Relation_Between_Order_and_Food c where c.order_id=?0", new Object[] {orderId}, Relation_Between_Order_and_Food.class);
	}

	public Relation_Between_Order_and_Food getByOrderAndFoodId(Integer orderId,Integer foodId) {
		return baseDao.get("select c from Relation_Between_Order_and_Food c where c.order_id=?0 and c.food_id=?1", new Object[] {orderId,foodId}, Relation_Between_Order_and_Food.class);
	}
	
	public void saveRelation(Relation_Between_Order_and_Food relation_Between_Order_and_Food) {
		baseDao.save(relation_Between_Order_and_Food);
	}
	
	public void saveOrUpdateRelation(Relation_Between_Order_and_Food relation_Between_Order_and_Food) {
		baseDao.saveOrUpdate(relation_Between_Order_and_Food);
	}
	
	public void deleteRelation(Relation_Between_Order_and_Food relation_Between_Order_and_Food) {
		baseDao.delete(relation_Between_Order_and_Food);
	}

	
	
}
