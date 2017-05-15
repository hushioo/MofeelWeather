package com.mofeel.mofeelweather.presenter.impl;

import com.mofeel.mofeelweather.util.RetrofitUtils;
import com.mofeel.mofeelweather.view.IBackIView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/14 21:53
 * @email:18328541378@163.com
 */

public class BackPresenter {

    /* 获取背景图片 */
    public void getBackImg(final IBackIView view) {
        RetrofitUtils.sendRequestGetImage(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.showBack(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                view.showBackError();
            }
        });
    }
}
