package com.example.hp.materialtest.db;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018/9/3.
 */

public class user_comment_shop extends DataSupport {
    private int id;
    private double score;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
