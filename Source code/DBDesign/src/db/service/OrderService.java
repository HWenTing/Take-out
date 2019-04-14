package db.service;

import java.util.Map;

public interface OrderService {

	
	/*
	 * user
	 */
	
	//向订单中添加菜品
	Map<String, Object> addFood_to_order(Integer order_id,Integer food_id);
	//从订单中删除菜品
	Map<String, Object> deleteFood_to_order(Integer order_id,Integer food_id);
	//生成订单
	Map<String, Object> generateOrder(String user_name,String shop_name,Double price,String currentTime,String food_list,String image_list);
	
	//获取已完成的订单
	Map<String, Object> getfinishedOrder_user(String user_name);
	
	//获取已完成的订单
	Map<String, Object> getunfinishedOrder_user(String user_name);
	
	
	/*
	 * deliver
	 */
	
	//接受订单
	Map<String, Object> receiveOrder(String deliver_name,Integer order_id);
	//完成订单
	Map<String, Object> finishOrder(String deliver_name,Integer order_id);
	//获取可接受订单
	Map<String, Object> getAvailableOrder();
	//获取正在派送的订单
	Map<String, Object> getWaitFinishOrder_deliver(String deliver_name);
	
	//获取已完成订单
	Map<String, Object> getfinishedOrder_deliver(String deliver_name);
	
	
	/*
	 * shop
	 */
	
	//确认订单生成
	Map<String, Object> certainOrder(Integer order_id);
	
	
	//得到完成过的订单
	Map<String, Object> getfinishedOrder_shop(String shop_name);
	//得到需要确认订单列表
	Map<String, Object> getNeedCertainOrder(String shop_name);
	
	//得到等待骑手订单列表
	Map<String, Object> getWaitDeliverOrder(String shop_name);
	
	//得到骑手送达订单列表
	Map<String, Object> getWaitFinishOrder(String shop_name);
	
	//根据订单得到食物列表
	Map<String, Object> getFoodByOrder(Integer order_id);
	
	//根据订单号得到订单
	Map<String, Object> getOrderByOrderID(Integer order_id);
	
}
