package com.mofeel.mofeelweather.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mofeel.mofeelweather.R;
import com.mofeel.mofeelweather.adapter.WeatherPagerAdapter;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.presenter.impl.BackPresenter;
import com.mofeel.mofeelweather.presenter.impl.WeatherPresenter;
import com.mofeel.mofeelweather.util.PermissionUtils;
import com.mofeel.mofeelweather.util.SqlUtils;
import com.mofeel.mofeelweather.view.IBackIView;
import com.mofeel.mofeelweather.view.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/14 20:49
 * @email:18328541378@163.com
 *
 * 呈现多个城市天气信息
 *
 */

public class WeatherTabActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,IBackIView {

    private DrawerLayout drawerLayout;
    private ImageView weatherBack;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private TabLayout tab;
    private ViewPager weatherViewpager;
    private FloatingActionButton fab;
    private NavigationView navView;

    private List<County> counties;

    private List<WeatherFragment> weatherFragments;
    private List<String> tabs;

    public static final int REQUEST_COUNTY = 1000, RESULT_COUNTY = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        initData();
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        weatherBack = (ImageView) findViewById(R.id.weather_back);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tab = (TabLayout) findViewById(R.id.tab);
        weatherViewpager = (ViewPager) findViewById(R.id.weather_viewpager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        navView = (NavigationView) findViewById(R.id.nav_view);
        toolbar.setBackgroundColor(getResources().getColor(R.color.maxTransBackground));
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "天气将保存到您的屏幕截屏中", Snackbar.LENGTH_SHORT).show();
                //PermissionUtils.requestPermissions(W.this, 1, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WeatherActivity.this);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        new BackPresenter().getBackImg(this);
    }
    /* 初始化数据 */
    private void initData() {
        /*presenter.getBackImg();*/
        counties = SqlUtils.getCounties();/* 根据已经关注的城市进行实例化 Fragment*/
        if (counties.size() > 0)// 如果已经有了关注的城市
        {
            weatherFragments = new ArrayList<>();
            tabs = new ArrayList<>();
            for (County county : counties){
                tabs.add(county.getName());
                WeatherFragment fragment = new WeatherFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("county",county);
                fragment.setArguments(bundle);// 将数据传递给Fragment
                weatherFragments.add(fragment);
            }
            WeatherPagerAdapter adapter = new WeatherPagerAdapter(getSupportFragmentManager(),tabs,weatherFragments);
            weatherViewpager.setAdapter(adapter);
            tab.setupWithViewPager(weatherViewpager);
        }
        else {
            // 没有运行添加城市界面
            Intent intent = new Intent(this, AddFollowCityActivity.class);
            startActivityForResult(intent, REQUEST_COUNTY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_COUNTY && resultCode == RESULT_COUNTY && data !=null){
            initData();// 从添加界面返回到天气界面 重新初始化数据一次
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
        drawerLayout.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_add) {
            Intent intent = new Intent(this, AddFollowCityActivity.class);
            startActivityForResult(intent, REQUEST_COUNTY);
        }
        return true;
    }

    @Override
    public void showBack(String url) {
        Glide.with(this).load(url).into(weatherBack);
    }

    @Override
    public void showBackError() {
        weatherBack.setColorFilter(getResources().getColor(R.color.colorPrimary));
    }
}
