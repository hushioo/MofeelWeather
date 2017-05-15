package com.mofeel.mofeelweather.presenter;

import com.mofeel.mofeelweather.model.entity.County;

import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/12 14:13
 * @email:18328541378@163.com
 *
 * 天气信息监听
 *
 */

public interface IWeatherPresenter {

    void getWeather(String weatherId);

    void refreshWeather(String weatherId);

}
