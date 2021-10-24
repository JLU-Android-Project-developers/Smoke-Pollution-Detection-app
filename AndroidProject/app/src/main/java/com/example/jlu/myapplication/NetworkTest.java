package com.example.jlu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dtflys.forest.config.ForestConfiguration;
import com.dtflys.forest.ssl.SSLUtils;

import java.io.IOException;

public class NetworkTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);
        forest_setting();
        ForestConfiguration forest = ForestConfiguration.configuration();
        MyClient myClient = forest.createInstance(MyClient.class);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread (new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String result = "";
                        result = myClient.sendRequest("spb");
                        Looper.prepare();
                        Toast.makeText(NetworkTest.this,result,
                                Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
            }
        });
    }

    static void forest_setting()
    {
        ForestConfiguration configuration = ForestConfiguration.configuration();
        configuration.setBackendName("httpclient");
        // 连接池最大连接数，默认值为500
        configuration.setMaxConnections(123);

        // 每个路由的最大连接数，默认值为500
        configuration.setMaxRouteConnections(222);

        // 请求超时时间，单位为毫秒, 默认值为3000
        configuration.setTimeout(3000);

        // 连接超时时间，单位为毫秒, 默认值为2000
        configuration.setConnectTimeout(2000);

        // 请求失败后重试次数，默认为0次不重试
        configuration.setRetryCount(3);

        // 单向验证的HTTPS的默认SSL协议，默认为SSLv3
        configuration.setSslProtocol(SSLUtils.SSL_3);

        // 打开或关闭日志，默认为true
        configuration.setLogEnabled(true);
    }
}