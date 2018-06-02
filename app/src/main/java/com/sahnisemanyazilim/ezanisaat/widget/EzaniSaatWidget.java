package com.sahnisemanyazilim.ezanisaat.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.Util;
import com.sahnisemanyazilim.ezanisaat.C;
import com.sahnisemanyazilim.ezanisaat.MainActivity;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;
import com.sahnisemanyazilim.ezanisaat.services.SaatWidgetService;

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

        int index=0;
        toDay=town.getVakitler().get(0);
        for (int i = 0; i < town.getVakitler().size(); i++) {
            TimesOfDay timesOfDay=town.getVakitler().get(i);
            if(!timesOfDay.isOld()){
                toDay=timesOfDay;
                index=i;
                break;
            }
        }
      /*  if(index>1){
            town.setVakitler(town.getVakitler().subList(index-1,town.getVakitler().size()));
            Util.savePref(context,C.KEY_LOCATIONS,getGson().toJson(towns));
        }*/
        if(index>0)
            toDay.setYesterDay(town.getVakitler().get(index-1));
        else {
            TimesOfDay yesterDay=getGson().fromJson(getGson().toJson(toDay),TimesOfDay.class);
            yesterDay.setDateToYesterDay();
            toDay.setYesterDay(yesterDay);
        }
        toDay.setToMorrow(town.getVakitler().get(index+1));
        toDay.setName(context.getString(R.string.umumi));



        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.saat_widget);
        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        views.setOnClickPendingIntent(R.id.saat_widget, configPendingIntent);
        views.setTextViewText(R.id.text_saat_widget_kalan,     toDay.getKalanWOsec());
        views.setTextViewText(R.id.text_hicri_tarih,      toDay.isEveningNight()?toDay.getToMorrow().getHicriTarihUzunGunlu():toDay.getHicriTarihUzunGunlu());
        views.setTextViewText(R.id.text_ezani_saat,      toDay.isEveningNight()?toDay.getEzaniSaatWithoutSec():toDay.getYesterDay().getEzaniSaatWithoutSec());


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
        for (int appWidgetId : appWidgetIds) {
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.text_kalan);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        try {
            final Intent intent = new Intent(context, SaatWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            long interval = 60000;
            alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+(interval-(SystemClock.elapsedRealtime()%60000)), interval, pending);
        }catch (Exception e){
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
//        Util.log("onUpdate asdasd");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(context,EzaniSaatWidget.class));
            final Intent intent = new Intent(context, SaatWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            long interval = 60000;
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+(interval-(SystemClock.elapsedRealtime()%60000)), interval, pending);
        }catch (Exception e){
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        super.onEnabled(context);

    }

    @Override
    public void onDisabled(Context context) {
        PendingIntent localPendingIntent = PendingIntent.getService(context, 0, new Intent(context, SaatWidgetService.class), 0);
        try {
            ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).cancel(localPendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

