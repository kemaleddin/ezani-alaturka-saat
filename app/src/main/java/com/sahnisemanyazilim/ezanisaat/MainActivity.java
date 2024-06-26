package com.sahnisemanyazilim.ezanisaat;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.BaseFragmentActivity;
import com.kemalettinsargin.mylib.Util;
import com.kemalettinsargin.mylib.ui.DepthPageTransformer;
import com.sahnisemanyazilim.ezanisaat.fragment.MainFragment;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;
import com.sahnisemanyazilim.ezanisaat.services.BigWidgetService;
import com.sahnisemanyazilim.ezanisaat.services.SaatWidgetService;
import com.sahnisemanyazilim.ezanisaat.services.UpdateTimesService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public class MainActivity extends BaseFragmentActivity {
    private static final int ADD_LOC_REQ_CODE = 1;
    private List<Town> towns, updatingTimes = new ArrayList<>();
    private ViewPager pager;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        createItems();
        checkTimes();
        checkAppWidget(this);
        scheduleJob();
    }



    private void checkTimes() {
        for (Town town : towns) {
            if (town.needUpdate()) {
                updatingTimes.add(town);
            }
        }
        if (updatingTimes.size() > 0)
            getSaatler(updatingTimes.get(0));
        else load();
    }

    private void checkAppWidget(Context context) {
        if (Util.getPrefs(this).getBoolean(SaatWidgetService.TAG, false)) {
            SaatWidgetService.Companion.scheduleWork(context);
        }
        if (Util.getPrefs(this).getBoolean(BigWidgetService.TAG, false)) {
            BigWidgetService.Companion.scheduleWork(context);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
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
                    Town newTown = town.getClone();
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

    private void scheduleJob() {
        UpdateTimesService.scheduleUpdateJob(this);
    }

}
