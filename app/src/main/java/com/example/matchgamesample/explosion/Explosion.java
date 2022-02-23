package com.example.matchgamesample.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.example.matchgamesample.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Explosion extends View {
    private List<ParticleSystem> mExplosions = new ArrayList<>();        //array storing animation on Piece
    private int[] mExpandInset = new int[2];                                //Expanding the explosion area

    public Explosion(Context context) {
        super(context);
        Arrays.fill(mExpandInset, Utils.dp2Px(32));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw Piece on screen
        for (ParticleSystem explosion : mExplosions) {
            explosion.draw(canvas);
        }
    }

    private void explode(Bitmap bitmap, Rect bound, long duration, int partLen) {
        final ParticleSystem explosion = new ParticleSystem(this, bitmap, bound, partLen);
        explosion.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mExplosions.remove(animation);
            }
        });
        explosion.setDuration(duration);
        mExplosions.add(explosion);
        explosion.start();
    }

    public void explode(final View view, int partLen) {
        //Rect storing view location
        Rect r = new Rect();
        //Passing view location to r
        view.getGlobalVisibleRect(r);
        //Get location on screen
        int[] location = new int[2];
        getLocationOnScreen(location);
        //Resize and relocation
        r.offset(-location[0], -location[1]);
        r.inset(-mExpandInset[0], -mExpandInset[1]);
        //Explode
        view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
        explode(Utils.createBitmapFromView(view), r, ParticleSystem.DEFAULT_DURATION, partLen);
    }

    public void clear() {
        mExplosions.clear();
        invalidate();
    }

    public static Explosion attach2Window(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        Explosion explosion = new Explosion(activity);
        rootView.addView(explosion, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return explosion;
    }

}