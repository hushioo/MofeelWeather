package com.mofeel.mofeelweather.view;

import com.mofeel.mofeelweather.model.entity.WeatherInfo;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/12 14:15
 * @email:18328541378@163.com
 */

public interface IWeatherView {

    void showLoading();

    void hideLoading();

    void showError();

    void showWeather(WeatherInfo weatherInfo);

    void successRefresh(WeatherInfo weatherInfo);

    void errorRefresh();


}
