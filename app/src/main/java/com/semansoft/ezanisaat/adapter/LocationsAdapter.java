package com.semansoft.ezanisaat.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kemalettinsargin.mylib.adapter.MyRecyclerAdapter;
import com.semansoft.ezanisaat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Written by "كمال الدّين صارغين"  on 4.07.2017.
 * و من الله توفیق
 */

public class LocationsAdapter extends MyRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<? extends Object> items;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;

    public LocationsAdapter(Activity context, List<? extends Object> items) {
        super(context);
        mOnClickListener = (View.OnClickListener) context;
        if (items == null) items = new ArrayList<>();
        this.items = new ArrayList<>(items);
        mInflater = context.getLayoutInflater();
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

    public void setItems(List<? extends Object> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    class Vh extends RecyclerView.ViewHolder {
        private TextView text;

        public Vh(View itemView) {
            super(itemView);
            itemView.setOnClickListener(mOnClickListener);
            text = (TextView) gc(R.id.text);
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
