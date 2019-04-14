package db.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import db.dao.Comment_User_To_DeliverDao;
import db.dao.Comment_User_To_ShopDao;
import db.dao.DeliverDao;
import db.entity.Comment_User_To_Deliver;
import db.entity.Comment_User_To_Shop;
import db.entity.Deliver;
import db.entity.Location_Information_Shop;
import db.entity.Shop;

@Transactional
@Service("deliverService")
public class DeliverServiceImpl implements DeliverService{

	@Resource
	private DeliverDao deliverDao;
	
	@Resource
	Comment_User_To_ShopDao comment_User_To_ShopDao ;
	
	@Resource
	Comment_User_To_DeliverDao comment_User_To_DeliverDao ;
	
	
	@Override
	public Map<String, Object> register(String deliver_name, String deliver_psd,Integer image) {
		Map<String,Object> result=new HashMap<>();
		
		if(deliver_name==null) {
			result.put("Result", "Error");
			result.put("Reason", "deliver_name is null");
			return result;
		}if(deliver_psd==null) {
			result.put("Result", "Error");
			result.put("Reason", "deliver_psd is null");
			return result;
		}
		
		if(deliverDao.getDeliverByDelivername(deliver_name) != null) {
			result.put("Result", "Error");
			result.put("Reason", "Deliver_name exists");
			return result;
		}
				
		Deliver mydeliver=new Deliver();
		
		mydeliver.setDeliver_name(deliver_name);
		mydeliver.setDeliver_psd(deliver_psd);
		mydeliver.setImage(image);
		deliverDao.saveDeliver(mydeliver);

		result.put("Result", "Success");
		return result;
		
		
	}

	@Override
	public Map<String, Object> getDeliverInformation(String deliver_name) {
		Map<String,Object> result=new HashMap<>();
		Deliver deliver = deliverDao.getDeliverByDelivername(deliver_name);
		
		if(deliver == null) {
			result.put("Result", "Error");
			result.put("Reason", "deliver not exist");
		}else {
			result.put("Result", "Success");
			result.put("deliver", deliver);
		}
		return result;
	}

	@Override
	public Map<String, Object> setDeliverInformation(String deliver_name, String deliver_mobile,
			String deliver_nickname, String deliver_IDcard) {
		
		Map<String,Object> result=new HashMap<>();
		Deliver deliver = deliverDao.getDeliverByDelivername(deliver_name);

		deliver.setDeliver_mobile(deliver_mobile);
		deliver.setDeliver_IDcard(deliver_IDcard);
		deliver.setDeliver_nickname(deliver_nickname);

		deliverDao.saveDeliver(deliver);

		result.put("Result", "Success");
		return result;
		
		
	}

	@Override
	public Map<String, Object> DeliverBalance_in_money(String deliver_name, Double in_money) {
		
		Map<String,Object> result=new HashMap<>();
		Deliver deliver = deliverDao.getDeliverByDelivername(deliver_name);
		
		Double former = deliver.getDeliver_balance();
		Double now = former+in_money;
		
		deliver.setDeliver_balance(now);
		deliverDao.saveDeliver(deliver);
		
		result.put("Result", "Success");
		return result;
	}

	@Override
	public Map<String, Object> DeliverBalance_out_money(String deliver_name, Double out_money) {
		
		Map<String,Object> result=new HashMap<>();
		Deliver deliver = deliverDao.getDeliverByDelivername(deliver_name);
		
		Double former = deliver.getDeliver_balance();
		if(former<out_money) {
			result.put("Result", "Error");
			result.put("Reason", "balance not enough");
		}else {
			Double now = former-out_money;
			deliver.setDeliver_balance(now);
			deliverDao.saveDeliver(deliver);
			result.put("Result", "Success");
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getCommentsFromUser(String deliver_name) {
		
		Map<String,Object> result=new HashMap<>();
		List<Comment_User_To_Deliver> DeliverComments = comment_User_To_DeliverDao.getCommentListByDeliverName(deliver_name);
				
		
		if(DeliverComments == null) {
			result.put("Result", "Error");
			result.put("Reason", "DeliverComments not exist");
		}else {
			result.put("Result", "Success");
			result.put("DeliverComments", DeliverComments);
		}
		return result;
	}

	@Override
	public Map<String, Object> geReceivedOrder(String deliver_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAvailableOrder() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
