package com.example.hp.materialtest.deliver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.User;
import com.example.hp.materialtest.shop.ShopMainActivity;
import com.google.gson.Gson;


import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by HP on 2018/9/24.
 */

public class deliver_ing_Adapter  extends RecyclerView.Adapter<deliver_ing_Adapter.ViewHolder>  {
    private Context mcontext;
    private List<deliver_order_Item> mOrderList;
    private Integer order_id;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View orderView;
        TextView user_name;
        TextView order_time;
        TextView shop_name;
        TextView shop_location;
        TextView user_location;

        public ViewHolder(View itemView) {
            super(itemView);
            orderView= itemView;
            user_name= (TextView) itemView.findViewById(R.id.user_name);
            order_time = (TextView) itemView.findViewById(R.id.order_time);
            shop_name = (TextView) itemView.findViewById(R.id.shop_name);
            shop_location = (TextView) itemView.findViewById(R.id.shop_location);
            user_location = (TextView) itemView.findViewById(R.id.user_location);

        }
    }

    public deliver_ing_Adapter(List<deliver_order_Item> orderList){
        mOrderList=orderList;
    }

    @Override
    public deliver_ing_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.deliver_order_item,parent,false);
        final deliver_ing_Adapter.ViewHolder holder = new deliver_ing_Adapter.ViewHolder(view);

//        点击事件
        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("order_id",order_id+"")
                        .build();

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/finishOrder.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(DeliverMainActivity.activity,"送单完成",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(DeliverMainActivity.activity,"送单失败",Toast.LENGTH_SHORT).show();

                }

            }
        });

        return holder;
    }

    public void onBindViewHolder(deliver_ing_Adapter.ViewHolder holder, int position) {
        deliver_order_Item orderItem=mOrderList.get(position);
        holder.user_name.setText(getUserNackname(orderItem.getUser_name()));
        holder.order_time.setText(orderItem.getOrder_time());
        holder.shop_location.setText(orderItem.getShop_location());
        holder.user_location.setText(orderItem.getUser_location());
        holder.shop_name.setText(getShopNackname(orderItem.getShop_name()));
        order_id = orderItem.getOrder_id();
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
    private String getUserNackname(String user_name) {
        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add("user_name", user_name)
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/DBDesign/db/get_user_information.action")
                    .post(requestBody)
                    .build();

            String response = MyRequest.sendRequestWithOkHttp(request);
            Gson gson = new Gson();
            User user = gson.fromJson(response, User.class).getUser();
            return user.getUser_nickname();
        } catch (Exception e) {
            return "未命名";
        }
    }
    private String getShopNackname(String user_name){
        try{
            RequestBody requestBody = new FormBody.Builder()
                    .add("shop_name",user_name)
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/DBDesign/db/get_shop_information.action")
                    .post(requestBody)
                    .build();

            String response = MyRequest.sendRequestWithOkHttp(request);
            Gson gson = new Gson();
            Shop shop = gson.fromJson(response,Shop.class).getShop();
            return shop.getShop_nickname();
        }catch (Exception e){
            return "未命名";
        }
    }

}
