package com.nativegame.match3game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Utils {
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
    private static final Canvas CANVAS = new Canvas();
    private static final BounceInterpolator BOUNCE_INTERPOLATOR = new BounceInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator();
    private static final int POP_UP_TIME = 300;
    private static final int POP_UP_INTERVAL = 50;

    private Utils() {
    }

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static double angle(double sideA, double sideB, double sideC) {   // cosA = (B^2 + C^2 - A^2) / 2BC
        double A = (Math.pow(sideC, 2) + Math.pow(sideB, 2) - Math.pow(sideA, 2)) / (2 * sideB * sideC);
        double angleA = Math.acos(A);
        angleA = Math.toDegrees(angleA);
        angleA = Math.round(angleA);
        return angleA;
    }

    public static void createColorFilter(View view) {
        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        view.invalidate();
    }

    public static void clearColorFilter(View view) {
        view.getBackground().clearColorFilter();
        view.invalidate();
    }

    public static void createButtonEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.animate().setStartDelay(0).setDuration(300).scaleX(0.8f).scaleY(0.8f)
                                .setInterpolator(BOUNCE_INTERPOLATOR);
                        createColorFilter(view);
                        break;
                    case MotionEvent.ACTION_UP:
                        button.animate().setStartDelay(0).setDuration(300).scaleX(1).scaleY(1)
                                .setInterpolator(BOUNCE_INTERPOLATOR);
                        clearColorFilter(view);
                        break;
                }
                return false;
            }
        });
    }

    public static void createPopUpEffect(View view) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate().setStartDelay(POP_UP_TIME).setDuration(200).scaleX(1).scaleY(1)
                .setInterpolator(OVERSHOOT_INTERPOLATOR);
    }

    public static void createPopUpEffect(View view, long delay) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate().setStartDelay(POP_UP_TIME + POP_UP_INTERVAL * delay).setDuration(200).scaleX(1).scaleY(1)
                .setInterpolator(OVERSHOOT_INTERPOLATOR);
    }

    public static Bitmap createBitmapFromView(View view) {
        if (view instanceof ImageView) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (CANVAS) {
                Canvas canvas = CANVAS;
                canvas.setBitmap(bitmap);
                view.draw(canvas);
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }

    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }
}
