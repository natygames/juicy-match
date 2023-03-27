package com.nativegame.match3game.ui.dialog;

import android.view.View;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameDialog;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class BaseDialog extends GameDialog implements View.OnClickListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected BaseDialog(GameActivity activity) {
        super(activity);
    }
    //========================================================

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
