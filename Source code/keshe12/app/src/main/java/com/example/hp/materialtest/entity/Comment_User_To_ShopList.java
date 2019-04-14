package com.example.hp.materialtest.entity;

import java.util.List;

/**
 * Created by HP on 2018/9/22.
 */

public class Comment_User_To_ShopList {
    private List<Comment_User_To_Shop> ToShopComments;


    public List<Comment_User_To_Shop> getToShopComments() {
        return ToShopComments;
    }

    public void setToShopComments(List<Comment_User_To_Shop> toShopComments) {
        ToShopComments = toShopComments;
    }
}
