package com.example.matchgamesample.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.effect.sound.SoundEvent;
import com.example.matchgamesample.level.Level;
import com.example.matchgamesample.level.LevelType;

public class LevelDialog extends BaseDialog implements View.OnClickListener {

    private final Level mLevel;
    private LevelDialogListener mListener;
    private int mSelectedId;

    public LevelDialog(MainActivity activity, int level) {
        super(activity);
        setContentView(R.layout.dialog_level);

        // Init button
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        Utils.createButtonEffect(btnPlay);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        Utils.createButtonEffect(btnCancel);

        // We show and hide from left
        setShowAnimation(R.anim.enter_from_left);
        setHideAnimation(R.anim.exit_to_left);

        mLevel = activity.getLevelManager().getLevel(level);
        init();
    }

    private void init() {

        // Init level text
        TextView txtLevel = (TextView) findViewById(R.id.txt_dialog_level);
        txtLevel.setText("Level " + String.valueOf(mLevel.mLevel));

        // Init level target text
        TextView txtTarget = (TextView) findViewById(R.id.txt_dialog_target);
        switch (mLevel.mLevelType) {
            case LEVEL_TYPE_SCORE:
                txtTarget.setText("Reach target score");
                break;
            case LEVEL_TYPE_COLLECT:
            case LEVEL_TYPE_STARFISH:
                txtTarget.setText("Collect items");
                break;
            case LEVEL_TYPE_ICE:
                txtTarget.setText("Break all the ice");
                break;
        }

        // Init level target image
        if (mLevel.mLevelType == LevelType.LEVEL_TYPE_SCORE) {
            TextView textView = (TextView) findViewById(R.id.txt_dialog_target_score);
            textView.setText(String.valueOf(mLevel.mTarget.get(0)));
            textView.setVisibility(View.VISIBLE);
        } else {
            if (mLevel.mCollect.size() == 1) {
                ImageView imageView = (ImageView) findViewById(R.id.image_dialog_target_center);
                imageView.setImageResource(mLevel.mCollect.get(0));
                imageView.setVisibility(View.VISIBLE);
            } else if (mLevel.mCollect.size() == 2) {
                ImageView imageView = (ImageView) findViewById(R.id.image_dialog_target_left);
                imageView.setImageResource(mLevel.mCollect.get(0));
                imageView.setVisibility(View.VISIBLE);

                ImageView imageView2 = (ImageView) findViewById(R.id.image_dialog_target_right);
                imageView2.setImageResource(mLevel.mCollect.get(1));
                imageView2.setVisibility(View.VISIBLE);
            } else if (mLevel.mCollect.size() == 3) {
                ImageView imageView = (ImageView) findViewById(R.id.image_dialog_target_left);
                imageView.setImageResource(mLevel.mCollect.get(0));
                imageView.setVisibility(View.VISIBLE);

                ImageView imageView2 = (ImageView) findViewById(R.id.image_dialog_target_center);
                imageView2.setImageResource(mLevel.mCollect.get(1));
                imageView2.setVisibility(View.VISIBLE);

                ImageView imageView3 = (ImageView) findViewById(R.id.image_dialog_target_right);
                imageView3.setImageResource(mLevel.mCollect.get(2));
                imageView3.setVisibility(View.VISIBLE);
            }
        }

    }

    public void setListener(LevelDialog.LevelDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_play) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            mSelectedId = view.getId();
            super.dismiss();
        } else if (view.getId() == R.id.btn_cancel) {
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
            mParent.getSoundManager().playSoundForSoundEvent(SoundEvent.SWEEP2);
            super.dismiss();
        }
    }

    @Override
    protected void onDismissed() {
        if (mSelectedId == R.id.btn_play) {
            mListener.startLevel();
        }
    }

    public interface LevelDialogListener {
        void startLevel();
    }
}
