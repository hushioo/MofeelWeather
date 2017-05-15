package com.mofeel.mofeelweather.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import org.litepal.LitePal;

import retrofit2.Retrofit;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 19:40
 * @email:18328541378@163.com
 */

public class WeatherApplication extends Application {

    private static WeatherApplication weatherApplication;

    public WeatherApplication getInstance() {
        return weatherApplication;
    }

    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化一下LitePal
        LitePal.initialize(this);
        weatherApplication = this;
        context = getApplicationContext();
    }

}
