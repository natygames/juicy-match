package com.example.matchgamesample.game.counter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.game.tile.TileUtils;
import com.example.matchgamesample.level.Level;
import com.example.matchgamesample.level.LevelType;

import java.util.ArrayList;

public class TargetCounter extends GameObject {

    private GameEngine mGameEngine;
    private final Level mLevel;
    private final ArrayList<TextView> mText = new ArrayList<>();
    private boolean mTargetsHaveChanged;

    public TargetCounter(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mLevel = gameEngine.mLevel;
        if (gameEngine.mLevel.mLevelType == LevelType.LEVEL_TYPE_SCORE) {
            mText.add(gameEngine.mActivity.findViewById(R.id.target_score));
        } else {
            setTargetImages(gameEngine);
        }

        mTargetsHaveChanged = false;
    }

    private void setTargetImages(GameEngine gameEngine) {
        Activity activity = gameEngine.mActivity;
        final ArrayList<ImageView> mTargetsImage = new ArrayList<>();

        ConstraintLayout board_target = (ConstraintLayout) activity.findViewById(R.id.board_target);

        switch (gameEngine.mLevel.mTarget.size()) {
            case 1:
                mText.add(activity.findViewById(R.id.target_txt_center));
                mTargetsImage.add(activity.findViewById(R.id.target_image_center));
                board_target.setBackgroundResource(R.drawable.board_target_1);
                break;
            case 2:
                mText.add(activity.findViewById(R.id.target_txt_leftCenter));
                mText.add(activity.findViewById(R.id.target_txt_rightCenter));
                mTargetsImage.add(activity.findViewById(R.id.target_image_leftCenter));
                mTargetsImage.add(activity.findViewById(R.id.target_image_rightCenter));
                board_target.setBackgroundResource(R.drawable.board_target_2);
                break;
            case 3:
                mText.add(activity.findViewById(R.id.target_txt_left));
                mText.add(activity.findViewById(R.id.target_txt_center));
                mText.add(activity.findViewById(R.id.target_txt_right));
                mTargetsImage.add(activity.findViewById(R.id.target_image_left));
                mTargetsImage.add(activity.findViewById(R.id.target_image_center));
                mTargetsImage.add(activity.findViewById(R.id.target_image_right));
                board_target.setBackgroundResource(R.drawable.board_target_3);
                break;
        }

        // Set mTarget image
        int imgSize = mTargetsImage.size();
        for (int i = 0; i < imgSize; i++) {

            int id = mLevel.mCollect.get(i);
            mTargetsImage.get(i).setBackgroundResource(id);
            mTargetsImage.get(i).setVisibility(View.VISIBLE);

            // Set scale
            if (id == R.drawable.striped_ball
                    || id == R.drawable.ice
                    || id == TileUtils.COOKIE) {
                mTargetsImage.get(i).setScaleX(0.8f);
                mTargetsImage.get(i).setScaleY(0.8f);
            }

        }

    }

    @Override
    public void startGame() {
        // Init mTarget text
        int txtSize = mText.size();
        for (int i = 0; i < txtSize; i++) {
            mText.get(i).setVisibility(View.VISIBLE);
        }
        mTargetsHaveChanged = true;
    }

    @Override
    public void onUpdate(long elapsedMillis) {

    }

    @Override
    public void onDraw() {
        if (mTargetsHaveChanged) {
            // Update mTarget text
            int txtSize = mText.size();
            for (int i = 0; i < txtSize; i++) {
                mText.get(i).setText(String.valueOf(mLevel.mTarget.get(i)));
            }
            mTargetsHaveChanged = false;
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        switch (gameEvents){
            case PLAYER_COLLECT:
                mTargetsHaveChanged = true;
                break;
            case PLAYER_REACH_TARGET:
            case PLAYER_OUT_OF_MOVE:
                mGameEngine.removeGameObject(this);
                break;

        }
    }
}
