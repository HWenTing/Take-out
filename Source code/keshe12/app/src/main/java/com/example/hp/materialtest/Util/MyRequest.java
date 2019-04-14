package com.example.hp.materialtest.Util;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by HP on 2018/9/19.
 */

public class MyRequest {

    public static String sendRequestWithOkHttp(Request request){
        try {

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            return  responseData;
        }catch (Exception e){
            return null;
        }

    }
}
