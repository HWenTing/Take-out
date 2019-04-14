package com.example.hp.materialtest.db;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018/9/3.
 */

public class address extends DataSupport {
    private int id;
    private String ad_provinve;
    private String ad_city;
    private String ad_district;
    private String ad_address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd_provinve() {
        return ad_provinve;
    }

    public void setAd_provinve(String ad_provinve) {
        this.ad_provinve = ad_provinve;
    }

    public String getAd_city() {
        return ad_city;
    }

    public void setAd_city(String ad_city) {
        this.ad_city = ad_city;
    }

    public String getAd_district() {
        return ad_district;
    }

    public void setAd_district(String ad_district) {
        this.ad_district = ad_district;
    }

    public String getAd_address() {
        return ad_address;
    }

    public void setAd_address(String ad_address) {
        this.ad_address = ad_address;
    }



}
