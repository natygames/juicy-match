package com.nativegame.match3game.dialog;

import android.view.View;
import android.widget.ImageButton;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.effect.sound.SoundEvent;

/**
 * ExitDialog will show when player
 * press back button in menu.
 */

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExitDialog extends BaseDialog implements View.OnClickListener {

    public ExitDialog(MainActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_exit);

        // Init button
        ImageButton btnExit = (ImageButton) findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(this);
        Utils.createButtonEffect(btnExit);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        Utils.createButtonEffect(btnCancel);

        // Init pop up
        Utils.createPopUpEffect(btnExit);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_exit) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            exit();
            super.dismiss();
        } else if (view.getId() == R.id.btn_cancel) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            super.dismiss();
        }
    }

    // Override this method to close the app
    public void exit() {

    }

}
