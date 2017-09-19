package com.example.michaeliverson.walmarttest.Injection;

import com.example.michaeliverson.walmarttest.View.MainActivity.MainActivity;

import dagger.Component;

/**
 * Created by michaeliverson on 9/15/17.
 */

@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
