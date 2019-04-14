//package com.example.hp.materialtest;
//
///**
// * Created by HP on 2018/9/8.
// */
//
//
//        import android.app.Activity;
//        import android.app.AlertDialog;
//        import android.app.Fragment;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.graphics.Bitmap;
//        import android.graphics.BitmapFactory;
//        import android.graphics.drawable.BitmapDrawable;
//        import android.graphics.drawable.Drawable;
//        import android.net.Uri;
//        import android.os.Build;
//        import android.os.Bundle;
//        import android.os.Environment;
//        import android.os.StrictMode;
//        import android.provider.MediaStore;
//        import android.support.annotation.Nullable;
//        import android.support.v7.app.AppCompatActivity;
//        import android.util.Log;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.Button;
//        import android.widget.ImageButton;
//        import android.widget.ImageView;
//        import android.widget.LinearLayout;
//        import android.widget.ListView;
//        import android.widget.TextView;
//
//
//        import com.example.hp.materialtest.Util.GsonUtil;
//        import com.example.hp.materialtest.Util.MyRequest;
//        import com.example.hp.materialtest.entity.User;
//        import com.google.gson.Gson;
//
//        import java.io.File;
//        import java.io.FileNotFoundException;
//        import java.io.FileOutputStream;
//        import java.io.IOException;
//        import java.util.Map;
//
//        import okhttp3.FormBody;
//        import okhttp3.OkHttpClient;
//        import okhttp3.Request;
//        import okhttp3.RequestBody;
//        import okhttp3.Response;
//
//        import static android.app.Activity.RESULT_OK;
//
///**
// * Created by Administrator on 2017/5/17.
// */
//
//public class test extends AppCompatActivity {
//
//    TextView responseText;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test);
//
//        if (Build.VERSION.SDK_INT >= 11) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
//        }
//
//        Button sendRequest = (Button) findViewById(R.id.send_request);
//        responseText = (TextView) findViewById(R.id.response_text);
//        sendRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RequestBody requestBody = new FormBody.Builder()
//                        .add("user_name","u1")
//                        .build();
//
//                Request request = new Request.Builder()
//                        .url("http://10.0.2.2:8080/DBDesign/db/get_user_information.action")
//                        .post(requestBody)
//                        .build();
//
//                String response = MyRequest.sendRequestWithOkHttp(request);
//                Gson gson = new Gson();
//                User user = gson.fromJson(response,User.class).getUser();
//                Double balance = user.getUser_balance();
//                responseText.setText(balance+"");
//
//
////                Map<String,Object> map = GsonUtil.fromJsonToMap(response);
////                Map<String,Object> user = (Map<String,Object>)map.get("user");
////                Double balance = (Double)user.get("user_balance");
////                responseText.setText(balance+"");
//            }
//        });
//
//    }
////    private void sendRequestWithOkHttp(){
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                try{
////
////                    OkHttpClient client = new OkHttpClient();
////                    RequestBody requestBody = new FormBody.Builder()
////                            .add("user_name","u1")
////                            .build();
////
////                    Request request = new Request.Builder()
////                            .url("http://10.0.2.2:8080/DBDesign/db/get_user_information.action")
////                            .post(requestBody)
////                            .build();
////
////                    Response response = client.newCall(request).execute();
////
////                    String responseData = response.body().string();
////                    Log.e("aa",responseData);
////                    showResponse(responseData);
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////            }
////        }).start();
////    }
////    private void showResponse(final String response){
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                responseText.setText(response);
////            }
////        });
////    }
////
//}
//
