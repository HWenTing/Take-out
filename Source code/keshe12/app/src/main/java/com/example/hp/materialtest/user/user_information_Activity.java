package com.example.hp.materialtest.user;


import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;



import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.User;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * A login screen that offers login via email/password.
 */
public class user_information_Activity extends AppCompatActivity  {

    private EditText nickname;
    private EditText email;
    private EditText gender;
    private EditText mobile;
    private Button confirm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_);

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        confirm = (Button) findViewById(R.id.information_confirm);

        nickname =(EditText) findViewById(R.id.information_nickname);
        email =(EditText) findViewById(R.id.information_email);
        gender =(EditText) findViewById(R.id.information_gender);
        mobile =(EditText) findViewById(R.id.information_mobile);

        final String input_account = getIntent().getStringExtra("input_account");
//        List<user> user_temp_list = DataSupport.where("user_name = ?", input_account).find(user.class);
//        final user muser = user_temp_list.get(0);
//        String text_nickname_former = muser.getUser_nickname();
//        String text_email_former = muser.getUser_email();
//        String text_gender_former = muser.getUser_gender();
//        String text_mobile_former = muser.getUser_mobile();

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

            String text_nickname_former = user.getUser_nickname();
            String text_email_former = user.getUser_email();
            String text_gender_former = user.getUser_gender();
            String text_mobile_former = user.getUser_mobile();

            nickname.setText(text_nickname_former);
            email.setText(text_email_former);
            gender.setText(text_gender_former);
            mobile.setText(text_mobile_former);
        }catch (Exception e){
            Toast.makeText(user_information_Activity.this,response,Toast.LENGTH_SHORT).show();
        }

        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_nickname_later = nickname.getText().toString();
                String text_email_later = email.getText().toString();
                String text_gender_later = gender.getText().toString();
                String text_mobile_later = mobile.getText().toString();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name",input_account)
                        .add("user_nickname",text_nickname_later)
                        .add("user_mobile",text_mobile_later)
                        .add("user_email",text_email_later)
                        .add("user_gender",text_gender_later)
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/set_user_information.action")
                        .post(requestBody)
                        .build();
                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(user_information_Activity.this,"信息更改成功",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(user_information_Activity.this,UserMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }else{
                    Toast.makeText(user_information_Activity.this, "信息更改失败", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(user_information_Activity.this,UserMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }

            }
        });
    }
}

