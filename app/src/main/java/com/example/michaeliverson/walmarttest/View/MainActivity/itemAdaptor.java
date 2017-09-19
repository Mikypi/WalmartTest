package com.example.michaeliverson.walmarttest.View.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by michaeliverson on 9/15/17.
 */

public class itemAdaptor extends RecyclerView.Adapter<itemAdaptor.ViewHolder>
{
    private final String zipCode = "com.example.michaeliverson.walmarttest.zipCodeKey";
    private final String shared = "com.example.michaeliverson.walmarttest";
    Context context;
    ArrayList<HourlyForecast> data;
    HashMap<String,ArrayList<HourlyForecast>> hourly;

    public itemAdaptor(Context context, ArrayList<HourlyForecast> list) {
        this.data = list;
        this.context = context;
        Splice();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(this.context);
        View holderView = inflator.inflate(R.layout.weather_card_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(holderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position) {
            case 1:
            {
                setUpView("Today",hourly.get("Today"), holder);
                break;
            }
            case 2:
            {
                setUpView("Tomorrow",hourly.get("Tomorrow"), holder);
                break;
            }
            case 3:
            {
                setUpView("Day After Tomorrow", hourly.get("Day After Tomorrow"), holder);
                break;
            }
            default:
                break;
        }
    }

    private void setUpView(String day, ArrayList<HourlyForecast> list, ViewHolder holder)
    {
        holder.title.setText(day);
        Glide.with(this.context).load(list.get(0).getIconUrl().toString()).into(holder.mainIcon);
        holder.mainCondition.setText(list.get(0).getCondition().toString());
        holder.zip.setText(sharedZipCode());
        holder.mainTemp.setText(list.get(0).getTemp().getEnglish().toString());
        Integer temp = Integer.parseInt(list.get(0).getTemp().getEnglish().toString());
        backgroundColor(temp.intValue(),holder);

    }

    private String sharedZipCode()
    {
        SharedPreferences preferences = this.context.getSharedPreferences(shared,this.context.MODE_PRIVATE);
        return preferences.getString(zipCode,"defaultStringIfNothingFound");
    }

    private void backgroundColor(int Temp,ViewHolder holder)
    {
        if ( 60 <= Temp )
            holder.view.setBackgroundColor(Color.parseColor("#ff9800"));
        else
            holder.view.setBackgroundColor(Color.parseColor("#03a9f4"));
    }

    @Override
    public int getItemCount() {
        return this.hourly.size();
    }


    // This Method is to set up the Hasmap
    private int sizeForHashTable()
    {
        String compare="";
        int numberOfdates = 0;
        for (int i=0; i <= hourly.size();i++)
        {
            if (i==0)
            {
                numberOfdates +=1;
                compare = data.get(i).getFCTTIME().getPretty();
            }else if(compare != data.get(i).getFCTTIME().getPretty())
            {
                numberOfdates+=1;
                compare = data.get(i).getFCTTIME().getPretty();
            }
        }
        return numberOfdates;
    }

    // Set Up the Hashmap Keys and Arraylist
    private void setUpHashMap(int number)
    {
        switch (number)
        {
            case 1:
            {
                this.hourly.put("Today",new ArrayList<HourlyForecast>());
                break;
            }
            case 2:
            {
                this.hourly.put("Today",new ArrayList<HourlyForecast>());
                this.hourly.put("Tomorrow", new ArrayList<HourlyForecast>());
                break;
            }
            case 3:
            {
                this.hourly.put("Today", new ArrayList<HourlyForecast>());
                this.hourly.put("Tomorrow", new ArrayList<HourlyForecast>());
                this.hourly.put("Day After Tomorrow", new ArrayList<HourlyForecast>());
                break;
            }default:
                break;
        }
    }

    // Load Hashmap from this.dat to Hashmap
    private void loadHashTable(int day, HourlyForecast hourly)
    {
        switch (day)
        {
            case 1:
            {
                this.hourly.get("Today").add(hourly);
                break;
            }
            case 2:
            {
                this.hourly.get("Tomorrow").add(hourly);
                break;
            }
            case 3:
            {
                this.hourly.get("Day After Tomorrow").add(hourly);
                break;
            }
            default:
                break;
        }
    }
    
    // This Method set the size of the hashMap
    private void Splice()
    {
        String dateCompare ="";
        int changeCount = 1;
        int numberOfHashes = sizeForHashTable();
        setUpHashMap(numberOfHashes);

        for (int i=0; i<= this.data.size();i++)
        {
            if (i == 0)
            {
                dateCompare = this.data.get(i).getFCTTIME().getPretty();
                loadHashTable(changeCount,this.data.get(i));
            }else if ( dateCompare != this.data.get(i).getFCTTIME().getPretty())
            {
                 changeCount += 1;
                 dateCompare = this.data.get(i).getFCTTIME().getPretty();
                 loadHashTable(changeCount,this.data.get(i));
            }else
            {
                loadHashTable(changeCount,this.data.get(i));
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private View view;

        private RecyclerView.LayoutManager layoutManager;

        @BindView(R.id.rvContainer)
        RecyclerView recycler;

        @BindView(R.id.tvTitle)  // DailyTitle
        public TextView title;

        @BindView(R.id.ivMainIcon)
        public ImageView mainIcon;

        @BindView(R.id.tvMainTemp)
        public TextView mainTemp;

        @BindView(R.id.tvCondition)
        public TextView mainCondition;

        @BindView(R.id.tvZip)
        public TextView zip;

        public ViewHolder(View itemView) {

            super(itemView);
            this.view = itemView;
        }

        public void IntiateSecondCarView(ArrayList<HourlyForecast> hourlyForecasts)
        {
            recycler.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this.view.getContext());
            recycler.setLayoutManager(layoutManager);
            recycler.setItemAnimator(new DefaultItemAnimator());
            recycler.setAdapter(new foreCastAdapter(this.view.getContext(),hourlyForecasts));
        }
    }
}
