package com.nativegame.juicymatch.ui.dialog;

import android.view.View;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Preferences;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.natyengine.audio.music.MusicManager;
import com.nativegame.natyengine.audio.sound.SoundManager;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.ui.GameImage;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SettingDialog extends BaseDialog implements View.OnClickListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SettingDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_setting);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        // Init button
        GameButton btnMusic = (GameButton) findViewById(R.id.btn_music);
        btnMusic.popUp(200, 300);
        btnMusic.setOnClickListener(this);

        GameButton btnSound = (GameButton) findViewById(R.id.btn_sound);
        btnSound.popUp(200, 400);
        btnSound.setOnClickListener(this);

        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        // Init switch
        GameImage imageSwitch = (GameImage) findViewById(R.id.switch_hint);
        imageSwitch.popUp(200, 500);
        imageSwitch.setOnClickListener(this);

        updateMusicButton();
        updateSoundButton();
        updateHintButton();
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_music) {
            toggleMusic();
            updateMusicButton();
        } else if (id == R.id.btn_sound) {
            toggleSound();
            updateSoundButton();
        } else if (id == R.id.switch_hint) {
            toggleHint();
            updateHintButton();
        } else if (id == R.id.btn_cancel) {
            dismiss();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void toggleMusic() {
        MusicManager musicManager = mParent.getMusicManager();
        boolean enable = musicManager.isAudioEnable();
        musicManager.setAudioEnable(!enable);
        // Save to Preference
        Preferences.PREF_SETTING.putBoolean(Preferences.KEY_MUSIC, !enable);
    }

    private void toggleSound() {
        SoundManager soundManager = mParent.getSoundManager();
        boolean enable = soundManager.isAudioEnable();
        soundManager.setAudioEnable(!enable);
        // Save to Preference
        Preferences.PREF_SETTING.putBoolean(Preferences.KEY_SOUND, !enable);
    }

    private void toggleHint() {
        boolean enable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_HINT, true);
        // Save to Preference
        Preferences.PREF_SETTING.putBoolean(Preferences.KEY_HINT, !enable);
    }

    private void updateMusicButton() {
        boolean enable = mParent.getMusicManager().isAudioEnable();
        GameButton btnMusic = (GameButton) findViewById(R.id.btn_music);
        if (enable) {
            btnMusic.setBackgroundResource(R.drawable.ui_btn_music_on);
        } else {
            btnMusic.setBackgroundResource(R.drawable.ui_btn_music_off);
        }
    }

    private void updateSoundButton() {
        boolean enable = mParent.getSoundManager().isAudioEnable();
        GameButton btnSound = (GameButton) findViewById(R.id.btn_sound);
        if (enable) {
            btnSound.setBackgroundResource(R.drawable.ui_btn_sound_on);
        } else {
            btnSound.setBackgroundResource(R.drawable.ui_btn_sound_off);
        }
    }

    private void updateHintButton() {
        boolean enable = Preferences.PREF_SETTING.getBoolean(Preferences.KEY_HINT, true);
        GameImage imageSwitch = (GameImage) findViewById(R.id.switch_hint);
        if (enable) {
            imageSwitch.setImageResource(R.drawable.ui_switch_thumb_on);
            imageSwitch.setBackgroundResource(R.drawable.ui_switch_track_on);
        } else {
            imageSwitch.setImageResource(R.drawable.ui_switch_thumb_off);
            imageSwitch.setBackgroundResource(R.drawable.ui_switch_track_off);
        }
    }
    //========================================================

}
