package com.sahnisemanyazilim.ezanisaat.services;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;

import androidx.annotation.Nullable;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sahnisemanyazilim.ezanisaat.widget.EzaniBigWidget;
import com.sahnisemanyazilim.ezanisaat.model.Town;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BigWidgetService extends JobService {
    public static final String TAG="BigWidgetService";
    private List<Town> updatingTimes = new ArrayList<>();


    @Override
    public boolean onStartJob(JobParameters job) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, EzaniBigWidget.class));
        if (allWidgetIds == null) {
            return false;
        }
        new EzaniBigWidget().onUpdate(this, appWidgetManager, allWidgetIds);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
