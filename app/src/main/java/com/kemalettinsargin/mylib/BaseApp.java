package com.kemalettinsargin.mylib;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.retro.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Written by "كمال الدّين صارغين"  on 1.09.2018.
 * و من الله توفیق
 */
public class BaseApp extends Application {
    private Api mApi;



    public Api getApi() {
        if (mApi == null) {
            mApi = Api.Companion.getApiInstance(this);
        }
        return mApi;
    }

}
