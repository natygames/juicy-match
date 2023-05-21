package com.nativegame.match3game.game.counter;

import android.view.View;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.database.DatabaseHelper;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.game.booster.BombController;
import com.nativegame.match3game.game.booster.BoosterController;
import com.nativegame.match3game.game.booster.GloveController;
import com.nativegame.match3game.game.booster.HammerController;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.match3game.item.Item;
import com.nativegame.match3game.level.Level;
import com.nativegame.match3game.level.TutorialType;
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

    private static final String INFINITE = "∞";

    private final TextView mTxtHammer;
    private final TextView mTxtBomb;
    private final TextView mTxtGlove;
    private final GameButton mBtnHammer;
    private final GameButton mBtnBomb;
    private final GameButton mBtnGlove;
    private final HammerController mHammerController;
    private final BombController mBombController;
    private final GloveController mGloveController;
    private final TutorialType mTutorialType;
    private final DatabaseHelper mDatabaseHelper;

    private BoosterState mState;
    private int mHammerCount;
    private int mBombCount;
    private int mGloveCount;
    private boolean mIsEnable = false;

    private boolean mIsAddBooster = false;
    private boolean mIsRemoveBooster = false;

    private enum BoosterState {
        WAITING,
        HAMMER,
        BOMB,
        GLOVE
    }

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public BoosterCounter(GameActivity activity, Engine engine, TileSystem tileSystem) {
        super(activity, engine);

        // Init booster count text
        mTxtHammer = (TextView) activity.findViewById(R.id.txt_hammer);
        mTxtBomb = (TextView) activity.findViewById(R.id.txt_bomb);
        mTxtGlove = (TextView) activity.findViewById(R.id.txt_gloves);

        // Init booster button
        mBtnHammer = (GameButton) activity.findViewById(R.id.btn_hammer);
        mBtnBomb = (GameButton) activity.findViewById(R.id.btn_bomb);
        mBtnGlove = (GameButton) activity.findViewById(R.id.btn_glove);
        mBtnHammer.setOnClickListener(this);
        mBtnBomb.setOnClickListener(this);
        mBtnGlove.setOnClickListener(this);

        // Init booster controller
        mHammerController = new HammerController(engine, tileSystem);
        mBombController = new BombController(engine, tileSystem);
        mGloveController = new GloveController(engine, tileSystem);
        mHammerController.setListener(this);
        mBombController.setListener(this);
        mGloveController.setListener(this);

        // Check is current level has booster tutorial
        mTutorialType = Level.LEVEL_DATA.getTutorialType();
        switch (mTutorialType) {
            case HAMMER:
                mTxtHammer.setText(INFINITE);
                break;
            case BOMB:
                mTxtBomb.setText(INFINITE);
                break;
            case GLOVE:
                mTxtGlove.setText(INFINITE);
                break;
        }

        mDatabaseHelper = DatabaseHelper.getInstance(activity);
        mHammerCount = mDatabaseHelper.getItemCount(Item.HAMMER);
        mBombCount = mDatabaseHelper.getItemCount(Item.BOMB);
        mGloveCount = mDatabaseHelper.getItemCount(Item.GLOVE);
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
        if (mTutorialType != TutorialType.HAMMER) {
            mTxtHammer.setText(String.valueOf(mHammerCount));
        }
        if (mTutorialType != TutorialType.BOMB) {
            mTxtBomb.setText(String.valueOf(mBombCount));
        }
        if (mTutorialType != TutorialType.GLOVE) {
            mTxtGlove.setText(String.valueOf(mGloveCount));
        }
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
                } else if (id == R.id.btn_bomb) {
                    mState = BoosterState.BOMB;
                } else if (id == R.id.btn_glove) {
                    mState = BoosterState.GLOVE;
                }
                lockButton();
                mIsAddBooster = true;
            } else {
                unLockButton();
                mIsRemoveBooster = true;
            }
            Sounds.BUTTON_CLICK.play();
        }
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case START_GAME:
            case STOP_COMBO:
            case ADD_EXTRA_MOVES:
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
                if (mTutorialType != TutorialType.HAMMER) {
                    mHammerCount--;
                    mDatabaseHelper.updateItemCount(Item.HAMMER, mHammerCount);
                }
                break;
            case BOMB:
                if (mTutorialType != TutorialType.BOMB) {
                    mBombCount--;
                    mDatabaseHelper.updateItemCount(Item.BOMB, mBombCount);
                }
                break;
            case GLOVE:
                if (mTutorialType != TutorialType.GLOVE) {
                    mGloveCount--;
                    mDatabaseHelper.updateItemCount(Item.GLOVE, mGloveCount);
                }
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
            case BOMB:
                mBombController.addToGame();
                break;
            case GLOVE:
                mGloveController.addToGame();
                break;
        }
    }

    private void removeBooster() {
        switch (mState) {
            case HAMMER:
                mHammerController.removeFromGame();
                break;
            case BOMB:
                mBombController.removeFromGame();
                break;
            case GLOVE:
                mGloveController.removeFromGame();
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
            case BOMB:
                mBtnHammer.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnGlove.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnHammer.setEnabled(false);
                mBtnGlove.setEnabled(false);
                break;
            case GLOVE:
                mBtnHammer.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnBomb.addColorFilter(GameButton.DEFAULT_PRESS_COLOR);
                mBtnHammer.setEnabled(false);
                mBtnBomb.setEnabled(false);
                break;
        }
    }

    private void unLockButton() {
        mBtnHammer.removeColorFilter();
        mBtnBomb.removeColorFilter();
        mBtnGlove.removeColorFilter();
        mBtnHammer.setEnabled(true);
        mBtnBomb.setEnabled(true);
        mBtnGlove.setEnabled(true);
    }
    //========================================================

}
