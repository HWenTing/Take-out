package db.service;

import java.util.Map;

import db.entity.Shop;
import db.entity.User;


public interface ShopService {

	Map<String, Object> register(String shop_name, String shop_psd,Integer image);
	
	Map<String, Object> getShopInformation(String shop_name);
	
	Map<String, Object> setShopInformation(String shop_name,String shop_nickname,String shop_mobile,String shop_license,String shop_email,String shop_tag);
	
	Map<String, Object> getShopLocation(String shop_name);
	
	Map<String, Object> setShopLocation(String shop_name,String province,String city,String county,String specific_location);
	
	Map<String, Object> ShopBalance_in_money(String shop_name,Double in_money);
	
	Map<String, Object> ShopBalance_out_money(String shop_name,Double out_money);
	
	
	
	Map<String, Object> getCommentsFromUser (String shop_name);//得到用户对其的所有评价
	
	
	Map<String, Object> addFood(String shop_name ,String food_name, Double food_price,String food_description,Integer image);

	Map<String, Object> deleteFood(Integer food_id);
	
	Map<String, Object> changeFood(Integer food_id,String food_name,String food_description,Double food_price);
	
	
	Map<String, Object> getFoodByShopName(String shop_name);
	
	
	


}