package com.example.spb;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PhotoDisplay extends AppCompatActivity {

    private ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        photo = (ImageView) findViewById(R.id.photo);


        Bitmap bitmap = null;
        Intent intent = getIntent();
        String path = intent.getStringExtra("image");

        Log.wtf("tts",path);
        if(path.equals("null"))
            photo.setImageResource(R.drawable.a);
        else {
            try
            {
                File file = new File(path);
                if(file.exists())
                {
                    bitmap = BitmapFactory.decodeFile(path);
                    photo.setImageBitmap(bitmap);
                }
            } catch (Exception e)
            {

            }
        }

        Button button = (Button) findViewById(R.id.go_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button button2 = (Button) findViewById(R.id.go_next);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // UCrop.of(path, "1.jpg").start(this);
            }
        });
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//            final Uri resultUri = UCrop.getOutput(data);
//        } else if (resultCode == UCrop.RESULT_ERROR) {
//            final Throwable cropError = UCrop.getError(data);
//        }
//    }
}