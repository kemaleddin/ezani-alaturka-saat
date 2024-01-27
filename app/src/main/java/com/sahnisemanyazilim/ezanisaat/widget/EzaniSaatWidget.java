package com.sahnisemanyazilim.ezanisaat.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.RemoteViews;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.Util;
import com.sahnisemanyazilim.ezanisaat.BuildConfig;
import com.sahnisemanyazilim.ezanisaat.C;
import com.sahnisemanyazilim.ezanisaat.MainActivity;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;
import com.sahnisemanyazilim.ezanisaat.services.SaatWidgetService;
import com.sahnisemanyazilim.ezanisaat.services.UpdateTimesService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of App Widget functionality.
 */
public class EzaniSaatWidget extends AppWidgetProvider {

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
        if (index < 0) {
            UpdateTimesService.scheduleUpdateJob(context);
            return;
        }
        if (index > 0)
            toDay.setYesterDay(town.getTimesOfDays().get(index - 1));
        else {
            TimesOfDay yesterDay = getGson().fromJson(getGson().toJson(toDay), TimesOfDay.class);
            yesterDay.setDateToYesterDay();
            toDay.setYesterDay(yesterDay);
        }
        toDay.setToMorrow(town.getTimesOfDays().get(index + 1));
        toDay.setName(context.getString(R.string.umumi));


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.saat_widget);
        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, PendingIntent.FLAG_MUTABLE);
        views.setOnClickPendingIntent(R.id.saat_widget, configPendingIntent);
        views.setTextViewText(R.id.text_saat_widget_kalan, toDay.getKalanWOsec());
        views.setTextViewText(R.id.text_hicri_tarih, toDay.isEveningNight() ? toDay.getToMorrow().getHicriTarihUzunGunlu() : toDay.getHicriTarihUzunGunlu());
        views.setTextViewText(R.id.text_ezani_saat, TimesOfDay.getSaat12(toDay.isEveningNight() ? toDay.getEzaniSaatWithoutSec() : toDay.getYesterDay().getEzaniSaatWithoutSec()));


        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    private static Gson getGson() {
        return Util.getGson();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (appWidgetIds == null) {
            super.onUpdate(context, appWidgetManager, null);
            return;
        }

        PowerManager.WakeLock wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "Ezani Saat : WAKE LOCK");
        wakeLock.acquire(60000);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        wakeLock.release();

        SaatWidgetService.Companion.scheduleWork(context);
        Util.log("onUpdate @EzaniSaatWidget");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        SaatWidgetService.Companion.scheduleWork(context);
        Util.getPrefs(context).edit().putBoolean(SaatWidgetService.TAG, true).apply();
        super.onEnabled(context);

    }


    @Override
    public void onDisabled(Context context) {
        SaatWidgetService.Companion.disableWork(context);
        Util.getPrefs(context).edit().putBoolean(SaatWidgetService.TAG, false).apply();
    }

}

