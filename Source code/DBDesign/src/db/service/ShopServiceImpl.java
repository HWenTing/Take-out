package db.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.Comment_User_To_DeliverDao;
import db.dao.Comment_User_To_ShopDao;
import db.dao.FoodDao;
import db.dao.Location_Information_ShopDao;
import db.dao.ShopDao;
import db.entity.Comment_User_To_Shop;
import db.entity.Food;
import db.entity.Location_Information_Shop;
import db.entity.Location_Information_User;
import db.entity.Shop;
import db.entity.User;

@Transactional
@Service("shopService")
public class ShopServiceImpl implements ShopService{

	@Resource
	private FoodDao foodDao;
	
	@Resource
	private ShopDao shopDao;
	
	@Resource
	Comment_User_To_ShopDao comment_User_To_ShopDao ;
	
	@Resource
	Comment_User_To_DeliverDao comment_User_To_DeliverDao ;
	
	@Resource
	private Location_Information_ShopDao location_Information_ShopDao;
	
	
	@Override
	public Map<String, Object> register(String shop_name, String shop_psd,Integer image) {
		Map<String,Object> result=new HashMap<>();
		
		if(shop_name==null) {
			result.put("Result", "Error");
			result.put("Reason", "shop_name is null");
			return result;
		}if(shop_psd==null) {
			result.put("Result", "Error");
			result.put("Reason", "shop_psd is null");
			return result;
		}
		
		if(shopDao.getShopByShopname(shop_name) != null) {
			result.put("Result", "Error");
			result.put("Reason", "Shopname exists");
			return result;
		}
				
		Shop myshop=new Shop();
		Location_Information_Shop shop_location = new Location_Information_Shop();
		myshop.setShop_name(shop_name);
		myshop.setShop_psd(shop_psd);
		myshop.setImage(image);
		
		myshop.setLocation_information_shop(shop_location);
		shopDao.saveShop(myshop);

		result.put("Result", "Success");
		return result;
	}

	@Override
	public Map<String, Object> getShopInformation(String shop_name) {
		Map<String,Object> result=new HashMap<>();
		Shop shop = shopDao.getShopByShopname(shop_name);
		
		if(shop == null) {
			result.put("Result", "Error");
			result.put("Reason", "shop not exist");
		}else {
			result.put("Result", "Success");
			result.put("shop", shop);
		}
		return result;
	}

	@Override
	public Map<String, Object> setShopInformation(String shop_name, String shop_nickname, String shop_mobile,
			String shop_license, String shop_email,String shop_tag) {
		
		Map<String,Object> result=new HashMap<>();
		Shop shop = shopDao.getShopByShopname(shop_name);

		shop.setShop_email(shop_email);
		shop.setShop_license(shop_license);
		shop.setShop_mobile(shop_mobile);
		shop.setShop_nickname(shop_nickname);
		shop.setShop_tag(shop_tag);
		shopDao.saveShop(shop);

		result.put("Result", "Success");
		return result;
		
	}

	@Override
	public Map<String, Object> getShopLocation(String shop_name) {
		
		Map<String,Object> result=new HashMap<>();
		Shop shop = shopDao.getShopByShopname(shop_name);
		
		if(shop == null) {
			result.put("Result", "Error");
			result.put("Reason", "shop not exist");
		}
		
		Location_Information_Shop shop_location = shop.getLocation_information_shop();

		if(shop_location == null) {
			result.put("Result", "Error");
			result.put("Reason", "shop_location not exist");
		}else {
			result.put("Result", "Success");
			result.put("shop_location", shop_location);
		}
		return result;
		
	}

	@Override
	public Map<String, Object> setShopLocation(String shop_name, String province, String city, String county,
			String specific_location) {
		
		Map<String,Object> result=new HashMap<>();
		Shop shop = shopDao.getShopByShopname(shop_name);
		
		if(shop == null) {
			result.put("Result", "Error");
			result.put("Reason", "shop not exist");
		}
		
		Location_Information_Shop shop_location = shop.getLocation_information_shop();
		if(shop_location == null) {
			result.put("Result", "Error");
			result.put("Reason", "shop_location not exist");
		}else {
			shop_location.setCity(city);
			shop_location.setCounty(county);
			shop_location.setProvince(province);
			shop_location.setSpecific_location(specific_location);
			
			location_Information_ShopDao.save_Shop_Location(shop_location);
			
			result.put("Result", "Success");
			result.put("shop_location", shop_location);
		}
		return result;
		
	}

	@Override
	public Map<String, Object> ShopBalance_in_money(String shop_name, Double in_money) {
		
		Map<String,Object> result=new HashMap<>();
		Shop shop = shopDao.getShopByShopname(shop_name);
		
		Double former = shop.getShop_balance();
		Double now = former+in_money;
		
		shop.setShop_balance(now);
		shopDao.saveShop(shop);
		
		result.put("Result", "Success");
		return result;
		
	}

	@Override
	public Map<String, Object> ShopBalance_out_money(String shop_name, Double out_money) {
		
		Map<String,Object> result=new HashMap<>();
		Shop shop = shopDao.getShopByShopname(shop_name);
		
		Double former = shop.getShop_balance();
		if(former<out_money) {
			result.put("Result", "Error");
			result.put("Reason", "balance not enough");
		}else {
			Double now = former-out_money;
			shop.setShop_balance(now);
			shopDao.saveShop(shop);
			result.put("Result", "Success");
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getCommentsFromUser(String shop_name) {
		
		Map<String,Object> result=new HashMap<>();
		List<Comment_User_To_Shop> ShopComments = comment_User_To_ShopDao.getCommentListByShopName(shop_name);
		
		if(ShopComments == null) {
			result.put("Result", "Error");
			result.put("Reason", "ShopComments not exist");
		}else {
			result.put("Result", "Success");
			result.put("ShopComments", ShopComments);
		}
		return result;
		
		
	}

	@Override
	public Map<String, Object> addFood(String shop_name, String food_name, Double food_price, String food_description,Integer image) {
		Map<String,Object> result=new HashMap<>();
		List<Food> foodList = foodDao.getFoodByShopName(shop_name);
		for(int i=0;i<foodList.size();i++) {
			if(foodList.get(i).getFood_name().equals(food_name)) {
				result.put("Result", "Error");
				result.put("Reason", "has existed");
				return result;
			}
		}
		
		Food food = new Food();
		food.setShop_name(shop_name);
		food.setFood_description(food_description);
		food.setFood_name(food_name);
		food.setPrice(food_price);
		food.setImage(image);
		
		foodDao.saveFood(food);
		result.put("Result", "Success");
		return result;
	}

	@Override
	public Map<String, Object> deleteFood(Integer food_id) {
		Map<String,Object> result=new HashMap<>();
		Food food = foodDao.getFoodById(food_id);
		foodDao.deleteFood(food);
		
		result.put("Result", "Success");
		return result;
		
	}

	@Override
	public Map<String, Object> getFoodByShopName(String shop_name) {
		Map<String,Object> result=new HashMap<>();
		List<Food> foodList = foodDao.getFoodByShopName(shop_name);
		
		if(foodList == null) {
			result.put("Result", "Error");
			result.put("Reason", "foodList not exist");
		}else {
			result.put("Result", "Success");
			result.put("foodList", foodList);
		}
		return result;
		
		
	}

	@Override
	public Map<String, Object> changeFood(Integer food_id,String food_name,String food_description,Double food_price) {
		Map<String,Object> result=new HashMap<>();
		Food food = foodDao.getFoodById(food_id);
		food.setFood_name(food_name);
		food.setFood_description(food_description);
		food.setPrice(food_price);
		
		foodDao.saveFood(food);
		result.put("Result", "Success");
		return result;
		
	}



	

}
