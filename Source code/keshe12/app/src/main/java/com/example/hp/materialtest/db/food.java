package com.example.hp.materialtest.db;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018/9/3.
 */

public class food extends DataSupport {
    private int id;
    private String food_name;
    private String food_description;
    private double food_price;
    private int food_sales_amount;
    private double food_score;

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public double getFood_price() {
        return food_price;
    }

    public void setFood_price(double food_price) {
        this.food_price = food_price;
    }

    public int getFood_sales_amount() {
        return food_sales_amount;
    }

    public void setFood_sales_amount(int food_sales_amount) {
        this.food_sales_amount = food_sales_amount;
    }

    public double getFood_score() {
        return food_score;
    }

    public void setFood_score(double food_score) {
        this.food_score = food_score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }
}
