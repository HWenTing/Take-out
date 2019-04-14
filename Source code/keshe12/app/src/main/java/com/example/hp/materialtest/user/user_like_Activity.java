package com.example.hp.materialtest.user;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.ShopList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class user_like_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    private List<Head> headList=new ArrayList<>();
    private HeadAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private String input_account ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_like);

        input_account = getIntent().getStringExtra("input_account");

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(user_like_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        initHeads();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new HeadAdapter(headList);
        recyclerView.setAdapter(adapter);

    }

    private boolean initHeads(){
        headList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getUserLike.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);

        try{
            Gson gson = new Gson();
            List<Shop> shopList = gson.fromJson(response,ShopList.class).getShopList();
            if(shopList.size()>0){

                for(int i=0;i<shopList.size();i++) {
                    requestBody = new FormBody.Builder()
                            .add("shop_name",shopList.get(i).getShop_name())
                            .build();

                    request = new Request.Builder()
                            .url("http://10.0.2.2:8080/DBDesign/db/get_shop_information.action")
                            .post(requestBody)
                            .build();

                    response = MyRequest.sendRequestWithOkHttp(request);

                    Shop shop = gson.fromJson(response,Shop.class).getShop();
                    headList.add(new Head(shopList.get(i).getShop_name(),shop.getShop_nickname(), shop.getImage()));
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
