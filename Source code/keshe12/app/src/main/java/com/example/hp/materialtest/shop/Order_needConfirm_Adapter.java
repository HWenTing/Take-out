package com.example.hp.materialtest.shop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.User;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by HP on 2018/9/23.
 */

public class Order_needConfirm_Adapter extends RecyclerView.Adapter<Order_needConfirm_Adapter.ViewHolder> {

    private Context mcontext;
    private List<shop_OrderItem> mOrderList;
    private Integer order_id;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View orderView;
        TextView user_name;
        TextView order_time;

        public ViewHolder(View itemView) {
            super(itemView);
            orderView= itemView;
            user_name= (TextView) itemView.findViewById(R.id.user_name);
            order_time = (TextView) itemView.findViewById(R.id.order_time);

        }
    }

    public Order_needConfirm_Adapter(List<shop_OrderItem> orderList){
        mOrderList=orderList;
    }

    @Override
    public Order_needConfirm_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.shop_order_item,parent,false);
        final Order_needConfirm_Adapter.ViewHolder holder = new Order_needConfirm_Adapter.ViewHolder(view);

        /*

        添加事件监听
         */
//
        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shop_manage_needConfirm_Activity.avtivity,shop_order_details_Activity.class);
                intent.putExtra("input_account",shop_manage_needConfirm_Activity.input_account);
                intent.putExtra("order_id",order_id);
                mcontext.startActivity(intent);
            }
        });



        return holder;
    }

    public void onBindViewHolder(Order_needConfirm_Adapter.ViewHolder holder, int position) {
        shop_OrderItem orderItem=mOrderList.get(position);
        holder.order_time.setText(orderItem.getOrder_time());
        holder.user_name.setText(getUserNackname(orderItem.getUser_name()));
        order_id = orderItem.getOrder_id();
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

}

