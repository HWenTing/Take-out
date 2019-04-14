package com.example.hp.materialtest.shop;

/**
 * Created by HP on 2018/9/6.
 */

public class Shop_food {
    private String food_name;

    private String food_description;

    private Double  price;
    private Integer foodId;
    private int imageID;

    public Shop_food(String food_name, String food_description, Double price, int imageID,Integer foodId) {
        this.food_name = food_name;
        this.imageID = imageID;
        this.price = price;
        this.food_description = food_description;
        this.foodId=foodId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
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

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
