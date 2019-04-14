package com.example.hp.materialtest.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.Util.Touxiang;
import com.example.hp.materialtest.entity.Food;
import com.example.hp.materialtest.entity.FoodList;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.ShopList;
import com.example.hp.materialtest.shop.shop_comment_Activity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class shop_click_Activity extends AppCompatActivity {
    public static final String SHOP_NAME = "shop_name";
    public static final String SHOP_IMAGE_ID = "shop_image_id";
    public static final String USER_NAME = "user_name";

    private Shop_itemFood_Adapter adapter;
    private List<Shop_itemFood> shop_itemFood_List=new ArrayList<>();
    String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_shop_click);


        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        //获取从上一活动的到的信息
        Intent intent = getIntent();
        shopName = intent.getStringExtra(SHOP_NAME);
        int shopImageId = intent.getIntExtra(SHOP_IMAGE_ID,0);
        //绑定控件
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView shopImageView= (ImageView) findViewById(R.id.shop_image_view);
        shopImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(shop_click_Activity.this,shop_comment_Activity.class);
                intent_info.putExtra("input_account",shopName);
                startActivity(intent_info);

            }
        });

        FloatingActionButton like= (FloatingActionButton) findViewById(R.id.user_like_button);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("shop_name",shopName)
                        .add("user_name",UserMainActivity.input_account)
                        .build();
            Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/addUserLikeShop.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                System.out.println(response);
                if (response.equals("success")){
                    Toast.makeText(shop_click_Activity.this,"添加喜欢成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(shop_click_Activity.this,"您已添加过该商家",Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton get_order= (FloatingActionButton) findViewById(R.id.user_get_order_button);
        get_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(intent.getStringExtra("nickname"));
        Glide.with(this).load(shopImageId).into(shopImageView);


//        //小项
//        ImageView user_shop_food_image = (ImageView) findViewById(R.id.user_shop_food_image);
//        TextView user_shop_food_name = (TextView) findViewById(R.id.user_shop_food_name);
//        Glide.with(this).load(shopImageId).into(user_shop_food_image);
//        user_shop_food_name.setText(shopName);

        init_shop_itemFood();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.shop_itemFood_recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Shop_itemFood_Adapter(shop_itemFood_List);
        recyclerView.setAdapter(adapter);

    }

    private boolean init_shop_itemFood() {
        shop_itemFood_List.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("shop_name",shopName)
                .build();


        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getFoodByShopName.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        try{
            Gson gson = new Gson();

            List<Food> shopList = gson.fromJson(response,FoodList.class).getFoodList();

            if(shopList.size()>0){
                for(int i=0;i<shopList.size();i++) {
                    shop_itemFood_List.add(new Shop_itemFood(shopList.get(i).getFood_name(),
                            shopList.get(i).getFood_description(),shopList.get(i).getPrice(),shopList.get(i).getFood_id(),shopList.get(i).getImage()));
                }
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(shop_click_Activity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(shop_click_Activity.this, R.layout.dialog_pay_or_leave, null);

        TextView to_pay = (TextView) view.findViewById(R.id.to_pay);
        TextView to_continue = (TextView) view.findViewById(R.id.to_continue);
        TextView to_leave = (TextView) view.findViewById(R.id.to_leave);

        to_pay.setOnClickListener(new View.OnClickListener() {// 支付
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(shop_click_Activity.this,user_pay_Activity.class);
                intent_info.putExtra("user_name",UserMainActivity.input_account);
                intent_info.putExtra("shop_name",shopName);
                startActivity(intent_info);
                dialog.dismiss();
            }
        });
        to_continue.setOnClickListener(new View.OnClickListener() {// 继续
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        to_leave.setOnClickListener(new View.OnClickListener() {//离开
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }


}
