package com.example.hp.materialtest.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.shop.ShopMainActivity;
import com.example.hp.materialtest.shop.shop_manage_needConfirm_Activity;
import com.example.hp.materialtest.shop.shop_manage_waitDeliver_Activity;
import com.example.hp.materialtest.shop.shop_manage_waitFinish_Activity;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/21.
 */

public class user_OrderAdapter extends RecyclerView.Adapter<user_OrderAdapter.ViewHolder> {
    private Context mcontext;
    private List<user_OrderItem> mOrderList;
    private Integer order_id;
    private String shop_name;
    private String deliver_name;
    private String nickname;
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

    public user_OrderAdapter(List<user_OrderItem> orderList){
        mOrderList=orderList;
    }

    @Override
    public user_OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user_order_item,parent,false);
        final user_OrderAdapter.ViewHolder holder = new user_OrderAdapter.ViewHolder(view);

        /*

        添加事件监听
         */
        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentChooseDialog();
            }
        });


        return holder;
    }

    public void onBindViewHolder(user_OrderAdapter.ViewHolder holder, int position) {
        user_OrderItem orderItem=mOrderList.get(position);
        holder.order_shop.setText(orderItem.getNackname());
        holder.order_price.setText(orderItem.getOrder_price()+"¥");
        order_id = orderItem.getOrder_id();
        shop_name = orderItem.getOrder_shop();
        deliver_name = orderItem.getDeliver_name();
        System.out.println("544544545544554545455455454");
        Glide.with(mcontext).load(orderItem.getImageID()).into(holder.shopImage);
        System.out.println("1111111111111111111111111");
        System.out.println(orderItem.getImageID());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    private void showCommentChooseDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(user_history_Activity.activity);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(user_history_Activity.activity, R.layout.dialog_select_comment, null);

        TextView comment_shop= (TextView) view.findViewById(R.id.comment_shop);
        TextView comment_deliver = (TextView) view.findViewById(R.id.comment_deliver);

        comment_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addCommentShopDialog();
            }
        });

        comment_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                addCommentDeliverDialog();
            }
        });

        dialog.setView(view);
        dialog.show();
    }

    private void addCommentShopDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(user_history_Activity.activity);
        final AlertDialog dialog = builder.create();
        final View mview = View.inflate(user_history_Activity.activity, R.layout.dialog_add_comment, null);

        final EditText score = (EditText) mview.findViewById(R.id.shop_addFood_description);
        final EditText description = (EditText) mview.findViewById(R.id.shop_addFood_price);

        Button cancel = (Button)mview.findViewById(R.id.cancel);
        Button confirm = (Button)mview.findViewById(R.id.confirm);
        dialog.setView(mview);
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name",UserMainActivity.input_account)
                        .add("shop_name",shop_name)
                        .add("comment",description.getText().toString())
                        .add("score",score.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/set_comment_from_user_to_shop.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(user_history_Activity.activity,"添加成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(user_history_Activity.activity,response,Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        });

    }

    private void addCommentDeliverDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(user_history_Activity.activity);
        final AlertDialog dialog = builder.create();
        final View mview = View.inflate(user_history_Activity.activity, R.layout.dialog_add_comment, null);

        final EditText score = (EditText) mview.findViewById(R.id.shop_addFood_description);
        final EditText description = (EditText) mview.findViewById(R.id.shop_addFood_price);

        Button cancel = (Button)mview.findViewById(R.id.cancel);
        Button confirm = (Button)mview.findViewById(R.id.confirm);
        dialog.setView(mview);
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name",UserMainActivity.input_account)
                        .add("deliver_name",deliver_name)
                        .add("comment",description.getText().toString())
                        .add("score",score.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/set_comment_from_user_to_deliver.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(user_history_Activity.activity,"添加成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(user_history_Activity.activity,response,Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        });

    }
}
