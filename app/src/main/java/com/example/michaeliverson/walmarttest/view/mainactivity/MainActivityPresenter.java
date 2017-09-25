package com.example.michaeliverson.walmarttest.view.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.michaeliverson.walmarttest.model.Pojos.HourlyForecast;
import com.example.michaeliverson.walmarttest.model.RestServices.WeatherChannel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;

/**
 * Created by michaeliverson on 9/15/17.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter


{
    @Inject MainActivityPresenter presentWeather;
    private MainActivityContract.View view;
    private Context context;
    private final String shared = "com.example.michaeliverson.walmarttest";
    private final String zipCode = "com.example.michaeliverson.walmarttest.zipCodeKey";


    @Override
    public void addView(MainActivityContract.View view) {
        this.view = view;
        this.context = (Context)view;
        ((WalmartTest.))
    }

    @Override
    public void removeView() {
        this.view = null;
        this.context = null;
    }

    @Override
    public void getHourlyReport(String zip) {

        final ArrayList<HourlyForecast> hourlyForcast = new ArrayList<>();
        Call<Result<List<HourlyForecast>>> result = new WeatherChannel().currentData(zip);
        result.enqueue(new Callback<Result<List<HourlyForecast>>>() {
            @Override
            public void onResponse(Call<Result<List<HourlyForecast>>> call, Response<Result<List<HourlyForecast>>> response) {
                view.upDateHourly(new ArrayList<HourlyForecast>(response.body().response().body()));
            }

            @Override
            public void onFailure(Call<Result<List<HourlyForecast>>> call, Throwable t) {
                Error error = new Error(t.getMessage());
                view.showError(error);
            }
        });
    }

    @Override
    public String sharedPref() {
        SharedPreferences preferences = this.context.getSharedPreferences(shared,this.context.MODE_PRIVATE);
        return preferences.getString(zipCode,"defaultStringIfNothingFound");
    }

    @Override
    public void saveShared(String zip) {
        SharedPreferences preferences = this.context.getSharedPreferences(shared,this.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(zipCode,zip).apply();
    }
}
