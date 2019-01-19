package com.sahnisemanyazilim.ezanisaat.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.MyApp;
import com.kemalettinsargin.mylib.Util;
import com.sahnisemanyazilim.ezanisaat.BuildConfig;
import com.sahnisemanyazilim.ezanisaat.C;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateTimesService extends JobService {
    public static final String TAG = "UpdateTimesService";
    private List<Town> updatingTimes = new ArrayList<>();
    private NotificationManagerCompat notificationManager;


    @Override
    public boolean onStartJob(JobParameters job) {
        if(BuildConfig.DEBUG)
        Util.showToast(getApplicationContext(),"job started");
        List<Town> towns = Util.getGson().fromJson(Util.getPref(this, C.KEY_LOCATIONS), new TypeToken<List<Town>>(){}.getType());
        if(towns==null)return false;
        updatingTimes.clear();
        for (Town town : towns) {
            if(town.needUpdate()){
                updatingTimes.add(town);
            }
        }
        if (updatingTimes != null && updatingTimes.size() > 0) {
            notificationManager = NotificationManagerCompat.from(this);
//            showUpdateNotification();
            getSaatler(updatingTimes.get(0),job);
            return true;
        } else
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


    public void getSaatler(final Town town,final JobParameters job) {
        Util.log("getsaatler");
        ((MyApp) getApplication()).getApi().getTimes(town.getIlceID()).enqueue(new Callback<List<TimesOfDay>>() {
            @Override
            public void onResponse(Call<List<TimesOfDay>> call, Response<List<TimesOfDay>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Town newTown = town.getClone();
                    newTown.setVakitler(response.body());
                    setSaatler(newTown,job);
                } else onFailure(null, null);
            }

            @Override
            public void onFailure(Call<List<TimesOfDay>> call, Throwable t) {
//                stopForeground(true);
                jobFinished(job,false);
//                showErrorNoti();
            }
        });
    }

    private void setSaatler(Town newTown,JobParameters job) {
        if(BuildConfig.DEBUG)
        Util.showToast(getApplicationContext(),"saatler geldi");
        Gson gson = Util.getGson();
        List<Town> towns = gson.fromJson(Util.getPref(this, C.KEY_LOCATIONS), new TypeToken<List<Town>>() {
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
        Util.savePref(this, C.KEY_LOCATIONS, gson.toJson(towns));
        int townIndex = updatingTimes.indexOf(newTown) + 1;
        if (townIndex < updatingTimes.size())
            getSaatler(updatingTimes.get(townIndex),job);
        else {
            jobFinished(job,false);
//            showFinishedNotification();
//            stopForeground(false);
        }
    }

    private void showUpdateNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_update));
        mBuilder.setContentTitle(getString(R.string.update))
                .setContentText(getString(R.string.updating_times_all))
                .setSmallIcon(R.drawable.ic_notify)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_LOW);
        Notification notification = mBuilder.build();
        startForeground(1,notification);

// Issue the initial notification with zero progress
//        int PROGRESS_MAX = 100;
//        int PROGRESS_CURRENT = 0;
//        mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, true);
//        notificationManager.notify(1, notification);
// Do the job here that tracks the progress.
// Usually, this should be in a worker thread
// To show progress, update PROGRESS_CURRENT and update the notification with:
// mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
// notificationManager.notify(notificationId, mBuilder.build());

// When done, update the notification one more time to remove the progress bar
      /*  mBuilder.setContentText("Download complete")
                .setProgress(0,0,false);
        notificationManager.notify(notificationId, mBuilder.build());*/
    }

    private void showFinishedNotification() {
        notificationManager.cancel(1);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_update));
        mBuilder.setContentTitle(getString(R.string.update))
                .setContentText(getString(R.string.updating_times_all))
                .setSmallIcon(R.drawable.ic_notify)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        Notification notification = mBuilder.build();
        stopForeground(true);
    }

    private void showErrorNoti() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_update));
        mBuilder.setContentTitle(getString(R.string.error))
                .setContentText(getString(R.string.err_msg))
                .setSmallIcon(R.drawable.ic_notify)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_LOW);
        Intent notificationIntent = new Intent(this, UpdateTimesService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, notificationIntent, 0);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notificationManager.notify(1, notification);
    }

    public static void scheduleUpdateJob(Context context){
        FirebaseJobDispatcher mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myJob = mDispatcher.newJobBuilder()
                .setService(UpdateTimesService.class)
                .setTag(UpdateTimesService.TAG)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0, 86400))
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setReplaceCurrent(true)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        mDispatcher.mustSchedule(myJob);
    }

}
