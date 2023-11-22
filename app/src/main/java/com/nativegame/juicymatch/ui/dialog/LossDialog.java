package com.nativegame.juicymatch.ui.dialog;

import com.nativegame.juicymatch.MainActivity;
import com.nativegame.juicymatch.R;
import com.nativegame.natyengine.ui.GameActivity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LossDialog extends BaseDialog {

    private static final long TIME_TO_LIVE = 1500;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LossDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_loss);
        setContainerView(R.layout.dialog_container_game);
        setEnterAnimationId(R.anim.enter_from_top);
        setExitAnimationId(R.anim.exit_to_bottom);

        // Reduce one live
        ((MainActivity) mParent).getLivesTimer().reduceLive();

        // Dismiss the dialog after 1500ms
        getContentView().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, TIME_TO_LIVE);
    }
    //========================================================

}
