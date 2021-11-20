package com.example.jlu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mingle.widget.ShapeLoadingDialog;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowResult extends AppCompatActivity {
    private ListView listView;
    private List<Smoke> SmokeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        listView = findViewById(R.id.listview);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Log.wtf("tts",data);
        SmokeList = new ArrayList<>();

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();
        for (JsonElement user : jsonArray) {
            Smoke userBean = gson.fromJson(user, Smoke.class);
            SmokeList.add(userBean);
        }
        ImageView main_photo = (ImageView) findViewById(R.id.imageView);
        main_photo.setImageURI(Uri.parse("file://" + SmokeList.get(0).url));

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        for (int i = 1; i < SmokeList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemImage", new File(SmokeList.get(i).url));
            map.put("ItemText", SmokeList.get(i).level);
            listItem.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(ShowResult.this,
            listItem,
            R.layout.item,
            new String[] {"ItemImage", "ItemText"},
            new int[] {R.id.imageView, R.id.textView});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent  =  new Intent(ShowResult.this,ShowDetailInfor.class);
                Gson gson = new Gson();
                intent.putExtra("data",gson.toJson(SmokeList.get(i+1)));
                startActivity(intent);
            }
        });
    }
}