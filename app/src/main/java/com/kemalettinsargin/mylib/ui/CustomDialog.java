package com.kemalettinsargin.mylib.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.semansoft.ezanisaat.R;


public class CustomDialog extends Dialog {
    private View view;
    public CustomDialog(Context context, View view) {
        super(context, R.style.Theme_UserDialog);
        this.view=view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(view);
    }

}