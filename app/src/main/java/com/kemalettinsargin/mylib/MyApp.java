package com.kemalettinsargin.mylib;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.retro.RetroInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Written by "كمال الدّين صارغين"  on 1.09.2018.
 * و من الله توفیق
 */
public class MyApp extends Application {
    private RetroInterface mApi;
    private Gson gson = new Gson();
    private OkHttpClient client;


    public Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation().create();
        }
        return gson;
    }

    public RetroInterface getApi() {
        if (mApi == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    /*.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);

                            return response;
                        }
                    })*/
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.base_url))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            mApi = retrofit.create(RetroInterface.class);
        }
        return mApi;
    }

    public OkHttpClient getClient() {
        return client;
    }
}
