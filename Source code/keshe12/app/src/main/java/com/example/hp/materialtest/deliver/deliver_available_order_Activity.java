package com.example.hp.materialtest.deliver;

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

public class deliver_available_order_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<deliver_order_Item> morderList=new ArrayList<>();
    private deliver_available_Adapter adapter;

    protected static String input_account ;
    private TextView number;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_available_order);

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
                        Toast.makeText(deliver_available_order_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
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
        adapter=new deliver_available_Adapter(morderList);
        recyclerView.setAdapter(adapter);

    }

    private int  initOrders(){
        morderList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("deliver_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getAvailableOrder.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        System.out.println(response);

        try{
            Gson gson = new Gson();
            List<Order> orderList = gson.fromJson(response,OrderList.class).getOrderList();

            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++) {
                    String shop_location = orderList.get(i).getLocation_information_shop().getProvince()
                            +"-"+orderList.get(i).getLocation_information_shop().getCity()
                            +"-"+orderList.get(i).getLocation_information_shop().getCounty()
                            +"-"+orderList.get(i).getLocation_information_shop().getSpecific_location();

                    String user_location = orderList.get(i).getLocation_information_user().getProvince()
                            +"-"+orderList.get(i).getLocation_information_user().getCity()
                            +"-"+orderList.get(i).getLocation_information_user().getCounty()
                            +"-"+orderList.get(i).getLocation_information_user().getSpecific_location();

                    morderList.add(new deliver_order_Item(orderList.get(i).getUser_name(),orderList.get(i).getShop_name(),user_location,shop_location,orderList.get(i).getOrder_time(),orderList.get(i).getOrder_price(),orderList.get(i).getOrder_id()));
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
