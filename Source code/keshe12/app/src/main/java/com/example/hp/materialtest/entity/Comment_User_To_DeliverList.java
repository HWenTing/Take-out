package com.example.hp.materialtest.entity;

import java.util.List;

/**
 * Created by HP on 2018/9/22.
 */

public class Comment_User_To_DeliverList {
    private List<Comment_User_To_Deliver> ToDeliverComments;

    public List<Comment_User_To_Deliver> getToDeliverComments() {
        return ToDeliverComments;
    }

    public void setToDeliverComments(List<Comment_User_To_Deliver> toDeliverComments) {
        ToDeliverComments = toDeliverComments;
    }
}
