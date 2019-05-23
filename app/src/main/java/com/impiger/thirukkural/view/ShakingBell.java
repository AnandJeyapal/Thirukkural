package com.impiger.thirukkural.view;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.impiger.thirukkural.R;

public class ShakingBell extends FrameLayout {

    ImageView bellCover;
    ImageView bell;
    View alarmOn;
    View alarmOff;
    Animation animation, animation2;
    ValueAnimator animation3;

    public ShakingBell(Context context) {
        this(context, null);
    }

    public ShakingBell(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShakingBell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.shaking_bell, this, true);
        bellCover = (ImageView) findViewById(R.id.bell_cover);
        bell = (ImageView) findViewById(R.id.bell);

        alarmOn = findViewById(R.id.notification_on);
        alarmOff = findViewById(R.id.notification_off);
        animation = new RotateAnimation(0, -20, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.3f);
        animation.setInterpolator(new SpringInterpolator());
        animation.setDuration(1000);

        animation2 = new TranslateAnimation(0, 20, 0, 0);
        animation2.setInterpolator(new SpringInterpolator());
        animation2.setDuration(1000);
        animation2.setStartOffset(200);

        animation3 = ValueAnimator.ofFloat(1);
        animation3.setDuration(600);
        animation3.setInterpolator(new OvershootInterpolator());
        animation3.setStartDelay(600);
        animation3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                bellCover.setVisibility(GONE);
                bell.setVisibility(GONE);
                alarmOff.setVisibility(GONE);
                alarmOn.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void shake() {
        alarmOff.setVisibility(GONE);
        alarmOn.setVisibility(GONE);
        bellCover.setVisibility(VISIBLE);
        bell.setVisibility(VISIBLE);
        bellCover.startAnimation(animation);
        bell.startAnimation(animation2);
        animation3.start();
    }

    public void setAlarmOff() {
        bellCover.setVisibility(GONE);
        bell.setVisibility(GONE);
        alarmOn.setVisibility(GONE);
        alarmOff.setVisibility(VISIBLE);
    }
}
