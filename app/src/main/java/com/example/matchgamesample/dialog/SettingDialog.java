package com.example.matchgamesample.dialog;

import android.view.View;
import android.widget.ImageButton;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;

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
    }

    private void updateSoundAndMusicButtons() {
        /*
        SoundManager soundManager = mParent.getSoundManager();
        boolean music = soundManager.getMusicStatus();
        ImageView btnMusic = (ImageView) findViewById(R.id.btn_music);
        if (music) {
            btnMusic.setImageResource(R.drawable.music_on_no_bg);
        }
        else {
            btnMusic.setImageResource(R.drawable.music_off_no_bg);
        }
        boolean sound = soundManager.getSoundStatus();
        ImageView btnSounds= (ImageView) findViewById(R.id.btn_sound);
        if (sound) {
            btnSounds.setImageResource(R.drawable.sounds_on_no_bg);
        }
        else {
            btnSounds.setImageResource(R.drawable.sounds_off_no_bg);
        }

         */
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sound) {
            //mParent.getSoundManager().toggleSoundStatus();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.btn_music) {
            //mParent.getSoundManager().toggleMusicStatus();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.btn_cancel) {
            super.dismiss();
        }
    }
}
