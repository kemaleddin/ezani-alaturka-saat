package com.sahnisemanyazilim.ezanisaat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.crashlytics.android.Crashlytics;
import com.kemalettinsargin.mylib.MyFragmentActivity;
import com.kemalettinsargin.mylib.Util;
import io.fabric.sdk.android.Fabric;


public class SplashActivity extends MyFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
            if(!Util.hasPref(SplashActivity.this,C.KEY_LOCATIONS)||"[]".equals(Util.getPref(SplashActivity.this,C.KEY_LOCATIONS)))
                startActivity(new Intent(SplashActivity.this,AddLocationsActivity.class));
            else
                startActivity(new Intent(SplashActivity.this,MainActivity.class));

            finish();
            }
        },2000);
    }

}
