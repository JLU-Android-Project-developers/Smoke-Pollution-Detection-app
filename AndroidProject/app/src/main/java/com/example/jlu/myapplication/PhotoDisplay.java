package com.example.jlu.myapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dtflys.forest.config.ForestConfiguration;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import android.graphics.Rect;

import id.zelory.compressor.Compressor;
public class PhotoDisplay extends AppCompatActivity {
    private ImageView photo;
    private Uri photoUri;
    private String newUri = "";
    private String op_sd = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_photo_display);
        photo = (ImageView) findViewById(R.id.photo);

        Bitmap bitmap = null;
        Intent intent = getIntent();
        final String path = intent.getStringExtra("image");

        if (path.equals("null"))
            photo.setImageResource(R.drawable.a);
        else {
            try {
                File file = new File(path);
                if (file.exists()) {
                    bitmap = BitmapFactory.decodeFile(path);
                    photo.setImageBitmap(bitmap);
                    photoUri = Uri.parse("file://" + path);

                }
            } catch (Exception e) {
            }
        }

        Button button = (Button) findViewById(R.id.go_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                ForestConfiguration forest = ForestConfiguration.configuration();
                MyClient myClient = forest.createInstance(MyClient.class);
                compress(path);
                Thread thread = new Thread (
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            String result = myClient.upload(path, progress -> {
                                System.out.println("total bytes: " + progress.getTotalBytes());   // 文件大小
                                System.out.println("current bytes: " + progress.getCurrentBytes());   // 已上传字节数
                                System.out.println("progress: " + Math.round(progress.getRate() * 100) + "%");  // 已上传百分比
                                if (progress.isDone()) {   // 是否上传完成
                                    System.out.println("--------   Upload Completed!   --------");
                                }
                            });

                            Gson gson = new Gson();
                            Map map = gson.fromJson(result,Map.class);
                            String downloadPath = (String) map.get("url");
                            op_sd = Method.getTimeStr();
                            File file = myClient.downloadFile(
                                    getExternalFilesDir(null).getPath(),
                                    op_sd + ".jpg" ,
                                    progress -> {
                                        System.out.println("total bytes: " + progress.getTotalBytes());   // 文件大小
                                        System.out.println("current bytes: " + progress.getCurrentBytes());   // 已下载字节数
                                        System.out.println("progress: " + Math.round(progress.getRate() * 100) + "%");  // 已下载百分比
                                        if (progress.isDone()) {   // 是否下载完成
                                            System.out.println("--------   Download Completed!   --------");
                                        }
                                    },
                                    downloadPath);
                        }
                    });

                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                }
                Uri downloadUri = Uri.parse("file://" + getExternalFilesDir(null).getPath() + "/" + op_sd + ".jpg");
                photo.setImageURI(downloadUri);
            }
        });
        Button button2 = (Button) findViewById(R.id.go_edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sd = Method.getTimeStr();
                newUri = "file://" + getExternalFilesDir(null).getPath() + "/" + sd + ".jpg";

                UCrop uCrop = UCrop.of( photoUri,  Uri.parse(newUri));
                UCrop.Options options = new UCrop.Options();

                //设置toolbar颜色
                options.setToolbarColor(ActivityCompat.getColor(PhotoDisplay.this, R.color.colorPrimary));
                //设置状态栏颜色
                options.setStatusBarColor(ActivityCompat.getColor(PhotoDisplay.this, R.color.colorPrimary));
                options.setFreeStyleCropEnabled(true);

                uCrop.withOptions(options);
                uCrop.start(PhotoDisplay.this);
            }
        });
    }

    protected void compress(String path)
    {
        try {
            File new_file = new Compressor(PhotoDisplay.this).compressToFile(new File(path));
            FileOutputStream fos = new FileOutputStream(new File(path));
            Bitmap bitmap = BitmapFactory.decodeFile(new_file.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        }
        catch (Exception e){
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            photoUri = resultUri;
            photo.setImageURI(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}