package com.kemalettinsargin.mylib.objects;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Written by "كمال الدّين صارغين"  on 5.11.2015.
 *و من الله توفیق
 *
 */
public class TypeFaces {
    public Typeface book, medium, bold,light;

    public TypeFaces createTypefaces(Context context) {
       /* bold        = Typeface.createFromAsset(context.getAssets(),"Gotham-Bold.otf");
        medium      = Typeface.createFromAsset(context.getAssets(),"Gotham-Medium.otf");
        book = Typeface.createFromAsset(context.getAssets(),"Gotham-Book.otf");
        light = Typeface.createFromAsset(context.getAssets(),"Gotham-Light.otf");*/
        return this;
    }
}

