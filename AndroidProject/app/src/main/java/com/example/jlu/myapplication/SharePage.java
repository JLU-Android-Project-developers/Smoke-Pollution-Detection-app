package com.example.jlu.myapplication;

import static android.view.View.DRAWING_CACHE_QUALITY_AUTO;
import static android.view.View.DRAWING_CACHE_QUALITY_HIGH;
import static android.view.View.DRAWING_CACHE_QUALITY_LOW;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SharePage extends AppCompatActivity {
    private ImageView share_img;
    private ImageView share_down;
    private TextView text;
    private String file_path;
    private Bitmap file_bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_share_page);
        text = (TextView) findViewById(R.id.share_text);
        share_img = (ImageView) findViewById(R.id.share_img);

        Intent intent = getIntent();
        file_path = intent.getStringExtra("path");
        try {
            File file = new File(file_path);
            if (file.exists()) {
                file_bitmap = BitmapFactory.decodeFile(file_path);
                share_img.setImageBitmap(file_bitmap);
            }
        } catch (Exception e) {
        }
        String num = intent.getStringExtra("num");
        String avg = intent.getStringExtra("avg");
        String over_num = intent.getStringExtra("over_num");
        String txt = "";
        Log.wtf("tts",num);
        if(!num.equals("0"))
            txt = "?????????????????????????????????????????????" +
            num +
            "?????????\n??????????????????" +
            avg +
            "\n??????" +
            over_num +
            "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\n???????????????????????????????????????????????????????????????????????????";
        else
            txt = "?????????????????????????????????????????????????????????\n" +
                    "??????????????????????????????????????????????????????";
        text.setText(txt);


        final ImageView imageChose = findViewById(R.id.share_chose);
        imageChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(imageChose);
            }
        });


        final ImageView imageDown = findViewById(R.id.share_down);
        imageDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout llContainer = (LinearLayout) findViewById(R.id.llContainer);
                Bitmap screen = testViewSnapshot(llContainer);
                String txt = "????????????????????????:";
                String save_path = Method.saveImageToGallery(SharePage.this,screen);
                Toast.makeText(SharePage.this,txt + save_path,Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void showPopupMenu(View view) {
        LinearLayout llContainer = (LinearLayout) findViewById(R.id.llContainer);
        Bitmap bitmap = testViewSnapshot(llContainer);
        String path = file_path;

        // View??????PopupMenu???????????????View?????????
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu??????
        popupMenu.getMenuInflater().inflate(R.menu.sharefile, popupMenu.getMenu());
        // menu???item????????????
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.qq)
                    Share.shareImageToQQFriend(SharePage.this,bitmap);
                else if(id == R.id.wechatFriend)
                    Share.shareImageToWechatFriend(SharePage.this,bitmap);
                else if(id == R.id.wechatMoments)
                    Share.shareImageToWechat(SharePage.this,bitmap);
                return true;
            }
        });

        // PopupMenu????????????
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(getApplicationContext(), "??????PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }

    private static Bitmap testViewSnapshot(View view) {
        view.setDrawingCacheEnabled(true);//???????????????????????????
        Bitmap drawingCache = view.getDrawingCache();//??????????????? Bitmap
        drawingCache = Bitmap.createBitmap(drawingCache);//??????????????? Bitmap
        view.setDrawingCacheEnabled(false);//?????????????????????
        return drawingCache;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //????????????????????????
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}