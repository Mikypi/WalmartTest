package com.example.michaeliverson.walmarttest.model.RestServices;

import com.example.michaeliverson.walmarttest.model.Pojos.HourlyForecast;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by michaeliverson on 9/15/17.
 */

@Module
public class WeatherChannel {
    private static final String APIKEY = "675cab30d61408e2";
    private static final String BASEURL = "http://api.wunderground.com";

    public WeatherChannel()
    {}


    public Retrofit Builder1()
    {
        return new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public Call<Result<List<HourlyForecast>>> currentData(String zipCode)
    {
        Retrofit retro = Builder1();
        WeatherService _Weather = retro.create(WeatherService.class);
        return _Weather.forecastForZipCallable(zipCode);
    }


    public interface WeatherService {

        /**
         * Get the forecast for a given zip code using {@link Call}
         */
        @GET("/api/" + WeatherChannel.APIKEY + "/conditions/hourly/q/{zip}.json")
        Call<Result<List<HourlyForecast>>> forecastForZipCallable(@Path("zip") String zipCode);
    }


}
