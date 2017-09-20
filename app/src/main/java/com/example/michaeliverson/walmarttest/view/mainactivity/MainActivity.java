package com.example.michaeliverson.walmarttest.view.mainactivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.michaeliverson.walmarttest.Injection.DaggerMainActivityComponent;
import com.example.michaeliverson.walmarttest.model.Pojos.HourlyForecast;
import com.example.michaeliverson.walmarttest.R;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View

{

    @Inject MainActivityPresenter presenter;

    @BindView(R.id.btnSearch)
    public Button search;

    @BindView(R.id.etLocation)
    public EditText zipCode;

    @BindView(R.id.my_recycler_view)
    public RecyclerView recylcerView;

    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setUpDagger();
        presenter.addView(this);
        String zipTest = presenter.sharedPref();
        if (zipTest == "defaultStringIfNothingFound")
        {
            showAlerts("Please Enter Your Zip Code");
        }else
        {
            progress();
            presenter.saveShared(zipTest);
            presenter.getHourlyReport(zipTest);
        }
    }

    @Override
    public void setUpDagger() {
        DaggerMainActivityComponent.create().inject(this);
    }


    @Override
    public void upDateHourly(ArrayList<HourlyForecast> hourly) {
        recylcerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recylcerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recylcerView.setLayoutManager(layoutManager);
        recylcerView.setItemAnimator(new DefaultItemAnimator());
        recylcerView.setAdapter(new ItemAdaptor(this,hourly));
    }

    @Override
    public void showError(Error error) {
        showAlerts(error.getMessage().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeView();
    }

    private void onSearch(View view) {
        if(zipCode.getText().toString() == null)
        {
            showAlerts("Please Enter Your Zip Code!!!");
        }else
        {
            progress();
            presenter.saveShared(zipCode.getText().toString());
            presenter.getHourlyReport(zipCode.getText().toString());
        }
    }

    private void showAlerts(String Message)
    {
        if (progressBar != null)
            progressBar.dismiss();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(Message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void progress()
    {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Geting Weather Information For Your Zip Code");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
    }

}
