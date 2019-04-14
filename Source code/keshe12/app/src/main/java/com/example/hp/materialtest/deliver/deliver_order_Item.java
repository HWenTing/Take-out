package com.example.hp.materialtest.deliver;

/**
 * Created by HP on 2018/9/24.
 */

public class deliver_order_Item {
    private String shop_name;
    private String user_name;
    private String order_time;

    private String shop_location;
    private String user_location;
    private Double price;
    private Integer order_id;


    public deliver_order_Item(String user_name, String shop_name, String user_location, String shop_location, String order_time, Double price, Integer order_id ) {
        this.shop_name = shop_name;
        this.price = price;
        this.user_location = user_location;
        this.shop_location = shop_location;
        this.order_time = order_time;
        this.user_name = user_name;
        this.order_id = order_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getShop_location() {
        return shop_location;
    }

    public void setShop_location(String shop_location) {
        this.shop_location = shop_location;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
