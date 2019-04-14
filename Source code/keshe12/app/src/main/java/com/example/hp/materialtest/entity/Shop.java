package com.example.hp.materialtest.entity;

public class Shop {
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	private Shop shop;

	private Integer shop_id;

    private String shop_name;

    private String shop_psd;

	private String shop_nickname;

    private String shop_mobile;

    private String shop_license;

    private String shop_email;

    private Double shop_balance=0.0;

    private String shop_tag;//ing or ed

	private Integer image;

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	private Location_Information_Shop  location_information_shop;
	

	public Location_Information_Shop getLocation_information_shop() {
		return location_information_shop;
	}

	public void setLocation_information_shop(Location_Information_Shop location_information_shop) {
		this.location_information_shop = location_information_shop;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_psd() {
		return shop_psd;
	}

	public void setShop_psd(String shop_psd) {
		this.shop_psd = shop_psd;
	}

	public String getShop_nickname() {
		return shop_nickname;
	}

	public void setShop_nickname(String shop_nickname) {
		this.shop_nickname = shop_nickname;
	}

	public String getShop_mobile() {
		return shop_mobile;
	}

	public void setShop_mobile(String shop_mobile) {
		this.shop_mobile = shop_mobile;
	}

	public String getShop_license() {
		return shop_license;
	}

	public void setShop_license(String shop_license) {
		this.shop_license = shop_license;
	}

	public String getShop_email() {
		return shop_email;
	}

	public void setShop_email(String shop_email) {
		this.shop_email = shop_email;
	}

	public Double getShop_balance() {
		return shop_balance;
	}

	public void setShop_balance(Double shop_balance) {
		this.shop_balance = shop_balance;
	}

	public Integer getShop_id() {
		return shop_id;
	}

	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_tag() {
		return shop_tag;
	}

	public void setShop_tag(String shop_tag) {
		this.shop_tag = shop_tag;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
