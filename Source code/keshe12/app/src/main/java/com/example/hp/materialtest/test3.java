//package com.example.hp.materialtest;
//
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
//
///**
// * Created by HP on 2018/9/8.
// */
//
//import android.Manifest;
//import android.content.ContentUris;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.lang.annotation.Target;
//
//public class test3 extends AppCompatActivity {
//    private Uri pictrueUri;
//    private ImageView selfPicture;
//    public static final int CHOOSE_PHOTO = 2;
//    public static final int CHOOSE_CAMERA = 1;
//
//    private static int output_X = 180;
//    private static int output_Y = 180;
//
//    private Button choose_from_camera,choose_from_album;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.test2);
//
//        selfPicture = (ImageView) findViewById(R.id.imageView);
//        choose_from_camera = (Button) findViewById(R.id.buttonCamera);
//        choose_from_album = (Button) findViewById(R.id.buttonLocal);
//        choose_from_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePhoto(v);
//            }
//        });
//
//        choose_from_album.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choosePicture(v);
//            }
//        });
//    }
//
//    //拍照并显示
//    public void takePhoto(View view) {
//        File outputPicture = new File(getExternalCacheDir(), "head.jpg");
//        if (outputPicture.exists()) {
//            outputPicture.delete();
//        }
//        try {
//            outputPicture.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if(Build.VERSION.SDK_INT>=24){
//            pictrueUri = FileProvider.getUriForFile(test3.this,"com.example.hp.materialtest.test3",outputPicture);
//        }else {
//            pictrueUri = Uri.fromFile(outputPicture);
//        }
//
//
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictrueUri);
//        startActivityForResult(intent, CHOOSE_CAMERA);
//    }
//
//    //选择照片并显示
//    public void choosePicture(View view) {
//        //动态申请权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
//        } else {
//            openAlbum();
//        }
//
//    }
//
//    private void openAlbum() {
////        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, CHOOSE_PHOTO);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 3:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openAlbum();
//                } else {
//                    Toast.makeText(this, "你已经拒绝了该权限", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CHOOSE_CAMERA:
//                if (resultCode == RESULT_OK) {
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(pictrueUri));
//                        selfPicture.setImageBitmap(bitmap);
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            case CHOOSE_PHOTO:
//                handleImageOnKitKat(data);
//                break;
//        }
//    }
//
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data) {
//        //获取资源定位符
//        Uri uri = data.getData();
//        //选择图片路径
//        String imagePath = null;
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            String docId = DocumentsContract.getDocumentId(uri);
//            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1];
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                imagePath = getImagePath(contentUri, null);
//            }
//
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            imagePath = getImagePath(uri, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            imagePath = uri.getPath();
//        }
//        showImage(imagePath);
//    }
//
//    private String getImagePath(Uri externalContentUri, String selection) {
//        String path = null;
//        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    private void showImage(String imagePath) {
//        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            selfPicture.setImageBitmap(bitmap);
//        } else {
//            Toast.makeText(this, "没有找到对应图片", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//}
