package com.example.hp.materialtest.deliver;

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
import com.example.hp.materialtest.entity.Deliver;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.shop.ShopMainActivity;
import com.example.hp.materialtest.shop.shop_information_Activity;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class deliver_information_Activity extends AppCompatActivity {
    private EditText nickname;
    private EditText IDcard;
    private EditText mobile;
    private Button confirm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_information);
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        confirm = (Button) findViewById(R.id.information_confirm);
        nickname =(EditText) findViewById(R.id.information_nickname);
        IDcard =(EditText) findViewById(R.id.information_ID_card);
        mobile =(EditText) findViewById(R.id.information_mobile);

        final String input_account = getIntent().getStringExtra("input_account");

        RequestBody requestBody = new FormBody.Builder()
                .add("deliver_name",input_account)
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/get_deliver_information.action")
                .post(requestBody)
                .build();
        String response = MyRequest.sendRequestWithOkHttp(request);

        try{
            Gson gson = new Gson();
            Deliver deliver = gson.fromJson(response,Deliver.class).getDeliver();

            String text_nickname_former = deliver.getDeliver_nickname();
            String text_IDcard_former = deliver.getDeliver_IDcard();
            String text_mobile_former = deliver.getDeliver_mobile();

            nickname.setText(text_nickname_former);
            IDcard.setText(text_IDcard_former);

            mobile.setText(text_mobile_former);
        }catch (Exception e){
            Toast.makeText(deliver_information_Activity.this,response,Toast.LENGTH_SHORT).show();
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_nickname_later = nickname.getText().toString();
                String text_IDcard_later = IDcard.getText().toString();
                String text_mobile_later = mobile.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("deliver_name",input_account)
                        .add("deliver_nickname",text_nickname_later)
                        .add("deliver_mobile",text_mobile_later)
                        .add("deliver_IDcard",text_IDcard_later)
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/set_deliver_information.action")
                        .post(requestBody)
                        .build();
                String response = MyRequest.sendRequestWithOkHttp(request);


                if(response.equals("success")){
                    Toast.makeText(deliver_information_Activity.this,"信息更改成功",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(deliver_information_Activity.this,DeliverMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }else{
                    Toast.makeText(deliver_information_Activity.this, "信息更改失败", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(deliver_information_Activity.this,DeliverMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }

            }
        });

    }
}
