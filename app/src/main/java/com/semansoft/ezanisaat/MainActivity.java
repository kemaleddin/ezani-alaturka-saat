package com.semansoft.ezanisaat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.MyFragmentActivity;
import com.kemalettinsargin.mylib.Util;
import com.semansoft.ezanisaat.model.TimesOfDay;
import com.semansoft.ezanisaat.model.Town;

import java.util.List;


/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public class MainActivity extends MyFragmentActivity {//fragment yapıcan
    private static final int ADD_LOC_REQ_CODE = 1;
    private TextView textKalan,textMiladi,textHicri,textEzani;
    private LinearLayout vakitlerLinear;
    private List<Town> towns;
    private LayoutInflater layoutInflater;
    private Handler mHandler=new Handler(Looper.getMainLooper());
    private TimesOfDay toDay;
    private Runnable updateKalanRunnable=new Runnable() {
        @Override
        public void run() {
            textKalan.setText(toDay.getKalan());
            textEzani.setText(toDay.isEveningNight()?toDay.getEzaniSaat():toDay.getYesterDay().getEzaniSaat());
            mHandler.postDelayed(this,1000);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        createItems();
        load();
    }
    private void createItems(){
        layoutInflater=getLayoutInflater();
        TextView titleEzani,titleVakit;
        titleEzani=(TextView) getChild(R.id.text_ezani_tit);
        vakitlerLinear=(LinearLayout) getChild(R.id.vakitler_linear);
        titleVakit=(TextView) getChild(R.id.text_vaktin_cikmasi);
        textKalan= (TextView) getChild(R.id.text_kalan);
        textMiladi= (TextView) getChild(R.id.text_miladi);
        textHicri= (TextView) getChild(R.id.text_hicri);
        textEzani= (TextView) getChild(R.id.text_ezani);
        towns=getGson().fromJson(Util.getPref(this,C.KEY_LOCATIONS), new TypeToken<List<Town>>(){}.getType());
    }
    private void load(){
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
        textMiladi.setText(toDay.getMiladiTarihUzun());
        textHicri.setText(toDay.isEveningNight()?toDay.getToMorrow().getHicriTarihUzun():toDay.getHicriTarihUzun());
        textKalan.setText(toDay.getKalan());
        toDay.setName(getString(R.string.umumi));
        vakitlerLinear.addView(getRow(new TimesOfDay(getString(R.string.aksam),
                getString(R.string.yatsi),getString(R.string.imsak),getString(R.string.gunes),
                getString(R.string.ogle),getString(R.string.ikindi))));
        vakitlerLinear.addView(getRowEzani(toDay));
        vakitlerLinear.addView(getLine());
        vakitlerLinear.addView(getRow(new TimesOfDay(getString(R.string.imsak),getString(R.string.gunes),
                getString(R.string.ogle),getString(R.string.ikindi),getString(R.string.aksam),
                getString(R.string.yatsi))));
        vakitlerLinear.addView(getRow(toDay));
        vakitlerLinear.addView(getLine());
    }

    private View getLine(){
        View line=new View(this);
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getScale()));
        line.setBackgroundResource(R.color.white);
        line.setAlpha(.5f);
        int mar= (int) (5*getScale());
        ((LinearLayout.MarginLayoutParams)line.getLayoutParams()).setMargins(mar,0,mar, (int) (2.5f*getScale()));
        return line;
    }

    private View getRow(TimesOfDay tod){
        View row=layoutInflater.inflate(R.layout.day_times_row,vakitlerLinear,false);
        TextView
                textImsak   =(TextView) row.findViewById(R.id.text_imsak),
                textGunes   =(TextView) row.findViewById(R.id.text_gunes),
                textOgle    =(TextView) row.findViewById(R.id.text_ogle),
                textIkindi  =(TextView) row.findViewById(R.id.text_ikindi),
                textAksam   =(TextView) row.findViewById(R.id.text_aksam),
                textYatsi   =(TextView) row.findViewById(R.id.text_yatsi),
                textGun     =(TextView) row.findViewById(R.id.text_day);
        textGun     .setText(tod.getGun());
        textImsak  .setText(tod.getImsak());
        textGunes  .setText(tod.getGunes());
        textOgle   .setText(tod.getOgle());
        textIkindi .setText(tod.getIkindi());
        textAksam  .setText(tod.getAksam());
        textYatsi  .setText(tod.getYatsi());
        return row;
    }
    private View getRowEzani(TimesOfDay tod){
        View row=layoutInflater.inflate(R.layout.day_times_row,vakitlerLinear,false);
        TextView
                textImsak   =(TextView) row.findViewById(R.id.text_imsak),
                textGunes   =(TextView) row.findViewById(R.id.text_gunes),
                textOgle    =(TextView) row.findViewById(R.id.text_ogle),
                textIkindi  =(TextView) row.findViewById(R.id.text_ikindi),
                textAksam   =(TextView) row.findViewById(R.id.text_aksam),
                textYatsi   =(TextView) row.findViewById(R.id.text_yatsi),
                textGun     =(TextView) row.findViewById(R.id.text_day);

        textImsak .setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textGunes .setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textOgle  .setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textIkindi.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textAksam .setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textYatsi .setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textGun   .setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

        textGun     .setText(getString(R.string.ezani));
        textImsak  .setText(R.string.saat_sifir);
        textGunes  .setText(tod.getYatsiEzani());
        textOgle   .setText(tod.getImsakEzani());
        textIkindi .setText(tod.getGunesEzani());
        textAksam  .setText(tod.getOgleEzani());
        textYatsi  .setText(tod.getIkindiEzani());
        return row;
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
                startActivityForResult(new Intent(this,AddLocationsActivity.class),ADD_LOC_REQ_CODE);
                return true;
            case R.id.action_info:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(updateKalanRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }
}
