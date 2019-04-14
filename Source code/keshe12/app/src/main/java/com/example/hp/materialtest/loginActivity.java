package com.example.hp.materialtest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.deliver.DeliverMainActivity;
import com.example.hp.materialtest.entity.Deliver;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.User;
import com.example.hp.materialtest.shop.ShopMainActivity;
import com.example.hp.materialtest.user.UserMainActivity;
import com.google.gson.Gson;


import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class loginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private RadioButton user, shop,deliver;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

//        //数据库
//         db = Connector.getDatabase();

        //用户输入
        account = (EditText)findViewById(R.id.et_userName);
        password = (EditText)findViewById(R.id.et_password);

        user = (RadioButton) findViewById(R.id.login_user);
        shop = (RadioButton) findViewById(R.id.login_shop);
        deliver = (RadioButton) findViewById(R.id.login_deliver);

        //登录按钮
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_account = account.getText().toString();
                String input_password = password.getText().toString();
                if(user.isChecked()&&user_register(input_account,input_password)){

                    Toast.makeText(loginActivity.this,input_account+"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this,UserMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }else if(shop.isChecked()&&shop_register(input_account,input_password)){
                    Toast.makeText(loginActivity.this,input_account+"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this,ShopMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }else if(deliver.isChecked()&&deliver_register(input_account,input_password)){
                    Toast.makeText(loginActivity.this,input_account+"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this,DeliverMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }else {
                    Toast.makeText(loginActivity.this,"登录失败，请重试",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //注册按钮
        Button login_to_button = (Button) findViewById(R.id.login_to_button);
        login_to_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }

    //user登录
    private boolean user_register(String input_account,String input_password){
        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/get_user_information.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        try{
            Gson gson = new Gson();
            User user = gson.fromJson(response,User.class).getUser();
            if(user.getUser_psd().equals(input_password)){
                return true;
            }

        }catch (Exception e){
        }
        return false;
    }
    //user登录
    private boolean shop_register(String input_account,String input_password){

        RequestBody requestBody = new FormBody.Builder()
                .add("shop_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/get_shop_information.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        try{
            Gson gson = new Gson();
            Shop shop = gson.fromJson(response,Shop.class).getShop();
            if(shop.getShop_psd().equals(input_password)){
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }
    //user登录
    private boolean deliver_register(String input_account,String input_password){
        RequestBody requestBody = new FormBody.Builder()
                .add("deliver_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/get_deliver_information.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        if(response.equals("error")){
            return false;
        }


        try{
            Gson gson = new Gson();
            Deliver deliver = gson.fromJson(response,Deliver.class).getDeliver();
            if(deliver.getDeliver_psd().equals(input_password)) {
                return true;
            }
        }catch (Exception e){

        }
        return false;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
//        db.close();
    }
}
