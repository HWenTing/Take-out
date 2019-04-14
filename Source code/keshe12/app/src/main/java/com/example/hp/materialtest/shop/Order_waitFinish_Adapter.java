package com.example.hp.materialtest.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Deliver;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.User;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by HP on 2018/9/23.
 */

public class Order_waitFinish_Adapter extends RecyclerView.Adapter<Order_waitFinish_Adapter.ViewHolder> {
    private Context mcontext;
    private List<OrderWithDeliverItem> mOrderList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View orderView;
        TextView user_name;
        TextView order_time;
        TextView deliver_name;

        public ViewHolder(View itemView) {
            super(itemView);
            orderView= itemView;
            user_name= (TextView) itemView.findViewById(R.id.user_name);
            order_time = (TextView) itemView.findViewById(R.id.order_time);
            deliver_name =(TextView) itemView.findViewById(R.id.deliver_name);
        }
    }

    public Order_waitFinish_Adapter(List<OrderWithDeliverItem> orderList){
        mOrderList=orderList;
    }

    @Override
    public Order_waitFinish_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.shop_order_with_delier_item,parent,false);
        final Order_waitFinish_Adapter.ViewHolder holder = new Order_waitFinish_Adapter.ViewHolder(view);

        /*

        添加事件监听
         */
        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "骑手详情",Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    public void onBindViewHolder(Order_waitFinish_Adapter.ViewHolder holder, int position) {
        OrderWithDeliverItem orderItem=mOrderList.get(position);
        holder.user_name.setText(getUserNackname(orderItem.getUser_name()));
        holder.order_time.setText(orderItem.getOrder_time());
        holder.deliver_name.setText(getDeliverNackname(orderItem.getDeliver_name()));
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    private String getUserNackname(String user_name){
        try{
            RequestBody requestBody = new FormBody.Builder()
                    .add("user_name",user_name)
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/DBDesign/db/get_user_information.action")
                    .post(requestBody)
                    .build();

            String response = MyRequest.sendRequestWithOkHttp(request);
            Gson gson = new Gson();
            User user = gson.fromJson(response,User.class).getUser();
            return user.getUser_nickname();
        }catch (Exception e){
            return "未命名";
        }
    }


    private String getDeliverNackname(String user_name){
        try{
            RequestBody requestBody = new FormBody.Builder()
                    .add("deliver_name",user_name)
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/DBDesign/db/get_deliver_information.action")
                    .post(requestBody)
                    .build();

            String response = MyRequest.sendRequestWithOkHttp(request);
            Gson gson = new Gson();
            Deliver deliver = gson.fromJson(response,Deliver.class).getDeliver();
            return deliver.getDeliver_nickname();
        }catch (Exception e){
            return "未命名";
        }
    }
}


