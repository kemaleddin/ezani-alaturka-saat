package com.sahnisemanyazilim.ezanisaat.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.RemoteViews;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.Util;
import com.sahnisemanyazilim.ezanisaat.C;
import com.sahnisemanyazilim.ezanisaat.MainActivity;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;
import com.sahnisemanyazilim.ezanisaat.services.BigWidgetService;
import com.sahnisemanyazilim.ezanisaat.services.UpdateTimesService;

import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * Implementation of App Widget functionality.
 */
public class EzaniBigWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        TimesOfDay toDay;
        List<Town> towns = new Gson().fromJson(Util.getPref(context, C.KEY_LOCATIONS), new TypeToken<List<Town>>() {
        }.getType());
        if (towns == null || towns.isEmpty()) {
            Util.showToast(context, context.getString(R.string.konum_ekle));
            return;
        }
        Town town = towns.get(0);
        String id = Util.getPref(context, C.KEY_ACTIVE);
        for (Town town1 : towns) {
            if (town1.getIlceID().equals(id))
                town = town1;
        }
        int index = -1;
        toDay = town.getTimesOfDays().get(0);
        for (int i = 0; i < town.getTimesOfDays().size(); i++) {
            TimesOfDay timesOfDay = town.getTimesOfDays().get(i);
            if (timesOfDay.equals(TimesOfDay.getToDay())) {
                toDay = timesOfDay;
                index = i;
                break;
            }
        }
        if(index<0){
            UpdateTimesService.scheduleUpdateJob(context);
            return;
        }
      /*  if(index>1){
            town.setVakitler(town.getTimesOfDays().subList(index-1,town.getTimesOfDays().size()));
            Util.savePref(context,C.KEY_LOCATIONS,getGson().toJson(towns));
        }*/
        if (index > 0)
            toDay.setYesterDay(town.getTimesOfDays().get(index - 1));
        else {
            TimesOfDay yesterDay = getGson().fromJson(getGson().toJson(toDay), TimesOfDay.class);
            yesterDay.setDateToYesterDay();
            toDay.setYesterDay(yesterDay);
        }
        toDay.setToMorrow(town.getTimesOfDays().get(index + 1));
        toDay.setName(context.getString(R.string.umumi));


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        views.setOnClickPendingIntent(R.id.widget, configPendingIntent);
        views.setTextViewText(R.id.text_miladi, toDay.isYatsiGecti() ? toDay.getToMorrow().getMiladiTarihUzun() : toDay.getMiladiTarihUzun());
        views.setTextViewText(R.id.text_hicri, toDay.isEveningNight() ? toDay.getToMorrow().getHicriTarihUzun() : toDay.getHicriTarihUzun());
        views.setTextViewText(R.id.text_ezani, TimesOfDay.getSaat12(toDay.isEveningNight() ? toDay.getEzaniSaatWithoutSec() : toDay.getYesterDay().getEzaniSaatWithoutSec()));
        views.setTextViewText(R.id.text_kalan, toDay.getKalanWOsec());
        views.setTextViewText(R.id.text_e_yatsi, TimesOfDay.getSaat12(toDay.getYatsiEzani()));
        views.setTextViewText(R.id.text_e_imsak, TimesOfDay.getSaat12(toDay.getImsakEzani()));
        views.setTextViewText(R.id.text_e_gunes, TimesOfDay.getSaat12(toDay.getGunesEzani()));
        views.setTextViewText(R.id.text_e_ogle, TimesOfDay.getSaat12(toDay.getOgleEzani()));
        views.setTextViewText(R.id.text_e_ikindi, TimesOfDay.getSaat12(toDay.getIkindiEzani()));
        views.setTextViewText(R.id.text_u_imsak, toDay.getChkImsak());
        views.setTextViewText(R.id.text_u_gunes, toDay.getChkGunes());
        views.setTextViewText(R.id.text_u_ogle, toDay.getChkOgle());
        views.setTextViewText(R.id.text_u_ikindi, toDay.getChkIkindi());
        views.setTextViewText(R.id.text_u_aksam, toDay.getChkAksam());
        views.setTextViewText(R.id.text_u_yatsi, toDay.getChkYatsi());
        views.setTextViewText(R.id.text_location, town.getIlceAdi());
        int text_ids[] = new int[]{R.id.text_miladi,
                R.id.text_hicri, R.id.text_ezani, R.id.text_kalan, R.id.text_e_yatsi, R.id.text_e_imsak,
                R.id.text_e_gunes, R.id.text_e_ogle, R.id.text_e_ikindi, R.id.text_e_aksam, R.id.text_u_imsak,
                R.id.text_u_gunes, R.id.text_u_ogle, R.id.text_u_ikindi, R.id.text_u_aksam,
                R.id.text_u_yatsi, R.id.text_location,
                R.id.text_u_tit_yatsi, R.id.text_u_tit_aksam, R.id.text_u_tit_ikindi,
                R.id.text_u_tit_ogle, R.id.text_u_tit_gunes, R.id.text_u_tit_imsak, R.id.text_e_tit_yatsi,
                R.id.text_e_tit_aksam, R.id.text_e_tit_ikindi, R.id.text_e_tit_ogle, R.id.text_e_tit_gunes,
                R.id.text_e_tit_imsak};
        for (int text_id : text_ids) {
            views.setTextColor(text_id, ContextCompat.getColor(context, R.color.white));
        }
        int ids[] = toDay.getNextIds();
        if (ids != null)
            for (int i : ids) {
                views.setTextColor(i, ContextCompat.getColor(context, R.color.colorPrimary));
            }


//        views.setRemoteAdapter(R.id.text_kalan,new Intent(context, WidgetService.class));
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.layout.widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);

//        Util.log("update=%s",System.currentTimeMillis()%60000);
    }


    private static Gson getGson() {
        return Util.getGson();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        scheduleJob(context);
        if (appWidgetIds == null) {
            super.onUpdate(context, appWidgetManager, appWidgetIds);
            return;
        }
        PowerManager.WakeLock wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "Ezani Saat:WAKE LOCK");
        wakeLock.acquire(60000);
        for (int appWidgetId : appWidgetIds) {
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.text_kalan);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        wakeLock.release();
   /*     try {
            final Intent intent = new Intent(context, BigWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            alarm.set(AlarmManager.RTC_WAKEUP, new Date().getTime() + C.interval_1, pending);
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, new Date().getTime()+(interval-(new Date().getTime()%60000)), interval, pending);
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }*/

//        Util.log("onUpdate asdasd");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, EzaniBigWidget.class));
       /*    try {      final Intent intent = new Intent(context, BigWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            alarm.set(AlarmManager.RTC_WAKEUP, new Date().getTime() + C.interval_1, pending);
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, new Date().getTime()+(interval-(new Date().getTime()%60000)), interval, pending);
        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }*/
       for (int appWidgetId : appWidgetIds) {
           updateAppWidget(context,appWidgetManager,appWidgetId);
       }
        scheduleJob(context);
        Util.getPrefs(context).edit().putBoolean(BigWidgetService.TAG,true).apply();
        super.onEnabled(context);

    }
    private void scheduleJob(Context context){
        FirebaseJobDispatcher mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myJob = mDispatcher.newJobBuilder()
                .setService(BigWidgetService.class)
                .setTag(BigWidgetService.TAG)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0, 30))
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setReplaceCurrent(true)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        mDispatcher.mustSchedule(myJob);
    }


    @Override
    public void onDisabled(Context context) {
      /*  PendingIntent localPendingIntent = PendingIntent.getService(context, 0, new Intent(context, BigWidgetService.class), 0);
        try {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).cancel(localPendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        FirebaseJobDispatcher mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        mDispatcher.cancel(BigWidgetService.TAG);
        Util.getPrefs(context).edit().putBoolean(BigWidgetService.TAG,false).apply();
    }

}

