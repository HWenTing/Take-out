package db.service;

import java.util.Map;

import db.entity.Deliver;
import db.entity.Shop;

public interface DeliverService {

	//����ע��
	Map<String, Object> register(String deliver_name, String deliver_psd,Integer image);
	//������ֻ�����Ϣ
	Map<String, Object> getDeliverInformation(String deliver_name);
	//�������ֻ�����Ϣ
	Map<String, Object> setDeliverInformation(String deliver_name,String deliver_mobile,String deliver_nickname,String deliver_IDcard);
	//ת����
	Map<String, Object> DeliverBalance_in_money(String deliver_name,Double in_money);
	//��ȡ���
	Map<String, Object> DeliverBalance_out_money(String deliver_name,Double out_money);
	//	����û���������
	Map<String, Object> getCommentsFromUser (String deliver_name);
	
	//	��ȡ��ɵĶ���
	Map<String, Object> geReceivedOrder(String deliver_name);
	//��ȡ�ɽ��ܵĶ���
	Map<String, Object> getAvailableOrder();


}