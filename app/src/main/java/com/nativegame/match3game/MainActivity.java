package com.nativegame.match3game;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import com.nativegame.match3game.database.DatabaseHelper;
import com.nativegame.match3game.dialog.BaseDialog;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.effect.sound.SoundManager;
import com.nativegame.match3game.fragment.BaseFragment;
import com.nativegame.match3game.fragment.GameFragment;
import com.nativegame.match3game.fragment.MenuFragment;
import com.nativegame.match3game.fragment.WinDialogFragment;
import com.nativegame.match3game.level.LevelManager;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/*
 *    MIT License
 *
 *    Copyright (c) 2022 Oscar Liang
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *    SOFTWARE.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "content";

    private LevelManager mLevelManager;
    private SoundManager mSoundManager;
    private DatabaseHelper mDatabaseHelper;
    private BaseDialog mCurrentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Match3Game);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mLevelManager = new LevelManager(this);
        mSoundManager = new SoundManager(this);
        mDatabaseHelper = new DatabaseHelper(this);

        // Show the menu fragment
        if (savedInstanceState == null) {
            navigateToFragment(new MenuFragment());
        }
    }

    public LevelManager getLevelManager() {
        return mLevelManager;
    }

    public SoundManager getSoundManager() {
        return mSoundManager;
    }

    public DatabaseHelper getDatabaseHelper() {
        return mDatabaseHelper;
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
        if (fragment != null && !(fragment instanceof GameFragment || fragment instanceof WinDialogFragment)) {
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
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
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
