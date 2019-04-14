package com.example.hp.materialtest.shop;

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
import com.example.hp.materialtest.entity.Deliver;
import com.example.hp.materialtest.entity.User;
import com.example.hp.materialtest.user.user_OrderItem;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/23.
 */
public class shop_OrderAdapter extends RecyclerView.Adapter<shop_OrderAdapter.ViewHolder> {
    private Context mcontext;
    private List<shop_OrderItem> mOrderList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View orderView;
        TextView user_name;
        TextView order_time;
//        TextView score;
//        TextView comment;

        public ViewHolder(View itemView) {
            super(itemView);
            orderView= itemView;
            user_name= (TextView) itemView.findViewById(R.id.user_name);
            order_time = (TextView) itemView.findViewById(R.id.order_time);
//            score = (TextView) itemView.findViewById(R.id.order_score);
//            comment = (TextView) itemView.findViewById(R.id.order_comment);

        }
    }

    public shop_OrderAdapter(List<shop_OrderItem> orderList){
        mOrderList=orderList;
    }

    @Override
    public shop_OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.shop_order_item,parent,false);
        final shop_OrderAdapter.ViewHolder holder = new shop_OrderAdapter.ViewHolder(view);

        /*

        添加事件监听
         */
        return holder;
    }

    public void onBindViewHolder(shop_OrderAdapter.ViewHolder holder, int position) {
        shop_OrderItem orderItem=mOrderList.get(position);
        holder.user_name.setText(getUserNackname(orderItem.getUser_name()));
        holder.order_time.setText(orderItem.getOrder_time());
//        holder.score.setText(orderItem.getScore()+"");
//        holder.comment.setText(orderItem.getComment());
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

