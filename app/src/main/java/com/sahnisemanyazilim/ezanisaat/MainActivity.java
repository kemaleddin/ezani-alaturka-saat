package com.sahnisemanyazilim.ezanisaat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.MyFragmentActivity;
import com.kemalettinsargin.mylib.Util;
import com.kemalettinsargin.mylib.ui.DepthPageTransformer;
import com.sahnisemanyazilim.ezanisaat.fragment.MainFragment;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;
import com.sahnisemanyazilim.ezanisaat.services.SaatWidgetService;
import com.sahnisemanyazilim.ezanisaat.services.BigWidgetService;
import com.sahnisemanyazilim.ezanisaat.widget.EzaniBigWidget;
import com.sahnisemanyazilim.ezanisaat.widget.EzaniSaatWidget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public class MainActivity extends MyFragmentActivity {
    private static final int ADD_LOC_REQ_CODE = 1;
    private List<Town> towns, updatingTimes = new ArrayList<>();
    private ViewPager pager;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        createItems();
        checkTimes();
        checkAppWidget(this);
    }

    private void checkTimes(){
        for (Town town : towns) {
            if(town.needUpdate()){
                updatingTimes.add(town);
            }
        }
        if (updatingTimes.size() > 0)
            getSaatler(updatingTimes.get(0));
        else load();
    }

    private void checkAppWidget(Context context) {
        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, EzaniBigWidget.class));
            if (appWidgetIds.length == 0) return;
            final Intent intent = new Intent(context, BigWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            alarm.set(AlarmManager.RTC_WAKEUP,new Date().getTime()+C.interval_1,pending);
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, new Date().getTime() + (interval - (new Date().getTime() % 60000)), interval, pending);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, EzaniSaatWidget.class));
            if (appWidgetIds.length == 0) return;
            final Intent intent = new Intent(context, SaatWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            alarm.set(AlarmManager.RTC_WAKEUP,new Date().getTime()+C.interval_1,pending);
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, new Date().getTime() + (interval - (new Date().getTime() % 60000)), interval, pending);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createItems() {
        pager = (ViewPager) getChild(R.id.pager);
        towns = getGson().fromJson(Util.getPref(this, C.KEY_LOCATIONS), new TypeToken<List<Town>>() {
        }.getType());

        for (Town town : towns) {
            int index = town.getTimesOfDays().indexOf(TimesOfDay.getToDay());
            if (index > 1) {
                town.setVakitler(town.getTimesOfDays().subList(index - 1, town.getTimesOfDays().size()));
            }
        }
        Util.savePref(this, C.KEY_LOCATIONS, getGson().toJson(towns));

        if (Util.hasPref(this, C.KEY_ACTIVE)) {
            String id = Util.getPref(this, C.KEY_ACTIVE);
            for (Town town : towns) {
                town.setActive(id);
            }
        } else towns.get(0).setActive(true);


    }

    private void setSaatler(Town newTown) {
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
        Util.savePref(this, C.KEY_LOCATIONS, getGson().toJson(towns));
        int townIndex = updatingTimes.indexOf(newTown) + 1;
        if (townIndex < updatingTimes.size())
            getSaatler(updatingTimes.get(townIndex));
        else load();
    }

    private void load() {
        pager.setPageTransformer(false, new DepthPageTransformer());
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.add_location:
                startActivityForResult(new Intent(this, LocationsActivity.class), ADD_LOC_REQ_CODE);
                return true;
            case R.id.gecerli_konum:
                for (Town town : towns) {
                    town.setActive(false);
                }
                Town town = towns.get(pager.getCurrentItem());
                town.setActive(true);
                Util.savePref(this, C.KEY_ACTIVE, town.getIlceID());
                sendBroadcast(new Intent(MainFragment.ACTION_ACTIVE_LOCATION_CHANGED));
                return true;
            case R.id.action_info:
                startActivity(new Intent(this, HakkindaActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainFragment.newInstance(towns.get(position));
        }

        @Override
        public int getCount() {
            return towns.size();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && requestCode == ADD_LOC_REQ_CODE) {
            createItems();
            checkTimes();
            if (towns.size() == 1) {
                checkAppWidget(this);
            }
        }
    }

    public void getSaatler(final Town town) {
        setLoadingMessage(getString(R.string.updating_times, town.getIlceAdi()));
        showLoading();
        getApi().getTimes(town.getIlceID()).enqueue(new Callback<List<TimesOfDay>>() {
            @Override
            public void onResponse(Call<List<TimesOfDay>> call, Response<List<TimesOfDay>> response) {
                if (isDestroyed()) return;
                dismissLoading();
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
//                    town.setVakitler(response.body());
                    Town newTown=town.getClone();
                    newTown.setVakitler(response.body());
                    setSaatler(newTown);
                } else onFailure(null, null);
            }

            @Override
            public void onFailure(Call<List<TimesOfDay>> call, Throwable t) {
                if (isDestroyed()) return;
                dismissLoading();
                showErrorDialogRetry(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            getSaatler(town);
                        } else finish();
                    }
                });
            }
        });
    }


}
