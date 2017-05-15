package com.mofeel.mofeelweather.model.entity;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 17:13
 * @email:18328541378@163.com
 *
 * 省会实体类
 *
 */

public class Province extends BaseInfo {
    @SerializedName("id")
    private int provinceCode;

    public Province(String name, int provinceCode) {
        super(name);
        this.provinceCode = provinceCode;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

}
