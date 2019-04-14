package com.example.hp.materialtest.user;

/**
 * Created by HP on 2018/9/21.
 */

public class user_OrderItem {
    private String order_shop;
    private Integer imageID;
    private double order_price;
    private String deliver_name;
    private Integer order_id;
    private String nackname;

    public user_OrderItem(String order_shop, Integer order_id, double order_price, String deliver_name, Integer imageID, String nackname) {
        this.order_shop = order_shop;
        this.imageID = imageID;
        this.order_price = order_price;
        this.deliver_name = deliver_name;
        this.order_id = order_id;
        this.nackname = nackname;
    }

    public String getNackname() {
        return nackname;
    }

    public void setNackname(String nackname) {
        this.nackname = nackname;
    }

    public String getOrder_shop() {
        return order_shop;
    }

    public void setOrder_shop(String order_shop) {
        this.order_shop = order_shop;
    }

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    public String getDeliver_name() {
        return deliver_name;
    }

    public void setDeliver_name(String deliver_name) {
        this.deliver_name = deliver_name;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
}
