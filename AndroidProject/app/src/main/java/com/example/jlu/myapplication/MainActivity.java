package com.example.jlu.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    String tag = "tst";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(tag,"I am creating");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this,"Hello!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent  =  new Intent(MainActivity.this,PhotoDisplay.class);
                intent.putExtra("image","null");
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                //Toast.makeText(this,"Add!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
                break;
            case R.id.remove_item:
                Toast.makeText(this,"Remove!",Toast.LENGTH_LONG).show();
                break;
            case R.id.ChoosePhoto_item:
                Intent intent2 = new Intent(MainActivity.this, ChoosePhoto.class);
                startActivity(intent2);
                break;
            default:
        }
        return true;
    }
}
