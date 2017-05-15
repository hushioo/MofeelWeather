package com.mofeel.mofeelweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 17:13
 * @email:18328541378@163.com
 *
 * 城市实体类
 *
 */

public class City extends BaseInfo{
    @SerializedName("id")
    private int cityCode;

    private transient int provinceCode;

    public City(String name,int cityCode,int provinceCode) {
        super(name);
        this.cityCode = cityCode;
        this.provinceCode = provinceCode;
    }


    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }


}
