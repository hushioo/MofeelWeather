package com.mofeel.mofeelweather.model.impl;

import com.mofeel.mofeelweather.model.IWeatherModel;
import com.mofeel.mofeelweather.model.entity.WeatherInfo;
import com.mofeel.mofeelweather.presenter.IResultListener;
import com.mofeel.mofeelweather.presenter.IWeatherResultListener;
import com.mofeel.mofeelweather.util.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/12 13:57
 * @email:18328541378@163.com
 *
 * 天气信息Model
 *
 */

public class WeatherModel implements IWeatherModel {

    /**
     * 获取天气集合
     * @param listener 返回监听 实现回调
     */
    @Override
    public void loadWeather(String weatherId, final IWeatherResultListener listener) {
        RetrofitUtils.sendRequestWeather(weatherId,new retrofit2.Callback<WeatherInfo>(){
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
