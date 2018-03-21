package com.semansoft.ezanisaat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.MyFragmentActivity;
import com.kemalettinsargin.mylib.Util;
import com.semansoft.ezanisaat.adapter.LocationsAdapter;
import com.semansoft.ezanisaat.model.Town;

import java.util.List;


/**
 * Written by "كمال الدّين صارغين"  on 21.12.2017.
 * و من الله توفیق
 */
public class LocationsActivity extends MyFragmentActivity implements View.OnClickListener,View.OnLongClickListener {
    private static final int ADD_REQ_CODE = 0;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private List<Town> towns;
    private LocationsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        findViewById(R.id.fab).setOnClickListener(this);
        recyclerView= (RecyclerView) getChild(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        towns= getGson().fromJson(Util.getPref(this,C.KEY_LOCATIONS),new TypeToken<List<Town>>(){}.getType());
        adapter=new LocationsAdapter(this,towns);
        adapter.setOnLongClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivityForResult(new Intent(this,AddLocationsActivity.class),ADD_REQ_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ADD_REQ_CODE&&resultCode==RESULT_OK){
            towns= getGson().fromJson(Util.getPref(this,C.KEY_LOCATIONS),new TypeToken<List<Town>>(){}.getType());
            adapter=new LocationsAdapter(this,towns);
            adapter.setOnLongClickListener(this);
            recyclerView.setAdapter(adapter);
            setResult(RESULT_OK);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onLongClick( final View v) {
        showYesNo(getString(R.string.sure_to_remove), "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             if(which==DialogInterface.BUTTON_POSITIVE){
                 towns.remove(v.getTag());
                 Util.savePref(LocationsActivity.this,C.KEY_LOCATIONS,getGson().toJson(towns));
                 adapter.remove(v.getTag());
                 setResult(RESULT_OK);
             }
            }
        });
        return false;
    }
}