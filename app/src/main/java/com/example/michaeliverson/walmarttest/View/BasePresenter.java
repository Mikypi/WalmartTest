package com.example.michaeliverson.walmarttest.View;

/**
 * Created by michaeliverson on 9/15/17.
 */

public interface BasePresenter <V extends BaseView>
{
    void addView(V view);
    void removeView();
}
