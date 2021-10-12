package com.example.spb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class PhotoDisplay extends AppCompatActivity {

    private ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        photo = (ImageView) findViewById(R.id.photo);

        Button button = (Button) findViewById(R.id.go_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bitmap bitmap = null;
        Intent intent = getIntent();
        String path = intent.getStringExtra("image");
        try
        {
            File file = new File(path);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(path);
                photo.setImageBitmap(bitmap);
            }
        } catch (Exception e)
        { }
    }
}