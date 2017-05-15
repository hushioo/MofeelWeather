package com.mofeel.mofeelweather.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 19:20
 * @email:18328541378@163.com
 *
 * 数据实体类基类 继承LitePal DataSupport 以简化数据库操作
 *
 */

public class BaseInfo extends DataSupport implements Serializable{

    protected transient int id;
    @SerializedName("name")
    protected String name;

    public BaseInfo(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
