package com.example.matchgamesample.counter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;
import com.example.matchgamesample.game.TileID;
import com.example.matchgamesample.level.Level;

import java.util.ArrayList;

public class TargetCounter extends GameObject {

    private final Level mLevel;
    private final ArrayList<TextView> mText = new ArrayList<>();
    private boolean mTargetsHaveChanged;

    public TargetCounter(View view, GameEngine gameEngine) {
        mLevel = gameEngine.mLevel;
        if (gameEngine.mLevel.targetType == 1) {
            mText.add(view.findViewById(R.id.target_score));
        } else {
            setTargetImages(gameEngine.mLevel.target.size(), view);
        }

    }

    private void setTargetImages(int target, View view) {
        final ArrayList<ImageView> mTargetsImage = new ArrayList<>();

        ConstraintLayout board_target = (ConstraintLayout) view.findViewById(R.id.board_target);

        switch (target) {
            case 1:
                mText.add(view.findViewById(R.id.target_txt_center));
                mTargetsImage.add(view.findViewById(R.id.target_image_center));
                board_target.setBackgroundResource(R.drawable.board_target_1);
                break;
            case 2:
                mText.add(view.findViewById(R.id.target_txt_leftCenter));
                mText.add(view.findViewById(R.id.target_txt_rightCenter));
                mTargetsImage.add(view.findViewById(R.id.target_image_leftCenter));
                mTargetsImage.add(view.findViewById(R.id.target_image_rightCenter));
                board_target.setBackgroundResource(R.drawable.board_target_2);
                break;
            case 3:
                mText.add(view.findViewById(R.id.target_txt_left));
                mText.add(view.findViewById(R.id.target_txt_center));
                mText.add(view.findViewById(R.id.target_txt_right));
                mTargetsImage.add(view.findViewById(R.id.target_image_left));
                mTargetsImage.add(view.findViewById(R.id.target_image_center));
                mTargetsImage.add(view.findViewById(R.id.target_image_right));
                board_target.setBackgroundResource(R.drawable.board_target_3);
                break;
        }

        // Set target image
        int imgSize = mTargetsImage.size();
        for (int i = 0; i < imgSize; i++) {

            int id = mLevel.collect.get(i);
            mTargetsImage.get(i).setBackgroundResource(id);
            mTargetsImage.get(i).setVisibility(View.VISIBLE);

            // Set scale
            if (id == R.drawable.striped_ball
                    || id == R.drawable.ice
                    || id == TileID.COOKIE) {
                mTargetsImage.get(i).setScaleX(0.8f);
                mTargetsImage.get(i).setScaleY(0.8f);
            }

        }

    }

    @Override
    public void startGame() {
        // Init target text
        int txtSize = mText.size();
        for (int i = 0; i < txtSize; i++) {
            mText.get(i).setText(String.valueOf(mLevel.target.get(i)));
            mText.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDraw() {
        if (mTargetsHaveChanged) {
            // Update target text
            int txtSize = mText.size();
            for (int i = 0; i < txtSize; i++) {
                mText.get(i).setText(String.valueOf(mLevel.target.get(i)));
            }
            mTargetsHaveChanged = false;
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == GameEvent.COLLECT) {
            mTargetsHaveChanged = true;
        }
    }
}
