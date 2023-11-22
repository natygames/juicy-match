package com.nativegame.juicymatch.ui.dialog;

import android.view.View;
import android.widget.TextView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.database.DatabaseHelper;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.level.TargetType;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameButton;
import com.nativegame.natyengine.ui.GameImage;
import com.nativegame.natyengine.ui.GameText;
import com.nativegame.natyengine.util.ResourceUtils;

import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LevelDialog extends BaseDialog implements View.OnClickListener {

    private int mSelectedId;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LevelDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_level);
        setContainerView(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);

        // Init level text
        TextView txtLevel = (TextView) findViewById(R.id.txt_level);
        txtLevel.setText(ResourceUtils.getString(activity, R.string.txt_level, Level.LEVEL_DATA.getLevel()));

        // Init button
        GameButton btnPlay = (GameButton) findViewById(R.id.btn_play);
        btnPlay.popUp(200, 700);
        btnPlay.setOnClickListener(this);

        GameButton btnCancel = (GameButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        initStar();
        initTargetImage();
        initTargetText();
    }
    //========================================================

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
        Sounds.BUTTON_CLICK.play();
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id == R.id.btn_play) {
            mSelectedId = id;
            dismiss();
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void initStar() {
        int level = Level.LEVEL_DATA.getLevel();
        // Init star image from current level star
        GameImage imageStar = (GameImage) findViewById(R.id.image_star);
        imageStar.popUp(200, 200);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(mParent);
        int star = databaseHelper.getLevelStar(level);
        if (star != -1) {
            switch (star) {
                case 1:
                    imageStar.setImageResource(R.drawable.ui_star_set_01);
                    break;
                case 2:
                    imageStar.setImageResource(R.drawable.ui_star_set_02);
                    break;
                case 3:
                    imageStar.setImageResource(R.drawable.ui_star_set_03);
                    break;

            }
        }
    }

    private void initTargetImage() {
        List<TargetType> targetTypes = Level.LEVEL_DATA.getTargetTypes();
        // Init target image from TargetType
        GameImage imageTargetA = (GameImage) findViewById(R.id.image_target_01);
        GameImage imageTargetB = (GameImage) findViewById(R.id.image_target_02);
        GameImage imageTargetC = (GameImage) findViewById(R.id.image_target_03);
        switch (targetTypes.size()) {
            case 1:
                imageTargetB.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetB.popUp(200, 300);
                imageTargetA.setVisibility(View.GONE);
                imageTargetB.setVisibility(View.VISIBLE);
                imageTargetC.setVisibility(View.GONE);
                break;
            case 2:
                imageTargetA.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetC.setImageResource(targetTypes.get(1).getDrawableId());
                imageTargetA.popUp(200, 300);
                imageTargetC.popUp(200, 400);
                imageTargetA.setVisibility(View.VISIBLE);
                imageTargetB.setVisibility(View.GONE);
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
        List<Integer> targetTypes = Level.LEVEL_DATA.getTargetCounts();
        // Init target image from TargetType
        GameText txtTargetA = (GameText) findViewById(R.id.txt_target_01);
        GameText txtTargetB = (GameText) findViewById(R.id.txt_target_02);
        GameText txtTargetC = (GameText) findViewById(R.id.txt_target_03);
        switch (targetTypes.size()) {
            case 1:
                txtTargetB.setText(String.valueOf(targetTypes.get(0)));
                txtTargetB.popUp(200, 300);
                txtTargetA.setVisibility(View.GONE);
                txtTargetB.setVisibility(View.VISIBLE);
                txtTargetC.setVisibility(View.GONE);
                break;
            case 2:
                txtTargetA.setText(String.valueOf(targetTypes.get(0)));
                txtTargetC.setText(String.valueOf(targetTypes.get(1)));
                txtTargetA.popUp(200, 300);
                txtTargetC.popUp(200, 400);
                txtTargetA.setVisibility(View.VISIBLE);
                txtTargetB.setVisibility(View.GONE);
                txtTargetC.setVisibility(View.VISIBLE);
                break;
            case 3:
                txtTargetA.setText(String.valueOf(targetTypes.get(0)));
                txtTargetB.setText(String.valueOf(targetTypes.get(1)));
                txtTargetC.setText(String.valueOf(targetTypes.get(2)));
                txtTargetA.popUp(200, 300);
                txtTargetB.popUp(200, 400);
                txtTargetC.popUp(200, 500);
                txtTargetA.setVisibility(View.VISIBLE);
                txtTargetB.setVisibility(View.VISIBLE);
                txtTargetC.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void startGame() {
    }
    //========================================================

}
