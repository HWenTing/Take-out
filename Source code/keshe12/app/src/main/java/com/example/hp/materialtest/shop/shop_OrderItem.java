package com.example.hp.materialtest.shop;

/**
 * Created by HP on 2018/9/23.
 */

public class shop_OrderItem {
    private String user_name;
    private String order_time;
    private Integer order_id;

    public shop_OrderItem(String user_name, String order_time, Integer order_id) {
        this.user_name = user_name;
        this.order_time = order_time;
        this.order_id = order_id;
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

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
}
