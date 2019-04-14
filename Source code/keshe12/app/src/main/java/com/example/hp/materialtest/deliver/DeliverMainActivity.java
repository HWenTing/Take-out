package com.example.hp.materialtest.deliver;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.db.deliver;
import com.example.hp.materialtest.entity.Deliver;
import com.example.hp.materialtest.entity.Order;
import com.example.hp.materialtest.entity.OrderList;
import com.google.gson.Gson;


import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DeliverMainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ImageView deliver_head_blur_image;
    private ImageView deliver_head_image;
    private SwipeRefreshLayout swipeRefresh;

    private deliver_ing_Adapter adapter;
    private List<deliver_order_Item> morderList=new ArrayList<>();


    /*用户登录账号*/
    private String input_account ;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 180;
    private static int output_Y = 180;
    public static DeliverMainActivity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_main);
        activity=this;

        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        Toolbar toolbar= (Toolbar) findViewById(R.id.deliver_toolbar);
        toolbar.setTitle("正在配送订单");
        setSupportActionBar(toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.deliver_drawer_layout);
        NavigationView navigationView= (NavigationView)findViewById(R.id.deliver_nav_view);


        final View headerLayout = navigationView.getHeaderView(0);
        deliver_head_image = (ImageView) headerLayout.findViewById(R.id.deliver_icon_image);

        deliver_head_blur_image = (ImageView) headerLayout.findViewById(R.id.deliver_icon_back_image);

        input_account = getIntent().getStringExtra("input_account");

        int p = getImage();
        Glide.with(this).load(p)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(deliver_head_blur_image);

        Glide.with(this).load(p)
                .into(deliver_head_image);


        deliver_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeliverMainActivity.this,"选择方式",Toast.LENGTH_SHORT).show();

                showTypeDialog();
            }
        });



        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
//        navigationView.setCheckedItem();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.deliver_info:

                        Intent intent_info = new Intent(DeliverMainActivity.this,deliver_information_Activity.class);
                        intent_info.putExtra("input_account",input_account);
                        startActivity(intent_info);
                        break;

                    case R.id.deliver_balance:
                        Intent intent_balance = new Intent(DeliverMainActivity.this,deliver_balance_Activity.class);
                        intent_balance.putExtra("input_account",input_account);
                        startActivity(intent_balance);
                        break;
                    case R.id.deliver_history:

                        Intent intent_history = new Intent(DeliverMainActivity.this,deliver_history_Activity.class);
                        intent_history.putExtra("input_account",input_account);
                        startActivity(intent_history);
                        break;

                    case R.id.deliver_comment:
                        Intent intent_comment = new Intent(DeliverMainActivity.this,deliver_comment_Activity.class);
                        intent_comment.putExtra("input_account",input_account);
                        startActivity(intent_comment);
                        break;
                    case R.id.deliver_available:
                        Intent intent_available = new Intent(DeliverMainActivity.this,deliver_available_order_Activity.class);
                        intent_available.putExtra("input_account",input_account);
                        startActivity(intent_available);
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.deliver_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DeliverMainActivity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });


        init();

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.deliver_recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new deliver_ing_Adapter(morderList);
        recyclerView.setAdapter(adapter);


        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.deliver_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHeads();
            }
        });

    }

    private int  init() {


        morderList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("deliver_name",input_account)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getIngOrderDeliver.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);
        System.out.println(response);

        try{
            Gson gson = new Gson();
            List<Order> orderList = gson.fromJson(response,OrderList.class).getOrderList();

            if(orderList.size()>0){
                for(int i=0;i<orderList.size();i++) {
                    String shop_location = orderList.get(i).getLocation_information_shop().getProvince()
                            +"-"+orderList.get(i).getLocation_information_shop().getCity()
                            +"-"+orderList.get(i).getLocation_information_shop().getCounty()
                            +"-"+orderList.get(i).getLocation_information_shop().getSpecific_location();

                    String user_location = orderList.get(i).getLocation_information_user().getProvince()
                            +"-"+orderList.get(i).getLocation_information_user().getCity()
                            +"-"+orderList.get(i).getLocation_information_user().getCounty()
                            +"-"+orderList.get(i).getLocation_information_user().getSpecific_location();

                    morderList.add(new deliver_order_Item(orderList.get(i).getUser_name(),orderList.get(i).getShop_name(),user_location,shop_location,orderList.get(i).getOrder_time(),orderList.get(i).getOrder_price(),orderList.get(i).getOrder_id()));
                }
                return orderList.size();
            }else{
                return 0;
            }
        }catch (Exception e){
            return 0;
        }

    }

    private void refreshHeads(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        init();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.deliver_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.deliver_Bar1:
                Toast.makeText(this,"Bar1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.deliver_Bar2:
                Toast.makeText(this,"Bar2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.deliver_Bar3:
                Toast.makeText(this,"Bar3",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return  true;
    }


    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(DeliverMainActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(DeliverMainActivity.this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }


    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        if(ContextCompat.checkSelfPermission(DeliverMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DeliverMainActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }else {
            Intent intentFromGallery = new Intent();
            // 设置文件类型
            intentFromGallery.setType("image/*");
            intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
        }
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                try{
                    cropRawPhoto(intent.getData());
                }catch (Exception e){
                    Toast.makeText(DeliverMainActivity.this,"选取图片格式失败",Toast.LENGTH_SHORT).show();
                }

                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            List<deliver> deliver_temp_list = DataSupport.where("deliver_name = ?", input_account).find(deliver.class);
            deliver mdeliver = deliver_temp_list.get(0);

            byte[]images=Bitmap2Bytes(photo);
            mdeliver.setDeliver_headImage(images);
            mdeliver.save();
            if (mdeliver.save()) {
                deliver_head_image.setImageBitmap(photo);
                Toast.makeText(DeliverMainActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DeliverMainActivity.this,"设置失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    private int getImage(){

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

            Integer image = deliver.getImage();
            return image;

        }catch (Exception e){
            return 1;
        }
    }


}
