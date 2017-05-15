package com.mofeel.mofeelweather.util;

import com.mofeel.mofeelweather.model.entity.City;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.Province;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/11 21:55
 * @email:18328541378@163.com 数据库工具类
 */

public class SqlUtils {

    /* 查询已经关心的城市 */
    public static List<County> getCounties() {
        List<County> counties = DataSupport.findAll(County.class);
        return counties;
    }

    /* 查询是否已经关注了这个城市 */
    public static boolean have(County county) {
        List<County> data = DataSupport.where("cityId like ?", county.getCityId() + "").find(County.class);
        return data.size() > 0 ? true : false;
    }

    /* 保存一个城市 */
    public static boolean saveOne(County county) {
        if (have(county))
            return false;
        county.save();
        return true;
    }


}
