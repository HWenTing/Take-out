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
import com.example.hp.materialtest.entity.Comment_User_To_Deliver;
import com.example.hp.materialtest.entity.Comment_User_To_DeliverList;
import com.example.hp.materialtest.entity.Comment_User_To_Shop;
import com.example.hp.materialtest.entity.Comment_User_To_ShopList;
import com.example.hp.materialtest.shop.shop_CommentAdapter;
import com.example.hp.materialtest.shop.shop_CommentItem;
import com.example.hp.materialtest.shop.shop_comment_Activity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class deliver_comment_Activity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<shop_CommentItem> morderList=new ArrayList<>();
    private shop_CommentAdapter adapter;

    private String input_account ;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_comment);
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
                        Toast.makeText(deliver_comment_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        score = (TextView) findViewById(R.id.score);
        double count=initOrders();
        score.setText(count+"分");

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new shop_CommentAdapter(morderList);
        recyclerView.setAdapter(adapter);

    }

    private double  initOrders(){
        morderList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("deliver_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/deliver_comments_from_user.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        double re = 0.0;
        try{
            Gson gson = new Gson();
            List<Comment_User_To_Deliver> orderList = gson.fromJson(response,Comment_User_To_DeliverList.class).getToDeliverComments();
            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++) {
                    re+=orderList.get(i).getScore();
                    morderList.add(new shop_CommentItem(orderList.get(i).getUser_name(),orderList.get(i).getScore(),orderList.get(i).getComment()));
                }
                return re/orderList.size();
            }else{
                return re;
            }
        }catch (Exception e){
            return re;
        }

    }
}
