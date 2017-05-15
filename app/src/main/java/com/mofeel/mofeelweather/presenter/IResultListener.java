package com.mofeel.mofeelweather.presenter;

import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 21:39
 * @email:18328541378@163.com
 *
 * 请求结果监听
 *
 */

public interface IResultListener<T> {

    void onSuccess(List<T> response);

    void onError();

}
