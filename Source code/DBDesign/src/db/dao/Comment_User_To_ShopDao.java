package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;


import db.entity.Comment_User_To_Shop;
import db.entity.Shop;
import db.entity.User;


@Repository("comment_User_To_ShopDao")
public class Comment_User_To_ShopDao {

	@Resource
	private BaseDao<Comment_User_To_Shop> baseDao;
	
	public void addComment_User_To_Shop(String user_name,String  shop_name,String comment,Double score) {
		Comment_User_To_Shop comments=new Comment_User_To_Shop();
		comments.setUser_name(user_name);
		comments.setShop_name(shop_name);
		comments.setComment(comment);
		comments.setScore(score);
		baseDao.saveOrUpdate(comments);
	}
	public List<Comment_User_To_Shop> getCommentListByUserName(String user_name){
		return baseDao.find("select c from Comment_User_To_Shop c where c.user_name=?0", new Object[] {user_name}, Comment_User_To_Shop.class);
	}
	
	public List<Comment_User_To_Shop> getCommentListByShopName(String shop_name){
		return baseDao.find("select c from Comment_User_To_Shop c where c.shop_name=?0", new Object[] {shop_name}, Comment_User_To_Shop.class);
	}
	
	public void saveComment_User_To_Shop(Comment_User_To_Shop comment_User_To_Shop) {
		baseDao.save(comment_User_To_Shop);
	}
}