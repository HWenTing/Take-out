package com.example.hp.materialtest.db;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018/9/3.
 */

public class deliver extends DataSupport {
    private int id;
    private String deliver_name;
    private String deliver_psd;
    private double score;
    private int amount;



    private byte[] deliver_headImage;//头像

    public byte[] getDeliver_headImage() {
        return deliver_headImage;
    }

    public void setDeliver_headImage(byte[] deliver_headImage) {
        this.deliver_headImage = deliver_headImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeliver_name() {
        return deliver_name;
    }

    public void setDeliver_name(String deliver_name) {
        this.deliver_name = deliver_name;
    }

    public String getDeliver_psd() {
        return deliver_psd;
    }

    public void setDeliver_psd(String deliver_psd) {
        this.deliver_psd = deliver_psd;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
