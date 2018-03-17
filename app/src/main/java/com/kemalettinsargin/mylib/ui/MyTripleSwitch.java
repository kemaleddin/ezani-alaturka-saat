package com.kemalettinsargin.mylib.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kemalettinsargin.mylib.Util;

/**
 * Written by "كمال الدّين صارغين"  on 18.05.2017.
 * و من الله توفیق
 */

public class MyTripleSwitch extends FrameLayout {
    private ImageView mThumb;
    private boolean measured;
    private MarginLayoutParams thumbLayoutParams;
    private int state = 1;
    public static final int ON = 1, OFF = 2, CLOSING=3,OPENING = 4;

    public MyTripleSwitch(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public MyTripleSwitch(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyTripleSwitch(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public MyTripleSwitch(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }
    public void init(){
        mThumb = (ImageView) getChildAt(0);
        measured=false;
        setState(state);
        post(new Runnable() {
            @Override
            public void run() {
                measured=true;
                setState(state);
            }
        });
    }

    public void setState(int state) {
        this.state = state;
        thumbLayoutParams = (MarginLayoutParams) mThumb.getLayoutParams();
        if(!measured){
            setThumbState();
            ((MarginLayoutParams) mThumb.getLayoutParams()).topMargin=getTopMargin();
            return;
        }
        int topMargin=getTopMargin();
        ValueAnimator animator = ValueAnimator.ofInt(thumbLayoutParams.topMargin, topMargin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                thumbLayoutParams.topMargin = (Integer) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animator.setDuration(150);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                    setThumbState();
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
    private int getTopMargin(){
        switch (state) {
            case 1:
                return 0;
            case 2:
                return getLayoutParams().height - Util.getHeightForWrapContent(mThumb);
            default:
                return  (int) ((float) (getLayoutParams().height - Util.getHeightForWrapContent(mThumb)) / 2);
        }
    }
    public int getState() {
        return state;
    }
    private void setThumbState(){
        switch (getState()) {
            case 1:
                setEnabled(true);
                mThumb.setEnabled(true);
                mThumb.setSelected(true);
                break;
            case 2:
                setEnabled(true);
                mThumb.setEnabled(true);
                mThumb.setSelected(false);
                break;
            default:
                mThumb.setEnabled(false);
                setEnabled(false);
                break;
        }
    }
}
