package com.semansoft.ezanisaat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViewsService;

import com.kemalettinsargin.mylib.Util;

/**
 * WidgetService is the {@link RemoteViewsService} that will return our RemoteViewsFactory
 */
public class WidgetService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        for (int allWidgetId : allWidgetIds) {
            Util.log("widget_id=%s",allWidgetId);
            CollectionWidget.updateAppWidget(this,appWidgetManager,allWidgetId);
        }
        Util.log("service");

        /*try {
            ((AlarmManager)getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, 60000L, PendingIntent.getService(this, 0, new Intent(this, WidgetService.class), 0));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
