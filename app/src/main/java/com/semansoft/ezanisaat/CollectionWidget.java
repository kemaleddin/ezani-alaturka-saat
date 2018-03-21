package com.semansoft.ezanisaat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.Util;
import com.semansoft.ezanisaat.model.TimesOfDay;
import com.semansoft.ezanisaat.model.Town;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class CollectionWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        TimesOfDay toDay;
        List<Town> towns=new Gson().fromJson(Util.getPref(context,C.KEY_LOCATIONS), new TypeToken<List<Town>>(){}.getType());
        Town town=towns.get(0);
        int index=0;
        for (int i = 0; i < town.getVakitler().size(); i++) {
            TimesOfDay timesOfDay=town.getVakitler().get(i);
            if(!timesOfDay.isOld()){
                index=i;
                break;
            }
        }
        toDay=town.getVakitler().get(index);
        if(index>0)
            toDay.setYesterDay(town.getVakitler().get(index-1));
        else {
            TimesOfDay yesterDay=getGson().fromJson(getGson().toJson(toDay),TimesOfDay.class);
            yesterDay.setDateToYesterDay();
            toDay.setYesterDay(yesterDay);
        }
        toDay.setToMorrow(town.getVakitler().get(index+1));
        toDay.setName(context.getString(R.string.umumi));



        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.text_miladi,toDay.isYatsiGecti()?toDay.getToMorrow().getMiladiTarihUzun():toDay.getMiladiTarihUzun());
        views.setTextViewText(R.id.text_hicri,toDay.isEveningNight()?toDay.getToMorrow().getHicriTarihUzun():toDay.getHicriTarihUzun());
        views.setTextViewText(R.id.text_ezani,toDay.isEveningNight()?toDay.getEzaniSaatWithoutSec():toDay.getYesterDay().getEzaniSaatWithoutSec());
        views.setTextViewText(R.id.text_kalan,toDay.getKalanWOsec());
        views.setTextViewText(R.id.text_e_yatsi,toDay.getYatsiEzani());
        views.setTextViewText(R.id.text_e_imsak,toDay.getImsakEzani());
        views.setTextViewText(R.id.text_e_gunes,toDay.getGunesEzani());
        views.setTextViewText(R.id.text_e_ogle,toDay.getOgleEzani());
        views.setTextViewText(R.id.text_e_ikindi,toDay.getIkindiEzani());
        views.setTextViewText(R.id.text_u_imsak,toDay.getImsak());
        views.setTextViewText(R.id.text_u_gunes,toDay.getGunes());
        views.setTextViewText(R.id.text_u_ogle,toDay.getOgle());
        views.setTextViewText(R.id.text_u_ikindi,toDay.getIkindi());
        views.setTextViewText(R.id.text_u_aksam,toDay.getAksam());
        views.setTextViewText(R.id.text_u_yatsi,toDay.getYatsi());
        views.setTextViewText(R.id.text_location,town.getIlceAdi());
//        views.setRemoteAdapter(R.id.text_kalan,new Intent(context, WidgetService.class));
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.layout.widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        Util.log("update=%s",System.currentTimeMillis()%60000);
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
            final Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            long interval = 60000;
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+(interval-(SystemClock.elapsedRealtime()%60000)), interval, pending);
        }catch (Exception e){
            e.printStackTrace();
        }
        Util.log("onUpdate asdasd");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

    }

    @Override
    public void onDisabled(Context context) {
        PendingIntent localPendingIntent = PendingIntent.getService(context, 0, new Intent(context, WidgetService.class), 0);
        try {
            ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).cancel(localPendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

