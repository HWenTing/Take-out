package db.service;

import java.util.Map;

public interface OrderService {

	
	/*
	 * user
	 */
	
	//�򶩵�����Ӳ�Ʒ
	Map<String, Object> addFood_to_order(Integer order_id,Integer food_id);
	//�Ӷ�����ɾ����Ʒ
	Map<String, Object> deleteFood_to_order(Integer order_id,Integer food_id);
	//���ɶ���
	Map<String, Object> generateOrder(String user_name,String shop_name,Double price,String currentTime,String food_list,String image_list);
	
	//��ȡ����ɵĶ���
	Map<String, Object> getfinishedOrder_user(String user_name);
	
	//��ȡ����ɵĶ���
	Map<String, Object> getunfinishedOrder_user(String user_name);
	
	
	/*
	 * deliver
	 */
	
	//���ܶ���
	Map<String, Object> receiveOrder(String deliver_name,Integer order_id);
	//��ɶ���
	Map<String, Object> finishOrder(String deliver_name,Integer order_id);
	//��ȡ�ɽ��ܶ���
	Map<String, Object> getAvailableOrder();
	//��ȡ�������͵Ķ���
	Map<String, Object> getWaitFinishOrder_deliver(String deliver_name);
	
	//��ȡ����ɶ���
	Map<String, Object> getfinishedOrder_deliver(String deliver_name);
	
	
	/*
	 * shop
	 */
	
	//ȷ�϶�������
	Map<String, Object> certainOrder(Integer order_id);
	
	
	//�õ���ɹ��Ķ���
	Map<String, Object> getfinishedOrder_shop(String shop_name);
	//�õ���Ҫȷ�϶����б�
	Map<String, Object> getNeedCertainOrder(String shop_name);
	
	//�õ��ȴ����ֶ����б�
	Map<String, Object> getWaitDeliverOrder(String shop_name);
	
	//�õ������ʹﶩ���б�
	Map<String, Object> getWaitFinishOrder(String shop_name);
	
	//���ݶ����õ�ʳ���б�
	Map<String, Object> getFoodByOrder(Integer order_id);
	
	//���ݶ����ŵõ�����
	Map<String, Object> getOrderByOrderID(Integer order_id);
	
}
