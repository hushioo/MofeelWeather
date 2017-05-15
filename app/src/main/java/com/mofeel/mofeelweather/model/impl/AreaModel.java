package com.mofeel.mofeelweather.model.impl;

import com.mofeel.mofeelweather.model.IAreaModel;
import com.mofeel.mofeelweather.model.entity.City;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.Province;
import com.mofeel.mofeelweather.presenter.IResultListener;
import com.mofeel.mofeelweather.util.RetrofitUtils;


import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 16:44
 * @email:18328541378@163.com
 *
 * 区域信息Model
 *
 */

public class AreaModel implements IAreaModel {
    /**
     * 获取省会集合
     * @param listener 返回监听 实现回调
     */
    @Override
    public void loadProvinces(final IResultListener<Province> listener) {
        RetrofitUtils.sendRequestProvinces(new retrofit2.Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                listener.onError();
            }
        });
    }
    /**
     * 获取城市集合
     * @param listener 返回监听 实现回调
     */
    @Override
    public void loadCities(final int id, final IResultListener<City> listener) {
        RetrofitUtils.sendRequestCities(id, new retrofit2.Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                listener.onError();
            }
        });


    }
    /**
     * 获取县区集合
     * @param listener 返回监听 实现回调
     */
    @Override
    public void loadCounties(final int proId, final int id, final IResultListener<County> listener) {
        RetrofitUtils.sendRequestCounties(proId, id, new retrofit2.Callback<List<County>>() {
            @Override
            public void onResponse(Call<List<County>> call, Response<List<County>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<County>> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
