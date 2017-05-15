package com.mofeel.mofeelweather.presenter.impl;

import com.mofeel.mofeelweather.model.IWeatherModel;
import com.mofeel.mofeelweather.model.entity.WeatherInfo;
import com.mofeel.mofeelweather.model.impl.WeatherModel;
import com.mofeel.mofeelweather.presenter.IWeatherPresenter;
import com.mofeel.mofeelweather.presenter.IWeatherResultListener;
import com.mofeel.mofeelweather.util.RetrofitUtils;
import com.mofeel.mofeelweather.view.IWeatherView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/12 14:14
 * @email:18328541378@163.com
 *
 * 天气信息表示器
 *
 */

public class WeatherPresenter implements IWeatherPresenter {

    private IWeatherModel model;

    private IWeatherView view;

    public WeatherPresenter(IWeatherView view) {
        this.view = view;
        this.model = new WeatherModel();
    }

    /**
     * 加载业务
     * @param weatherId
     */
    @Override
    public void getWeather(final String weatherId) {
        view.showLoading();
        model.loadWeather(weatherId, new IWeatherResultListener() {
            @Override
            public void onSuccess(WeatherInfo info) {
                view.showWeather(info);
            }

            @Override
            public void onError() {
                view.showError();
            }
        });
        view.hideLoading();
    }

    /**
     * 刷新业务
     * @param weatherId
     */
    @Override
    public void refreshWeather(String weatherId) {
        view.showLoading();
        model.loadWeather(weatherId, new IWeatherResultListener() {
            @Override
            public void onSuccess(WeatherInfo info) {
                view.successRefresh(info);
            }

            @Override
            public void onError() {
                view.errorRefresh();
            }
        });
        view.hideLoading();
    }

    /* 获取背景图片 */
    public void getBackImg() {
        RetrofitUtils.sendRequestGetImage(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //view.showImageBack(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                //view.showImageBackError();
            }
        });
    }
}
