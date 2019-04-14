package db.service;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.Comment_User_To_DeliverDao;
import db.dao.Comment_User_To_ShopDao;
import db.dao.Location_Information_UserDao;
import db.dao.OrderDao;
import db.dao.ShopDao;
import db.dao.UserDao;
import db.dao.User_LikeDao;
import db.entity.Comment_User_To_Deliver;
import db.entity.Comment_User_To_Shop;
import db.entity.Location_Information_User;
import db.entity.Order;
import db.entity.Shop;
import db.entity.User;
import db.entity.UserLike;


@Transactional
@Service("userService")
public class UserServiceImpl implements UserService{

	
	@Resource
	private UserDao userDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	User_LikeDao user_likeDao;
	
	@Resource
	private ShopDao shopDao;
	
	@Resource
	Comment_User_To_ShopDao comment_User_To_ShopDao ;
	
	@Resource
	Comment_User_To_DeliverDao comment_User_To_DeliverDao ;
	
	@Resource
	private Location_Information_UserDao location_Information_UserDao;
	
	
	@Override
	public Map<String, Object> register(String user_name, String user_psd,Integer image) {
		
		Map<String,Object> result=new HashMap<>();
		
		if(user_name==null) {
			result.put("Result", "Error");
			result.put("Reason", "user_name is null");
			return result;
		}if(user_psd==null) {
			result.put("Result", "Error");
			result.put("Reason", "user_psd is null");
			return result;
		}
		
		if(userDao.getUserByUsername(user_name) != null) {
			result.put("Result", "Error");
			result.put("Reason", "Username exists");
			return result;
		}
				
		User myuser=new User();
		Location_Information_User user_location = new Location_Information_User();
		myuser.setUser_name(user_name);
		myuser.setUser_psd(user_psd);
		myuser.setImage(image);
		myuser.setLocation_information_user(user_location);
		userDao.saveUser(myuser);

		result.put("Result", "Success");
		return result;
	}
	
	@Override
	public Map<String, Object> getUserInformation(String user_name) {
		Map<String,Object> result=new HashMap<>();
		User user = userDao.getUserByUsername(user_name);
		
		if(user == null) {
			result.put("Result", "Error");
			result.put("Reason", "user not exist");
		}else {
			result.put("Result", "Success");
			result.put("user", user);
		}
		return result;
		
	}

	@Override
	public Map<String, Object> setUserInformation(String user_name,String user_nickname,String user_mobile,String user_gender,String user_email) {
		Map<String,Object> result=new HashMap<>();
		User user = userDao.getUserByUsername(user_name);

		user.setUser_nickname(user_nickname);
		user.setUser_email(user_email);
		user.setUser_gender(user_gender);
		user.setUser_mobile(user_mobile);
		
		userDao.saveUser(user);

		result.put("Result", "Success");
		return result;
		
	}
	
	@Override
	public Map<String, Object> getUserLocation(String user_name) {
		
		Map<String,Object> result=new HashMap<>();
		User user = userDao.getUserByUsername(user_name);
		
		if(user == null) {
			result.put("Result", "Error");
			result.put("Reason", "user not exist");
		}
		
		Location_Information_User user_location = user.getLocation_information_user();

		if(user_location == null) {
			result.put("Result", "Error");
			result.put("Reason", "user_location not exist");
		}else {
			result.put("Result", "Success");
			result.put("user_location", user_location);
		}
		return result;
		
	}
	
	@Override
	public Map<String, Object> setUserLocation(String user_name, String province, String city, String county,
			String specific_location) {
		
		Map<String,Object> result=new HashMap<>();
		User user = userDao.getUserByUsername(user_name);
		
//		try {
//			FileUtil.putDataToFile(new File("D:\\eeworkspace\\DBDesign\\text.txt"),province);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		if(user == null) {
			result.put("Result", "Error");
			result.put("Reason", "user not exist");
		}
		System.out.println(province);
		
		Location_Information_User user_location = user.getLocation_information_user();

		if(user_location == null) {
			result.put("Result", "Error");
			result.put("Reason", "user_location not exist");
		}else {
			user_location.setCity(city);
			user_location.setCounty(county);
			user_location.setProvince(province);
			user_location.setSpecific_location(specific_location);
			
			location_Information_UserDao.save_User_Location(user_location);
			
			result.put("Result", "Success");
			result.put("user_location", user_location);
		}
		return result;
		
	}
	
	@Override
	public Map<String, Object> UserBalance_in_money(String user_name, Double in_money) {
		Map<String,Object> result=new HashMap<>();
		User user = userDao.getUserByUsername(user_name);
		
		Double former = user.getUser_balance();
		Double now = former+in_money;
		
		user.setUser_balance(now);
		userDao.saveUser(user);
		
		result.put("Result", "Success");
		return result;
		
	}

	@Override
	public Map<String, Object> UserBalance_out_money(String user_name, Double out_money) {
		Map<String,Object> result=new HashMap<>();
		User user = userDao.getUserByUsername(user_name);
		
		Double former = user.getUser_balance();
		if(former<out_money) {
			result.put("Result", "Error");
			result.put("Reason", "balance not enough");
		}else {
			Double now = former-out_money;
			user.setUser_balance(now);
			userDao.saveUser(user);
			result.put("Result", "Success");
		}
		
		return result;
		
	}
	
	@Override
	public Map<String, Object> getCommentToShop(String user_name) {
		Map<String,Object> result=new HashMap<>();
		List<Comment_User_To_Shop> ToShopComments = comment_User_To_ShopDao.getCommentListByUserName(user_name);
		
		if(ToShopComments == null) {
			result.put("Result", "Error");
			result.put("Reason", "ToShopComments not exist");
		}else {
			result.put("Result", "Success");
			result.put("ToShopComments", ToShopComments);
		}
		return result;
	}

	@Override
	public Map<String, Object> getCommentToDeliver(String user_name) {
		
		Map<String,Object> result=new HashMap<>();
		List<Comment_User_To_Deliver> ToDeliverComments = comment_User_To_DeliverDao.getCommentListByUserName(user_name);
		
		if(ToDeliverComments == null) {
			result.put("Result", "Error");
			result.put("Reason", "ToDeliverComments not exist");
		}else {
			result.put("Result", "Success");
			result.put("ToDeliverComments", ToDeliverComments);
		}
		return result;
		
	}

	@Override
	public Map<String, Object> setCommentToShop(String user_name, String shop_name, String comment, Double score) {
		Map<String,Object> result=new HashMap<>();
		Comment_User_To_Shop comment_User_To_Shop = new Comment_User_To_Shop();
		
		comment_User_To_Shop.setComment(comment);
		comment_User_To_Shop.setScore(score);
		comment_User_To_Shop.setUser_name(user_name);
		comment_User_To_Shop.setShop_name(shop_name);

		comment_User_To_ShopDao.saveComment_User_To_Shop(comment_User_To_Shop); 
		result.put("Result", "Success");
		return result;
		
	}

	@Override
	public Map<String, Object> setCommentToDeliver(String user_name, String deliver_name, String comment,
			Double score) {
		Map<String,Object> result=new HashMap<>();
		Comment_User_To_Deliver comment_User_To_Deliver = new Comment_User_To_Deliver();
		
		comment_User_To_Deliver.setComment(comment);
		comment_User_To_Deliver.setScore(score);
		comment_User_To_Deliver.setUser_name(user_name);
		comment_User_To_Deliver.setDeliver_name(deliver_name);

		comment_User_To_DeliverDao.saveComment_User_To_Deliver(comment_User_To_Deliver); 
		result.put("Result", "Success");
		return result;
	}

	@Override
	public Map<String, Object> getShopByShopTag(String Tagname) {
		Map<String,Object> result=new HashMap<>();
		List<Shop> shopList = shopDao.getShopByShopTag(Tagname);
		
		if(shopList == null) {
			result.put("Result", "Error");
			result.put("Reason", "shopList not exist");
		}else {
			result.put("Result", "Success");
			result.put("shopList", shopList);
		}
		return result;
	}

	@Override
	public Map<String, Object> getAllShop() {
		
		Map<String,Object> result=new HashMap<>();
		List<Shop> shopList = shopDao.getAllShop();
		
		if(shopList == null) {
			result.put("Result", "Error");
			result.put("Reason", "shopList not exist");
		}else {
			result.put("Result", "Success");
			result.put("shopList", shopList);
		}
		return result;
	
	}

	@Override
	public Map<String, Object> getAllOrder(String user_name) {
		Map<String,Object> result=new HashMap<>();
		 List<Order> orderList = orderDao.getFinishedOrderByUserName(user_name);
		 
		 if(orderList == null) {
				result.put("Result", "Error");
				result.put("Reason", "orderList not exist");
			}else {
				result.put("Result", "Success");
				result.put("orderList", orderList);
			}
			return result;
	}

	@Override
	public Map<String, Object> getUserLikeShop(String user_name) {
		Map<String,Object> result=new HashMap<>();
		List<UserLike> shopList = user_likeDao.getByUserName(user_name);
		if(shopList == null) {
			result.put("Result", "Error");
			result.put("Reason", "shopList not exist");
		}else {
			result.put("Result", "Success");
			result.put("shopList", shopList);
		}
		return result;
		 
	}

	@Override
	public Map<String, Object> addUserLikeShop(String user_name, String shop_name) {
		Map<String,Object> result=new HashMap<>();
		UserLike u = new UserLike();
		u.setShop_name(shop_name);
		u.setUser_name(user_name);
		List<UserLike> former = user_likeDao.getByUserName(user_name);
		for(int i =0;i<former.size();i++) {
			if(shop_name.equals(former.get(i).getShop_name())) {
				result.put("Result", "Error");
				return result;
			}
		}
		user_likeDao.addUserLikeShop(u);
		result.put("Result", "Success");
		return result;
	}


	




}
