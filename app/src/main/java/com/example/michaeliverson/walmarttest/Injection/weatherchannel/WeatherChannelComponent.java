package com.example.michaeliverson.walmarttest.Injection.weatherchannel;

import com.example.michaeliverson.walmarttest.model.RestServices.WeatherChannel;
import com.example.michaeliverson.walmarttest.view.mainactivity.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by michaeliverson on 9/23/17.
 */

@Singleton
@Component(modules = {WeatherChannel.class})
public interface WeatherChannelComponent {
        void inject(MainActivityPresenter mainActivityPresenter);
}
