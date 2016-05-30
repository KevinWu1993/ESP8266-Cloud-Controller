package cn.kevinwu.esp8266_cloud_controller;

import android.app.Application;
import android.content.Context;

/**
 * Created by KevinWu on 16-5-29.
 * KevinWu.cn
 */
public class MyApplication extends Application {
    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.AppContext=getApplicationContext();
    }
}
