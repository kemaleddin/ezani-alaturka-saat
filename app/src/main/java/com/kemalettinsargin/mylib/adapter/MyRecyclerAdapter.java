package com.kemalettinsargin.mylib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.kemalettinsargin.mylib.LocaleHelper;
import com.kemalettinsargin.mylib.objects.TypeFaces;


/**
 * Written by "كمال الدّين صارغين"  on 17.12.2015.
 *و من الله توفیق
 *
 */
public abstract class MyRecyclerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    private float mheight, mweight,scale;
    public TypeFaces typeFaces=new TypeFaces();
    private Context context;
    private LayoutInflater mInflater;
    public boolean isEn;
    public MyRecyclerAdapter(Context context) {
        this.context=context;
        setSpecialParams();
        isEn= LocaleHelper.isLocaleEn(context);
    }

    private void setSpecialParams(){
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        mheight = dm.heightPixels;
        mweight = dm.widthPixels;
        scale=dm.density;
        typeFaces.createTypefaces(context);
        mInflater= LayoutInflater.from(context);
    }
    public float getHeight() {
        return mheight;
    }

    public float getWidth() {
        return mweight;
    }

    public float getScale() {
        return scale;
    }

    public Context getContext() {
        return context;
    }
    public LayoutInflater getLayoutInflater(){
        return mInflater;
    }


}
