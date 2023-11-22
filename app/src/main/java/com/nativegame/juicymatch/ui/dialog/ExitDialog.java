package com.nativegame.juicymatch.ui.dialog;

import android.view.View;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.ui.GameText;

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

        // Init text
        GameText txtExit = (GameText) findViewById(R.id.txt_exit);
        txtExit.popUp(200, 300);

        // Init button
        GameButton btnExit = (GameButton) findViewById(R.id.btn_exit);
        btnExit.popUp(200, 500);
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
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id == R.id.btn_exit) {
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
