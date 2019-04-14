package com.example.hp.materialtest.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Shop;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/24.
 */

public class user_Order_unfinish_Adapter extends RecyclerView.Adapter<user_Order_unfinish_Adapter.ViewHolder> {
    private Context mcontext;
    private List<user_OrderItem> mOrderList;
    private String deliver_name;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View orderView;
        TextView order_shop;
        TextView order_price;
        ImageView shopImage;

        public ViewHolder(View itemView) {
            super(itemView);
            orderView= itemView;
            order_shop= (TextView) itemView.findViewById(R.id.order_shop);
            order_price = (TextView) itemView.findViewById(R.id.order_price);
            shopImage= (ImageView) itemView.findViewById(R.id.shop_image);
        }
    }

    public user_Order_unfinish_Adapter(List<user_OrderItem> orderList){
        mOrderList=orderList;
    }

    @Override
    public user_Order_unfinish_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user_order_item,parent,false);
        final user_Order_unfinish_Adapter.ViewHolder holder = new user_Order_unfinish_Adapter.ViewHolder(view);

        /*
        添加事件监听
         */
        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;


    }

    public void onBindViewHolder(user_Order_unfinish_Adapter.ViewHolder holder, int position) {
        user_OrderItem orderItem=mOrderList.get(position);
        holder.order_shop.setText(getShopNackname(orderItem.getOrder_shop()));
        holder.order_price.setText(orderItem.getOrder_price()+"¥");
        Glide.with(mcontext).load(orderItem.getImageID()).into(holder.shopImage);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
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
