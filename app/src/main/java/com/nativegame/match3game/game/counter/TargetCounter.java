package com.nativegame.match3game.game.counter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nativegame.match3game.R;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.level.Level;
import com.nativegame.match3game.level.TargetType;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.runnable.RunnableEntity;
import com.nativegame.nattyengine.ui.GameActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TargetCounter extends RunnableEntity implements EventListener {

    private final List<TargetType> mTargetTypes;
    private final List<Integer> mTargetNums;

    private final List<TextView> mTexts = new ArrayList<>();
    private final List<ImageView> mImages = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TargetCounter(GameActivity activity, Engine engine) {
        super(activity, engine);
        mTargetTypes = Level.LEVEL_DATA.getTargetTypes();
        mTargetNums = Level.LEVEL_DATA.getTargetNums();
        init();
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        setPostRunnable(true);
    }

    @Override
    protected void onUpdateRunnable() {
        int size = mTargetNums.size();
        for (int i = 0; i < size; i++) {
            TextView textView = mTexts.get(i);
            int num = mTargetNums.get(i);
            if (num == 0) {
                textView.setText("");
                textView.setBackgroundResource(R.drawable.check);
            } else {
                textView.setText(String.valueOf(num));
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case PLAYER_COLLECT:
                setPostRunnable(true);
                break;
            case GAME_WIN:
            case GAME_OVER:
                removeFromGame();
                break;
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void init() {
        // Init target layout
        ConstraintLayout layout = (ConstraintLayout) mActivity.findViewById(R.id.layout_target);
        int size = mTargetNums.size();
        switch (size) {
            case 1:
                mTexts.add(mActivity.findViewById(R.id.target_txt_center));
                mImages.add(mActivity.findViewById(R.id.target_image_center));
                layout.setBackgroundResource(R.drawable.board_target_01);
                break;
            case 2:
                mTexts.add(mActivity.findViewById(R.id.target_txt_leftCenter));
                mTexts.add(mActivity.findViewById(R.id.target_txt_rightCenter));
                mImages.add(mActivity.findViewById(R.id.target_image_leftCenter));
                mImages.add(mActivity.findViewById(R.id.target_image_rightCenter));
                layout.setBackgroundResource(R.drawable.board_target_02);
                break;
            case 3:
                mTexts.add(mActivity.findViewById(R.id.target_txt_left));
                mTexts.add(mActivity.findViewById(R.id.target_txt_center));
                mTexts.add(mActivity.findViewById(R.id.target_txt_right));
                mImages.add(mActivity.findViewById(R.id.target_image_left));
                mImages.add(mActivity.findViewById(R.id.target_image_center));
                mImages.add(mActivity.findViewById(R.id.target_image_right));
                layout.setBackgroundResource(R.drawable.board_target_03);
                break;
        }

        // Init target image
        for (int i = 0; i < size; i++) {
            int id = mTargetTypes.get(i).getDrawableId();
            mImages.get(i).setImageResource(id);
            mImages.get(i).setVisibility(View.VISIBLE);
            mTexts.get(i).setVisibility(View.VISIBLE);
        }
    }
    //========================================================

}
