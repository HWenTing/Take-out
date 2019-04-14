package com.example.hp.materialtest.db;

import org.litepal.crud.DataSupport;
/**
 * Created by HP on 2018/9/3.
 */

public class user extends DataSupport {

    private int id;

    private String user_name;//登录账号
    private String user_psd;//登录密码
    private String user_nickname;//昵称



    private String user_mobile;//手机号
    private String user_gender;//性别
    private String user_email;//电子邮箱

    private byte[] user_headImage;//头像

    private double user_balance;//用户余额

    public byte[] getUser_headImage() {
        return user_headImage;
    }

    public void setUser_headImage(byte[] user_headImage) {
        this.user_headImage = user_headImage;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_psd(String user_psd) {
        this.user_psd = user_psd;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_balance(double user_balance) {
        this.user_balance = user_balance;
    }

    public int getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_psd() {
        return user_psd;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public double getUser_balance() {
        return user_balance;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }
}
