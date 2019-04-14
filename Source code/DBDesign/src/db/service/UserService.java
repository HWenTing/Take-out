package db.service;



import java.util.Map;

import db.entity.User;

public interface UserService {
	
	Map<String, Object> register(String user_name, String user_psd,Integer image);
	
	Map<String, Object> getUserInformation(String user_name);
	
	Map<String, Object> setUserInformation(String user_name,String user_nickname,String user_mobile,String user_gender,String user_email);
	
	Map<String, Object> getUserLocation(String user_name);
	
	Map<String, Object> setUserLocation(String user_name,String province,String city,String county,String specific_location);
	
	Map<String, Object> UserBalance_in_money(String user_name,Double in_money);
	
	Map<String, Object> UserBalance_out_money(String user_name,Double out_money);
	
	Map<String, Object> getShopByShopTag(String Tagname);
	
	Map<String, Object> getAllShop();
	
	//获取用户喜欢的商家
	Map<String, Object> getUserLikeShop(String user_name); 
	
	//添加喜欢商家
	Map<String, Object> addUserLikeShop(String user_name,String shop_name);
	
	
	//得到历史订单
	Map<String, Object> getAllOrder(String user_name);
	
	
	//得到对所有商店的评论
	Map<String, Object> getCommentToShop(String user_name);
	
	//得到对所有骑手的评论
	Map<String, Object> getCommentToDeliver(String user_name);
	
	//给某个商家评论
	Map<String, Object> setCommentToShop(String user_name,String shop_name,String comment,Double score);
	
	//给某个骑手评论
	Map<String, Object> setCommentToDeliver(String user_name,String deliver_name,String comment,Double score);
	



}