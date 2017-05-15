package com.mofeel.mofeelweather.presenter.impl;

import com.mofeel.mofeelweather.model.IAreaModel;
import com.mofeel.mofeelweather.model.entity.City;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.Province;
import com.mofeel.mofeelweather.model.impl.AreaModel;
import com.mofeel.mofeelweather.presenter.IAreaPresenter;
import com.mofeel.mofeelweather.presenter.IResultListener;
import com.mofeel.mofeelweather.util.RetrofitUtils;
import com.mofeel.mofeelweather.view.IAreaView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 21:48
 * @email:18328541378@163.com
 *
 * 区域表示器
 *
 */

public class AreaPresenter implements IAreaPresenter{

    private IAreaView view;// View

    private IAreaModel model;// Model

    public AreaPresenter(IAreaView view){
        this.view = view;
        this.model = new AreaModel();
    }

    /**
     * 通过Model获取省会信息
     * 通过View崭新相关信息
     */
    @Override
    public void getProvinces() {
        view.showLoading();
        model.loadProvinces(new IResultListener<Province>() {
            @Override
            public void onSuccess(List<Province> response) {
                view.showProvinces(response);
            }

            @Override
            public void onError() {
                view.showError();
            }
        });
        view.hideLoading();
    }

    @Override
    public void getCities(int provinceId) {
        view.showLoading();
        model.loadCities(provinceId, new IResultListener<City>() {
            @Override
            public void onSuccess(List<City> response) {
                view.showCities(response);
            }

            @Override
            public void onError() {
                view.showError();
            }
        });
        view.hideLoading();
    }

    @Override
    public void getCounties(int provinceId,int cityId) {
        view.showLoading();
        model.loadCounties(provinceId,cityId, new IResultListener<County>() {
            @Override
            public void onSuccess(List<County> response) {
                view.showCounties(response);
            }

            @Override
            public void onError() {
                view.showError();
            }
        });
        view.hideLoading();
    }


}
