package com.example.hp.materialtest.user;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.User;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/11.
 */

public class user_balance_Activity extends AppCompatActivity {
    private TextView money;
    private Button money_in,money_out;
    private String input_account;
    private Request request;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        input_account = getIntent().getStringExtra("input_account");


        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        money = (TextView) findViewById(R.id.user_balance_textView);
        money_in = (Button) findViewById(R.id.money_in);
        money_out = (Button) findViewById(R.id.money_out);
        reflash();
        money_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoneyInDialog();
            }
        });

        money_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoneyOutDialog();
            }
        });
    }
    private void reflash(){
        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",input_account)
                .build();
        request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/get_user_information.action")
                .post(requestBody)
                .build();
        String response = MyRequest.sendRequestWithOkHttp(request);
        Gson gson = new Gson();
        User user = gson.fromJson(response,User.class).getUser();
        double former = user.getUser_balance();
        money.setText(former+"元");
    }

    private void showMoneyInDialog() {

        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(user_balance_Activity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(user_balance_Activity.this, R.layout.change_money, null);
        final EditText  inputText = (EditText) view.findViewById(R.id.money_input);
        Button confirm = (Button) view.findViewById(R.id.money_input_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name",input_account)
                        .add("in_money",inputText.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/user_in_money.action")
                        .post(requestBody)
                        .build();
                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(user_balance_Activity.this,"充入成功",Toast.LENGTH_SHORT).show();
                    reflash();
                }else{
                    Toast.makeText(user_balance_Activity.this,"充入失败",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }

    private void showMoneyOutDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(user_balance_Activity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(user_balance_Activity.this, R.layout.change_money, null);
        final EditText  input = (EditText) view.findViewById(R.id.money_input);
        Button confirm = (Button) view.findViewById(R.id.money_input_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name",input_account)
                        .add("out_money",input.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/user_out_money.action")
                        .post(requestBody)
                        .build();
                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(user_balance_Activity.this,"转出成功",Toast.LENGTH_SHORT).show();
                    reflash();
                }else{
                    Toast.makeText(user_balance_Activity.this,"转出失败",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }
}
