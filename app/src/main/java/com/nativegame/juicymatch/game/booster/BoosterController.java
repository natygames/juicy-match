package com.nativegame.juicymatch.game.booster;

import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;
import com.nativegame.natyengine.entity.shape.primitive.Plane;
import com.nativegame.natyengine.entity.timer.Timer;
import com.nativegame.natyengine.entity.timer.TimerEvent;
import com.nativegame.natyengine.input.touch.TouchEvent;
import com.nativegame.natyengine.input.touch.TouchEventListener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class BoosterController extends Entity implements TouchEventListener, TimerEvent.TimerEventListener {

    private static final long TIME_TO_LIVE = 1500;

    private final Tile[][] mTiles;
    private final Plane mShadowBg;
    private final Timer mTimer;
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
        mMarginX = (GameWorld.WORLD_WIDTH - mTotalCol * 300) / 2;
        mMarginY = (GameWorld.WORLD_HEIGHT - mTotalRow * 300) / 2;
        mShadowBg = new Plane(engine, Colors.BLACK_60);
        mTimer = new Timer(engine);
        mTimer.addTimerEvent(new TimerEvent(this, TIME_TO_LIVE));
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
                if (isAddBooster(mTouchDownTile, mTouchUpTile)) {
                    onAddBooster(mTiles, mTouchDownTile, mTouchUpTile, mTotalRow, mTotalCol);
                    if (mListener != null) {
                        mListener.onConsumeBooster();
                    }
                    mTimer.start();
                    removeFromGame();
                }
                break;
        }
    }

    @Override
    public void onTimerEvent(long eventTime) {
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
