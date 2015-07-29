package com.example.camara.myapplication;


import android.app.Application;

import com.example.camara.myapplication.util.AppUtil;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        AppUtil.CONTEXT = getApplicationContext();
        super.onCreate();
    }
}
