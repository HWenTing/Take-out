package com.example.hp.materialtest.deliver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.User;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/24.
 */

public class deliver_historyItem_Adapter extends RecyclerView.Adapter<deliver_historyItem_Adapter.ViewHolder> {

    private Context mcontext;
    private List<deliver_order_Item> mOrderList;

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

    public deliver_historyItem_Adapter(List<deliver_order_Item> orderList){
        mOrderList=orderList;
    }

    @Override
    public deliver_historyItem_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.deliver_order_item,parent,false);
        final deliver_historyItem_Adapter.ViewHolder holder = new deliver_historyItem_Adapter.ViewHolder(view);

        /*

        添加事件监听
         */
        return holder;
    }

    public void onBindViewHolder(deliver_historyItem_Adapter.ViewHolder holder, int position) {
        deliver_order_Item orderItem=mOrderList.get(position);
        holder.user_name.setText(getUserNackname(orderItem.getUser_name()));
        holder.order_time.setText(orderItem.getOrder_time());
        holder.shop_location.setText(orderItem.getShop_location());
        holder.user_location.setText(orderItem.getUser_location());
        holder.shop_name.setText(getShopNackname(orderItem.getShop_name()));
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
