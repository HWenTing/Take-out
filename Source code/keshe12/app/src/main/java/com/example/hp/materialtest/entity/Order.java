package com.example.hp.materialtest.entity;

public class Order {
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	private Order order;
	private Integer order_id;

	private String order_time;

	private Double order_price;

	private String shop_name;

	private String deliver_name;

	private String user_name;

	private Location_Information_User  location_information_user;

	private Location_Information_Shop  location_information_shop;
	/*
	 * ini 初始值
	 * available 被商家确定后
	 * ing 被后骑手接单
	 * ed 被骑手确认送达
	 */

	private String order_state="ini";

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public Double getOrder_price() {
		return order_price;
	}

	public void setOrder_price(Double order_price) {
		this.order_price = order_price;
	}



	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getDeliver_name() {
		return deliver_name;
	}

	public void setDeliver_name(String deliver_name) {
		this.deliver_name = deliver_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Location_Information_User getLocation_information_user() {
		return location_information_user;
	}

	public void setLocation_information_user(Location_Information_User location_information_user) {
		this.location_information_user = location_information_user;
	}

	public Location_Information_Shop getLocation_information_shop() {
		return location_information_shop;
	}

	public void setLocation_information_shop(Location_Information_Shop location_information_shop) {
		this.location_information_shop = location_information_shop;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}



	
	
}
