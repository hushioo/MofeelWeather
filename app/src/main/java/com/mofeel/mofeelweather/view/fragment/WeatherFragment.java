package com.mofeel.mofeelweather.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mofeel.mofeelweather.R;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.WeatherInfo;
import com.mofeel.mofeelweather.presenter.impl.WeatherPresenter;
import com.mofeel.mofeelweather.view.IWeatherView;

import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/14 20:21
 * @email:18328541378@163.com
 * <p>
 * 呈现天气信息主要界面
 */

public class WeatherFragment extends Fragment implements IWeatherView{


    private FrameLayout contentWeather;
    private SwipeRefreshLayout swipe;
    private NestedScrollView weatherScroll;
    private TextView textDegree;
    private TextView textWeatherInfo;
    private TextView textWeatherUpdate;
    private LinearLayout forecastLayout;
    private TextView textAQI;
    private TextView textPM;
    private TextView textComfort;
    private TextView textCar;
    private TextView textSport;

    private County county;

    private ProgressDialog progressDialog;

    private WeatherPresenter presenter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_weather, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {

        contentWeather = (FrameLayout) view.findViewById(R.id.content_weather);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        weatherScroll = (NestedScrollView) view.findViewById(R.id.weather_scroll);
        textDegree = (TextView) view.findViewById(R.id.textDegree);
        textWeatherInfo = (TextView) view.findViewById(R.id.textWeatherInfo);
        textWeatherUpdate = (TextView) view.findViewById(R.id.textWeatherUpdate);
        forecastLayout = (LinearLayout) view.findViewById(R.id.forecastLayout);
        textAQI = (TextView)view. findViewById(R.id.textAQI);
        textPM = (TextView) view.findViewById(R.id.textPM);
        textComfort = (TextView)view. findViewById(R.id.textComfort);
        textCar = (TextView)view. findViewById(R.id.textCar);
        textSport = (TextView)view. findViewById(R.id.textSport);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshWeather(county.getWeatherId());
            }
        });
    }

    private void initData() {
        county = (County) getArguments().getSerializable("county");
         presenter = new WeatherPresenter(this);
        presenter.getWeather(county.getWeatherId());
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(getActivity(),"MoFeel","加载数据");
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(),"数据加载异常",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWeather(WeatherInfo weatherInfo) {
        List<WeatherInfo.HeWeather5Bean> bean = weatherInfo.getHeWeather5();
        WeatherInfo.HeWeather5Bean weather = bean.get(0);
        /* 当前信息 */
        WeatherInfo.HeWeather5Bean.NowBean nowBean = weather.getNow();
        WeatherInfo.HeWeather5Bean.NowBean.CondBean condBean = nowBean.getCond();
        textDegree.setText(nowBean.getTmp() + "°C");
        textWeatherInfo.setText(condBean.getTxt());
        textWeatherUpdate.setText("更新时间:" + weather.getBasic().getUpdate().getLoc());
        /* 预测信息 */
        List<WeatherInfo.HeWeather5Bean.DailyForecastBean> daily_forecastlist = weather.getDaily_forecast();
        forecastLayout.removeAllViews();// 将之前的view删除 避免数据重复
        for (WeatherInfo.HeWeather5Bean.DailyForecastBean day : daily_forecastlist) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.forecast_item, forecastLayout, false);
            TextView textForeDate = (TextView) view.findViewById(R.id.textForeDate);
            TextView textForeInfo = (TextView) view.findViewById(R.id.textForeInfo);
            TextView textForeMax = (TextView) view.findViewById(R.id.textForeMax);
            TextView textForeMin = (TextView) view.findViewById(R.id.textForeMin);
            textForeDate.setText(day.getDate());
            textForeInfo.setText(day.getCond().getTxt_n());
            textForeMax.setText(day.getTmp().getMax() + "°C");
            textForeMin.setText(day.getTmp().getMin() + "°C");
            forecastLayout.addView(view);
        }
        /* AQI　PM */
        WeatherInfo.HeWeather5Bean.AqiBean aqiBean = weather.getAqi();
        textAQI.setText(aqiBean.getCity().getAqi());
        textPM.setText(aqiBean.getCity().getPm25());
        /* suggestion */
        WeatherInfo.HeWeather5Bean.SuggestionBean suggestionBean = weather.getSuggestion();
        textComfort.setText("舒适度:" + suggestionBean.getAir().getTxt());
        textSport.setText("运动建议:" + suggestionBean.getSport().getTxt());
        textCar.setText("洗车建议:" + suggestionBean.getComf().getTxt());
    }

    @Override
    public void successRefresh(WeatherInfo info) {
        Snackbar.make(view,"刷新成功",Snackbar.LENGTH_SHORT).show();
        showWeather(info);
        swipe.setRefreshing(false);
    }

    @Override
    public void errorRefresh() {
        Snackbar.make(view,"刷新失败",Snackbar.LENGTH_SHORT).show();
        swipe.setRefreshing(false);
    }
}
