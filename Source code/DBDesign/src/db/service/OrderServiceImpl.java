package db.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.FoodDao;
import db.dao.Location_Information_ShopDao;
import db.dao.Location_Information_UserDao;
import db.dao.OrderDao;
import db.dao.Relation_Between_Order_and_FoodDao;
import db.dao.ShopDao;
import db.dao.UserDao;
import db.entity.Food;
import db.entity.Location_Information_Shop;
import db.entity.Location_Information_User;
import db.entity.Order;
import db.entity.Relation_Between_Order_and_Food;
import db.entity.Shop;
import db.entity.User;

@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Resource
	private Relation_Between_Order_and_FoodDao relation_Between_Order_and_FoodDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private ShopDao shopDao;
	
	@Resource
	private FoodDao foodDao;
	
	@Resource
	Location_Information_ShopDao location_Information_ShopDao;
	@Resource
	Location_Information_UserDao location_Information_UserDao;
	/*
	 * user
	 */
	
	@Override
	public Map<String, Object> addFood_to_order(Integer order_id, Integer food_id) {
		Map<String,Object> result=new HashMap<>();
		
		Relation_Between_Order_and_Food relation_Between_Order_and_Food = new Relation_Between_Order_and_Food();
		relation_Between_Order_and_Food.setFood_id(food_id);
		relation_Between_Order_and_Food.setOrder_id(order_id);
		
		relation_Between_Order_and_FoodDao.saveRelation(relation_Between_Order_and_Food);
		
		result.put("Result", "Success");
		return result;
	}

	@Override
	public Map<String, Object> deleteFood_to_order(Integer order_id, Integer food_id) {
		
		Map<String,Object> result=new HashMap<>();
		
		Relation_Between_Order_and_Food relation_Between_Order_and_Food = relation_Between_Order_and_FoodDao.getByOrderAndFoodId(order_id, food_id);
		
		relation_Between_Order_and_FoodDao.deleteRelation(relation_Between_Order_and_Food);		
		result.put("Result", "Success");
		return result;

	}

	@Override
	public Map<String, Object> generateOrder(String user_name, String shop_name ,Double price,String currentTime,String food_list,String image_list) {
		Map<String,Object> result=new HashMap<>();
		User user = userDao.getUserByUsername(user_name);
		Double former = user.getUser_balance();
		Integer userloid= user.getLocation_information_user().getLocation_information_id();
		Shop shop = shopDao.getShopByShopname(shop_name);
		Integer shoploid= shop.getLocation_information_shop().getLocation_information_id();
		 
		Location_Information_Shop location_information_shop =location_Information_ShopDao.getLocationByid(shoploid);
		
		Location_Information_User location_information_user =location_Information_UserDao.getLocationByid(userloid);
		
		List<String> foodIDList = Arrays.asList(food_list.split(","));

		
		if(former>=price) {
			Order order = new Order();
			order.setUser_name(user_name);
			order.setShop_name(shop_name);
			order.setOrder_price(price);
			order.setOrder_time(currentTime);
			
			order.setLocation_information_shop(location_information_shop);
			order.setLocation_information_user(location_information_user);
			
			orderDao.saveOrder(order);
			Integer order_id = order.getOrder_id();
			for(int i=0;i<foodIDList.size();i++) {
				Relation_Between_Order_and_Food s = new Relation_Between_Order_and_Food();
				s.setOrder_id(order_id);
				s.setFood_id(Integer.parseInt(foodIDList.get(i)));
				relation_Between_Order_and_FoodDao.saveRelation(s);
				
//				Food food = foodDao.getFoodById(Integer.parseInt(foodIDList.get(i)));
//				food.setImage(Integer.parseInt(imageList.get(i)));
//				foodDao.saveFood(food);
			}
			
			
			user.setUser_balance(former-price);
			userDao.saveUser(user);
			result.put("Result", "Success");
		}else {
			result.put("Result", "Error");
			result.put("Reason", "Óà¶î²»×ã");
		}
		
		return result;
		
		
		
	}
	@Override
	public Map<String, Object> getfinishedOrder_user(String user_name) {
		
		
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getFinishedOrderByUserName(user_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
		
	}
	
	
	/*
	 * deliver
	 */
	
	
	@Override
	public Map<String, Object> receiveOrder(String deliver_name, Integer order_id) {
		Map<String,Object> result=new HashMap<>();
		Order order = orderDao.getOrderByOrderID(order_id);
		
		order.setDeliver_name(deliver_name);
		order.setOrder_state("ing");
		
		orderDao.saveOrder(order);
		result.put("Result", "Success");
		return result;

	}

	@Override
	public Map<String, Object> finishOrder(String deliver_name, Integer order_id) {
		
		Map<String,Object> result=new HashMap<>();
		Order order = orderDao.getOrderByOrderID(order_id);
		
		order.setOrder_state("ed");
		
		orderDao.saveOrder(order);
		result.put("Result", "Success");
		return result;
		
	}

	@Override
	public Map<String, Object> getAvailableOrder() {
		
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getOrderByState("available");
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
	}

	@Override
	public Map<String, Object> getfinishedOrder_deliver(String deliver_name) {
		
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getFinishedOrderByDeliverName(deliver_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
		
		
	}
	
	
	/*
	 * shop
	 */
	

	@Override
	public Map<String, Object> certainOrder( Integer order_id) {
		
		Map<String,Object> result=new HashMap<>();
		Order order = orderDao.getOrderByOrderID(order_id);

		if(order == null) {
			result.put("Result", "Error");
			result.put("Reason", "order is null");
			
		}else {
			order.setOrder_state("available");
			result.put("Result", "Success");
	
		}
		return result;
		
		
	}

	@Override
	public Map<String, Object> getfinishedOrder_shop(String shop_name) {
		
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getFinishedOrderByShopName(shop_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
		
		
	}

	@Override
	public Map<String, Object> getNeedCertainOrder(String shop_name) {
		
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getOrderNeededCertainByShopName(shop_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
	}

	@Override
	public Map<String, Object> getWaitDeliverOrder(String shop_name) {
		Map<String,Object> result=new HashMap<>();
		
		List<Order> orderList = orderDao.getOrderWaitDeliverByShopName(shop_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
		
	}

	@Override
	public Map<String, Object> getWaitFinishOrder(String shop_name) {
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getOrderWaitFinishByShopName(shop_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
		
	}

	@Override
	public Map<String, Object> getWaitFinishOrder_deliver(String deliver_name) {
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getOrderWaitFinishByDeliverName(deliver_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
		
		
	}

	@Override
	public Map<String, Object> getunfinishedOrder_user(String user_name) {
		
		
		Map<String,Object> result=new HashMap<>();
		List<Order> orderList = orderDao.getunFinishedOrderByUserName(user_name);
		
		if(orderList.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			result.put("Result", "Success");
			result.put("orderList", orderList);
		}
		return result;
		
		
	}

	@Override
	public Map<String, Object> getFoodByOrder(Integer order_id) {
		Map<String,Object> result=new HashMap<>();
		
		List<Relation_Between_Order_and_Food> Relation_Between_Order_and_Food_List =relation_Between_Order_and_FoodDao.getByOrderId(order_id);
		
		
		if(Relation_Between_Order_and_Food_List.isEmpty()) {
			result.put("Result", "Error");
			result.put("Reason", "orderList is null");
			
		}else {
			List<Food> foodList = new ArrayList<>();
			for(int i =0;i<Relation_Between_Order_and_Food_List.size();i++) {
				foodList.add(foodDao.getFoodById(Relation_Between_Order_and_Food_List.get(i).getFood_id()));
			}
	
			result.put("Result", "Success");
			result.put("foodList", foodList);
		}
		return result;
	}

	@Override
	public Map<String, Object> getOrderByOrderID(Integer order_id) {
		Map<String,Object> result=new HashMap<>();
		Order order = orderDao.	getOrderByOrderID(order_id);
		
		
		result.put("Result", "Success");
		result.put("order", order);
		
		return result;

	}




}
