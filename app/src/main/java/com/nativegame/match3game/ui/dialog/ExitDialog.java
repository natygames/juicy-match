package com.nativegame.match3game.ui.dialog;

import android.view.View;

import com.nativegame.match3game.R;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameButton;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExitDialog extends BaseDialog implements View.OnClickListener {

    private int mSelectedId;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExitDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_exit);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        GameButton btnExit = (GameButton) findViewById(R.id.btn_exit);
        btnExit.popUp(200, 300);
        btnExit.setOnClickListener(this);
        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onHide() {
        if (mSelectedId == R.id.btn_exit) {
            exit();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.btn_exit) {
            mSelectedId = id;
            dismiss();
        } else if (id == R.id.btn_cancel) {
            mSelectedId = id;
            dismiss();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void exit() {
    }
    //========================================================

}
