package com.nativegame.match3game.ui.dialog;

import android.view.View;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.ad.AdManager;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameButton;
import com.nativegame.nattyengine.ui.GameImage;
import com.nativegame.nattyengine.ui.GameText;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class MoreLivesDialog extends BaseDialog implements AdManager.AdRewardListener, View.OnClickListener {

    private int mSelectedId;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public MoreLivesDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_more_lives);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        // Init image
        GameImage imageLives = (GameImage) findViewById(R.id.image_lives);
        imageLives.popUp(200, 300);

        // Init text
        GameText txtLives = (GameText) findViewById(R.id.txt_lives);
        txtLives.popUp(200, 500);

        // Init button
        GameButton btnWatchAd = (GameButton) findViewById(R.id.btn_watch_ad);
        btnWatchAd.popUp(200, 700);
        btnWatchAd.setOnClickListener(this);

        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onHide() {
        if (mSelectedId == R.id.btn_watch_ad) {
            showRewardAd();
        }
    }

    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id == R.id.btn_watch_ad) {
            mSelectedId = id;
            dismiss();
        }
    }

    @Override
    public void onEarnReward() {
        ((MainActivity) mParent).getLivesTimer().addLive();
    }

    @Override
    public void onLossReward() {
        // We do nothing
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void showRewardAd() {
        AdManager adManager = ((MainActivity) mParent).getAdManager();
        adManager.setListener(this);
        boolean isConnect = adManager.showRewardAd();
        // Show error dialog if no internet connect
        if (!isConnect) {
            ErrorDialog dialog = new ErrorDialog(mParent) {
                @Override
                public void retry() {
                    adManager.requestAd();
                    showRewardAd();
                }
            };
            mParent.showDialog(dialog);
        }
    }
    //========================================================

}
