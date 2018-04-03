package com.sahnisemanyazilim.ezanisaat.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kemalettinsargin.mylib.adapter.MyRecyclerAdapter;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.model.Town;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Written by "كمال الدّين صارغين"  on 4.07.2017.
 * و من الله توفیق
 */

public class LocationsAdapter extends MyRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<Object> items;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private View.OnLongClickListener mOnLongClickListener;

    public LocationsAdapter(Activity context, List<? extends Object> items) {
        super(context);
        mOnClickListener = (View.OnClickListener) context;
        if (items == null) items = new ArrayList<>();
        this.items = new ArrayList<>(items);
        mInflater = context.getLayoutInflater();
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Vh(mInflater.inflate(R.layout.locations_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((Vh) holder).onBind(position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(Object[] items) {
        this.items = Arrays.asList(items);
        notifyDataSetChanged();
    }

    public void remove(Object object) {
        int index=items.indexOf(object);
        items.remove(object);
        notifyItemRemoved(index);

    }
    public Town remove(int pos) {
        Town town= (Town) items.get(pos);
        items.remove(pos);
        notifyItemRemoved(pos);
        return town;
    }
    public void add(Object town,int pos){
        items.add(pos,town);
        notifyItemInserted(pos);
    }


    class Vh extends RecyclerView.ViewHolder {
        private TextView text;

        public Vh(View itemView) {
            super(itemView);
            itemView.setOnClickListener(mOnClickListener);
            if(mOnLongClickListener!=null){
                itemView.setLongClickable(true);
                itemView.setOnLongClickListener(mOnLongClickListener);
            }
            text = (TextView) gc(R.id.text_location_item);
        }

        void onBind(int pos) {
            itemView.setTag(items.get(pos));
            text.setText(text.getTag().toString());
        }

        View gc(int id) {
            return itemView.findViewById(id);
        }
    }

}
