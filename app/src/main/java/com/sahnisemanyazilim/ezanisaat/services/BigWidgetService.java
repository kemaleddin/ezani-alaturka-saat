package com.sahnisemanyazilim.ezanisaat.services;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.MyApp;
import com.kemalettinsargin.mylib.Util;
import com.sahnisemanyazilim.ezanisaat.C;
import com.sahnisemanyazilim.ezanisaat.widget.EzaniBigWidget;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;
import com.sahnisemanyazilim.ezanisaat.retro.RetroInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
