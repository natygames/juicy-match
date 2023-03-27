package com.nativegame.match3game.game.counter;

import android.view.View;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.game.booster.BombController;
import com.nativegame.match3game.game.booster.BoosterController;
import com.nativegame.match3game.game.booster.GloveController;
import com.nativegame.match3game.game.booster.HammerController;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.runnable.RunnableEntity;
import com.nativegame.nattyengine.ui.GameActivity;
import com.nativegame.nattyengine.ui.GameButton;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class BoosterCounter extends RunnableEntity implements View.OnClickListener,
        EventListener, BoosterController.BoosterListener {

    private final TextView mTxtHammer;
    private final TextView mTxtGloves;
    private final TextView mTxtBomb;
    private final GameButton mBtnHammer;
    private final GameButton mBtnGlove;
    private final GameButton mBtnBomb;
    private final HammerController mHammerController;
    private final GloveController mGloveController;
    private final BombController mBombController;

    private BoosterState mState;
    private int mHammerCount = 5;
    private int mGlovesCount = 5;
    private int mBombCount = 5;
    private boolean mIsEnable = false;

    private boolean mIsAddBooster = false;
    private boolean mIsRemoveBooster = false;

    private enum BoosterState {
        WAITING,
        HAMMER,
        GLOVE,
        BOMB
    }

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public BoosterCounter(GameActivity activity, Engine engine, TileSystem tileSystem) {
        super(activity, engine);
        mTxtHammer = (TextView) activity.findViewById(R.id.txt_hammer);
        mTxtGloves = (TextView) activity.findViewById(R.id.txt_gloves);
        mTxtBomb = (TextView) activity.findViewById(R.id.txt_bomb);

        mBtnHammer = (GameButton) activity.findViewById(R.id.btn_hammer);
        mBtnGlove = (GameButton) activity.findViewById(R.id.btn_gloves);
        mBtnBomb = (GameButton) activity.findViewById(R.id.btn_bomb);
        mBtnHammer.setOnClickListener(this);
        mBtnGlove.setOnClickListener(this);
        mBtnBomb.setOnClickListener(this);

        mHammerController = new HammerController(engine, tileSystem);
        mGloveController = new GloveController(engine, tileSystem);
        mBombController = new BombController(engine, tileSystem);
        mHammerController.setListener(this);
        mGloveController.setListener(this);
        mBombController.setListener(this);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mState = BoosterState.WAITING;
        setPostRunnable(true);
    }

    @Override
    public void onUpdate(long elapsedMillis) {
        if (mIsAddBooster) {
            addBooster();
            dispatchEvent(GameEvent.ADD_BOOSTER);
            mIsAddBooster = false;
        }

        if (mIsRemoveBooster) {
            removeBooster();
            dispatchEvent(GameEvent.REMOVE_BOOSTER);
            mState = BoosterState.WAITING;
            mIsRemoveBooster = false;
        }
    }

    @Override
    protected void onUpdateRunnable() {
        mTxtHammer.setText(String.valueOf(mHammerCount));
        mTxtGloves.setText(String.valueOf(mGlovesCount));
        mTxtBomb.setText(String.valueOf(mBombCount));
        unLockButton();
    }

    @Override
    public void onClick(View view) {
        synchronized (this) {
            if (!mIsEnable) {
                return;
            }
            if (mState == BoosterState.WAITING) {
                int id = view.getId();
                if (id == R.id.btn_hammer) {
                    mState = BoosterState.HAMMER;
                } else if (id == R.id.btn_gloves) {
                    mState = BoosterState.GLOVE;
                } else if (id == R.id.btn_bomb) {
                    mState = BoosterState.BOMB;
                }
                lockButton();
                mIsAddBooster = true;
            } else {
                unLockButton();
                mIsRemoveBooster = true;
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case START_GAME:
            case STOP_COMBO:
                mIsEnable = true;
                break;
            case PLAYER_SWAP:
                mIsEnable = false;
                break;
            case GAME_WIN:
            case GAME_OVER:
                removeFromGame();
                break;
        }
    }

    @Override
    public void onConsumeBooster() {
        switch (mState) {
            case HAMMER:
                mHammerCount--;
                break;
            case GLOVE:
                mGlovesCount--;
                break;
            case BOMB:
                mBombCount--;
                break;
        }
        setPostRunnable(true);
        mState = BoosterState.WAITING;
        mIsEnable = false;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void addBooster() {
        switch (mState) {
            case HAMMER:
                mHammerController.addToGame();
                break;
            case GLOVE:
                mGloveController.addToGame();
                break;
            case BOMB:
                mBombController.addToGame();
                break;
        }
    }

    private void removeBooster() {
        switch (mState) {
            case HAMMER:
                mHammerController.removeFromGame();
                break;
            case GLOVE:
                mGloveController.removeFromGame();
                break;
            case BOMB:
                mBombController.removeFromGame();
                break;
        }
    }

    private void lockButton() {
        switch (mState) {
            case HAMMER:
                mBtnGlove.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnBomb.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnGlove.setEnabled(false);
                mBtnBomb.setEnabled(false);
                break;
            case GLOVE:
                mBtnHammer.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnBomb.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnHammer.setEnabled(false);
                mBtnBomb.setEnabled(false);
                break;
            case BOMB:
                mBtnHammer.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnGlove.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnHammer.setEnabled(false);
                mBtnGlove.setEnabled(false);
                break;
        }
    }

    private void unLockButton() {
        mBtnHammer.removeColorFilter();
        mBtnGlove.removeColorFilter();
        mBtnBomb.removeColorFilter();
        mBtnHammer.setEnabled(true);
        mBtnGlove.setEnabled(true);
        mBtnBomb.setEnabled(true);
    }
    //========================================================

}
