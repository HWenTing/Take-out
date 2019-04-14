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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Comment_User_To_Shop;
import com.example.hp.materialtest.entity.Comment_User_To_ShopList;
import com.example.hp.materialtest.entity.Shop;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class user_comment_to_shop_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<CommentItem> mcommentList=new ArrayList<>();
    private ShopCommentAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private TextView title;
    private String input_account ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comment);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        title= (TextView) findViewById(R.id.title_comment);
        title.setText("评价(商家)");

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        input_account = getIntent().getStringExtra("input_account");

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(user_comment_to_shop_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        initComments();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ShopCommentAdapter(mcommentList);
        recyclerView.setAdapter(adapter);
    }


    private boolean initComments(){
        mcommentList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/get_comments_from_user_to_shop.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);


        Gson gson = new Gson();
        List<Comment_User_To_Shop> orderList = gson.fromJson(response,Comment_User_To_ShopList.class).getToShopComments();

        try{
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


                    mcommentList.add(new CommentItem(orderList.get(i).getShop_name(),orderList.get(i).getComment(), orderList.get(i).getScore(),shop.getImage()));
                }
                System.out.println(mcommentList.size());
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }
}
