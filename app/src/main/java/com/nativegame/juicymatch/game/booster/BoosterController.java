package com.nativegame.juicymatch.game.booster;

import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.game.JuicyMatch;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.entity.primitive.Color;
import com.nativegame.nattyengine.entity.timer.Timer;
import com.nativegame.nattyengine.input.touch.TouchEvent;
import com.nativegame.nattyengine.input.touch.TouchEventListener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class BoosterController extends Entity implements TouchEventListener, Timer.TimerListener {

    private static final long TIME_TO_LIVE = 1500;

    private final Tile[][] mTiles;
    private final Timer mTimer;
    private final Color mShadowBg;
    private final int mTotalRow;
    private final int mTotalCol;
    private final int mMarginX;
    private final int mMarginY;

    private BoosterListener mListener;
    private Tile mTouchDownTile;
    private Tile mTouchUpTile;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected BoosterController(Engine engine, TileSystem tileSystem) {
        super(engine);
        mTiles = tileSystem.getChild();
        mTotalRow = tileSystem.getTotalRow();
        mTotalCol = tileSystem.getTotalColumn();
        mMarginX = (JuicyMatch.WORLD_WIDTH - mTotalCol * 300) / 2;
        mMarginY = (JuicyMatch.WORLD_HEIGHT - mTotalRow * 300) / 2;
        mTimer = new Timer(engine, this, TIME_TO_LIVE);
        mShadowBg = new Color(engine, Colors.BLACK_60);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public BoosterListener getListener() {
        return mListener;
    }

    public void setListener(BoosterListener listener) {
        mListener = listener;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mShadowBg.addToGame();
    }

    @Override
    public void onRemove() {
        mShadowBg.removeFromGame();
    }

    @Override
    public void onTouchEvent(int type, float touchX, float touchY) {
        switch (type) {
            case TouchEvent.TOUCH_DOWN:
                // Reset touch tile
                mTouchDownTile = null;
                mTouchUpTile = null;
                // Check is out of bound
                if (touchX < mMarginX || touchY < mMarginY || touchX > 2700 - mMarginX || touchY > 2700 - mMarginY) {
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
                if (isAddBooster(mTouchDownTile, mTouchUpTile)) {
                    onAddBooster(mTiles, mTouchDownTile, mTouchUpTile, mTotalRow, mTotalCol);
                    if (mListener != null) {
                        mListener.onConsumeBooster();
                    }
                    mTimer.startTimer();
                    removeFromGame();
                }
                break;
        }
    }

    @Override
    public void onTimerComplete(Timer timer) {
        onRemoveBooster(mTiles, mTouchDownTile, mTouchUpTile, mTotalRow, mTotalCol);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected abstract boolean isAddBooster(Tile touchDownTile, Tile touchUpTile);

    protected abstract void onAddBooster(Tile[][] tiles, Tile touchDownTile, Tile touchUpTile, int row, int col);

    protected abstract void onRemoveBooster(Tile[][] tiles, Tile touchDownTile, Tile touchUpTile, int row, int col);
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    public interface BoosterListener {

        void onConsumeBooster();

    }
    //========================================================

}
