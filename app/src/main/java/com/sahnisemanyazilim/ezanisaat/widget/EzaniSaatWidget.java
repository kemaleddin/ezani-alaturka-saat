package com.sahnisemanyazilim.ezanisaat.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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
import com.sahnisemanyazilim.ezanisaat.services.SaatWidgetService;
import com.sahnisemanyazilim.ezanisaat.services.UpdateTimesService;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class EzaniSaatWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        TimesOfDay toDay;
        List<Town> towns=new Gson().fromJson(Util.getPref(context, C.KEY_LOCATIONS), new TypeToken<List<Town>>(){}.getType());
        if(towns==null||towns.isEmpty()) {
            Util.showToast(context,context.getString(R.string.konum_ekle));
            return;
        }
        Town town=towns.get(0);
        String id=Util.getPref(context,C.KEY_ACTIVE);
        for (Town town1 : towns) {
            if(town1.getIlceID().equals(id))
                town=town1;
        }
        int index=-1;
        toDay=town.getTimesOfDays().get(0);
        for (int i = 0; i < town.getTimesOfDays().size(); i++) {
            TimesOfDay timesOfDay=town.getTimesOfDays().get(i);
            if(timesOfDay.equals(TimesOfDay.getToDay())){
                toDay=timesOfDay;
                index=i;
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
        if(index>0)
            toDay.setYesterDay(town.getTimesOfDays().get(index-1));
        else {
            TimesOfDay yesterDay=getGson().fromJson(getGson().toJson(toDay),TimesOfDay.class);
            yesterDay.setDateToYesterDay();
            toDay.setYesterDay(yesterDay);
        }
        toDay.setToMorrow(town.getTimesOfDays().get(index+1));
        toDay.setName(context.getString(R.string.umumi));



        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.saat_widget);
        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        views.setOnClickPendingIntent(R.id.saat_widget, configPendingIntent);
        views.setTextViewText(R.id.text_saat_widget_kalan,     toDay.getKalanWOsec());
        views.setTextViewText(R.id.text_hicri_tarih,      toDay.isEveningNight()?toDay.getToMorrow().getHicriTarihUzunGunlu():toDay.getHicriTarihUzunGunlu());
        views.setTextViewText(R.id.text_ezani_saat,      TimesOfDay.getSaat12(toDay.isEveningNight()?toDay.getEzaniSaatWithoutSec():toDay.getYesterDay().getEzaniSaatWithoutSec()));


//        views.setRemoteAdapter(R.id.text_kalan,new Intent(context, WidgetService.class));
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.layout.widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);

//        Util.log("update=%s",System.currentTimeMillis()%60000);
    }



    private static Gson getGson(){
        return Util.getGson();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if(appWidgetIds==null){
            super.onUpdate(context,appWidgetManager,appWidgetIds);
            return;
        }

        PowerManager.WakeLock wakeLock = ((PowerManager)context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "Ezani Saat : WAKE LOCK");
        wakeLock.acquire(60000);
        for (int appWidgetId : appWidgetIds) {
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.text_kalan);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        wakeLock.release();
   /*     try {
            final Intent intent = new Intent(context, SaatWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
//            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime()+(interval-(new Date().getTime()%interval)), interval, pending);
            alarm.set(AlarmManager.RTC_WAKEUP,new Date().getTime()+C.interval_1,pending);
        }catch (Exception e){
            e.printStackTrace();
            Crashlytics.logException(e);
        }*/

        scheduleJob(context);
//        Util.log("onUpdate asdasd");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
/*        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(context,EzaniSaatWidget.class));
            final Intent intent = new Intent(context, SaatWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            alarm.set(AlarmManager.RTC_WAKEUP,new Date().getTime()+C.interval_1,pending);
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, new Date().getTime()+(interval-(new Date().getTime()%60000)), interval, pending);
        }catch (Exception e){
            e.printStackTrace();
            Crashlytics.logException(e);
        }*/

        scheduleJob(context);
        Util.getPrefs(context).edit().putBoolean(SaatWidgetService.TAG,true).apply();
        super.onEnabled(context);

    }

    private void scheduleJob(Context context){
        FirebaseJobDispatcher mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myJob = mDispatcher.newJobBuilder()
                .setService(SaatWidgetService.class)
                .setTag(SaatWidgetService.TAG)
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
       /* PendingIntent localPendingIntent = PendingIntent.getService(context, 0, new Intent(context, SaatWidgetService.class), 0);
        try {
            ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).cancel(localPendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        FirebaseJobDispatcher mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        mDispatcher.cancel(SaatWidgetService.TAG);
        Util.getPrefs(context).edit().putBoolean(SaatWidgetService.TAG,false).apply();
    }

}

