package com.nativegame.match3game.ui.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.database.DatabaseHelper;
import com.nativegame.match3game.level.Level;
import com.nativegame.match3game.level.LevelType;
import com.nativegame.match3game.level.TargetType;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameButton;
import com.nativegame.nattyengine.ui.GameImage;
import com.nativegame.nattyengine.ui.GameText;
import com.nativegame.nattyengine.util.resource.ResourceUtils;

import java.util.List;

public class LevelDialog extends BaseDialog {

    private int mSelectedId;

    public LevelDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_level);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        GameButton btnPlay = (GameButton) findViewById(R.id.btn_play);
        btnPlay.popUp(200, 700);
        btnPlay.setOnClickListener(this);
        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        TextView txtLevel = (TextView) findViewById(R.id.txt_level);
        txtLevel.setText(ResourceUtils.getString(activity, R.string.txt_level, Level.LEVEL_DATA.getLevel()));

        initStar();
        initTargetImage();
        initTargetText();
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    protected void onHide() {
        if (mSelectedId == R.id.btn_play) {
            startGame();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.btn_play) {
            mSelectedId = R.id.btn_play;
            dismiss();
        } else if (id == R.id.btn_cancel) {
            mSelectedId = R.id.btn_cancel;
            dismiss();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void initStar() {
        int level = Level.LEVEL_DATA.getLevel();

        ImageView image = (ImageView) findViewById(R.id.image_star);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(mParent);
        int star = databaseHelper.getLevelStar(level);
        if (star != -1) {
            switch (star) {
                case 1:
                    image.setImageResource(R.drawable.star_set_01);
                    break;
                case 2:
                    image.setImageResource(R.drawable.star_set_02);
                    break;
                case 3:
                    image.setImageResource(R.drawable.star_set_03);
                    break;

            }
        }
    }

    private void initTargetImage() {
        List<TargetType> targetTypes = Level.LEVEL_DATA.getTargetTypes();

        GameImage imageTargetA = (GameImage) findViewById(R.id.image_target_01);
        GameImage imageTargetB = (GameImage) findViewById(R.id.image_target_02);
        GameImage imageTargetC = (GameImage) findViewById(R.id.image_target_03);
        switch (targetTypes.size()) {
            case 1:
                imageTargetB.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetB.popUp(200, 300);
                imageTargetA.setVisibility(View.INVISIBLE);
                imageTargetB.setVisibility(View.VISIBLE);
                imageTargetC.setVisibility(View.INVISIBLE);
                break;
            case 2:
                imageTargetA.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetC.setImageResource(targetTypes.get(1).getDrawableId());
                imageTargetA.popUp(200, 300);
                imageTargetC.popUp(200, 400);
                imageTargetA.setVisibility(View.VISIBLE);
                imageTargetB.setVisibility(View.INVISIBLE);
                imageTargetC.setVisibility(View.VISIBLE);
                break;
            case 3:
                imageTargetA.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetB.setImageResource(targetTypes.get(1).getDrawableId());
                imageTargetC.setImageResource(targetTypes.get(2).getDrawableId());
                imageTargetA.popUp(200, 300);
                imageTargetB.popUp(200, 400);
                imageTargetC.popUp(200, 500);
                imageTargetA.setVisibility(View.VISIBLE);
                imageTargetB.setVisibility(View.VISIBLE);
                imageTargetC.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initTargetText() {
        LevelType levelType = Level.LEVEL_DATA.getLevelType();

        GameText txtTarget = (GameText) findViewById(R.id.txt_level_target);
        txtTarget.popUp(200, 600);
        switch (levelType) {
            case COLLECT:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_collect_items));
                break;
            case ICE:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_break_all_the_ice));
                break;
            case STARFISH:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_collect_starfish));
                break;
        }
    }

    public void startGame() {
    }
    //========================================================

}
