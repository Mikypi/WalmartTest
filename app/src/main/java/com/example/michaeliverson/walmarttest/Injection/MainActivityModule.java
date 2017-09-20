package com.example.michaeliverson.walmarttest.Injection;

import com.example.michaeliverson.walmarttest.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by michaeliverson on 9/15/17.
 */

@Module
public class MainActivityModule {

    @Provides
    public MainActivityPresenter provides()
    {
        return new MainActivityPresenter();
    }
}
