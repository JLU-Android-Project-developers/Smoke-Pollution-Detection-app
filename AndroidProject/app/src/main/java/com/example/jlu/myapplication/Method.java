package com.example.jlu.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Method {
    static public String getTimeStr()
    {
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String sd = sdf.format(new Date(timeStamp));
        return sd;
    }
}
