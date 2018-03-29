package com.semansoft.ezanisaat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
public class LocationsActivity extends MyFragmentActivity implements View.OnClickListener {
    private static final int ADD_REQ_CODE = 0;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private List<Town> towns;
    private LocationsAdapter adapter;
    private Town deletedTown;
    private int removed_pos;

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
        recyclerView.setAdapter(adapter);
      /*  ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(LocationsActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(LocationsActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                adapter.remove(position);

            }
        };
        ItemTouchHelper helper=new ItemTouchHelper(simpleItemTouchCallback);
        helper.attachToRecyclerView(recyclerView);*/
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)

        {

            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(LocationsActivity.this, R.drawable.ic_add_white_48dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Toast.makeText(LocationsActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                    Toast.makeText(LocationsActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar
                        .make(getRoot(),getString(R.string.deleted_suc), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                adapter.add(deletedTown,removed_pos);
                                towns.add(removed_pos,deletedTown);
                                Util.savePref(getApplicationContext(),C.KEY_LOCATIONS,getGson().toJson(towns));
                                if(deletedTown.isActive()){
                                    Util.savePref(LocationsActivity.this,C.KEY_ACTIVE,deletedTown.getIlceID());
                                }
                                setResult(RESULT_OK);
                            }
                        });

                snackbar.show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
               deletedTown= adapter.remove(position);
                removed_pos=position;
                if(Util.hasPref(LocationsActivity.this,C.KEY_ACTIVE)){
                    String id=Util.getPref(LocationsActivity.this,C.KEY_ACTIVE);
                    if(deletedTown.getIlceID().equals(id)){
                        deletedTown.setActive(true);
                        Util.removePref(LocationsActivity.this,C.KEY_ACTIVE);
                    }
                }
                towns.remove(position);
                Util.savePref(getApplicationContext(),C.KEY_LOCATIONS,getGson().toJson(towns));
                setResult(RESULT_OK);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                int xMarkLeft = 0;
                int xMarkRight = 0;
                int xMarkTop = (int) (itemView.getTop() + (itemView.getHeight() - getHeight()) / 2);
                int xMarkBottom = (int) (xMarkTop + getHeight());
                if (dX < 0) {
                    xMarkLeft = (int) (itemView.getRight() - xMarkMargin - getWidth());
                    xMarkRight = itemView.getRight() - xMarkMargin;
                } else {
                    xMarkLeft = itemView.getLeft() + xMarkMargin;
                    xMarkRight = (int) (itemView.getLeft() + xMarkMargin + getWidth());
                }
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);
                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);






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
            recyclerView.setAdapter(adapter);
            setResult(RESULT_OK);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}