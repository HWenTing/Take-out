package com.example.hp.materialtest.shop;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.text.TextPaint;
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
import com.example.hp.materialtest.Util.Touxiang;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/23.
 */

public class Shop_food_Adapter  extends RecyclerView.Adapter<Shop_food_Adapter.ViewHolder> {
    private Context mcontext;
    private List<Shop_food> mShop_food_List;

    public static  List<Shop_food> order_foods = new ArrayList<Shop_food>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View Shop_food_View;
        ImageView Shop_food_Image;
        TextView Shop_food_name;
        TextView Shop_food_price;
        TextView Shop_food_description;
        Button Shop_food_choose;


        public ViewHolder(View itemView) {
            super(itemView);
            Shop_food_View= itemView;
            Shop_food_Image= (ImageView) itemView.findViewById(R.id.shop_itemFood_image);

            Shop_food_name= (TextView) itemView.findViewById(R.id.user_click_shop_food_name);
            TextPaint tp = Shop_food_name.getPaint();
            tp.setFakeBoldText(true);

            Shop_food_price=(TextView) itemView.findViewById(R.id.user_click_shop_food_price);
            Shop_food_description=(TextView) itemView.findViewById(R.id.user_click_shop_food_description);
            Shop_food_choose = (Button)itemView.findViewById(R.id.user_click_shop_food_choose);
        }
    }
    public Shop_food_Adapter(List<Shop_food> Shop_food_List){
        mShop_food_List=Shop_food_List;
    }
    @Override
    public Shop_food_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.shop_food,parent,false);
        final Shop_food_Adapter.ViewHolder holder = new Shop_food_Adapter.ViewHolder(view);
        //给小框框加监听
        //view
        holder.Shop_food_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Shop_food shop_itemFood = mShop_food_List.get(position);
                changeFoodDialog(shop_itemFood);
            }
        });
        holder.Shop_food_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });
        //按钮
        holder.Shop_food_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Shop_food shop_foodood = mShop_food_List.get(position);

                RequestBody requestBody = new FormBody.Builder()
                        .add("food_id",shop_foodood.getFoodId()+"")
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/deleteFood.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")) {
                    Toast.makeText(ShopMainActivity.activity,"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShopMainActivity.activity,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(Shop_food_Adapter.ViewHolder holder, int position) {
        Shop_food shop_itemFood = mShop_food_List.get(position);
        holder.Shop_food_name.setText(shop_itemFood.getFood_name());
        holder.Shop_food_description.setText(shop_itemFood.getFood_description());
        holder.Shop_food_price.setText(shop_itemFood.getPrice()+"¥");
        Glide.with(mcontext).load(shop_itemFood.getImageID()).into(holder.Shop_food_Image);
    }

    @Override
    public int getItemCount() {
        return mShop_food_List.size();
    }

    private void changeFoodDialog(final Shop_food f){


        AlertDialog.Builder builder = new AlertDialog.Builder(ShopMainActivity.activity);
        final AlertDialog dialog = builder.create();
        final View mview = View.inflate(ShopMainActivity.activity, R.layout.dialog_shop_addfood, null);


        final EditText addFood_name = (EditText)mview.findViewById(R.id.shop_addFood_name);

        final EditText addFood_description = (EditText) mview.findViewById(R.id.shop_addFood_description);

        final EditText addFood_price = (EditText) mview.findViewById(R.id.shop_addFood_price);
        addFood_name.setText(f.getFood_name());
        addFood_description.setText(f.getFood_description());
        addFood_price.setText(f.getPrice()+"");

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
                        .add("food_id",f.getFoodId()+"")
                        .add("food_name",addFood_name.getText().toString())
                        .add("food_description",addFood_description.getText().toString())
                        .add("food_price",addFood_price.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/changeFood.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(ShopMainActivity.activity,"修改成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShopMainActivity.activity,response,Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopMainActivity.activity);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(ShopMainActivity.activity, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }


}
