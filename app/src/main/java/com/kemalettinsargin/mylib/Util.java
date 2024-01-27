package com.kemalettinsargin.mylib;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kemalettinsargin.mylib.customListeners.OnInputListener;
import com.kemalettinsargin.mylib.ui.AlertViewHolder;
import com.kemalettinsargin.mylib.ui.CustomDialog;
import com.sahnisemanyazilim.ezanisaat.BuildConfig;
import com.sahnisemanyazilim.ezanisaat.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Written by "كمال الدّين صارغين"  on 06.12.2014.
 * و من الله توفیق
 */
public class Util {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        return gson;
    }


    public static float getImageHeight(float oW, float oH, float nW) {

        return nW * oH / oW;
    }

    public static float getImageWidth(float oW, float oH, float nH) {
        return nH * oW / oH;
    }

    public static boolean hasHoneyComb() {
        return Build.VERSION.SDK_INT > 10;
    }

    public static boolean hasJELLY_BEAN() {
        return Build.VERSION.SDK_INT > 15;
    }

    public static boolean hasJELLY_BEAN_MR2() {
        return Build.VERSION.SDK_INT > 18;
    }

    public static int getstatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static String md5(String input) {
        StringBuilder res = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(input.getBytes());
            byte[] md5 = algorithm.digest();
            String tmp = "";
            for (int i = 0; i < md5.length; i++) {
                tmp = (Integer.toHexString(0xFF & md5[i]));
                if (tmp.length() == 1) {
                    res.append("0").append(tmp);
                } else {
                    res.append(tmp);
                }
            }
        } catch (NoSuchAlgorithmException ex) {
        }
        return res.toString();
    }

    public static boolean isStatusBarAtTop(Activity act) {


        Window window = act.getWindow();


        Rect rect = new Rect();

        window.getDecorView().getWindowVisibleDisplayFrame(rect);
//		View ourView = window.findViewById(Window.ID_ANDROID_CONTENT);

        //	Log.d("Menu",
        //            "Window Top: " + ourView.getTop() + ", " + ourView.getBottom()
        //                     + ", " + ourView.getLeft() + ", " + ourView.getRight());
        Log.d("Menu", "Decor View Dimensions" + rect.flattenToString());

        return rect.bottom == act.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getStatusBarHeight(Activity act) {


        Window window = act.getWindow();


        Rect rect = new Rect();

        window.getDecorView().getWindowVisibleDisplayFrame(rect);
//		View ourView = window.findViewById(Window.ID_ANDROID_CONTENT);

//		Log.d("Menu",
//				"Window Top: " + ourView.getTop() + ", " + ourView.getBottom()
//						+ ", " + ourView.getLeft() + ", " + ourView.getRight());
//		Log.d("Menu", "Decor View Dimensions" + rect.flattenToString());

        return rect.top;
    }


    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideKeyboard(Activity context) {
        try {
            hideKeyboard(context, context.getCurrentFocus());
        } catch (Exception e) {
        }
    }


    public static boolean isInternetAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if(cm != null){
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) return false;
        try {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } catch (Exception e) {
            return false;
        }
    }

    public static int getHeightForWrapContent(View view) {
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(widthMeasureSpec, heightMeasureSpec);
        int height = view.getMeasuredHeight();
        log("wrapped_height=%s", height);
        return height;
    }

    public static int getWidthForWrapContent(View view) {
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(widthMeasureSpec, heightMeasureSpec);
        int width = view.getMeasuredWidth();
        log("wrap_content=%s", width);
        return width;
    }

    public static int getWidthForMatchParent(View view) {
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        int width = view.getMeasuredWidth();
        log("match_parent=%s", width);
        return width;
    }

    public static void showToast(Context context, String msg) {
        TextView v = (TextView) LayoutInflater.from(context).inflate(R.layout.toast_view, null, false);
        v.setText(msg);
//        v.setTypeface(new TypeFaces().createTypefaces(context).book);
        Toast toast = new Toast(context.getApplicationContext());
        //toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(v);

        toast.show();
    }

    public static void log(String msg) {
       if (BuildConfig.DEBUG)
            Log.d("mylib", msg);
    }

    public static void log(String msg, Object... format) {
        if (BuildConfig.DEBUG)
            Log.d("mylib", String.format(msg, format));
    }

    public static float getComplexUnitSp(int i, DisplayMetrics metrics) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, i, metrics);
    }

    public static float getComplexUnitDp(int i, DisplayMetrics metrics) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, metrics);
    }

    public static void share(Context context, String url) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, url);
        context.startActivity(Intent.createChooser(sharingIntent, "Şununla Paylaş:"));
    }

    public static void composeEmail(String[] addresses, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "E-posta Gönder:"));
        } else {
            Toast.makeText(context, "E-posta uygulaması bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }


    public static void savePref(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static SharedPreferences.Editor editPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void removePref(Context context, String key) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).apply();
    }

    public static String getPref(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    public static long getLongPref(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0);
    }

    public static boolean hasPref(Context context, String keyUser) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(keyUser);
    }

    public static AlertViewHolder showAlert(Context context, String title, String message, String btnPos, String btnNeg, DialogInterface.OnClickListener listener) {
        View mView = LayoutInflater.from(context).inflate(R.layout.alert_layout, null);
        CustomDialog dialog = new CustomDialog(context, mView);
        AlertViewHolder alertView = new AlertViewHolder(mView);
        alertView.setDialog(dialog)
                .setMessage(message)
                .setPositiveButton(btnPos)
                .setNegativeButton(btnNeg)
                .setDialogListener(listener)
                .setTitle(title);
        dialog.show();
        return alertView;
    }

    public static AlertViewHolder showAlertSifre(Activity context, String btnPos, String btnNeg, OnInputListener onInputListener) {
        View mView = LayoutInflater.from(context).inflate(R.layout.alert_layout, null);
        CustomDialog dialog = new CustomDialog(context, mView);
        AlertViewHolder alertView = new AlertViewHolder(mView);
        alertView.setDialog(dialog)
                .setPositiveButton(btnPos)
                .setNegativeButton(btnNeg)
                .setOnInputListener(onInputListener)
                .setType(AlertViewHolder.TYPE_EMAIL);
        dialog.show();
        return alertView;
    }

    public static void showAlertErr(Context context, String title, String message, String btnPos, String btnNeg, DialogInterface.OnClickListener listener) {
        View mView = LayoutInflater.from(context).inflate(R.layout.alert_layout, null);
        Dialog dialog = new CustomDialog(context, mView);
        AlertViewHolder alertView = new AlertViewHolder(mView);
        alertView.setDialog(dialog)
                .setMessage(message)
                .setPositiveButton(btnPos)
                .setNegativeButton(btnNeg)
                .setDialogListener(listener)
                .setTitle(title)
                .setType(AlertViewHolder.TYPE_ERROR);
        dialog.show();
    }

    public static void showAlertInput(Context context, String title, String message, String btnPos, String btnNeg, DialogInterface.OnClickListener listener, OnInputListener onInputListener) {
        View mView = LayoutInflater.from(context).inflate(R.layout.alert_layout, null);
        Dialog dialog = new CustomDialog(context, mView);
        AlertViewHolder alertView = new AlertViewHolder(mView);
        alertView.setDialog(dialog)
                .setMessage(message)
                .setPositiveButton(btnPos)
                .setNegativeButton(btnNeg)
                .setDialogListener(listener)
                .setType(AlertViewHolder.TYPE_INPUT)
                .setOnInputListener(onInputListener)
                .setTitle(title);
        dialog.show();
    }

    public static void showAlert(Context context, Spanned message, String title, String btnPos, String btnNeg, DialogInterface.OnClickListener listener) {
        View mView = LayoutInflater.from(context).inflate(R.layout.alert_layout, null);
        Dialog dialog = new CustomDialog(context, mView);
        AlertViewHolder alertView = new AlertViewHolder(mView);
        alertView.setDialog(dialog)
                .setMessage(message)
                .setPositiveButton(btnPos)
                .setNegativeButton(btnNeg)
                .setDialogListener(listener)
                .setTitle(title);
        dialog.show();
    }

    public static void showDefaultAlert(Context context, String title, String message, String btnPos, String btnNeg, DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if (listener == null) {
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, btnPos, listener);
        if (null != btnNeg)
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, btnNeg, listener);
        alertDialog.show();
    }

    public static void setStatusHeight(View statView, Activity context) {
        if (isStatusBarAtTop(context) && Build.VERSION.SDK_INT > 18)
            statView.getLayoutParams().height = getstatusBarHeight(context);
        else statView.getLayoutParams().height = 0;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator createCenteredReveal(View view) {
        // Could optimize by reusing a temporary Rect instead of allocating a new one
        Rect bounds = new Rect();
        view.getDrawingRect(bounds);
        int centerX = bounds.centerX();
        int centerY = bounds.centerY();
        int finalRadius = Math.max(bounds.width(), bounds.height());
        return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, finalRadius, 0f);
    }
}
