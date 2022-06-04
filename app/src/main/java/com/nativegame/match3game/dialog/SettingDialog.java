package com.nativegame.match3game.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.effect.sound.SoundManager;

/**
 * SettingDialog will show when player press
 * setting button in menu and map, containing
 * volume button and other customizing setting
 */

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SettingDialog extends BaseDialog implements View.OnClickListener {

    private final SharedPreferences mPrefs;
    private boolean mHintEnable;
    private boolean mFPSEnable;

    private static final String PREFS_NAME = "prefs_setting";
    private static final String HINT_PREF_KEY = "hint";
    private static final String FPS_PREF_KEY = "fps";

    public SettingDialog(MainActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_setting);

        mPrefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mHintEnable = mPrefs.getBoolean(HINT_PREF_KEY, true);
        mFPSEnable = mPrefs.getBoolean(FPS_PREF_KEY, true);

        init();
    }

    private void init() {
        // Init button
        ImageButton btnMusic = (ImageButton) findViewById(R.id.btn_music);
        btnMusic.setOnClickListener(this);
        Utils.createButtonEffect(btnMusic);
        ImageButton btnSound = (ImageButton) findViewById(R.id.btn_sound);
        btnSound.setOnClickListener(this);
        Utils.createButtonEffect(btnSound);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        Utils.createButtonEffect(btnCancel);

        // Init switch
        ImageButton btnHint = (ImageButton) findViewById(R.id.switch_hint_thumb);
        btnHint.setOnClickListener(this);
        Utils.createButtonEffect(btnHint);
        ImageButton btnFPS = (ImageButton) findViewById(R.id.switch_fps_thumb);
        btnFPS.setOnClickListener(this);
        Utils.createButtonEffect(btnFPS);

        // Init pop up
        Utils.createPopUpEffect(btnMusic);
        Utils.createPopUpEffect(btnSound, 1);
        Utils.createPopUpEffect(btnHint, 2);
        Utils.createPopUpEffect(findViewById(R.id.switch_hint_track), 2);
        Utils.createPopUpEffect(btnFPS, 3);
        Utils.createPopUpEffect(findViewById(R.id.switch_fps_track), 3);

        updateSoundAndMusicButtons();
        btnHint.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateHintButton();
                updateFPSButton();
            }
        }, 10);

    }

    private void updateSoundAndMusicButtons() {
        SoundManager soundManager = mParent.getSoundManager();

        // Update music state
        boolean music = soundManager.getMusicStatus();
        ImageButton btnMusic = (ImageButton) findViewById(R.id.btn_music);
        if (music) {
            btnMusic.setBackgroundResource(R.drawable.btn_music_on);
        } else {
            btnMusic.setBackgroundResource(R.drawable.btn_music_off);
        }

        // Update sound state
        boolean sound = soundManager.getSoundStatus();
        ImageButton btnSounds = (ImageButton) findViewById(R.id.btn_sound);
        if (sound) {
            btnSounds.setBackgroundResource(R.drawable.btn_sound_on);
        } else {
            btnSounds.setBackgroundResource(R.drawable.btn_sound_off);
        }
    }

    private void updateHintButton() {
        // Update hint state
        ImageView thumb = (ImageView) findViewById(R.id.switch_hint_thumb);
        ImageView track = (ImageView) findViewById(R.id.switch_hint_track);
        if (mHintEnable) {
            thumb.setX(track.getX() + track.getMeasuredWidth() * 0.5f);
            track.setBackgroundResource(R.drawable.switch_track_on);
        } else {
            thumb.setX(track.getX());
            track.setBackgroundResource(R.drawable.switch_track_off);
        }
    }

    private void updateFPSButton() {
        // Update hint state
        ImageView thumb = (ImageView) findViewById(R.id.switch_fps_thumb);
        ImageView track = (ImageView) findViewById(R.id.switch_fps_track);
        if (mFPSEnable) {
            thumb.setX(track.getX() + track.getMeasuredWidth() * 0.5f);
            track.setBackgroundResource(R.drawable.switch_track_on);
        } else {
            thumb.setX(track.getX());
            track.setBackgroundResource(R.drawable.switch_track_off);
        }
    }

    private void toggleHintStatus() {
        mHintEnable = !mHintEnable;
        // Save it to preferences
        mPrefs.edit()
                .putBoolean(HINT_PREF_KEY, mHintEnable)
                .apply();
    }

    private void toggleFPSStatus() {
        mFPSEnable = !mFPSEnable;
        // Save it to preferences
        mPrefs.edit()
                .putBoolean(FPS_PREF_KEY, mFPSEnable)
                .apply();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sound) {
            mParent.getSoundManager().toggleSoundStatus();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.btn_music) {
            mParent.getSoundManager().toggleMusicStatus();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.switch_hint_thumb) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            toggleHintStatus();
            updateHintButton();
        } else if (view.getId() == R.id.switch_fps_thumb) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            toggleFPSStatus();
            updateFPSButton();
        } else if (view.getId() == R.id.btn_cancel) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            super.dismiss();
        }
    }
}
