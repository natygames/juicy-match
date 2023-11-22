package com.nativegame.juicymatch.game.counter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.juicymatch.level.TargetType;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.runnable.RunnableEntity;
import com.nativegame.natyengine.event.Event;
import com.nativegame.natyengine.event.EventListener;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TargetCounter extends RunnableEntity implements EventListener {

    private final Animation mPulseAnimation;

    private final List<Integer> mTargetCounts = new ArrayList<>();
    private final List<ImageView> mTargetImages = new ArrayList<>();
    private final List<TextView> mTargetTexts = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TargetCounter(GameActivity activity, Engine engine) {
        super(activity, engine);
        mPulseAnimation = AnimationUtils.loadAnimation(activity, R.anim.target_pulse);
        mTargetCounts.addAll(Level.LEVEL_DATA.getTargetCounts());
        initLevelText();
        initTargetImage();
        initTargetText();
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
        List<Integer> targetCounts = Level.LEVEL_DATA.getTargetCounts();
        int size = mTargetCounts.size();
        for (int i = 0; i < size; i++) {
            // Check is target change
            int count = targetCounts.get(i);
            if (count == mTargetCounts.get(i)) {
                continue;
            }
            mTargetCounts.set(i, count);

            // Update target text
            TextView txtTarget = mTargetTexts.get(i);
            if (count == 0) {
                txtTarget.setText("");
                txtTarget.setBackgroundResource(R.drawable.ui_sign_check);
            } else {
                txtTarget.setText(String.valueOf(count));
            }

            // Play target image animation
            ImageView imageTarget = mTargetImages.get(i);
            imageTarget.startAnimation(mPulseAnimation);
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
    private void initLevelText() {
        TextView txtLevel = (TextView) mActivity.findViewById(R.id.txt_level);
        txtLevel.setText(ResourceUtils.getString(mActivity, R.string.txt_level, Level.LEVEL_DATA.getLevel()));
    }

    private void initTargetImage() {
        List<TargetType> targetTypes = Level.LEVEL_DATA.getTargetTypes();
        // Init target image from TargetType
        ImageView imageTargetA = (ImageView) mActivity.findViewById(R.id.image_target_01);
        ImageView imageTargetB = (ImageView) mActivity.findViewById(R.id.image_target_02);
        ImageView imageTargetC = (ImageView) mActivity.findViewById(R.id.image_target_03);
        switch (targetTypes.size()) {
            case 1:
                imageTargetB.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetA.setVisibility(View.GONE);
                imageTargetB.setVisibility(View.VISIBLE);
                imageTargetC.setVisibility(View.GONE);
                mTargetImages.add(imageTargetB);
                break;
            case 2:
                imageTargetA.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetC.setImageResource(targetTypes.get(1).getDrawableId());
                imageTargetA.setVisibility(View.VISIBLE);
                imageTargetB.setVisibility(View.GONE);
                imageTargetC.setVisibility(View.VISIBLE);
                mTargetImages.add(imageTargetA);
                mTargetImages.add(imageTargetC);
                break;
            case 3:
                imageTargetA.setImageResource(targetTypes.get(0).getDrawableId());
                imageTargetB.setImageResource(targetTypes.get(1).getDrawableId());
                imageTargetC.setImageResource(targetTypes.get(2).getDrawableId());
                imageTargetA.setVisibility(View.VISIBLE);
                imageTargetB.setVisibility(View.VISIBLE);
                imageTargetC.setVisibility(View.VISIBLE);
                mTargetImages.add(imageTargetA);
                mTargetImages.add(imageTargetB);
                mTargetImages.add(imageTargetC);
                break;
        }
    }

    private void initTargetText() {
        List<Integer> targetCounts = Level.LEVEL_DATA.getTargetCounts();
        // Init target text from TargetType
        TextView txtTargetA = (TextView) mActivity.findViewById(R.id.txt_target_01);
        TextView txtTargetB = (TextView) mActivity.findViewById(R.id.txt_target_02);
        TextView txtTargetC = (TextView) mActivity.findViewById(R.id.txt_target_03);
        switch (targetCounts.size()) {
            case 1:
                txtTargetB.setText(String.valueOf(targetCounts.get(0)));
                txtTargetA.setVisibility(View.GONE);
                txtTargetB.setVisibility(View.VISIBLE);
                txtTargetC.setVisibility(View.GONE);
                mTargetTexts.add(txtTargetB);
                break;
            case 2:
                txtTargetA.setText(String.valueOf(targetCounts.get(0)));
                txtTargetC.setText(String.valueOf(targetCounts.get(1)));
                txtTargetA.setVisibility(View.VISIBLE);
                txtTargetB.setVisibility(View.GONE);
                txtTargetC.setVisibility(View.VISIBLE);
                mTargetTexts.add(txtTargetA);
                mTargetTexts.add(txtTargetC);
                break;
            case 3:
                txtTargetA.setText(String.valueOf(targetCounts.get(0)));
                txtTargetB.setText(String.valueOf(targetCounts.get(1)));
                txtTargetC.setText(String.valueOf(targetCounts.get(2)));
                txtTargetA.setVisibility(View.VISIBLE);
                txtTargetB.setVisibility(View.VISIBLE);
                txtTargetC.setVisibility(View.VISIBLE);
                mTargetTexts.add(txtTargetA);
                mTargetTexts.add(txtTargetB);
                mTargetTexts.add(txtTargetC);
                break;
        }
    }
    //========================================================

}
