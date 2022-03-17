package com.example.matchgamesample.dialog;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;

public class BaseDialog implements View.OnTouchListener, Animation.AnimationListener {

    private boolean mIsShowing;

    protected final MainActivity mParent;
    private ViewGroup mRootLayout;
    private View mRootView;
    private boolean mIsHiding;

    public BaseDialog(MainActivity activity) {
        mParent = activity;
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
        Animation dialogIn = AnimationUtils.loadAnimation(mParent, R.anim.enter_from_top);
        mRootView.startAnimation(dialogIn);
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
        Animation dialogOut = AnimationUtils.loadAnimation(mParent, R.anim.exit_to_top);
        dialogOut.setAnimationListener(this);
        mRootView.startAnimation(dialogOut);
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
        mParent.setShowingDialog(false);
    }

    @Override
    public void onAnimationRepeat(Animation paramAnimation) {
    }

}
