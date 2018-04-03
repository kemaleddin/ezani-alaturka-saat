package com.sahnisemanyazilim.ezanisaat.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.Util;
import com.kemalettinsargin.mylib.fragments.MyFragment;
import com.sahnisemanyazilim.ezanisaat.C;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.TimesOfDay;
import com.sahnisemanyazilim.ezanisaat.model.Town;

import java.util.ArrayList;
import java.util.List;


/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public class MainFragment extends MyFragment {
    private TextView textKalan, textMiladi, textHicri, textEzani, textTown;
    private LinearLayout vakitlerLinear;
    private LayoutInflater layoutInflater;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TimesOfDay toDay;
    private Town town;
    public static final String ACTION_ACTIVE_LOCATION_CHANGED = "com.sahnisemanyazilim.ezanisaat.ACTION_LOCATION_CHANGED";
    private Runnable updateKalanRunnable = new Runnable() {
        @Override
        public void run() {
            textKalan.setText(toDay.getKalan());
            textEzani.setText(toDay.isEveningNight() ? toDay.getEzaniSaat() : toDay.getYesterDay().getEzaniSaat());
            mHandler.postDelayed(this, 1000);
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_ACTIVE_LOCATION_CHANGED.equals(intent.getAction())) {
                town.setActive(Util.getPref(getActivity(), C.KEY_ACTIVE));
                if (town.isActive())
                    textTown.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                else textTown.setTextColor(Color.WHITE);
            }
        }
    };

    public static MainFragment newInstance(Town town) {
        Bundle args = new Bundle();
        args.putInt(KEY_RES_ID, R.layout.fragment_main);
        args.putParcelable(C.KEY_TOWN, town);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void createItems() {
        town = getArguments().getParcelable(C.KEY_TOWN);
        layoutInflater = getLayoutInflater();
        TextView titleEzani, titleVakit;
        titleEzani = (TextView) getChild(R.id.text_ezani_tit);
        vakitlerLinear = (LinearLayout) getChild(R.id.vakitler_linear);
        titleVakit = (TextView) getChild(R.id.text_vaktin_cikmasi);
        textKalan = (TextView) getChild(R.id.text_kalan);
        textTown = (TextView) getChild(R.id.text_town);
        textMiladi = (TextView) getChild(R.id.text_miladi);
        textHicri = (TextView) getChild(R.id.text_hicri);
        textEzani = (TextView) getChild(R.id.text_ezani);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load();
    }

    private void load() {
        vakitlerLinear.removeAllViews();
        textTown.setText(town.getIlceAdi());
        if (town.isActive())
            textTown.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        int index = 0;
        for (int i = 0; i < town.getVakitler().size(); i++) {
            TimesOfDay timesOfDay = town.getVakitler().get(i);
            if (!timesOfDay.isOld()) {
                index = i;
                break;
            }
        }
        toDay = town.getVakitler().get(index);
        if (index > 0)
            toDay.setYesterDay(town.getVakitler().get(index - 1));
        else {
            TimesOfDay yesterDay = getGson().fromJson(getGson().toJson(toDay), TimesOfDay.class);
            yesterDay.setDateToYesterDay();
            toDay.setYesterDay(yesterDay);
        }
        toDay.setToMorrow(town.getVakitler().get(index + 1));
        textMiladi.setText(toDay.getMiladiTarihUzun());
        textHicri.setText(toDay.isEveningNight() ? toDay.getToMorrow().getHicriTarihUzun() : toDay.getHicriTarihUzun());
        textKalan.setText(toDay.getKalan());
        toDay.setName(getString(R.string.umumi));
        TimesOfDay title=new TimesOfDay(getString(R.string.imsak), getString(R.string.gunes),
                getString(R.string.ogle), getString(R.string.ikindi), getString(R.string.aksam),
                getString(R.string.yatsi));
        //Ezani
        vakitlerLinear.addView(getRowEzani(title));
        vakitlerLinear.addView(getRowEzani(toDay));
        vakitlerLinear.addView(getLine());
        //Umumi
        vakitlerLinear.addView(getRow(title));
        vakitlerLinear.addView(getRow(toDay));
        vakitlerLinear.addView(getLine());
    }

    private View getLine() {
        View line = new View(getActivity());
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getScale()));
        line.setBackgroundResource(R.color.colorPrimaryDark);
        line.setAlpha(.5f);
        int mar = (int) (5 * getScale());
        ((LinearLayout.MarginLayoutParams) line.getLayoutParams()).setMargins(mar, 0, mar, (int) (2.5f * getScale()));
        return line;
    }

    private View getRow(TimesOfDay tod) {
        View row = layoutInflater.inflate(R.layout.day_times_row, vakitlerLinear, false);
        TextView
                textImsak = (TextView) row.findViewById(R.id.text_imsak),
                textGunes = (TextView) row.findViewById(R.id.text_gunes),
                textOgle = (TextView) row.findViewById(R.id.text_ogle),
                textIkindi = (TextView) row.findViewById(R.id.text_ikindi),
                textAksam = (TextView) row.findViewById(R.id.text_aksam),
                textYatsi = (TextView) row.findViewById(R.id.text_yatsi),
                textGun = (TextView) row.findViewById(R.id.text_day);
        textImsak.setTextColor(Color.WHITE);
        textGunes.setTextColor(Color.WHITE);
        textOgle.setTextColor(Color.WHITE);
        textIkindi.setTextColor(Color.WHITE);
        textAksam.setTextColor(Color.WHITE);
        textYatsi.setTextColor(Color.WHITE);

        textGun.setText(tod.getGun());
        textImsak.setText(tod.getChkImsak());
        textGunes.setText(tod.getChkGunes());
        textOgle.setText(tod.getChkOgle());
        textIkindi.setText(tod.getChkIkindi());
        textAksam.setText(tod.getChkAksam());
        textYatsi.setText(tod.getChkYatsi());
        try {
            ((TextView) row.findViewById(toDay.getNextId())).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    private View getRowEzani(TimesOfDay tod) {
        View row = layoutInflater.inflate(R.layout.day_times_row, vakitlerLinear, false);
        TextView
                textImsak = (TextView) row.findViewById(R.id.text_imsak),
                textGunes = (TextView) row.findViewById(R.id.text_gunes),
                textOgle = (TextView) row.findViewById(R.id.text_ogle),
                textIkindi = (TextView) row.findViewById(R.id.text_ikindi),
                textAksam = (TextView) row.findViewById(R.id.text_aksam),
                textYatsi = (TextView) row.findViewById(R.id.text_yatsi),
                textGun = (TextView) row.findViewById(R.id.text_day);

        textImsak.setTextColor(Color.WHITE);
        textGunes.setTextColor(Color.WHITE);
        textOgle.setTextColor(Color.WHITE);
        textIkindi.setTextColor(Color.WHITE);
        textAksam.setTextColor(Color.WHITE);
        textYatsi.setTextColor(Color.WHITE);

        ViewGroup vg = (ViewGroup) row;
        vg.removeView(textYatsi);
        vg.removeView(textAksam);
        vg.addView(textAksam, 1);
        vg.addView(textYatsi, 2);

        textImsak.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textGunes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textOgle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textIkindi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textAksam.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textYatsi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textGun.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        textGun.setText(getString(R.string.ezani));
        if (tod.getAksam().equals(getString(R.string.aksam))) {
            textGun.setText(tod.getGun());
            textImsak.setText(tod.getChkImsak());
            textGunes.setText(tod.getChkGunes());
            textOgle.setText(tod.getChkOgle());
            textIkindi.setText(tod.getChkIkindi());
            textAksam.setText(tod.getChkAksam());
            textYatsi.setText(tod.getChkYatsi());
            textImsak.setTextSize(TypedValue.COMPLEX_UNIT_SP,   14);
            textGunes.setTextSize(TypedValue.COMPLEX_UNIT_SP,   14);
            textOgle.setTextSize(TypedValue.COMPLEX_UNIT_SP,    14);
            textIkindi.setTextSize(TypedValue.COMPLEX_UNIT_SP,  14);
            textAksam.setTextSize(TypedValue.COMPLEX_UNIT_SP,   14);
            textYatsi.setTextSize(TypedValue.COMPLEX_UNIT_SP,   14);
            textGun.setTextSize(TypedValue.COMPLEX_UNIT_SP,     14);
        } else {
            textAksam.setText(R.string.saat_sifir);
            textYatsi.setText(tod.getYatsiEzani());
            textImsak.setText(tod.getImsakEzani());
            textGunes.setText(tod.getGunesEzani());
            textOgle.setText(tod.getOgleEzani());
            textIkindi.setText(tod.getIkindiEzani());
        }
        try {
            ((TextView) row.findViewById(toDay.getNextId())).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            getActivity().registerReceiver(mReceiver, new IntentFilter(ACTION_ACTIVE_LOCATION_CHANGED));

            String locID = Util.getPref(getActivity(), C.KEY_ACTIVE);
            town.setActive(locID);
            textTown.setTextColor(ContextCompat.getColor(getActivity(), town.isActive() ? R.color.colorPrimary : R.color.white));
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(updateKalanRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onStop() {
        try {
            getActivity().unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
        super.onStop();
    }
}
