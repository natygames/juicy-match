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

public class ErrorDialog extends BaseDialog implements View.OnClickListener {

    private int mSelectedId = R.id.btn_cancel;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ErrorDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_error);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        // Init text
        GameText txtError = (GameText) findViewById(R.id.txt_error);
        txtError.popUp(200, 300);

        // Init button
        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        GameButton btnRetry = (GameButton) findViewById(R.id.btn_retry);
        btnRetry.popUp(200, 500);
        btnRetry.setOnClickListener(this);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onHide() {
        if (mSelectedId == R.id.btn_cancel) {
            quit();
        } else if (mSelectedId == R.id.btn_retry) {
            retry();
        }
    }

    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            mSelectedId = id;
            dismiss();
        } else if (id == R.id.btn_retry) {
            mSelectedId = id;
            dismiss();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void quit() {
    }

    public void retry() {
    }
    //========================================================

}
