package com.mofeel.mofeelweather.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 17:13
 * @email:18328541378@163.com
 *
 * 区县实体类
 *
 */

public class County extends BaseInfo implements Serializable {
    @SerializedName("id")
    private int cityId;
    @SerializedName("weather_id")
    private String weatherId;


    public County(String name, int cityId, String weatherId) {
        super(name);
        this.cityId = cityId;
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
