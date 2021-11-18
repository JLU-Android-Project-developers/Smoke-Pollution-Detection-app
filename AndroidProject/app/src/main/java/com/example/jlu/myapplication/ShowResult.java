package com.example.jlu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.mingle.widget.ShapeLoadingDialog;
public class ShowResult extends AppCompatActivity {
    private ShapeLoadingDialog shapeLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();
//        shapeLoadingDialog.dismiss();
    }
}