package com.example.spb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class useCamera extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    private ImageView photo;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_camera);

        Button takePhoto = (Button) findViewById(R.id.take_photo);
        photo = (ImageView) findViewById(R.id.photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if(outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                imageUri = FileProvider.getUriForFile(useCamera.this,
                        "com.example.cameraalbumtest.fileprovider",outputImage);


                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                    }
                    catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }
}