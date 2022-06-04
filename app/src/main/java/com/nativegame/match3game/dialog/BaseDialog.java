package com.nativegame.match3game.dialog;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;

/**
 * BaseDialog control dialog lifecycle, and contain
 * method that can be override by child dialog, including
 * show dialog, dismiss dialog, transition animation.
 */

public class BaseDialog implements View.OnTouchListener, Animation.AnimationListener {

    private boolean mIsShowing;

    protected final MainActivity mParent;
    private ViewGroup mRootLayout;
    private View mRootView;
    private boolean mIsHiding;

    private int mShowAnimation;
    private int mHideAnimation;

    public BaseDialog(MainActivity activity) {
        mParent = activity;

        // This is the default animation
        mShowAnimation = R.anim.enter_from_top;
        mHideAnimation = R.anim.exit_to_top;
    }

    protected void onViewClicked() {
        // Ignore clicks on this view
    }

    protected void setContentView(int dialogResId) {
        ViewGroup activityRoot = (ViewGroup) mParent.findViewById(android.R.id.content);
        mRootView = LayoutInflater.from(mParent).inflate(dialogResId, activityRoot, false);
    }

    public void show() {
        if (mIsShowing) {
            return;
        }
        mIsShowing = true;
        mIsHiding = false;

        ViewGroup activityRoot = (ViewGroup) mParent.findViewById(android.R.id.content);
        mRootLayout = (ViewGroup) LayoutInflater.from(mParent).inflate(R.layout.dialog_container, activityRoot, false);
        activityRoot.addView(mRootLayout);
        mRootLayout.setOnTouchListener(this);
        mRootLayout.addView(mRootView);
        startShowAnimation();
    }

    private void startShowAnimation() {
        Animation dialogIn = AnimationUtils.loadAnimation(mParent, mShowAnimation);
        mRootView.startAnimation(dialogIn);
    }

    public void setShowAnimation(int id) {
        mShowAnimation = id;
    }

    public void dismiss() {
        if (!mIsShowing) {
            return;
        }
        if (mIsHiding) {
            return;
        }
        mIsHiding = true;
        startHideAnimation();
    }

    protected void onDismissed() {
    }

    private void startHideAnimation() {
        Animation dialogOut = AnimationUtils.loadAnimation(mParent, mHideAnimation);
        dialogOut.setAnimationListener(this);
        mRootView.startAnimation(dialogOut);
    }

    public void setHideAnimation(int id) {
        mHideAnimation = id;
    }

    private void hideViews() {
        mRootLayout.removeView(mRootView);
        ViewGroup activityRoot = (ViewGroup) mParent.findViewById(android.R.id.content);
        activityRoot.removeView(mRootLayout);
    }

    protected View findViewById(int id) {
        return mRootView.findViewById(id);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Ignoring touch events on the gray outside
        return true;
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    @Override
    public void onAnimationStart(Animation paramAnimation) {
    }

    @Override
    public void onAnimationEnd(Animation paramAnimation) {
        hideViews();
        mIsShowing = false;
        onDismissed();
    }

    @Override
    public void onAnimationRepeat(Animation paramAnimation) {
    }

}
