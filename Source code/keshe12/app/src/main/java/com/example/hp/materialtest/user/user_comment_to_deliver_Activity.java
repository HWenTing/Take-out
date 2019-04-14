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
import com.example.hp.materialtest.entity.Comment_User_To_Deliver;
import com.example.hp.materialtest.entity.Comment_User_To_DeliverList;
import com.example.hp.materialtest.entity.Deliver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class user_comment_to_deliver_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<CommentItem> mcommentList=new ArrayList<>();
    private DeliverCommentAdapter adapter;
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
        title.setText("评价(骑手)");

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        input_account = getIntent().getStringExtra("input_account");

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(user_comment_to_deliver_Activity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        initComments();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new DeliverCommentAdapter(mcommentList);
        recyclerView.setAdapter(adapter);
    }


    private boolean initComments(){
        mcommentList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/get_comments_from_user_to_deliver.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);

        try{
            Gson gson = new Gson();
            List<Comment_User_To_Deliver> orderList = gson.fromJson(response,Comment_User_To_DeliverList.class).getToDeliverComments();
            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++) {

                    requestBody = new FormBody.Builder()
                            .add("deliver_name",orderList.get(i).getDeliver_name())
                            .build();

                    request = new Request.Builder()
                            .url("http://10.0.2.2:8080/DBDesign/db/get_deliver_information.action")
                            .post(requestBody)
                            .build();

                    response = MyRequest.sendRequestWithOkHttp(request);
                    Deliver deliver = gson.fromJson(response,Deliver.class).getDeliver();

                    mcommentList.add(new CommentItem(orderList.get(i).getDeliver_name(),orderList.get(i).getComment(), orderList.get(i).getScore(),deliver.getImage()));
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