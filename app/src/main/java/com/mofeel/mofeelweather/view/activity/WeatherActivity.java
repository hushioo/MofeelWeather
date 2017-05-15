package com.mofeel.mofeelweather.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mofeel.mofeelweather.R;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.WeatherInfo;
import com.mofeel.mofeelweather.model.entity.WeatherInfo.HeWeather5Bean;
import com.mofeel.mofeelweather.model.entity.WeatherInfo.HeWeather5Bean.AqiBean;
import com.mofeel.mofeelweather.model.entity.WeatherInfo.HeWeather5Bean.DailyForecastBean;
import com.mofeel.mofeelweather.model.entity.WeatherInfo.HeWeather5Bean.NowBean;
import com.mofeel.mofeelweather.model.entity.WeatherInfo.HeWeather5Bean.NowBean.CondBean;
import com.mofeel.mofeelweather.presenter.impl.WeatherPresenter;
import com.mofeel.mofeelweather.util.PermissionUtils;
import com.mofeel.mofeelweather.util.ShotUtil;
import com.mofeel.mofeelweather.util.SqlUtils;
import com.mofeel.mofeelweather.view.IWeatherView;

import java.io.IOException;
import java.util.List;
/* 未使用 单个天气信息 */
public class WeatherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IWeatherView, PermissionUtils.OnPermissionListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FrameLayout contentWeather;
    private FloatingActionButton fab;
    private NavigationView navView;
    private ProgressDialog progressDialog;
    private ScrollView weatherScroll;
    private TextView textDegree;
    private TextView textWeatherInfo;
    private LinearLayout forecastLayout;
    private TextView textAQI;
    private TextView textPM;
    private TextView textComfort;
    private TextView textCar;
    private TextView textSport;
    private TextView textWeatherUpdate;
    public static final int REQUEST_COUNTY = 1000, RESULT_COUNTY = 1001;
    private WeatherPresenter presenter;
    private ImageView weatherBack;
    private SwipeRefreshLayout swipe;
    private List<County> counties;
    private TabLayout tab;
    private ViewPager weatherViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initData();
        initView();

    }

    /* 初始化数据 */
    private void initData() {
        presenter = new WeatherPresenter(this);
        presenter.getBackImg();
        counties = SqlUtils.getCounties();
        if (counties.size() > 0)// 如果已经有了关注的城市
            presenter.getWeather(counties.get(0).getWeatherId());
        else {
            // 没有运行添加城市界面
            Intent intent = new Intent(this, AddFollowCityActivity.class);
            startActivityForResult(intent, REQUEST_COUNTY);
        }
    }

    /* 权限申请回调 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(1, permissions, grantResults);
    }

    /* 未做提示用户操作  进行直接写入 写入到默认的屏幕截图中*/
    @Override
    public void onPermissionGranted() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ShotUtil.shot(WeatherActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /* 用户不同意该项权限的时候 */
    @Override
    public void onPermissionDenied() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* 来自添加界面的回调 */
        if (requestCode == REQUEST_COUNTY && resultCode == RESULT_COUNTY && data != null) {/*从添加界面回来*/
            /* 解析数据 */
            County county = (County) data.getSerializableExtra("county");
            presenter.getWeather(county.getWeatherId());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_add) {
            Intent intent = new Intent(this, AddFollowCityActivity.class);
            startActivityForResult(intent, REQUEST_COUNTY);
        }
        return true;
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.maxTransBackground));
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
        contentWeather = (FrameLayout) findViewById(R.id.content_weather);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "天气将保存到您的屏幕截屏中", Snackbar.LENGTH_SHORT).show();
                PermissionUtils.requestPermissions(WeatherActivity.this, 1, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WeatherActivity.this);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        weatherScroll = (ScrollView) findViewById(R.id.weather_scroll);
        textDegree = (TextView) findViewById(R.id.textDegree);
        textWeatherInfo = (TextView) findViewById(R.id.textWeatherInfo);
        forecastLayout = (LinearLayout) findViewById(R.id.forecastLayout);
        textAQI = (TextView) findViewById(R.id.textAQI);
        textPM = (TextView) findViewById(R.id.textPM);

        textComfort = (TextView) findViewById(R.id.textComfort);
        textCar = (TextView) findViewById(R.id.textCar);
        textSport = (TextView) findViewById(R.id.textSport);
        textWeatherUpdate = (TextView) findViewById(R.id.textWeatherUpdate);
        weatherBack = (ImageView) findViewById(R.id.weather_back);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (counties.size() > 0)
                    presenter.getWeather(counties.get(0).getWeatherId());
                swipe.setRefreshing(false);
            }
        });
        tab = (TabLayout) findViewById(R.id.tab);
        weatherViewpager = (ViewPager) findViewById(R.id.weather_viewpager);
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(this, "Mofeel", "加载中");
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showError() {

    }

//    @Override
//    public void showImageBack(String url) {
//        Glide.with(this).load(url).into(weatherBack);
//    }

//    @Override
//    public void showImageBackError() {
//        Toast.makeText(this, "背景图片获取异常", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void showWeather(WeatherInfo weatherInfo) {
        List<HeWeather5Bean> bean = weatherInfo.getHeWeather5();

        HeWeather5Bean weather = bean.get(0);
        toolbar.setTitle(weather.getBasic().getCity());
        /* 当前信息 */
        NowBean nowBean = weather.getNow();
        CondBean condBean = nowBean.getCond();
        textDegree.setText(nowBean.getTmp() + "°C");
        textWeatherInfo.setText(condBean.getTxt());
        textWeatherUpdate.setText("更新时间:" + weather.getBasic().getUpdate().getLoc());
        /* 预测信息 */
        List<DailyForecastBean> daily_forecastlist = weather.getDaily_forecast();
        forecastLayout.removeAllViews();// 将之前的view删除 避免数据重复
        for (DailyForecastBean day : daily_forecastlist) {
            View view = getLayoutInflater().inflate(R.layout.forecast_item, forecastLayout, false);
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
        AqiBean aqiBean = weather.getAqi();
        textAQI.setText(aqiBean.getCity().getAqi());
        textPM.setText(aqiBean.getCity().getPm25());
        /* suggestion */
        HeWeather5Bean.SuggestionBean suggestionBean = weather.getSuggestion();
        textComfort.setText("舒适度:" + suggestionBean.getAir().getTxt());
        textSport.setText("运动建议:" + suggestionBean.getSport().getTxt());
        textCar.setText("洗车建议:" + suggestionBean.getComf().getTxt());
    }

    @Override
    public void successRefresh(WeatherInfo weatherInfo) {

    }

    @Override
    public void errorRefresh() {

    }
}
