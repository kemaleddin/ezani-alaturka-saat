package com.sahnisemanyazilim.ezanisaat.services;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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


public class BigWidgetService extends Service {
    private RetroInterface mApi;
    private List<Town> updatingTimes=new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this                .getApplicationContext());
        int[] allWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(this,EzaniBigWidget.class));
        if(allWidgetIds==null){
            stopSelf();
            return super.onStartCommand(intent,flags,startId);
        }

        /*for (int allWidgetId : allWidgetIds) {
            Util.log("widget_id=%s",allWidgetId);
            EzaniBigWidget.updateAppWidget(this,appWidgetManager,allWidgetId);
        }*/
        new EzaniBigWidget().onUpdate(this,appWidgetManager,allWidgetIds);
//        Util.log("service");

        /*try {
            ((AlarmManager)getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, 60000L, PendingIntent.getService(this, 0, new Intent(this, WidgetService.class), 0));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        updatingTimes.clear();
        List<Town> towns= Util.getGson().fromJson(Util.getPref(this, C.KEY_LOCATIONS),new TypeToken<List<Town>>(){}.getType());
            for (Town town : towns) {
                if(town.getTimesOfDays().size()<4){
                    updatingTimes.add(town);
                }
            }
            if(updatingTimes.size()>0) {
                createApi();
                getSaatler(updatingTimes.get(0));
            }else
                stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }


    private void createApi(){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().create();
        OkHttpClient client = new OkHttpClient.Builder()
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
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mApi = retrofit.create(RetroInterface.class);

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void getSaatler(final Town town) {
        mApi.getTimes(town.getIlceID()).enqueue(new Callback<List<TimesOfDay>>() {
            @Override
            public void onResponse(Call<List<TimesOfDay>> call, Response<List<TimesOfDay>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Town newTown=town.getClone();
                    newTown.setVakitler(response.body());
                    setSaatler(newTown);
                }
            }

            @Override
            public void onFailure(Call<List<TimesOfDay>> call, Throwable t) {
                stopSelf();
            }
        });
    }
    private void setSaatler(Town newTown){
        Gson gson=Util.getGson();
        List<Town> towns = gson.fromJson(Util.getPref(this,C.KEY_LOCATIONS),new TypeToken<List<Town>>(){}.getType());
        Town oldTown=towns.get(towns.indexOf(newTown));
        int index=0;
        TimesOfDay toDayTimes=newTown.getTimesOfDays().get(0);
        for (TimesOfDay timesOfDay : oldTown.getTimesOfDays()) {
            if(timesOfDay.equals(toDayTimes)){
                index=oldTown.getTimesOfDays().indexOf(timesOfDay);
                break;
            }
        }
        oldTown.setVakitler(oldTown.getTimesOfDays().subList(0,index));
        oldTown.getTimesOfDays().addAll(newTown.getTimesOfDays());
        Util.savePref(this,C.KEY_LOCATIONS,gson.toJson(towns));
        int townIndex=updatingTimes.indexOf(newTown)+1;
        if(townIndex<updatingTimes.size())
            getSaatler(updatingTimes.get(townIndex));
        else stopSelf();
    }
}
