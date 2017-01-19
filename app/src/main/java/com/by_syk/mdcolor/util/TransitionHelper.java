package com.by_syk.mdcolor.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.by_syk.mdcolor.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by liubaoyua on 2017/1/19.
 */

public class TransitionHelper {

    private static TransitionHelper sHelper = new TransitionHelper();
    private WeakReference<Bitmap> screenShot;
    private int statusBarColor;

    public static TransitionHelper getInstance() {
        return sHelper;
    }

    public void onRestartActivity(MainActivity activity) {
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        statusBarColor = activity.getWindow().getStatusBarColor();
        screenShot = new WeakReference<>(takeSnapshot(decorView));
    }

    public void onActivityCreate(final MainActivity activity) {
        if (screenShot != null && screenShot.get() != null) {
            final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            final View view = new View(activity);
            final int endStatusBarColor = activity.getWindow().getStatusBarColor();

            view.setBackground(new BitmapDrawable(activity.getResources(), screenShot.get()));
            view.setClickable(true);
            decorView.addView(view, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            view.animate().alpha(0.0f).setDuration(400)
                    .setListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            decorView.removeView(view);
                            screenShot = null;
                        }
                    })
                    .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        ArgbEvaluator evaluator = new ArgbEvaluator();

                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int color = (int) evaluator.evaluate(animation.getAnimatedFraction(), statusBarColor, endStatusBarColor);
                            activity.getWindow().setStatusBarColor(color);
                        }
                    })
                    .start();
        }
    }

    private static Bitmap takeSnapshot(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        Bitmap snapshot = null;
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap != null) {
            snapshot = Bitmap.createBitmap(cacheBitmap);
            view.setDrawingCacheEnabled(false);
            view.destroyDrawingCache();
        }
        return snapshot;
    }
}
