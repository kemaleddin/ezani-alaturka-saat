package com.semansoft.ezanisaat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kemalettinsargin.mylib.MyFragmentActivity;


/**
 * Written by "كمال الدّين صارغين"  on 21.12.2017.
 * و من الله توفیق
 */
public class LocationsActivity extends MyFragmentActivity implements View.OnClickListener {
    private FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        findViewById(R.id.fab).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:

                break;
        }
    }
}