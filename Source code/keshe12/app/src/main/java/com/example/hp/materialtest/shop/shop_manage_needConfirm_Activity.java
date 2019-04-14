package com.example.hp.materialtest.shop;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Order;
import com.example.hp.materialtest.entity.OrderList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class shop_manage_needConfirm_Activity extends Activity {

    private DrawerLayout mDrawerLayout;
    private List<shop_OrderItem> morderList=new ArrayList<>();
    private Order_needConfirm_Adapter adapter;
    public static String input_account ;
    private TextView number;
    public static shop_manage_needConfirm_Activity avtivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order_needconfirm);
        avtivity=this;
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
                        Toast.makeText(shop_manage_needConfirm_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
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
        adapter=new Order_needConfirm_Adapter(morderList);
        recyclerView.setAdapter(adapter);

    }

    private int  initOrders(){
        morderList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("shop_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getNeedCertainOrder.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);

        Gson gson = new Gson();
        try{
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
