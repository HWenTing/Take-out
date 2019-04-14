package com.example.hp.materialtest.user;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.db.user;
import com.example.hp.materialtest.entity.Shop;
import com.example.hp.materialtest.entity.ShopList;
import com.example.hp.materialtest.entity.User;
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

public class UserMainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    private List<Head> headList=new ArrayList<>();
    private HeadAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private ImageView user_head_blur_image;
    private ImageView user_head_image;

    private EditText search_shop_EditText;
    private Button search_shop_Button;

    /*用户登录账号*/
    public static String input_account ;
    public static UserMainActivity activity;
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
        setContentView(R.layout.activity_user_main);
        activity=this;
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        search_shop_EditText = (EditText) findViewById(R.id.search_shop_EditText);
        search_shop_Button = (Button) findViewById(R.id.search_shop_Button);

        search_shop_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHeadsWithTag(search_shop_EditText.getText().toString());
                adapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("搜索商家");
        setSupportActionBar(toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);

        /*连接数据库*/
//        SQLiteDatabase db = Connector.getDatabase();

//        //防止找不到nav的状况
        final View headerLayout = navigationView.getHeaderView(0);


        user_head_image = (ImageView) headerLayout.findViewById(R.id.user_icon_image);

        user_head_blur_image = (ImageView) headerLayout.findViewById(R.id.user_icon_back_image);

        input_account = getIntent().getStringExtra("input_account");



        int p = getImage();
        Glide.with(this).load(p)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(user_head_blur_image);
        Glide.with(this).load(p)
                .into(user_head_image);


        user_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserMainActivity.this,"选择方式",Toast.LENGTH_SHORT).show();

                showTypeDialog();
            }
        });

        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.user_info:

                        Intent intent_info = new Intent(UserMainActivity.this,user_information_Activity.class);
                        intent_info.putExtra("input_account",input_account);
                        startActivity(intent_info);
                        break;
                    case R.id.user_location:
                        Intent intent_location = new Intent(UserMainActivity.this,user_location_Activity.class);
                        intent_location.putExtra("input_account",input_account);
                        startActivity(intent_location);
                        break;
                    case R.id.user_balance:
                        Intent intent_balance = new Intent(UserMainActivity.this,user_balance_Activity.class);
                        intent_balance.putExtra("input_account",input_account);
                        startActivity(intent_balance);

                        break;
                    case R.id.user_history:
                        Intent intent_history = new Intent(UserMainActivity.this,user_history_Activity.class);
                        intent_history.putExtra("input_account",input_account);
                        startActivity(intent_history);
                        break;

                    case R.id.user_like:
                        Intent intent_like = new Intent(UserMainActivity.this,user_like_Activity.class);
                        intent_like.putExtra("input_account",input_account);
                        startActivity(intent_like);
                        break;
                    case R.id.user_comment_to_shop:
                        Intent intent_comment_to_shop = new Intent(UserMainActivity.this,user_comment_to_shop_Activity.class);
                        intent_comment_to_shop.putExtra("input_account",input_account);
                        startActivity(intent_comment_to_shop);
                        break;
                    case R.id.user_comment_to_deliver:
                        Intent intent_comment_to_deliver = new Intent(UserMainActivity.this,user_comment_to_deliver_Activity.class);
                        intent_comment_to_deliver.putExtra("input_account",input_account);
                        startActivity(intent_comment_to_deliver);
                        break;
                    case R.id.user_order_unfinish:
                        Intent intent_order_unfinish = new Intent(UserMainActivity.this,user_order_unfinish_Activity.class);
                        intent_order_unfinish.putExtra("input_account",input_account);
                        startActivity(intent_order_unfinish);
                        break;


                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UserMainActivity.this,"Data ",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        initHeads();

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new HeadAdapter(headList);
        recyclerView.setAdapter(adapter);
        swipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHeads();
            }
        });


    }
    private void refreshHeads(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initHeads();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.address:
                Toast.makeText(this,"address",Toast.LENGTH_SHORT).show();
                break;
            case R.id.camera:
                Toast.makeText(this,"camera",Toast.LENGTH_SHORT).show();
                break;
            case R.id.calendar:
                Toast.makeText(this,"calendar",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return  true;
    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(UserMainActivity.this, R.layout.dialog_shop_addfood, null);
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
        if(ContextCompat.checkSelfPermission(UserMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UserMainActivity.this,new String[]{
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
                    Toast.makeText(UserMainActivity.this,"选取图片格式失败",Toast.LENGTH_SHORT).show();
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
            List<user> user_temp_list = DataSupport.where("user_name = ?", input_account).find(user.class);
            user muser = user_temp_list.get(0);

            byte[]images=Bitmap2Bytes(photo);
            muser.setUser_headImage(images);
            muser.save();
            if (muser.save()) {
                user_head_image.setImageBitmap(photo);
                Toast.makeText(UserMainActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(UserMainActivity.this,"设置失败",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        Shop_itemFood_Adapter.order_foods = new ArrayList<Shop_itemFood>();
    }


    private boolean initHeads(){
        headList.clear();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getAllShop.action")
                .build();
        String response = MyRequest.sendRequestWithOkHttp(request);
        try{
            Gson gson = new Gson();
            List<Shop> shopList = gson.fromJson(response,ShopList.class).getShopList();
            if(shopList.size()>0){
                for(int i=0;i<shopList.size();i++) {
                    headList.add(new Head(shopList.get(i).getShop_name(),shopList.get(i).getShop_nickname(),shopList.get(i).getImage() ));
                }
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }


    private boolean initHeadsWithTag(String tag){
        headList.clear();

        RequestBody requestBody = new FormBody.Builder()
                .add("ShopTag",tag)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/DBDesign/db/getShopByShopTag.action")
                .post(requestBody)
                .build();
        String response = MyRequest.sendRequestWithOkHttp(request);
        try {
            Gson gson = new Gson();
            List<Shop> shopList = gson.fromJson(response,ShopList.class).getShopList();
            if(shopList.size()>0){
                for(int i=0;i<shopList.size();i++) {
                    headList.add(new Head(shopList.get(i).getShop_name(),shopList.get(i).getShop_nickname(),shopList.get(i).getImage()));
                }
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }

    private int getImage(){

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

            Integer image = user.getImage();
            return image;

        }catch (Exception e){
            return 1;
        }
    }
}
