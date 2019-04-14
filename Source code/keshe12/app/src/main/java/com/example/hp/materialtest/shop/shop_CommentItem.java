package com.example.hp.materialtest.shop;

/**
 * Created by HP on 2018/9/23.
 */

public class shop_CommentItem {
    private String user_name;
    private Double score;
    private String comment;

    public shop_CommentItem(String user_name, Double score, String comment) {
        this.user_name = user_name;
        this.score = score;
        this.comment = comment;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
