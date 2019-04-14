package com.example.hp.materialtest.shop;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class shop_location_Activity extends AppCompatActivity {
    private Spinner spinner_sheng;//省
    private Spinner spinner_shi;//市
    private Spinner spinner_qu;//区

    private EditText et_detailAddress;//输入详细地址

    private Button btn_confirm;//确认按钮
    private int resultCode = 102;//返回码

    //全局的jsonObject
    private JSONObject jsonObject;//把全国的省市区的信息以json的格式保存，解析完成后赋值为null
    private String[] allProv;//所有的省

    private ArrayAdapter<String> provinceAdapter;//省份数据适配器
    private ArrayAdapter<String> cityAdapter;//城市数据适配器
    private ArrayAdapter<String> areaAdapter;//区县数据适配器

    private String[] allSpinList;//在spinner中选出来的地址，后面需要用空格隔开省市区

    private String address;//用来接收intent的参数
    private String allAddress;//用来接收intent参数


    private String provinceName;//省的名字
    private String areaName;//区的名字
    private Boolean isFirstLoad = true;//判断是不是最近进入对话框
    private Boolean ifSetFirstAddress = true;//判断是否已经设置了，初始的详细地址

    //省市区的集合
    private Map<String, String[]> cityMap = new HashMap<String, String[]>();//key:省p---value:市n  value是一个集合
    private Map<String, String[]> areaMap = new HashMap<String, String[]>();//key:市n---value:区s    区也是一个集合

    private  String input_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口不显示标题
        setContentView(R.layout.activity_shop_location);

        input_account = getIntent().getStringExtra("input_account");

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        initJsonData();//初始化json数据
        initDatas();//初始化省市区数据
        initView();//初始化控件
        initClick();//初始化点击
        setSpinnerData();//为spinner设置值
    }

    /**
     * spinner设置值（默认设置值）
     */
    private void setSpinnerData() {
        int selectPosition = 0;//有数据传入时
        address = getIntent().getStringExtra("address");
        allAddress = getIntent().getStringExtra("allAddress");
        if (address != null && !address.equals("") && allAddress != null && !allAddress.equals("")) {
            allSpinList = address.split(" ");//用空格隔开allSpinList地址
        }
        /**
         * 设置省市区的适配器，进行动态设置
         */
        provinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);//系统默认的
        for (int i = 0; i < allProv.length; i++) {
            //给spinner省赋值,设置默认值
            if (address != null && !address.equals("") && allAddress != null && !allAddress.equals("")&& allSpinList.length > 0 && allSpinList[0].equals(allProv[i])) {
                selectPosition = i;
            }
            provinceAdapter.add(allProv[i]);//添加每一个省
        }
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//按下的效果
        spinner_sheng.setAdapter(provinceAdapter);
//        spinner_sheng.setSelection(selectPosition);//设置选中的省，默认

        //市
        cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shi.setAdapter(cityAdapter);

        //区县
        areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_qu.setAdapter(areaAdapter);

        setListener();//设置spinner的点击监听

    }

    //设置spinner的点击监听
    private void setListener() {
        //省
        spinner_sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provinceName = parent.getSelectedItem() + "";//获取点击列表spinner item的省名字
                if (isFirstLoad) {
                    // 判断是否省市区都存在
                    if (address != null && !address.equals("") && allAddress != null && !allAddress.equals("")&& allSpinList.length > 1 && allSpinList.length < 3) {
                        updateCityAndArea(provinceName, allSpinList[1], null);//更新市和区
                    } else if (address != null && !address.equals("") && allAddress != null && !allAddress.equals("") && allSpinList.length >= 3) {
                        //存在省市区
                        //去更新
                        updateCityAndArea(provinceName, allSpinList[1], allSpinList[2]);
                    } else {
                        updateCityAndArea(provinceName, null, null);
                    }
                } else {
                    updateCityAndArea(provinceName, null, null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //市
        spinner_shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirstLoad) {
                    //更新区
                    updateArea(parent.getSelectedItem() + "", null);
                } else {
                    if (address != null && !address.equals("") && allAddress != null && !allAddress.equals("") && allSpinList.length == 4) {
                        et_detailAddress.setText(allSpinList[3]);//没有进入区的对话框，
                    }
                }
                isFirstLoad = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //区
        spinner_qu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //直接获取区的名字
                areaName = parent.getSelectedItem() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * 根据当前的省，更新市和区的信息
     */
    private void updateCityAndArea(Object object, Object city, Object area) {
        int selectPosition = 0;//有数据时，进行匹配城市，默认选中
        String[] cities = cityMap.get(object);
        cityAdapter.clear();//清空adapter的数据
        for (int i = 0; i < cities.length; i++) {
            if (city != null && city.toString().equals(cities[i])) {//判断传入的市在集合中匹配
                selectPosition = i;
            }
            cityAdapter.add(cities[i]);//将这个列表“市”添加到adapter中
        }
        cityAdapter.notifyDataSetChanged();//刷新
        if (city == null) {
            updateArea(cities[0], null);//更新区,没有市则默认第一个给它
        } else {
            spinner_shi.setSelection(selectPosition);
            updateArea(city, area);//穿入的区去集合中匹配
        }

    }

    //根据当前的市，更新区的信息
    private void updateArea(Object object, Object myArea) {
        boolean isArea = false;//判断第三个地址是地区还是详细地址
        int selectPosition = 0;//当有数据时，进行匹配地区，默认选中
        String[] area = areaMap.get(object);
        areaAdapter.clear();//清空
        if (area != null) {
            for (int i = 0; i < area.length; i++) {
                if (myArea != null && myArea.toString().equals(area[i])) {//去集合中匹配
                    selectPosition = i;
                    isArea = true;//地区
                }
                areaAdapter.add(area[i]);//填入到这个列表

            }
            areaAdapter.notifyDataSetChanged();//刷新
            spinner_qu.setSelection(selectPosition);//默认选中
        }
        //第三个地址是详细地址，并且是第一次设置edtext值，正好，地址的长度为3的时候，设置详细地址
        if (!isArea && ifSetFirstAddress && address != null && !address.equals("") && allAddress != null && !allAddress.equals("") && allSpinList.length == 3) {
            et_detailAddress.setText(allSpinList[2]);
            ifSetFirstAddress = false;
        }
    }

    //初始化省市区数据
    private void initDatas() {
        try {

            InputStreamReader isr = new InputStreamReader(getAssets().open("city.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
//

            JSONArray jsonArray = new JSONArray(builder.toString());
//            JSONArray jsonArray = jsonObject.getJSONArray();//获取整个json数据
            allProv = new String[jsonArray.length()];//封装数据

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);//jsonArray转jsonObject
                String provStr = jsonP.getString("name");//获取所有的省
                allProv[i] = provStr;//封装所有的省
                JSONArray jsonCity = null;

                try {
                    jsonCity = jsonP.getJSONArray("city");//在所有的省中取出所有的市，转jsonArray

                } catch (Exception e) {
                    continue;
                }
                //所有的市
                String[] allCity = new String[jsonCity.length()];//所有市的长度
                for (int c = 0; c < jsonCity.length(); c++) {
                    JSONObject jsonCy = jsonCity.getJSONObject(c);//转jsonObject
                    String cityStr = jsonCy.getString("name");//取出所有的市
                    allCity[c] = cityStr;//封装市集合
                    JSONArray jsonArea = null;
                    try {
                        jsonArea = jsonCy.getJSONArray("area");//在从所有的市里面取出所有的区,转jsonArray

                    } catch (Exception e) {
                        continue;
                    }

                    String[] allArea = new String[jsonArea.length()];//所有的区
                    for (int a = 0; a < jsonArea.length(); a++) {
                        allArea[a] = jsonArea.get(a).toString();//封装起来
                    }
                    areaMap.put(cityStr, allArea);//某个市取出所有的区集合


                }
                cityMap.put(provStr, allCity);//某个省取出所有的市,
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject = null;//清空所有的数据
    }

    /**
     * 从assert文件夹中获取json数据
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len = -1;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, "utf-8"));//根据字节长度设置编码
            }
            is.close();//关闭流
            jsonObject = new JSONObject(sb.toString());//为json赋值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化点击
    private void initClick() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody requestBody = new FormBody.Builder()
                        .add("characterEncoding","utf8")
                        .add("shop_name",input_account)
                        .add("province",spinner_sheng.getSelectedItem().toString())
                        .add("city",spinner_shi.getSelectedItem().toString())
                        .add("county",spinner_qu.getSelectedItem().toString())
                        .add("specific_location",et_detailAddress.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/set_shop_location.action")
                        .post(requestBody)
                        .build();
                String response = MyRequest.sendRequestWithOkHttp(request);
                System.out.println(response);
                if(response.equals("success")){
                    Toast.makeText(shop_location_Activity.this,"位置信息更改成功",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(shop_location_Activity.this,ShopMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }else{
                    Toast.makeText(shop_location_Activity.this, "位置信息更改失败", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(shop_location_Activity.this,ShopMainActivity.class);
                    intent.putExtra("input_account",input_account);
                    startActivity(intent);
                }

                Intent intent = new Intent();
                setResult(resultCode, intent);
                finish();
            }
        });
    }

    //初始化控件
    private void initView() {
        spinner_sheng = (Spinner) findViewById(R.id.spinner_province);
        spinner_shi = (Spinner) findViewById(R.id.spinner_city);
        spinner_qu = (Spinner) findViewById(R.id.spinner_county);
        et_detailAddress = (EditText) findViewById(R.id.location_detailed);
        btn_confirm = (Button) findViewById(R.id.location_confirm);
    }
}