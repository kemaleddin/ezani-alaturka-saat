package com.kemalettinsargin.mylib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sahnisemanyazilim.ezanisaat.R;
import com.sahnisemanyazilim.ezanisaat.retro.Api;


/**
 * Created by "كمال الدّين صارغين"  on 4.11.2015.
 * و من الله توفیق
 */
public class BaseFragmentActivity extends AppCompatActivity implements DialogInterface.OnShowListener {
    private ProgressDialog mDialog;
    private float mheight, mweight, scale;
    private DisplayMetrics metrics = new DisplayMetrics();
    public static boolean isPaused = false, itemsLoaded = false, isExecuting = false;
    private boolean isDestroyed = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDestroyed = false;


        int theme = Build.VERSION.SDK_INT >= 23 ? android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar : ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT;
        mDialog = new ProgressDialog(this, theme);
        mDialog.setOnShowListener(this);
        resetLoadingMessage();
        SetDisplaySizes();

    }

    public void openDetail(int holderId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(holderId, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    private void SetDisplaySizes() {
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mheight = metrics.heightPixels;
        mweight = metrics.widthPixels;
        scale = metrics.density;
    }



    public void setLoadingMessage(String message) {
        mDialog.setMessage(message);
    }

    public void resetLoadingMessage() {
        mDialog.setMessage(getString(R.string.yukleniyor));
    }

    public void showLoading() {
        try {
            if (!mDialog.isShowing()) mDialog.show();
        } catch (Throwable e) {

        }
    }

    public void showLoading(String msg) {
        try {
            if (!mDialog.isShowing()) {
                mDialog.setMessage(msg);
                mDialog.show();
            }
        } catch (Exception e) {

        }
    }

    public void dismissLoading() {
        try {
            mDialog.dismiss();
            resetLoadingMessage();
        } catch (Exception e) {

        }
    }

    public ViewGroup getRoot() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        return viewGroup;
    }

    public boolean isProgressShown() {
        try {
            return mDialog.isShowing();
        } catch (Exception e) {

        }
        return false;
    }



    public View getChild(int id) {
        return findViewById(id);
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

    public DisplayMetrics getMetrics() {
        return metrics;
    }


    public Gson getGson() {
        return Util.getGson();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        ((ProgressBar) mDialog.findViewById(android.R.id.progress)).getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        ((TextView) mDialog.findViewById(android.R.id.message)).setTextColor(getResources().getColor(R.color.colorAccent));
    }

    /*    @Override
            public void onBackPressed() {
                if(null==getChild(R.id.root)){
                    super.onBackPressed();
                    return;
                }
                Animation animation=AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        getChild(R.id.root).setVisibility(View.GONE);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                getChild(R.id.root).startAnimation(animation);
            }*/
    public void showErrorDialog() {
        Util.showAlert(this, getString(R.string.error), getString(Util.isInternetAvailable(getApplicationContext()) ? R.string.err_msg : R.string.net_err_msg), getString(R.string.tamam), null, null);

    }

    public void showErrorDialogRetry(DialogInterface.OnClickListener listener) {
        boolean b = Util.isInternetAvailable(getApplicationContext());
        Util.showAlertErr(this, getString(R.string.error), getString(b ? R.string.err_msg : R.string.net_err_msg), getString(R.string.yinele), getString(R.string.kapat), listener);
    }

    public void showErrorDialogRetry(String msg, String tittle, DialogInterface.OnClickListener listener) {
        Util.showAlertErr(this, tittle, msg, getString(R.string.tamam), "", listener);
    }

    public void showYesNo(String msg, String tittle, DialogInterface.OnClickListener listener) {
    }


    public void showErrorDialog(String msg) {
        Util.showAlertErr(this, getString(R.string.error), msg, getString(R.string.tamam), null, null);
    }

    public void showErrorDialog(String msg, DialogInterface.OnClickListener listener) {
        Util.showAlert(this,getString(R.string.error),msg,getString(R.string.tamam),null,listener);
    }

    public void showInfo(String msg) {
        Util.showAlert(this, getString(R.string.bilgi), msg, getString(R.string.tamam), null, null);
    }

    public void showInfo(String msg, DialogInterface.OnClickListener listener) {
        Util.showAlert(this, getString(R.string.bilgi), msg, getString(R.string.tamam), null, listener);
    }

    public void showInfo_1(String msg, DialogInterface.OnClickListener listener) {
    }


    public void showSuccess(String msg) {

        showSuccess(msg, null);
    }

    public void showSuccess(String msg, DialogInterface.OnClickListener listener) {
        Util.showAlert(this, getString(R.string.success), msg, getString(R.string.tamam), null, listener);
    }

    @Override
    protected void onResume() {
        isPaused = false;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isPaused = true;
        super.onPause();
    }


    public Api getApi() {
        return ((BaseApp)getApplication()).getApi();
    }

    @Override
    @SuppressLint("inlinedApi")
    public boolean isDestroyed() {
        if (Build.VERSION.SDK_INT > 16)
            return super.isDestroyed();
        else return isDestroyed;
    }

    public void showAlert(String tittle, String message, String btnPositive, String btnNegative, final DialogInterface.OnClickListener listener) {
        Util.showAlert(this, tittle, message, btnPositive, btnNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (listener != null)
                    listener.onClick(dialogInterface, i);
            }
        });
    }

    public void animateShow(final View view) {
        AnimatorSet set = new AnimatorSet();
        view.setPivotX(.5f * getWidth());
        view.setPivotY(getHeight());
        ObjectAnimator anim3, anim2, anim1 = ObjectAnimator.ofFloat(view, "scaleX", .5f, 1f);
        anim2 = ObjectAnimator.ofFloat(view, "scaleY", .5f, 1f);
        anim3 = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
       /* anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.getParent().requestLayout();
            }
        });*/
        set.playTogether(anim1, anim2, anim3);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        set.setDuration(300).setInterpolator(new AccelerateInterpolator());
        set.start();
        /*Animation animation= AnimationUtils.loadAnimation(this,R.anim.slide_in_right1);
        animation.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        animation.start();*/

    }

    public void animateHide(final View view) {
        AnimatorSet set = new AnimatorSet();
        view.setPivotX(.5f * getWidth());
        view.setPivotY(getHeight());
        ObjectAnimator anim3, anim2, anim1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, .5f);
        anim2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, .5f);
        anim3 = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
       /* anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.getParent().requestLayout();
            }
        });*/
        set.playTogether(anim1, anim2, anim3);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        set.setDuration(300).setInterpolator(new AccelerateInterpolator());
        set.start();
        /*Animation animation= AnimationUtils.loadAnimation(this,R.anim.slide_in_right1);
        animation.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        animation.start();*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        isDestroyed=true;
        /*try {
            ((MyApp)getApplication()).getClient().dispatcher().cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        super.onDestroy();
    }
}
