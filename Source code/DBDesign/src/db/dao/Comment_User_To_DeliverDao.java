package db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Comment_User_To_Deliver;
import db.entity.Comment_User_To_Shop;
import db.entity.Deliver;
import db.entity.User;



@Repository("comment_User_To_DeliverDao")
public class Comment_User_To_DeliverDao {

	@Resource
	private BaseDao<Comment_User_To_Deliver> baseDao;
	
	public void addComment_User_To_Deliver(String user_name,String deliver_name,String comment,Double score) {
		Comment_User_To_Deliver comments=new Comment_User_To_Deliver();
		comments.setUser_name(user_name);
		comments.setDeliver_name(deliver_name);

		comments.setComment(comment);
		comments.setScore(score);
		baseDao.saveOrUpdate(comments);
	}
	
	public List<Comment_User_To_Deliver> getCommentListByUserName(String user_name){
		return baseDao.find("select c from Comment_User_To_Deliver c where c.user_name=?0", new Object[] {user_name}, Comment_User_To_Deliver.class);
	}
	
	public List<Comment_User_To_Deliver> getCommentListByDeliverName(String deliver_name){
		return baseDao.find("select c from Comment_User_To_Deliver c where c.deliver_name=?0", new Object[] {deliver_name}, Comment_User_To_Deliver.class);
	}
	
	public void saveComment_User_To_Deliver(Comment_User_To_Deliver comment_User_To_Deliver) {
		baseDao.save(comment_User_To_Deliver);
	}
	
}
