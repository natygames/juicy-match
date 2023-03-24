package com.nativegame.match3game.ui.dialog;

import android.view.View;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameDialog;

public abstract class BaseDialog extends GameDialog implements View.OnClickListener {

    protected BaseDialog(GameActivity activity) {
        super(activity);
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onShow() {
        Sounds.DIALOG_SLIDE.play();
    }

    @Override
    protected void onDismiss() {
        Sounds.DIALOG_SLIDE.play();
    }

    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
    }
    //========================================================

}
