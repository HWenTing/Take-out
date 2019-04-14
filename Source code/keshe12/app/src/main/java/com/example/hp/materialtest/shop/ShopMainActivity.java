package com.example.hp.materialtest.shop;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.Util.Touxiang;
import com.example.hp.materialtest.db.shop;
import com.example.hp.materialtest.entity.FoodList;
import com.example.hp.materialtest.entity.Shop;
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


public class ShopMainActivity extends AppCompatActivity {

    private Shop_food_Adapter adapter;
    private List<Shop_food> food_List=new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;
    private ImageView shop_head_blur_image;
    private ImageView shop_head_image;
    public static ShopMainActivity activity;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        activity=this;

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("菜单管理");
        setSupportActionBar(toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.shop_drawer_layout);
        NavigationView navigationView= (NavigationView)findViewById(R.id.shop_nav_view);

        final View headerLayout = navigationView.getHeaderView(0);
        shop_head_image = (ImageView) headerLayout.findViewById(R.id.shop_icon_image);
        shop_head_blur_image = (ImageView) headerLayout.findViewById(R.id.shop_icon_back_image);


        input_account = getIntent().getStringExtra("input_account");

        int p = getImage();
        Glide.with(this).load(p)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(shop_head_blur_image);

            Glide.with(this).load(p)
                    .into(shop_head_image);



        shop_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopMainActivity.this,"选择方式",Toast.LENGTH_SHORT).show();

                showTypeDialog();
            }
        });




        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

//        TextView name = (TextView) navigationView.findViewById(R.id.shop_mail);
//        name.setText(input_account);

        navigationView.setCheckedItem(R.id.shop_food_manage);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.shop_info:
                        Intent intent_info = new Intent(ShopMainActivity.this,shop_information_Activity.class);
                        intent_info.putExtra("input_account",input_account);
                        startActivity(intent_info);
                        break;
                    case R.id.shop_location:
                        Intent intent_location = new Intent(ShopMainActivity.this,shop_location_Activity.class);
                        intent_location.putExtra("input_account",input_account);
                        startActivity(intent_location);
                        break;
                    case R.id.shop_balance:
                        Intent intent_balance = new Intent(ShopMainActivity.this,shop_balance_Activity.class);
                        intent_balance.putExtra("input_account",input_account);
                        startActivity(intent_balance);
                        break;
                    case R.id.shop_history:
                        Intent intent_history = new Intent(ShopMainActivity.this,shop_history_Activity.class);
                        intent_history.putExtra("input_account",input_account);
                        startActivity(intent_history);
                        break;

                    case R.id.shop_food_manage:
                        break;
                    case R.id.shop_comment:
                        Intent intent_comment = new Intent(ShopMainActivity.this,shop_comment_Activity.class);
                        intent_comment.putExtra("input_account",input_account);
                        startActivity(intent_comment);
                        break;
                    case R.id.shop_order_manage:
                        showOrderChooseDialog();
                        Toast.makeText(ShopMainActivity.this,"选择类别",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
//                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.shop_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodDialog();
            }
        });
        initfoods();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.shop_recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Shop_food_Adapter(food_List);
        recyclerView.setAdapter(adapter);


        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.shop_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHeads();
            }
        });

    }

    private void addFoodDialog(){


        AlertDialog.Builder builder = new AlertDialog.Builder(ShopMainActivity.this);
        final AlertDialog dialog = builder.create();
        final View mview = View.inflate(ShopMainActivity.this, R.layout.dialog_shop_addfood, null);


        final EditText addFood_name = (EditText)mview.findViewById(R.id.shop_addFood_name);

        final EditText addFood_description = (EditText) mview.findViewById(R.id.shop_addFood_description);

        final EditText addFood_price = (EditText) mview.findViewById(R.id.shop_addFood_price);

        Button cancel = (Button)mview.findViewById(R.id.cancel);
        Button confirm = (Button)mview.findViewById(R.id.confirm);
        dialog.setView(mview);
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {



                RequestBody requestBody = new FormBody.Builder()
                        .add("shop_name",input_account)
                        .add("food_name",addFood_name.getText().toString())
                        .add("food_price",addFood_price.getText().toString())
                        .add("food_description",addFood_description.getText().toString())
                        .add("food_image",Touxiang.getFoodImage()+"")
                        .build();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DBDesign/db/addFood.action")
                        .post(requestBody)
                        .build();

                String response = MyRequest.sendRequestWithOkHttp(request);
                if(response.equals("success")){
                    Toast.makeText(ShopMainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShopMainActivity.this,response,Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        });

    }





    private boolean initfoods() {

        food_List.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("shop_name",input_account)
                .build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getFoodByShopName.action")
                .post(requestBody)
                .build();

        String response = MyRequest.sendRequestWithOkHttp(request);

        try{
            Gson gson = new Gson();
            List<com.example.hp.materialtest.entity.Food> shopList = gson.fromJson(response,FoodList.class).getFoodList();
            if(shopList.size()>0){
                for(int i=0;i<shopList.size();i++) {
                    food_List.add(new Shop_food(shopList.get(i).getFood_name(),
                            shopList.get(i).getFood_description(),shopList.get(i).getPrice(),shopList.get(i).getImage(),shopList.get(i).getFood_id()));
                }
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
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
                        initfoods();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.shop_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.Bar1:
                Toast.makeText(this,"Bar1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Bar2:
                Toast.makeText(this,"Bar2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Bar3:
                Toast.makeText(this,"Bar3",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return  true;
    }

    private void showOrderChooseDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopMainActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(ShopMainActivity.this, R.layout.dialog_select_order, null);

        TextView confirm= (TextView) view.findViewById(R.id.confirm);
        TextView wait_deliver = (TextView) view.findViewById(R.id.wait_deliver);
        TextView wait_finish = (TextView) view.findViewById(R.id.wait_finish);

        confirm.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent_confirm = new Intent(ShopMainActivity.this,shop_manage_needConfirm_Activity.class);
                intent_confirm.putExtra("input_account",input_account);
                startActivity(intent_confirm);
                dialog.dismiss();

            }
        });
        wait_deliver.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent_wait_deliver = new Intent(ShopMainActivity.this,shop_manage_waitDeliver_Activity.class);
                intent_wait_deliver.putExtra("input_account",input_account);
                startActivity(intent_wait_deliver);
                dialog.dismiss();
            }
        });
        wait_finish.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {

                Intent intent_wait_finish = new Intent(ShopMainActivity.this,shop_manage_waitFinish_Activity.class);
                intent_wait_finish.putExtra("input_account",input_account);
                startActivity(intent_wait_finish);
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }



    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopMainActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(ShopMainActivity.this, R.layout.dialog_select_photo, null);
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
        if(ContextCompat.checkSelfPermission(ShopMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ShopMainActivity.this,new String[]{
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
                    Toast.makeText(ShopMainActivity.this,"选取图片格式失败",Toast.LENGTH_SHORT).show();
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
            List<shop> shop_temp_list = DataSupport.where("shop_name = ?", input_account).find(shop.class);
            shop mshop = shop_temp_list.get(0);

            byte[]images=Bitmap2Bytes(photo);
            mshop.setShop_headImage(images);
            mshop.save();
            if (mshop.save()) {
                shop_head_image.setImageBitmap(photo);
                Toast.makeText(ShopMainActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ShopMainActivity.this,"设置失败",Toast.LENGTH_SHORT).show();
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

            Integer image = shop.getImage();
            return image;

        }catch (Exception e){
            return 1;
        }
    }
}

