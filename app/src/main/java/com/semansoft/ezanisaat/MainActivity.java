package com.semansoft.ezanisaat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.MyFragmentActivity;
import com.kemalettinsargin.mylib.Util;
import com.kemalettinsargin.mylib.ui.DepthPageTransformer;
import com.semansoft.ezanisaat.fragment.MainFragment;
import com.semansoft.ezanisaat.model.Town;

import java.util.List;


/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public class MainActivity extends MyFragmentActivity {
    private static final int ADD_LOC_REQ_CODE = 1;
    private List<Town> towns;
    private ViewPager pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        createItems();
        load();
    }

    private void createItems() {
        pager = (ViewPager) getChild(R.id.pager);
        towns = getGson().fromJson(Util.getPref(this, C.KEY_LOCATIONS), new TypeToken<List<Town>>() {
        }.getType());
        if(Util.hasPref(this,C.KEY_ACTIVE)){
            String id=Util.getPref(this,C.KEY_ACTIVE);
            for (Town town : towns) {
                town.setActive(id);
            }
        }else towns.get(0).setActive(true);
    }

    private void load() {
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if(position<-1||position>1)return;
                View titleEzani,titleVakit,vakitlerLinear,textKalan,textTown,textMiladi,textHicri,textEzani;
                titleEzani= page.findViewById(R.id.text_ezani_tit);
                vakitlerLinear= (View) getChild(R.id.vakitler_linear).getParent();
                titleVakit=page.findViewById(R.id.text_vaktin_cikmasi);
                textKalan= page.findViewById(R.id.text_kalan);
                textTown=  page.findViewById(R.id.text_town);
                textMiladi= page.findViewById(R.id.text_miladi);
                textHicri= page.findViewById(R.id.text_hicri);
                textEzani= page.findViewById(R.id.text_ezani);

                vakitlerLinear.setTranslationX(getWidth()*position);
                Util.log("position=%s width=%s",position,getWidth());

            }
        });
        pager.setPageTransformer(false,new DepthPageTransformer());
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
                Town town=towns.get(pager.getCurrentItem());
                town.setActive(true);
                Util.savePref(this,C.KEY_ACTIVE,town.getIlceID());
                sendBroadcast(new Intent(MainFragment.ACTION_ACTIVE_LOCATION_CHANGED));
                return true;
            case R.id.action_info:
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
            load();
        }
    }
}
