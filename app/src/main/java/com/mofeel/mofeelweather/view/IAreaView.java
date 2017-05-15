package com.mofeel.mofeelweather.view;

import com.mofeel.mofeelweather.model.entity.City;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.Province;

import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 16:37
 * @email:18328541378@163.com
 */

public interface IAreaView {

    void showLoading();

    void hideLoading();

    void showError();

    void showProvinces(List<Province> provinces);

    void showCities(List<City> cities);

    void showCounties(List<County> counties);


}
