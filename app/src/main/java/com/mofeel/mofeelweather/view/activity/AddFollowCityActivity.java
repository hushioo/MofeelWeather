package com.mofeel.mofeelweather.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.mofeel.mofeelweather.adapter.AreaAdapter;
import com.mofeel.mofeelweather.R;
import com.mofeel.mofeelweather.app.WeatherApplication;
import com.mofeel.mofeelweather.model.entity.BaseInfo;
import com.mofeel.mofeelweather.model.entity.City;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.Province;
import com.mofeel.mofeelweather.presenter.IAreaPresenter;
import com.mofeel.mofeelweather.presenter.impl.AreaPresenter;
import com.mofeel.mofeelweather.util.SqlUtils;
import com.mofeel.mofeelweather.view.IAreaView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 增加关注的城市Activity
 */
public class AddFollowCityActivity extends AppCompatActivity implements IAreaView {

    private RecyclerView maiLv;
    private ProgressDialog loading;
    private IAreaPresenter presenter;
    private Toolbar addToolbar;
    private AreaAdapter areaAdapter;
    private int requestCode, pId, cId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_follow_city);
        initView();
    }

    @Override
    public void showLoading() {
        loading = ProgressDialog.show(this, "MoFeelWeather", "获取信息");
    }

    @Override
    public void showError() {
        Toast.makeText(this, "未获取到数据！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProvinces(List<Province> provinces) {
        addToolbar.setTitle("省份");
        areaAdapter.setList(provinces);
    }

    @Override
    public void showCities(List<City> cities) {
        addToolbar.setTitle("城市");
        areaAdapter.setList(cities);
    }


    @Override
    public void showCounties(List<County> counties) {
        addToolbar.setTitle("区县");
        areaAdapter.setList(counties);
    }

    @Override
    public void hideLoading() {
        loading.dismiss();
    }


    private void initView() {
        maiLv = (RecyclerView) findViewById(R.id.maiLv);
        maiLv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<BaseInfo> arrayList = new ArrayList<>();
        areaAdapter = new AreaAdapter(arrayList, this);
        areaAdapter.setListener(new AreaAdapter.IOnItemClickListener() {
            @Override
            public void onItemClickListener(BaseInfo baseInfo) {
                switch (requestCode) {
                    case 0:
                        pId = ((Province) baseInfo).getProvinceCode();
                        presenter.getCities(pId);
                        break;
                    case 1:
                        cId = ((City) baseInfo).getCityCode();
                        presenter.getCounties(pId, cId);
                        break;
                    case 2:
                        /*传递天气引用到下一个界面*/
                        Intent intent = new Intent();
                        County county = (County)baseInfo;
                        if(!SqlUtils.have(county))
                            county.save();
                        else
                            Toast.makeText(WeatherApplication.getContext(),"您已经关注过该城市了",Toast.LENGTH_SHORT).show();
                        intent.putExtra("county",county);
                        setResult(WeatherActivity.RESULT_COUNTY,intent);
                        finish();
                        break;
                }
                requestCode++;
            }
        });
        maiLv.setAdapter(areaAdapter);
        addToolbar = (Toolbar) findViewById(R.id.addToolbar);
        setSupportActionBar(addToolbar);
        presenter = new AreaPresenter(this);
        presenter.getProvinces();
    }

}
