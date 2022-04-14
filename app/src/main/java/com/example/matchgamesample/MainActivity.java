package com.example.matchgamesample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.matchgamesample.dialog.BaseDialog;
import com.example.matchgamesample.effect.sound.SoundEvent;
import com.example.matchgamesample.effect.sound.SoundManager;
import com.example.matchgamesample.fragment.BaseFragment;
import com.example.matchgamesample.fragment.GameFragment;
import com.example.matchgamesample.fragment.LoadingFragment;
import com.example.matchgamesample.level.LevelManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "content";

    private BaseDialog mCurrentDialog;
    private boolean mShowingDialog;

    private LevelManager mLevelManager;
    private SoundManager mSoundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MatchGameSample);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            //Add LoadingFragment to screen
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.container, new LoadingFragment(), TAG_FRAGMENT)
                    .commit();
        }

        mLevelManager = new LevelManager(this);
        mSoundManager = new SoundManager(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

    }

    public LevelManager getLevelManager() {
        return mLevelManager;
    }

    public SoundManager getSoundManager() {
        return mSoundManager;
    }

    public void startGame(int level) {
        // Navigate the game fragment, which makes the start automatically
        navigateToFragment(GameFragment.newInstance(level));
    }

    public void navigateToFragment(BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out)
                .replace(R.id.container, fragment, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    public void navigateBack() {
        // Do a push on the navigation history
        getSupportFragmentManager().popBackStack();
    }

    public void setShowingDialog(boolean b) {
        mShowingDialog = b;
    }

    public void showDialog(BaseDialog newDialog, boolean dismissOtherDialog) {
        if (mCurrentDialog != null && mCurrentDialog.isShowing()) {
            if (dismissOtherDialog) {
                mCurrentDialog.dismiss();
            } else {
                return;
            }
        }
        mCurrentDialog = newDialog;
        mCurrentDialog.show();
        mSoundManager.playSoundForSoundEvent(SoundEvent.SWEEP2);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentDialog != null && mCurrentDialog.isShowing()) {
            mCurrentDialog.dismiss();
            return;
        }
        final BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundManager.pauseBgMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment != null && !(fragment instanceof GameFragment)) {
            mSoundManager.resumeBgMusic();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSoundManager.unloadMusic();
        mSoundManager.unloadSounds();
    }

    @Override
    protected void onStart() {
        super.onStart();
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        } else {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LOW_PROFILE);
            } else {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

}