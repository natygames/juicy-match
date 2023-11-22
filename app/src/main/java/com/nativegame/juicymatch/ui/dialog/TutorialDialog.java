package com.nativegame.juicymatch.ui.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.level.TutorialType;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.util.ResourceUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TutorialDialog extends BaseDialog implements View.OnClickListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TutorialDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_tutorial);
        setContainerView(R.layout.dialog_container_game);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        // Init tutorial image
        TutorialType tutorialType = Level.LEVEL_DATA.getTutorialType();
        ImageView imageTutorial = (ImageView) findViewById(R.id.image_tutorial);
        imageTutorial.setImageResource(tutorialType.getDrawableId());

        // Init tutorial text
        TextView txtTutorial = (TextView) findViewById(R.id.txt_tutorial);
        txtTutorial.setText(ResourceUtils.getString(activity, tutorialType.getStringId()));

        // Init button
        GameButton btnPlay = (GameButton) findViewById(R.id.btn_play);
        btnPlay.popUp(200, 300);
        btnPlay.setOnClickListener(this);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onHide() {
        showTutorial();
    }

    @Override
    public void onClick(View view) {
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_play) {
            dismiss();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void showTutorial() {
    }
    //========================================================

}
