package com.nativegame.match3game.dialog;

import android.view.View;
import android.widget.ImageButton;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.effect.sound.SoundManager;

/**
 * PauseDialog will show when player press
 * pause button in game, containing volume
 * and quit button
 */

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class PauseDialog extends BaseDialog implements View.OnClickListener {

    private int mSelectedId;

    public PauseDialog(MainActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_pause);

        // Init button
        ImageButton btnMusic = (ImageButton) findViewById(R.id.btn_music);
        btnMusic.setOnClickListener(this);
        Utils.createButtonEffect(btnMusic);
        ImageButton btnSound = (ImageButton) findViewById(R.id.btn_sound);
        btnSound.setOnClickListener(this);
        Utils.createButtonEffect(btnSound);
        ImageButton btnQuit = (ImageButton) findViewById(R.id.btn_quit);
        btnQuit.setOnClickListener(this);
        Utils.createButtonEffect(btnQuit);
        ImageButton btnResume = (ImageButton) findViewById(R.id.btn_resume);
        btnResume.setOnClickListener(this);
        Utils.createButtonEffect(btnResume);

        // Init pop up
        Utils.createPopUpEffect(btnMusic);
        Utils.createPopUpEffect(btnSound, 1);
        Utils.createPopUpEffect(btnQuit, 2);

        updateSoundAndMusicButtons();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sound) {
            mParent.getSoundManager().toggleSoundStatus();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.btn_music) {
            mParent.getSoundManager().toggleMusicStatusInGame();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.btn_quit) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            mSelectedId = view.getId();
            super.dismiss();
        } else if (view.getId() == R.id.btn_resume) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            mSelectedId = view.getId();
            super.dismiss();
        }
    }

    @Override
    protected void onDismissed() {
        if (mSelectedId == R.id.btn_quit) {
            quitGame();
        } else if (mSelectedId == R.id.btn_resume) {
            resumeGame();
        }
    }

    // Override this method to quit the game
    public void quitGame() {

    }

    // Override this method to resume the game
    public void resumeGame() {

    }

}
