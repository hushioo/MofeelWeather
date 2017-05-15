package com.mofeel.mofeelweather.util;

import android.util.Log;

import com.mofeel.mofeelweather.model.entity.City;
import com.mofeel.mofeelweather.model.entity.County;
import com.mofeel.mofeelweather.model.entity.Province;
import com.mofeel.mofeelweather.model.entity.WeatherInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/10 19:58
 * @email:18328541378@163.com
 *
 * 网路请求工具类
 *
 */

public class RetrofitUtils {
    /* 和风天气API_KEY*/
    private static final String API_KEY_CODE = "c7e20d2aa66344ad890a43834e0ae05c";
    /* 省会请求接口 */
    public interface IProvinces {
        String BASEURL = "http://guolin.tech/api/";

        @GET("china")
        Call<List<Province>> getProvinces();
    }

    /**
     * 发送请求获取省信息
     * @param callback 回调
     */
    public static void sendRequestProvinces(retrofit2.Callback<List<Province>> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IProvinces.BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        IProvinces iProvinces = retrofit.create(IProvinces.class);
        Call<List<Province>> provinceCall = iProvinces.getProvinces();
        provinceCall.enqueue(callback);
    }
    /* 获取城市接口 */
    public interface ICities {
        String BASEURL = "http://guolin.tech/api/china/";

        @GET("{provinceId}")
        Call<List<City>> getCities(@Path("provinceId") int provinceId);

    }

    /**
     * 发送请求获取城市信息
     * @param provinceId 省会ID
     * @param callback
     */
    public static void sendRequestCities(int provinceId, retrofit2.Callback<List<City>> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ICities.BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        ICities iCities = retrofit.create(ICities.class);
        Call<List<City>> call = iCities.getCities(provinceId);
        call.enqueue(callback);
    }
    /* 获取区县信息接口 */
    public interface ICounties {
        String BASEURL = "http://guolin.tech/api/china/";

        @GET("{provinceId}/{cityId}")
        Call<List<County>> getCounties(@Path("provinceId") int provinceId, @Path("cityId") int cityId);

    }

    /**
     * 发送请求获取区县信息
     * @param provinceId 省会id
     * @param cityId 城市id
     * @param callback
     */
    public static void sendRequestCounties(int provinceId, int cityId, retrofit2.Callback<List<County>> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ICities.BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        ICounties iCounties = retrofit.create(ICounties.class);
        Call<List<County>> call = iCounties.getCounties(provinceId, cityId);
        call.enqueue(callback);
    }

    public interface IWeather {
        String BASE_URL = "https://free-api.heweather.com/v5/";

        @GET("weather")
        Call<WeatherInfo> getWeather(@Query("city") String weatherId, @Query("key") String key);

    }

    public static void sendRequestWeather(String weatherId, retrofit2.Callback<WeatherInfo> callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IWeather.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        IWeather iWeather = retrofit.create(IWeather.class);
        Call<WeatherInfo> call = iWeather.getWeather(weatherId, API_KEY_CODE);
        Log.i("RetrofitURL",call.request().url().toString());
        call.enqueue(callback);
    }

    public interface IImageBack{

        String BASEURL = "http://guolin.tech/api/";
        @GET("bing_pic")
        Call<String> getImage();

    }

    public static void sendRequestGetImage(retrofit2.Callback<String> callback){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IImageBack.BASEURL).addConverterFactory(ScalarsConverterFactory.create()).build();
        IImageBack iImageBack = retrofit.create(IImageBack.class);
        Call<String> call = iImageBack.getImage();
        call.enqueue(callback);
    }


}
