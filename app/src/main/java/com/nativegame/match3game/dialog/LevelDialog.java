package com.nativegame.match3game.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.database.DatabaseHelper;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.level.Level;
import com.nativegame.match3game.level.LevelType;

/**
 * LevelDialog will show when player press
 * level button in map, and showing highest
 * star, target, and description of level.
 */

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LevelDialog extends BaseDialog implements View.OnClickListener {

    private final Level mLevel;
    private int mSelectedId;

    private final DatabaseHelper mDatabaseHelper;

    public LevelDialog(MainActivity activity, int level) {
        super(activity);
        setContentView(R.layout.dialog_level);

        // Load star data
        mDatabaseHelper = new DatabaseHelper(activity);

        // Init button
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        Utils.createButtonEffect(btnPlay);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        Utils.createButtonEffect(btnCancel);

        // Init pop up
        Utils.createPopUpEffect(btnPlay);

        // We show and hide from left
        setShowAnimation(R.anim.enter_from_left);
        setHideAnimation(R.anim.exit_to_left);

        mLevel = activity.getLevelManager().getLevel(level);
        init();
    }

    private void init() {

        // Init level text
        TextView txtLevel = (TextView) findViewById(R.id.txt_dialog_level);
        txtLevel.setText(mParent.getResources().getString(R.string.txt_level, mLevel.mLevel));

        // Init star
        ImageView imgStar = (ImageView) findViewById(R.id.image_dialog_star);
        int star = mDatabaseHelper.getLevelStar(mLevel.mLevel);
        if (star != -1) {
            switch (star) {
                case 1:
                    imgStar.setImageResource(R.drawable.star_set_1);
                    break;
                case 2:
                    imgStar.setImageResource(R.drawable.star_set_2);
                    break;
                case 3:
                    imgStar.setImageResource(R.drawable.star_set_3);
                    break;

            }
        }

        // Init level target text
        TextView txtTarget = (TextView) findViewById(R.id.txt_dialog_target);
        switch (mLevel.mLevelType) {
            case LEVEL_TYPE_SCORE:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_reach_target_score));
                break;
            case LEVEL_TYPE_COLLECT:
            case LEVEL_TYPE_STARFISH:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_collect_items));
                break;
            case LEVEL_TYPE_ICE:
                txtTarget.setText(mParent.getResources().getString(R.string.txt_break_all_the_ice));
                break;
        }

        // Init level target image
        if (mLevel.mLevelType == LevelType.LEVEL_TYPE_SCORE) {
            TextView textView = (TextView) findViewById(R.id.txt_dialog_target_score);
            textView.setText(String.valueOf(mLevel.mTarget.get(0)));
            textView.setVisibility(View.VISIBLE);
            // Init pop up
            Utils.createPopUpEffect(textView);
        } else {
            if (mLevel.mCollect.size() == 1) {
                ImageView imageView = (ImageView) findViewById(R.id.image_dialog_target_center);
                imageView.setImageResource(mLevel.mCollect.get(0));
                imageView.setVisibility(View.VISIBLE);
                // Init pop up
                Utils.createPopUpEffect(imageView);
            } else if (mLevel.mCollect.size() == 2) {
                ImageView imageView = (ImageView) findViewById(R.id.image_dialog_target_left);
                imageView.setImageResource(mLevel.mCollect.get(0));
                imageView.setVisibility(View.VISIBLE);
                // Init pop up
                Utils.createPopUpEffect(imageView);

                ImageView imageView2 = (ImageView) findViewById(R.id.image_dialog_target_right);
                imageView2.setImageResource(mLevel.mCollect.get(1));
                imageView2.setVisibility(View.VISIBLE);
                // Init pop up
                Utils.createPopUpEffect(imageView2, 1);
            } else if (mLevel.mCollect.size() == 3) {
                ImageView imageView = (ImageView) findViewById(R.id.image_dialog_target_left);
                imageView.setImageResource(mLevel.mCollect.get(0));
                imageView.setVisibility(View.VISIBLE);
                // Init pop up
                Utils.createPopUpEffect(imageView);

                ImageView imageView2 = (ImageView) findViewById(R.id.image_dialog_target_center);
                imageView2.setImageResource(mLevel.mCollect.get(1));
                imageView2.setVisibility(View.VISIBLE);
                // Init pop up
                Utils.createPopUpEffect(imageView2, 1);

                ImageView imageView3 = (ImageView) findViewById(R.id.image_dialog_target_right);
                imageView3.setImageResource(mLevel.mCollect.get(2));
                imageView3.setVisibility(View.VISIBLE);
                // Init pop up
                Utils.createPopUpEffect(imageView3, 2);
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_play) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            mSelectedId = view.getId();
            super.dismiss();
        } else if (view.getId() == R.id.btn_cancel) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            super.dismiss();
        }
    }

    @Override
    protected void onDismissed() {
        if (mSelectedId == R.id.btn_play) {
            startGame();
        }
    }

    // Override this method to start the game
    public void startGame() {

    }

}
