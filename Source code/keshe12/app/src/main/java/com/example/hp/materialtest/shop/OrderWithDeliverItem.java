package com.example.hp.materialtest.shop;

/**
 * Created by HP on 2018/9/23.
 */

public class OrderWithDeliverItem {
    private String user_name;
    private String order_time;
    private String deliver_name;
    private Integer order_id;

    public OrderWithDeliverItem(String user_name, String order_time, String deliver_name, Integer order_id) {
        this.user_name = user_name;
        this.order_time = order_time;
        this.deliver_name = deliver_name;
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

    public String getDeliver_name() {
        return deliver_name;
    }

    public void setDeliver_name(String deliver_name) {
        this.deliver_name = deliver_name;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
}
