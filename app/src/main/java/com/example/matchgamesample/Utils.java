package com.example.matchgamesample;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class Utils {
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
    private static final Canvas CANVAS = new Canvas();

    private Utils() {
    }

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static double angle(double sideA, double sideB, double sideC) {
        double A=(Math.pow(sideC,2)+Math.pow(sideB,2)-Math.pow(sideA,2))/(2*sideB*sideC);
        double angleA=Math.acos(A);
        angleA=Math.toDegrees(angleA);
        angleA=Math.round(angleA);
        return angleA;
        //cosA = (B^2 + C^2 - A^2) / 2BC
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
