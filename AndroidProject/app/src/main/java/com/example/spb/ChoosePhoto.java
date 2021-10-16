package com.example.spb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChoosePhoto extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private ImageView photo;
    private Uri imageUri;
    private Button goNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_camera);
        photo = (ImageView) findViewById(R.id.photo);

        goNext = (Button) findViewById(R.id.go_next);
        goNext.setVisibility(View.GONE);
        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = setimage(photo);
                String path = saveImageToGallery(bitmap);
                Log.wtf("tts",path);
                Intent intent = new Intent(ChoosePhoto.this,PhotoDisplay.class);
                intent.putExtra("image",path);
                startActivity(intent);
            }
        });

        Button takePhoto = (Button) findViewById(R.id.take_photo);
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
                imageUri = FileProvider.getUriForFile(ChoosePhoto.this,
                        "com.example.cameraalbumtest.fileprovider",outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                // 指定图片的输出地址
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });

        Button choosePhoto = (Button) findViewById(R.id.choose_photo);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ChoosePhoto.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChoosePhoto.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    // openAlbum
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent,CHOOSE_PHOTO);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                        goNext.setVisibility(View.VISIBLE);
                    }
                    catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    photo.setImageURI(uri);
                    goNext.setVisibility(View.VISIBLE);
//                  String uriAuthority = uri.getAuthority();
//                  Log.wtf("tts",uri.getScheme());
//                  Log.wtf("tts",uri.getPath());
                }
                break;
            default:
                break;
        }
    }

    private Bitmap setimage(ImageView view1){
        Bitmap image = ((BitmapDrawable)view1.getDrawable()).getBitmap();
        Bitmap bitmap1 = Bitmap.createBitmap(image);
        return bitmap1;
    }

    public String saveImageToGallery(Bitmap bmp) {
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String sd = sdf.format(new Date(timeStamp));

        File file = new File(getExternalFilesDir(null).getPath() + "/" + sd + ".jpg");
        Log.wtf("tts",file.getPath());
        try {
            if(file.exists())
                file.delete();
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}