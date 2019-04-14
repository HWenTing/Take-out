package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Order;

@Repository("orderDao")
public class OrderDao {
	
	@Resource
	private BaseDao<Order> baseDao;
	

	public Order getOrderByOrderID(Integer orderID){
		 
		return baseDao.get("select o from Order o where o.order_id=?0", new Object[]{orderID},Order.class);
	};
	
	public List<Order> getFinishedOrderByDeliverName(String deliverName){
		List<Order> l = baseDao.find("select o from Order o where o.deliver_name=?0 and o.order_state=?1", new Object[]{deliverName,"ed"},Order.class);
		return l;
	};
	
	public List<Order> getFinishedOrderByShopName(String shopName){
		List<Order> l = baseDao.find("select o from Order o where o.shop_name=?0 and o.order_state=?1", new Object[]{shopName,"ed"},Order.class);
		return l;
	};
	
	public List<Order> getFinishedOrderByUserName(String userName){
		List<Order> l = baseDao.find("select o from Order o where o.user_name=?0 and o.order_state=?1", new Object[]{userName,"ed"},Order.class);
		return l;
	};
	
	public List<Order> getunFinishedOrderByUserName(String userName){
		List<Order> l = baseDao.find("select o from Order o where o.user_name=?0 and (o.order_state=?1 or o.order_state=?2 or o.order_state=?3)", new Object[]{userName,"available","ing","ini"},Order.class);
		return l;
	};
	
	public List<Order> getOrderByState(String state){
		List<Order> l = baseDao.find("select o from Order o where o.order_state=?0", new Object[]{state},Order.class);
		return l;
	};
	
	public List<Order> getOrderNeededCertainByShopName(String shop_name){
		List<Order> l = baseDao.find("select o from Order o where o.order_state=?0 and o.shop_name=?1", new Object[]{"ini",shop_name},Order.class);
		return l;
	};
	
	public List<Order> getOrderWaitDeliverByShopName(String shop_name){
		List<Order> l = baseDao.find("select o from Order o where o.order_state=?0 and o.shop_name=?1", new Object[]{"available",shop_name},Order.class);
		return l;
	};
	
	public List<Order> getOrderWaitFinishByShopName(String shop_name){
		List<Order> l = baseDao.find("select o from Order o where o.order_state=?0 and o.shop_name=?1", new Object[]{"ing",shop_name},Order.class);
		return l;
	};
	
	
	public List<Order> getOrderWaitFinishByDeliverName(String deliver_name){
		List<Order> l = baseDao.find("select o from Order o where o.order_state=?0 and o.deliver_name=?1", new Object[]{"ing",deliver_name},Order.class);
		return l;
	};
//	
//	public List<Order> getOrderWaitFinishByUserName(String user_name){
//		List<Order> l = baseDao.find("select o from Order o where o.order_state=?0 and o.user_name=?1", new Object[]{"ing",user_name},Order.class);
//		return l;
//	};
	
	
	
	public void saveOrder(Order order) {
		baseDao.save(order);
	}
	
	public void saveOrUpdateOrder(Order order) {
		baseDao.saveOrUpdate(order);
	}
	
}