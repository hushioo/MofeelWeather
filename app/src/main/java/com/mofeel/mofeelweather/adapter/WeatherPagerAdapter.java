package com.mofeel.mofeelweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mofeel.mofeelweather.view.fragment.WeatherFragment;

import java.util.List;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/14 20:55
 * @email:18328541378@163.com
 * Weather ViewPager Adapter
 *
 * 显示多个天气使用了ViewPager 选择FragmentPagerAdapter
 *
 */

public class WeatherPagerAdapter extends FragmentPagerAdapter {

    private List<String> tabs;
    private List<WeatherFragment> fragments;

    public WeatherPagerAdapter(FragmentManager fm,List<String> tabs, List<WeatherFragment> fragments) {
        super(fm);
        this.tabs = tabs;
        this.fragments =fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
