package com.example.hp.materialtest.user;

/**
 * Created by HP on 2018/9/6.
 */

public class Shop_itemFood {
    private String food_name;

    private String food_description;

    private Double  price;
    private Integer food_id;
    private int imageID;

    public Shop_itemFood(String food_name, String food_description, Double price, Integer food_id, int imageID) {
        this.food_name = food_name;
        this.food_description = food_description;
        this.price = price;
        this.food_id = food_id;
        this.imageID = imageID;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
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

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
