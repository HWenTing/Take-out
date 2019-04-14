package com.example.hp.materialtest.user;

/**
 * Created by HP on 2018/9/22.
 */

public class CommentItem {
    private String shop_name;
    private int imageID;
    private double shop_score;
    private String comments;

    public CommentItem(String shop_name,String comments,double shop_score,int imageID){
        this.shop_name = shop_name;
        this.imageID = imageID;
        this.shop_score = shop_score;
        this.comments = comments;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public double getShop_score() {
        return shop_score;
    }

    public void setShop_score(double shop_score) {
        this.shop_score = shop_score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
