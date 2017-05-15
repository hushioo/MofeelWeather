package com.mofeel.mofeelweather.presenter;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 16:38
 * @email:18328541378@163.com
 */

public interface IAreaPresenter {
    /* 获取省 */
    void getProvinces();
    /* 获取城市 */
    void getCities(int provinceId);
    /* 获取县区 */
    void getCounties(int provinceId,int cityId);

}
