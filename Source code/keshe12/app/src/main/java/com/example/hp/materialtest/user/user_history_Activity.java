package com.example.hp.materialtest.user;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Order;
import com.example.hp.materialtest.entity.OrderList;
import com.example.hp.materialtest.entity.Shop;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/21.
 */

public class user_history_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<user_OrderItem> morderList=new ArrayList<>();
    private user_OrderAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    private String input_account ;
    public static user_history_Activity activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        activity=this;
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
                        Toast.makeText(user_history_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        initOrders();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new user_OrderAdapter(morderList);
        recyclerView.setAdapter(adapter);
//        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshHeads();
//            }
//        });
    }

    private boolean initOrders(){
        morderList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getAllOrder.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        System.out.println(response);

        try{
            Gson gson = new Gson();
            List<Order> orderList = gson.fromJson(response,OrderList.class).getOrderList();
            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++) {
                    requestBody = new FormBody.Builder()
                            .add("shop_name",orderList.get(i).getShop_name())
                            .build();

                    request = new Request.Builder()
                            .url("http://10.0.2.2:8080/DBDesign/db/get_shop_information.action")
                            .post(requestBody)
                            .build();

                    response = MyRequest.sendRequestWithOkHttp(request);

                    Shop shop = gson.fromJson(response,Shop.class).getShop();


                    morderList.add(new user_OrderItem(orderList.get(i).getShop_name(),
                            orderList.get(i).getOrder_id(),
                            orderList.get(i).getOrder_price(),
                            orderList.get(i).getDeliver_name(),
                            shop.getImage(),shop.getShop_nickname()));
                }
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }
}
