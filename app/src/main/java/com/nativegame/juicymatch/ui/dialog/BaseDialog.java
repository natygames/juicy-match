package com.nativegame.juicymatch.ui.dialog;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameDialog;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class BaseDialog extends GameDialog {

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
    //========================================================

}
