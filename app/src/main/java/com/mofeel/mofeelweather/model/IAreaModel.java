package com.mofeel.mofeelweather.model;


import com.mofeel.mofeelweather.model.entity.City;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.Province;
import com.mofeel.mofeelweather.presenter.IResultListener;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 16:37
 * @email:18328541378@163.com
 */

public interface IAreaModel {

    /*获取所有省*/
    void loadProvinces(IResultListener<Province> listener);

    /*根据省id获取所有市*/
    void loadCities(int id,IResultListener<City> listener);

    /*根据市id获取所有县、区*/
    void loadCounties(int proId, int id,IResultListener<County> listener);

}
