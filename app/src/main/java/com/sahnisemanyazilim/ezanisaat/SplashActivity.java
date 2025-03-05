package com.sahnisemanyazilim.ezanisaat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.kemalettinsargin.mylib.BaseFragmentActivity;
import com.kemalettinsargin.mylib.Util;


/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public class SplashActivity extends BaseFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        if(BuildConfig.DEBUG)
            Util.showToast(this,"dp değeri="+getResources().getDimension(R.dimen.status_padding)+"\n api v="+ Build.VERSION.SDK_INT);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
        if(!Util.hasPref(SplashActivity.this,C.KEY_LOCATIONS)||"[]".equals(Util.getPref(SplashActivity.this,C.KEY_LOCATIONS)))
            startActivity(new Intent(SplashActivity.this,AddLocationsActivity.class));
        else
            startActivity(new Intent(SplashActivity.this,MainActivity.class));

        finish();
        },2000);
    }

}
