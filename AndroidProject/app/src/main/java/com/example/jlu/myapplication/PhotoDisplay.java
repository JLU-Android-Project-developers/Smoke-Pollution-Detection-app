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

import com.yalantis.ucrop.UCrop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoDisplay extends AppCompatActivity {

    private ImageView photo;
    private Uri photoUri;
    private String newUri="";
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

        Log.wtf("tts", "create" + path);
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

        Button button2 = (Button) findViewById(R.id.go_edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long timeStamp = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String sd = sdf.format(new Date(timeStamp));
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