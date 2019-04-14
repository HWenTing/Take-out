//package com.example.hp.materialtest;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.content.ContentUris;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.File;
//
///**
// * Created by HP on 2018/9/8.
// */
//
//public class test2 extends AppCompatActivity {
//
//    /* 头像文件 */
//    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
//
//    /* 请求识别码 */
//    private static final int CODE_GALLERY_REQUEST = 0xa0;
//    private static final int CODE_CAMERA_REQUEST = 0xa1;
//    private static final int CODE_RESULT_REQUEST = 0xa2;
//
//    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
//    private static int output_X = 180;
//    private static int output_Y = 180;
//
//    private ImageView headImage = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test2);
//
//        headImage = (ImageView) findViewById(R.id.imageView);
//
//        Button buttonLocal = (Button) findViewById(R.id.buttonLocal);
//        buttonLocal.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                choseHeadImageFromGallery();
//            }
//        });
//
//        Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
//        buttonCamera.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                choseHeadImageFromCameraCapture();
//            }
//        });
//    }
//
//    // 从本地相册选取图片作为头像
//    private void choseHeadImageFromGallery() {
//        if(ContextCompat.checkSelfPermission(test2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(test2.this,new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            },1);
//        }else {
//            Intent intentFromGallery = new Intent();
//            // 设置文件类型
//            intentFromGallery.setType("image/*");
//            intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
//        }
//    }
//
//    // 启动手机相机拍摄照片作为头像
//    private void choseHeadImageFromCameraCapture() {
//        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        // 判断存储卡是否可用，存储照片文件
//        if (hasSdcard()) {
//            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//                    .fromFile(new File(Environment
//                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
//        }
//
//        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent intent) {
//
//        // 用户没有进行有效的设置操作，返回
//        if (resultCode == RESULT_CANCELED) {
//            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        switch (requestCode) {
//            case CODE_GALLERY_REQUEST:
//                try{
//                    cropRawPhoto(intent.getData());
//                }catch (Exception e){
//                    Toast.makeText(test2.this,"选取图片格式失败",Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//
//            case CODE_CAMERA_REQUEST:
//                if (hasSdcard()) {
//                    File tempFile = new File(
//                            Environment.getExternalStorageDirectory(),
//                            IMAGE_FILE_NAME);
//                    cropRawPhoto(Uri.fromFile(tempFile));
//                } else {
//                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
//                            .show();
//                }
//
//                break;
//
//            case CODE_RESULT_REQUEST:
//                if (intent != null) {
//                    setImageToHeadView(intent);
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, intent);
//    }
//
//    /**
//     * 裁剪原始的图片
//     */
//    public void cropRawPhoto(Uri uri) {
//
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//
//        // aspectX , aspectY :宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//
//        // outputX , outputY : 裁剪图片宽高
//        intent.putExtra("outputX", output_X);
//        intent.putExtra("outputY", output_Y);
//        intent.putExtra("return-data", true);
//
//        startActivityForResult(intent, CODE_RESULT_REQUEST);
//    }
//
//    /**
//     * 提取保存裁剪之后的图片数据，并设置头像部分的View
//     */
//    private void setImageToHeadView(Intent intent) {
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            headImage.setImageBitmap(photo);
//        }
//    }
//
//    /**
//     * 检查设备是否存在SDCard的工具方法
//     */
//    public static boolean hasSdcard() {
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            // 有存储的SDCard
//            return true;
//        } else {
//            return false;
//        }
//    }
////    public static Uri getImageStreamFromExternal(String imageName) {
////        File externalPubPath = Environment.getExternalStoragePublicDirectory(
////                Environment.DIRECTORY_PICTURES
////        );
////
////        File picPath = new File(externalPubPath, imageName);
////        Uri uri = null;
////        if(picPath.exists()) {
////            uri = Uri.fromFile(picPath);
////        }
////
////        return uri;
////    }
////
////    @TargetApi(19)
////    private String handleImageOnKitKat(Intent data) {
////        //获取资源定位符
////        Uri uri = data.getData();
////        //选择图片路径
////        String imagePath = null;
////        if (DocumentsContract.isDocumentUri(this, uri)) {
////            String docId = DocumentsContract.getDocumentId(uri);
////            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
////                String id = docId.split(":")[1];
////                String selection = MediaStore.Images.Media._ID + "=" + id;
////                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
////            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
////                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
////                imagePath = getImagePath(contentUri, null);
////            }
////
////        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
////            imagePath = getImagePath(uri, null);
////        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
////            imagePath = uri.getPath();
////        }
////        return  imagePath;
////    }
////
////    private String getImagePath(Uri externalContentUri, String selection) {
////        String path = null;
////        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
////        if (cursor != null) {
////            if (cursor.moveToFirst()) {
////                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
////            }
////            cursor.close();
////        }
////        return path;
////    }
//}
