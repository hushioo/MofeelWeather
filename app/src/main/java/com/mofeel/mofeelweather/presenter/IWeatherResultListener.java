package com.mofeel.mofeelweather.presenter;

import com.mofeel.mofeelweather.model.entity.WeatherInfo;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/12 14:10
 * @email:18328541378@163.com
 *
 * 天气结果监听
 *
 */

public interface IWeatherResultListener {

    void onSuccess(WeatherInfo info);

    void onError();

}
