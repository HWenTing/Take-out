package com.example.hp.materialtest;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.Util.Touxiang;
import com.example.hp.materialtest.db.user;
import com.example.hp.materialtest.db.shop;
import com.example.hp.materialtest.db.deliver;
import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Register extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private Button btn_register;

    private RadioButton user, shop,deliver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        // 注册按钮
        btn_register = (Button) findViewById(R.id.btn_register);
        account = (EditText)findViewById(R.id.et_account_register);
        password = (EditText)findViewById(R.id.et_password_register);

        user = (RadioButton) findViewById(R.id.register_user);
        shop = (RadioButton) findViewById(R.id.register_shop);
        deliver = (RadioButton) findViewById(R.id.register_deliver);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_account = account.getText().toString();
                String input_password = password.getText().toString();
                if(user.isChecked()){
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_name",input_account)
                            .add("user_psd",input_password)
                            .add("image", Touxiang.getTouxiang()+"")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/DBDesign/db/user_register.action")
                            .post(requestBody)
                            .build();

                    String response = MyRequest.sendRequestWithOkHttp(request);
                    if(response.equals("success")){
                        Toast.makeText(Register.this,"user注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(Register.this,loginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Register.this, "注册失败,请重试", Toast.LENGTH_SHORT).show();
                    }
                }else if(shop.isChecked()){
                    RequestBody requestBody = new FormBody.Builder()
                            .add("shop_name",input_account)
                            .add("shop_psd",input_password)
                            .add("image", Touxiang.getShopImage()+"")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/DBDesign/db/shop_register.action")
                            .post(requestBody)
                            .build();

                    String response = MyRequest.sendRequestWithOkHttp(request);
                    Log.d("dd",response);
                    if(response.equals("success")){
                        Toast.makeText(Register.this,"shop注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(Register.this,loginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Register.this, "注册失败,请重试", Toast.LENGTH_SHORT).show();
                    }

                }else if (deliver.isChecked()){
                    RequestBody requestBody = new FormBody.Builder()
                            .add("deliver_name",input_account)
                            .add("deliver_psd",input_password)
                            .add("image",Touxiang.getTouxiang()+"")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/DBDesign/db/deliver_register.action")
                            .post(requestBody)
                            .build();

                    String response = MyRequest.sendRequestWithOkHttp(request);
                    if(response.equals("success")){
                        Toast.makeText(Register.this,"deliver注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(Register.this,loginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Register.this, "注册失败,请重试", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Register.this,"please choose a role",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
