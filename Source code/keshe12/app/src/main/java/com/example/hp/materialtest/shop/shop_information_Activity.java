package com.example.hp.materialtest.shop;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Shop;
import com.google.gson.Gson;


import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class shop_information_Activity extends AppCompatActivity {
    private EditText nickname;
    private EditText email;
    private EditText license;
    private EditText mobile;
    private EditText tag;
    private Button confirm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        confirm = (Button) findViewById(R.id.information_confirm);
        nickname =(EditText) findViewById(R.id.information_nickname);
        email =(EditText) findViewById(R.id.information_email);
        license =(EditText) findViewById(R.id.information_production_license);
        mobile =(EditText) findViewById(R.id.information_mobile);
        tag = (EditText) findViewById(R.id.information_tag);

        final String input_account = getIntent().getStringExtra("input_account");

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

            String text_nickname_former = shop.getShop_nickname();
            String text_email_former = shop.getShop_email();
            String text_license_former = shop.getShop_license();
            String text_mobile_former = shop.getShop_mobile();
            String text_tag_former = shop.getShop_tag();

            nickname.setText(text_nickname_former);
            email.setText(text_email_former);
            license.setText(text_license_former);
            mobile.setText(text_mobile_former);
            tag.setText(text_tag_former);

        }catch (Exception e){
            Toast.makeText(shop_information_Activity.this,response,Toast.LENGTH_SHORT).show();
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_nickname_later = nickname.getText().toString();
                String text_email_later = email.getText().toString();
                String text_license_later = license.getText().toString();
                String text_mobile_later = mobile.getText().toString();
                String text_tag_later = tag.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("shop_name",input_account)
                        .add("shop_nickname",text_nickname_later)
                        .add("shop_mobile",text_mobile_later)
                        .add("shop_email",text_email_later)
                        .add("shop_license",text_license_later)
                        .add("shop_tag",text_tag_later)
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/set_shop_information.action")
                        .post(requestBody)
                        .build();
                String response = MyRequest.sendRequestWithOkHttp(request);


                if(response.equals("success")){
                    Toast.makeText(shop_information_Activity.this,"信息更改成功",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(shop_information_Activity.this,ShopMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }else{
                    Toast.makeText(shop_information_Activity.this, "信息更改失败", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(shop_information_Activity.this,ShopMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }

            }
        });






    }
}
