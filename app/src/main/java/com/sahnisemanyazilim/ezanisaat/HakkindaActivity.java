package com.sahnisemanyazilim.ezanisaat;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.kemalettinsargin.mylib.MyFragmentActivity;


public class HakkindaActivity extends MyFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkinda);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
