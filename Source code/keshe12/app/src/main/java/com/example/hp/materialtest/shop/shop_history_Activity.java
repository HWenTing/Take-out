package com.example.hp.materialtest.shop;

import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Order;
import com.example.hp.materialtest.entity.OrderList;
import com.example.hp.materialtest.user.user_OrderAdapter;
import com.example.hp.materialtest.user.user_OrderItem;
import com.example.hp.materialtest.user.user_history_Activity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class shop_history_Activity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<shop_OrderItem> morderList=new ArrayList<>();
    private shop_OrderAdapter adapter;

    private String input_account ;
    private TextView number;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_history);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        input_account = getIntent().getStringExtra("input_account");

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(shop_history_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        number = (TextView) findViewById(R.id.number);
        int count=initOrders();
        number.setText(count+"单");

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new shop_OrderAdapter(morderList);
        recyclerView.setAdapter(adapter);

    }

    private int  initOrders(){
        morderList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("shop_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getfinishedOrder_shop.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        System.out.println(response);
        try{
            Gson gson = new Gson();
            List<Order> orderList = gson.fromJson(response,OrderList.class).getOrderList();

            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++) {
                    morderList.add(new shop_OrderItem(orderList.get(i).getUser_name(),orderList.get(i).getOrder_time(),orderList.get(i).getOrder_id()));
                }
                return orderList.size();
            }else{
                return 0;
            }
        }catch (Exception e){
            return 0;
        }

    }


}
