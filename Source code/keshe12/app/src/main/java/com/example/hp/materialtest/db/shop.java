package com.example.hp.materialtest.db;

import org.litepal.crud.DataSupport;;

/**
 * Created by HP on 2018/9/3.
 */

public class shop extends DataSupport {
    private int id;
    private String shop_name;
    private String shop_psd;
    private String shop_mobile;
    private String shop_tag;
    private String shop_address;
    private double shop_score;
    private String license;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    private byte[] shop_headImage;//头像

    public byte[] getShop_headImage() {
        return shop_headImage;
    }

    public void setShop_headImage(byte[] shop_headImage) {
        this.shop_headImage = shop_headImage;
    }

    public String getShop_tag() {
        return shop_tag;
    }

    public void setShop_tag(String shop_tag) {
        this.shop_tag = shop_tag;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public double getShop_score() {
        return shop_score;
    }

    public void setShop_score(double shop_score) {
        this.shop_score = shop_score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_psd() {
        return shop_psd;
    }

    public void setShop_psd(String shop_psd) {
        this.shop_psd = shop_psd;
    }

    public String getShop_mobile() {
        return shop_mobile;
    }

    public void setShop_mobile(String shop_mobile) {
        this.shop_mobile = shop_mobile;
    }



}
