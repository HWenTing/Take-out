package com.example.hp.materialtest.shop;

import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Deliver;
import com.example.hp.materialtest.entity.Food;
import com.example.hp.materialtest.entity.FoodList;
import com.example.hp.materialtest.entity.Order;
import com.example.hp.materialtest.entity.OrderList;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.User;
import com.example.hp.materialtest.user.pay_food_item;
import com.example.hp.materialtest.user.pay_food_item_Adapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class shop_order_details_Activity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private List<pay_food_item> morderList=new ArrayList<>();
    private pay_food_item_Adapter adapter;
    private String input_account ;
    private Integer order_id ;


    private TextView user_name;
    private TextView user_location;
    private TextView shop_name;
    private TextView shop_location;
    private TextView order_time;
    private Button confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order_details);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        user_name = (TextView) findViewById(R.id.user_name);
        user_location = (TextView) findViewById(R.id.user_location);
        shop_name = (TextView) findViewById(R.id.shop_name);
        shop_location = (TextView) findViewById(R.id.shop_location);
        order_time = (TextView) findViewById(R.id.order_time);
        confirm = (Button) findViewById(R.id.confirm);


        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        input_account = getIntent().getStringExtra("input_account");
        order_id = getIntent().getIntExtra("order_id",1);


        try{
            RequestBody requestBody = new FormBody.Builder()
                    .add("order_id",order_id+"")
                    .build();
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/DBDesign/db/getOrderByOrderID.action")
                    .post(requestBody)
                    .build();
            String response = MyRequest.sendRequestWithOkHttp(request);
            Gson gson = new Gson();
            Order order = gson.fromJson(response,Order.class).getOrder();
            String Tshop_location = order.getLocation_information_shop().getProvince()
                    +"-"+order.getLocation_information_shop().getCity()
                    +"-"+order.getLocation_information_shop().getCounty()
                    +"-"+order.getLocation_information_shop().getSpecific_location();

            String Tuser_location = order.getLocation_information_user().getProvince()
                    +"-"+order.getLocation_information_user().getCity()
                    +"-"+order.getLocation_information_user().getCounty()
                    +"-"+order.getLocation_information_user().getSpecific_location();

            user_name .setText(getUserNackname(order.getUser_name()));
            user_location.setText(Tuser_location);
            shop_name.setText(getShopNackname(order.getShop_name()));
            shop_location .setText(Tshop_location);
            order_time .setText(order.getOrder_time());

        }catch (Exception e){

        }
        confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("order_id",order_id+"")
                        .build();

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/certainOrder.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(ShopMainActivity.activity,"接收成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(ShopMainActivity.activity,"接收失败",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        initOrders();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new pay_food_item_Adapter(morderList);
        recyclerView.setAdapter(adapter);

    }

    private int  initOrders(){
        morderList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("order_id",order_id+"")
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getFoodByOrder.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        System.out.println(response);
        try{
            Gson gson = new Gson();
            List<Food> foodList = gson.fromJson(response,FoodList.class).getFoodList();

            if(foodList.size()>0){
                for(int i=0;i<foodList.size();i++) {
                    morderList.add(new pay_food_item(foodList.get(i).getFood_name(),foodList.get(i).getPrice(),foodList.get(i).getFood_id()));
                }
                return foodList.size();
            }else{
                return 0;
            }
        }catch (Exception e){
            return 0;
        }

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
