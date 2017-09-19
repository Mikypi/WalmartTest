package com.example.michaeliverson.walmarttest.View.MainActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.michaeliverson.walmarttest.Model.Pojos.HourlyForecast;
import com.example.michaeliverson.walmarttest.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by michaeliverson on 9/18/17.
 */


public class foreCastAdapter extends RecyclerView.Adapter<foreCastAdapter.ViewHolder>
{
    private ArrayList<HourlyForecast> hourly;
    private Context context;

    public foreCastAdapter() {
    }

    public foreCastAdapter(ArrayList<HourlyForecast> hourlyList)
    {
        this.hourly = hourlyList;
    }

    public foreCastAdapter(Context context, ArrayList<HourlyForecast> list)
    {
        this.context = context;
        this.hourly = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(this.context);
        View holderView = inflator.inflate(R.layout.forecastcardview,parent,false);
        foreCastAdapter.ViewHolder viewHolder = new foreCastAdapter.ViewHolder(holderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(this.context).load(this.hourly.get(position).getIconUrl().toString()).into(holder.forecaste);
        holder.temperature.setText(this.hourly.get(position).getTemp().getEnglish());
    }


    @Override
    public int getItemCount() {
        return this.hourly.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imForecaste)
        public ImageView forecaste;

        @BindView(R.id.tcTemperature)
        public TextView temperature;

        public ViewHolder(View itemView) {

            super(itemView);
        }
    }
}
