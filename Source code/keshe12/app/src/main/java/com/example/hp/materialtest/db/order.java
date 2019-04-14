package com.example.hp.materialtest.db;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018/9/3.
 */

public class order extends DataSupport {
    private int id;
    private double totalprice;
    private String ordertime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }
}
