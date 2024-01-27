package com.sahnisemanyazilim.ezanisaat.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.BaseApp;
import com.kemalettinsargin.mylib.Util;
import com.sahnisemanyazilim.ezanisaat.BuildConfig;
import com.sahnisemanyazilim.ezanisaat.C;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;
import com.sahnisemanyazilim.ezanisaat.retro.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateTimesService extends Worker {
    public static final String TAG = "UpdateTimesService";
    private List<Town> updatingTimes = new ArrayList<>();
    private NotificationManagerCompat notificationManager;

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public UpdateTimesService(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Util.log("job started@" + this.getClass().getSimpleName());
        List<Town> towns = Util.getGson().fromJson(Util.getPref(getApplicationContext(), C.KEY_LOCATIONS), new TypeToken<List<Town>>() {
        }.getType());
        if (towns == null)
            return Result.success();
        updatingTimes.clear();
        for (Town town : towns) {
            if (town.needUpdate()) {
                updatingTimes.add(town);
            }
        }
        if (updatingTimes != null && updatingTimes.size() > 0) {
            notificationManager = NotificationManagerCompat.from(getApplicationContext());
//            showUpdateNotification();
            getSaatler(updatingTimes.get(0));
        }
        return Result.success();
    }


    public void getSaatler(final Town town) {
        Util.log("getsaatler");
        Api.Companion.getApiInstance(getApplicationContext()).getTimes(town.getIlceID()).enqueue(new Callback<List<TimesOfDay>>() {
            @Override
            public void onResponse(Call<List<TimesOfDay>> call, Response<List<TimesOfDay>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Town newTown = town.getClone();
                    newTown.setVakitler(response.body());
                    setSaatler(newTown);
                } else onFailure(null, null);
            }

            @Override
            public void onFailure(Call<List<TimesOfDay>> call, Throwable t) {
                Util.log("Update saatler failure");
            }
        });
    }

    private void setSaatler(Town newTown) {
        if (BuildConfig.DEBUG)
            Util.showToast(getApplicationContext(), "saatler geldi");
        Gson gson = Util.getGson();
        List<Town> towns = gson.fromJson(Util.getPref(getApplicationContext(), C.KEY_LOCATIONS), new TypeToken<List<Town>>() {
        }.getType());
        Town oldTown = towns.get(towns.indexOf(newTown));
        int index = 0;
        TimesOfDay toDayTimes = newTown.getTimesOfDays().get(0);
        for (TimesOfDay timesOfDay : oldTown.getTimesOfDays()) {
            if (timesOfDay.equals(toDayTimes)) {
                index = oldTown.getTimesOfDays().indexOf(timesOfDay);
                break;
            }
        }
        oldTown.setVakitler(oldTown.getTimesOfDays().subList(0, index));
        oldTown.getTimesOfDays().addAll(newTown.getTimesOfDays());
        Util.savePref(getApplicationContext(), C.KEY_LOCATIONS, gson.toJson(towns));
        int townIndex = updatingTimes.indexOf(newTown) + 1;
        if (townIndex < updatingTimes.size())
            getSaatler(updatingTimes.get(townIndex));
        else {
        }
    }


    private String getString(@StringRes int id) {
        return getApplicationContext().getString(id);
    }

    public static void scheduleUpdateJob(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        WorkManager workManager = WorkManager.getInstance(context);
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UpdateTimesService.class)
                .setConstraints(constraints)
                .addTag(UpdateTimesService.TAG)
                .build();

        workManager.enqueue(request);

    }
}
