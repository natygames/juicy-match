package com.nativegame.juicymatch.ui.dialog;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;

import com.nativegame.juicymatch.MainActivity;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.ad.AdManager;
import com.nativegame.juicymatch.asset.Preferences;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.database.DatabaseHelper;
import com.nativegame.juicymatch.item.prize.Prize;
import com.nativegame.juicymatch.item.prize.PrizeManager;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.ui.GameText;
import com.nativegame.natyengine.util.RandomUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class WheelDialog extends BaseDialog implements AdManager.AdRewardListener, View.OnClickListener {

    private static final long WHEEL_CD_TIME = 86400000;   // 1 day = 86400000ms

    private final boolean mIsWheelEnable;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public WheelDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_wheel);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);

        // Init button
        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.popUp(200, 300);
        btnCancel.setOnClickListener(this);

        GameButton btnPlay = (GameButton) findViewById(R.id.btn_play);
        btnPlay.popUp(200, 700);
        btnPlay.setOnClickListener(this);

        // Init text
        GameText txtBonus = (GameText) findViewById(R.id.txt_wheel);
        txtBonus.popUp(200, 500);

        // Check is wheel ready
        long lastTime = Preferences.PREF_SETTING.getLong(Preferences.KEY_LAST_PLAY_TIME, 0);
        mIsWheelEnable = lastTime == 0 || (System.currentTimeMillis() - lastTime > WHEEL_CD_TIME);

        // Show watch ad button if wheel not ready
        if (!mIsWheelEnable) {
            btnPlay.setBackgroundResource(R.drawable.ui_btn_watch_ad);
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id == R.id.btn_play) {
            if (mIsWheelEnable) {
                // Save current time and spin wheel
                Preferences.PREF_SETTING.putLong(Preferences.KEY_LAST_PLAY_TIME, System.currentTimeMillis());
                spinWheel();
            } else {
                showRewardAd();
            }
            // Hide play button
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onEarnReward() {
        spinWheel();
    }

    @Override
    public void onLossReward() {
        // We do nothing
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void spinWheel() {
        // Generate a random degree from 0 to 360
        int random = RandomUtils.nextInt(360);
        // We want to rotate at least 3 times
        int degree = random + 720;

        // Wheel rotate animation
        RotateAnimation wheelAnimation = new RotateAnimation(0, degree,
                1, 0.5f, 1, 0.5f);
        wheelAnimation.setDuration(4000);
        wheelAnimation.setFillAfter(true);
        wheelAnimation.setInterpolator(new DecelerateInterpolator());
        wheelAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Dismiss and show prize
                dismiss();
                showPrize(degree);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        findViewById(R.id.image_wheel).startAnimation(wheelAnimation);

        // Pointer rotate animation
        RotateAnimation pointerAnimation = new RotateAnimation(0, -30,
                1, 0.5f, 1, 0.35f);
        pointerAnimation.setDuration(200);
        pointerAnimation.setRepeatMode(Animation.REVERSE);
        pointerAnimation.setRepeatCount(15);
        findViewById(R.id.image_wheel_pointer).startAnimation(pointerAnimation);

        // Play spinning sound
        Sounds.WHEEL_SPIN.play();
    }

    private void showPrize(int degree) {
        // Get degree
        degree = degree % 360;

        // Save prizes base on degree
        Prize prize = getPrize(degree);
        savePrizes(prize.getName(), prize.getCount());
        updateCoin();

        // Show prize dialog
        NewPrizeDialog newPrizeDialog = new NewPrizeDialog(mParent, prize);
        mParent.showDialog(newPrizeDialog);
    }

    private Prize getPrize(int degree) {
        PrizeManager prizeManager = new PrizeManager();
        if (degree < 72) {
            return prizeManager.getPrize(PrizeManager.PRIZE_HAMMER);
        } else if (degree < 144) {
            return prizeManager.getPrize(PrizeManager.PRIZE_BOMB);
        } else if (degree < 216) {
            return prizeManager.getPrize(PrizeManager.PRIZE_COIN_50);
        } else if (degree < 288) {
            return prizeManager.getPrize(PrizeManager.PRIZE_GLOVE);
        } else {
            return prizeManager.getPrize(PrizeManager.PRIZE_COIN_150);
        }
    }

    private void savePrizes(String name, int count) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(mParent);
        int saving = databaseHelper.getItemCount(name);
        databaseHelper.updateItemCount(name, saving + count);
    }

    private void showRewardAd() {
        AdManager adManager = ((MainActivity) mParent).getAdManager();
        adManager.setListener(this);
        boolean isConnect = adManager.showRewardAd();
        // Show error dialog if no internet connect
        if (!isConnect) {
            ErrorDialog dialog = new ErrorDialog(mParent) {
                @Override
                public void retry() {
                    adManager.requestAd();
                    showRewardAd();
                }
            };
            mParent.showDialog(dialog);
        }
    }

    public void updateCoin() {
    }
    //========================================================

}
