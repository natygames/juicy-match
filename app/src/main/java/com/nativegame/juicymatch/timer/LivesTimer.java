package com.nativegame.juicymatch.timer;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Preferences;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.util.ResourceUtils;

import java.util.Locale;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LivesTimer {

    public static final int MAX_LIVES = 5;
    public static final long LIVES_CD = 1200000;   // 20 min = 1200000ms

    private final Activity mActivity;

    private LivesCountDownTimer mCountDownTimer;

    private int mLivesNum;
    private long mTimeLeftInMillis;
    private long mEndTime;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LivesTimer(Activity activity) {
        mActivity = activity;
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getLivesCount() {
        return mLivesNum;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void startTimer() {
        mLivesNum = Preferences.PREF_LIVES.getInt(Preferences.KEY_LIVES, MAX_LIVES);
        if (mLivesNum == MAX_LIVES) {
            updateLivesText();
            return;
        }

        // We get the previous time and calculate the time remaining
        mEndTime = Preferences.PREF_LIVES.getLong(Preferences.KEY_END_TIME, 0);
        mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

        // The timer has finish one cd time
        if (mTimeLeftInMillis < 0) {
            // We first calculate how much time pass
            // and how many lives obtain
            long timePass = -mTimeLeftInMillis;
            long timeRemaining = timePass % LIVES_CD;
            int livesPass = 1 + (int) (timePass / LIVES_CD);

            // We then add back to the previous lives
            // and subtract the remaining time to cd time
            mLivesNum += livesPass;
            mTimeLeftInMillis = LIVES_CD - timeRemaining;

            if (mLivesNum >= MAX_LIVES) {
                mLivesNum = MAX_LIVES;
                mTimeLeftInMillis = 0;
                mEndTime = 0;
            } else {
                startCountDownTimer();
            }

        } else {
            // The timer hasn't finish one cd time, so we just resume timer
            startCountDownTimer();
        }

        updateLivesText();
    }

    public void stopTimer() {
        // Stop the timer
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        // Save to preferences
        Preferences.PREF_LIVES.putInt(Preferences.KEY_LIVES, mLivesNum);
        Preferences.PREF_LIVES.putLong(Preferences.KEY_MILLIS_LEFT, mTimeLeftInMillis);
        Preferences.PREF_LIVES.putLong(Preferences.KEY_END_TIME, mEndTime);
    }

    public void addLive() {
        if (mLivesNum < MAX_LIVES) {
            mLivesNum++;
        }

        // Save to preferences
        Preferences.PREF_LIVES.putInt(Preferences.KEY_LIVES, mLivesNum);
    }

    public void reduceLive() {
        mLivesNum--;

        // Check is any current time left, so we won't override the current state
        if (mTimeLeftInMillis == 0L) {
            // We set them as start value
            mTimeLeftInMillis = LIVES_CD;
            mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        }

        // Save to preferences
        Preferences.PREF_LIVES.putInt(Preferences.KEY_LIVES, mLivesNum);
        Preferences.PREF_LIVES.putLong(Preferences.KEY_MILLIS_LEFT, mTimeLeftInMillis);
        Preferences.PREF_LIVES.putLong(Preferences.KEY_END_TIME, mEndTime);
    }

    private void updateLivesText() {
        // Update text
        TextView txtLives = (TextView) mActivity.findViewById(R.id.txt_lives);
        txtLives.setText(String.valueOf(mLivesNum));

        // Update button
        GameButton btnLives = (GameButton) mActivity.findViewById(R.id.btn_lives);
        if (mLivesNum == 0) {
            btnLives.setBackgroundResource(R.drawable.ui_lives_lock);
        } else {
            btnLives.setBackgroundResource(R.drawable.ui_lives);
            if (mLivesNum == MAX_LIVES) {
                TextView txtTime = mActivity.findViewById(R.id.txt_lives_time);
                txtTime.setText(ResourceUtils.getString(mActivity, R.string.txt_lives_full));
            }
        }
    }

    private void startCountDownTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        // Stop the previous timer
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        // Init the timer
        mCountDownTimer = new LivesCountDownTimer(mTimeLeftInMillis, 1000);
        mCountDownTimer.start();
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    private class LivesCountDownTimer extends CountDownTimer {

        //--------------------------------------------------------
        // Constructors
        //--------------------------------------------------------
        public LivesCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        //========================================================

        //--------------------------------------------------------
        // Overriding methods
        //--------------------------------------------------------
        @Override
        public void onTick(long timeLeftInMillis) {
            mTimeLeftInMillis = timeLeftInMillis;

            // Convert to time format
            int minutes = (int) (timeLeftInMillis / 1000) / 60;
            int seconds = (int) (timeLeftInMillis / 1000) % 60;
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

            // Update time text
            TextView txtTime = mActivity.findViewById(R.id.txt_lives_time);
            txtTime.setText(timeLeftFormatted);
        }

        @Override
        public void onFinish() {
            // Check if live is full
            if (mLivesNum < MAX_LIVES) {
                mLivesNum++;

                if (mLivesNum != MAX_LIVES) {
                    // Live is not full yet, so we start timer recursively
                    mTimeLeftInMillis = LIVES_CD;
                    startCountDownTimer();
                } else {
                    // Live is full, so we reset the time
                    mTimeLeftInMillis = 0;  // Somehow it won't be 0 (Actually a little bigger than 1000)
                    mEndTime = 0;
                    TextView txtTime = mActivity.findViewById(R.id.txt_lives_time);
                    txtTime.setText(ResourceUtils.getString(mActivity, R.string.txt_lives_full));
                }
            }

            updateLivesText();
        }
        //========================================================

    }
    //========================================================

}
