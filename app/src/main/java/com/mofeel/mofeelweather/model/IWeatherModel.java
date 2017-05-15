package com.mofeel.mofeelweather.model;

import com.mofeel.mofeelweather.model.entity.WeatherInfo;
import com.mofeel.mofeelweather.presenter.IResultListener;
import com.mofeel.mofeelweather.presenter.IWeatherResultListener;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/12 13:36
 * @email:18328541378@163.com
 */

public interface IWeatherModel {
    void loadWeather(String weatherId, IWeatherResultListener listener);
}
