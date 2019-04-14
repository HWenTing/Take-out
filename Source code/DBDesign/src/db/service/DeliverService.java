package db.service;

import java.util.Map;

import db.entity.Deliver;
import db.entity.Shop;

public interface DeliverService {

	//骑手注册
	Map<String, Object> register(String deliver_name, String deliver_psd,Integer image);
	//获得骑手基本信息
	Map<String, Object> getDeliverInformation(String deliver_name);
	//设置骑手基本信息
	Map<String, Object> setDeliverInformation(String deliver_name,String deliver_mobile,String deliver_nickname,String deliver_IDcard);
	//转入金额
	Map<String, Object> DeliverBalance_in_money(String deliver_name,Double in_money);
	//提取金额
	Map<String, Object> DeliverBalance_out_money(String deliver_name,Double out_money);
	//	获得用户对其评价
	Map<String, Object> getCommentsFromUser (String deliver_name);
	
	//	获取完成的订单
	Map<String, Object> geReceivedOrder(String deliver_name);
	//获取可接受的订单
	Map<String, Object> getAvailableOrder();


}