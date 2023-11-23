package com.nativegame.juicymatch.game.swap;

import com.nativegame.juicymatch.algorithm.Match3Algorithm;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.algorithm.special.combine.SpecialCombineHandler;
import com.nativegame.juicymatch.game.algorithm.special.combine.SpecialCombineHandlerManager;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;
import com.nativegame.natyengine.entity.timer.Timer;
import com.nativegame.natyengine.entity.timer.TimerEvent;
import com.nativegame.natyengine.event.Event;
import com.nativegame.natyengine.event.EventListener;
import com.nativegame.natyengine.input.touch.TouchEvent;
import com.nativegame.natyengine.input.touch.TouchEventListener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SwapController extends Entity implements TouchEventListener,
        EventListener, SwapModifier.SwapListener, TimerEvent.TimerEventListener {

    private final Tile[][] mTiles;
    private final int mTotalRow;
    private final int mTotalCol;
    private final int mMarginX;
    private final int mMarginY;
    private final SwapModifier mSwapModifier;
    private final SwapModifier mSwapBackModifier;
    private final SpecialCombineHandlerManager mSpecialCombineHandler;
    private final Timer mTimer;

    private Tile mTouchDownTile;
    private Tile mTouchUpTile;
    private boolean mEnable = false;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SwapController(Engine engine, TileSystem tileSystem) {
        super(engine);
        mTiles = tileSystem.getChild();
        mTotalRow = tileSystem.getTotalRow();
        mTotalCol = tileSystem.getTotalColumn();
        mMarginX = (GameWorld.WORLD_WIDTH - mTotalCol * 300) / 2;
        mMarginY = (GameWorld.WORLD_HEIGHT - mTotalRow * 300) / 2;
        mSwapModifier = new SwapModifier(engine);
        mSwapModifier.setListener(this);
        mSwapBackModifier = new SwapModifier(engine);
        mSpecialCombineHandler = new SpecialCombineHandlerManager(engine);
        mTimer = new Timer(engine);
        mTimer.addTimerEvent(new TimerEvent(this));
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onTouchEvent(int type, float touchX, float touchY) {
        if (!mEnable) {
            return;
        }
        switch (type) {
            case TouchEvent.TOUCH_DOWN:
                // Check is out of bound
                if (touchX < mMarginX
                        || touchY < mMarginY
                        || touchX > GameWorld.WORLD_WIDTH - mMarginX
                        || touchY > GameWorld.WORLD_HEIGHT - mMarginY) {
                    return;
                }
                int touchDownCol = (int) ((touchX - mMarginX) / 300);
                int touchDownRow = (int) ((touchY - mMarginY) / 300);
                mTouchDownTile = mTiles[touchDownRow][touchDownCol];
                mTouchDownTile.selectTile();
                break;
            case TouchEvent.TOUCH_UP:
                if (mTouchDownTile == null) {
                    return;
                }
                mTouchDownTile.unSelectTile();
                int row = mTouchDownTile.getRow();
                int col = mTouchDownTile.getColumn();
                if (touchX < mTouchDownTile.getX()) {
                    // Swap left tile
                    if (col > 0) {
                        mTouchUpTile = mTiles[row][col - 1];
                    }
                } else if (touchX > mTouchDownTile.getEndX()) {
                    // Swap right tile
                    if (col < mTotalCol - 1) {
                        mTouchUpTile = mTiles[row][col + 1];
                    }
                } else if (touchY < mTouchDownTile.getY()) {
                    // Swap up tile
                    if (row > 0) {
                        mTouchUpTile = mTiles[row - 1][col];
                    }
                } else if (touchY > mTouchDownTile.getEndY()) {
                    // Swap down tile
                    if (row < mTotalRow - 1) {
                        mTouchUpTile = mTiles[row + 1][col];
                    }
                }
                // Check is tile swappable
                if (mTouchUpTile != null && mTouchUpTile.isSwappable() && mTouchDownTile.isSwappable()) {
                    Match3Algorithm.swapTile(mTiles, mTouchDownTile, mTouchUpTile);
                    mSwapModifier.activate(mTouchDownTile, mTouchUpTile);
                    mEnable = false;
                }
                // Reset touch tile
                mTouchDownTile = null;
                mTouchUpTile = null;
                break;
        }
    }

    @Override
    public void onSwap(Tile tileA, Tile tileB) {
        // Check is special combine detected
        if (checkSpecialCombine(tileA, tileB)) {
            mTimer.start();
        } else {
            // Otherwise, check is player match any tile
            Match3Algorithm.findMatchTile(mTiles, mTotalRow, mTotalCol);
            if (Match3Algorithm.isMatch(mTiles, mTotalRow, mTotalCol)) {
                // Start the Algorithm if found
                tileA.setSelect(true);
                tileB.setSelect(true);
                dispatchEvent(GameEvent.PLAYER_SWAP);
            } else {
                // Swap back if not found
                Match3Algorithm.swapTile(mTiles, tileA, tileB);
                mSwapBackModifier.activate(tileA, tileB);
                mEnable = true;
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case START_GAME:
            case STOP_COMBO:
            case REMOVE_BOOSTER:
            case ADD_EXTRA_MOVES:
                mEnable = true;
                break;
            case ADD_BOOSTER:
                mEnable = false;
                break;
            case GAME_WIN:
            case GAME_OVER:
                removeFromGame();
                break;
        }
    }

    @Override
    public void onTimerEvent(long eventTime) {
        dispatchEvent(GameEvent.PLAYER_SWAP);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private boolean checkSpecialCombine(Tile tileA, Tile tileB) {
        SpecialCombineHandler handler = mSpecialCombineHandler.checkSpecialCombine(mTiles, tileA, tileB, mTotalRow, mTotalCol);
        if (handler != null) {
            mTimer.getAllTimerEvents().get(0).setEventTime(handler.getStartDelay());
            return true;
        }

        return false;
    }
    //========================================================

}
