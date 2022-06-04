package com.nativegame.match3game.dialog;

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

    public SettingDialog(MainActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_setting);

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

        // Init pop up
        Utils.createPopUpEffect(btnMusic);
        Utils.createPopUpEffect(btnSound, 1);

        updateSoundAndMusicButtons();
    }

    private void updateSoundAndMusicButtons() {
        SoundManager soundManager = mParent.getSoundManager();

        // Update music state
        boolean music = soundManager.getMusicStatus();
        ImageView btnMusic = (ImageView) findViewById(R.id.btn_music);
        if (music) {
            btnMusic.setBackgroundResource(R.drawable.btn_music_on);
        } else {
            btnMusic.setBackgroundResource(R.drawable.btn_music_off);
        }

        // Update sound state
        boolean sound = soundManager.getSoundStatus();
        ImageView btnSounds = (ImageView) findViewById(R.id.btn_sound);
        if (sound) {
            btnSounds.setBackgroundResource(R.drawable.btn_sound_on);
        } else {
            btnSounds.setBackgroundResource(R.drawable.btn_sound_off);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sound) {
            mParent.getSoundManager().toggleSoundStatus();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.btn_music) {
            mParent.getSoundManager().toggleMusicStatus();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.btn_cancel) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
            super.dismiss();
        }
    }
}