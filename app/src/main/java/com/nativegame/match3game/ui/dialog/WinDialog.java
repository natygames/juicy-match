package com.nativegame.match3game.ui.dialog;

import com.nativegame.match3game.R;
import com.nativegame.nattyengine.ui.GameActivity;

public class WinDialog extends BaseDialog {

    private static final long TIME_TO_LIVE = 1500;

    public WinDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_win);
        setContainerView(R.layout.dialog_container_game);
        setEnterAnimationId(R.anim.enter_from_top);
        setExitAnimationId(R.anim.exit_to_bottom);
        init();
    }

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void init() {
        getContentView().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, TIME_TO_LIVE);
    }
    //========================================================

}
