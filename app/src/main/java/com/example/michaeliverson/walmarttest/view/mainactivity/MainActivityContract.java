package com.example.michaeliverson.walmarttest.view.mainactivity;

import com.example.michaeliverson.walmarttest.model.Pojos.HourlyForecast;
import com.example.michaeliverson.walmarttest.view.BasePresenter;
import com.example.michaeliverson.walmarttest.view.BaseView;

import java.util.ArrayList;

/**
 * Created by michaeliverson on 9/15/17.
 */

public interface MainActivityContract {

    interface View extends BaseView
    {
        void upDateHourly(ArrayList<HourlyForecast> hourly);
    }

    interface Presenter extends BasePresenter<View>
    {
        void getHourlyReport(String zip);
        String sharedPref();
        void saveShared(String zip);

    }
}
