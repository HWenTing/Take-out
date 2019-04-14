package com.example.hp.materialtest.user;

/**
 * Created by HP on 2018/9/22.
 */

public class pay_food_item {
    private String food_name;
    private Double price;
    private Integer food_id;

    public pay_food_item(String food_name, Double price, Integer food_id) {
        this.food_name = food_name;
        this.price = price;
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }
}
