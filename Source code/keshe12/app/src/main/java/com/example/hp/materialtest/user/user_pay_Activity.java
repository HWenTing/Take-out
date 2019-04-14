package com.example.hp.materialtest.user;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
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
import com.example.hp.materialtest.Util.Time;
import com.example.hp.materialtest.Util.Touxiang;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class user_pay_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<pay_food_item> mpay_food_itemList=new ArrayList<>();
    private pay_food_item_Adapter adapter;
    private String user_name,shop_name ,currenTime;
    private Double money;
    String food_idList="";
    String food_image ="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_pay);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        TextView total = (TextView) findViewById(R.id.total_money);
        Button pay = (Button) findViewById(R.id.pay);


        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        user_name = UserMainActivity.input_account;
        shop_name = getIntent().getStringExtra("shop_name");



        money =3.0;
        List<pay_food_item> mpay_food_itemList = new ArrayList<pay_food_item>();

        for(int i =0;i<Shop_itemFood_Adapter.order_foods.size();i++){
            money+=Shop_itemFood_Adapter.order_foods.get(i).getPrice();
            mpay_food_itemList.add(new pay_food_item(Shop_itemFood_Adapter.order_foods.get(i).getFood_name(),Shop_itemFood_Adapter.order_foods.get(i).getPrice(),Shop_itemFood_Adapter.order_foods.get(i).getFood_id()));
//            food_idList.add(Shop_itemFood_Adapter.order_foods.get(i).getFood_id());
            food_idList+=Shop_itemFood_Adapter.order_foods.get(i).getFood_id()+",";
            food_image+=Touxiang.getTouxiang()+",";
        }



        currenTime = Time.currentTime();

        total.setText(money+"¥");
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new pay_food_item_Adapter(mpay_food_itemList);
        recyclerView.setAdapter(adapter);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(user_name);
                System.out.println(shop_name);
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name",user_name)
                        .add("shop_name",shop_name)
                        .add("price",money+"")
                        .add("currentTime",currenTime)
                        .add("food_list",food_idList)
                        .add("image",food_image)
                        .build();

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/generate_order.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);

                if(response.equals("success")){




                    Toast.makeText(user_pay_Activity.this,"支付成功",Toast.LENGTH_SHORT).show();
                    Intent intent_info = new Intent(user_pay_Activity.this,UserMainActivity.class);
                    intent_info.putExtra("input_account",user_name);
                    startActivity(intent_info);
                }else {
                    Toast.makeText(user_pay_Activity.this,"余额不足，支付失败",Toast.LENGTH_SHORT).show();
                    Intent intent_info = new Intent(user_pay_Activity.this,UserMainActivity.class);
                    intent_info.putExtra("input_account",user_name);
                    startActivity(intent_info);
                }
            }
        });
    }

}
