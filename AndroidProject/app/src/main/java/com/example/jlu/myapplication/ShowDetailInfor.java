package com.example.jlu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ShowDetailInfor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_infor);
        Gson gson = new Gson();
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Smoke item = gson.fromJson(data, Smoke.class);
        ImageView photo = (ImageView) findViewById(R.id.imageView);
        TextView text = (TextView) findViewById(R.id.textView);
        photo.setImageURI(Uri.parse("file://" + item.url));
        text.setText(item.level);

    }
}