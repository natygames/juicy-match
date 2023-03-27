package com.nativegame.match3game.game.layer.tile.type;

import com.nativegame.match3game.algorithm.TileState;
import com.nativegame.match3game.game.layer.tile.FruitType;
import com.nativegame.match3game.game.layer.tile.TileSystem;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class ObstacleTile extends SolidTile {

    protected int mObstacleLayer;
    protected boolean mIsObstacle = true;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected ObstacleTile(TileSystem tileSystem, Engine engine, Texture texture, FruitType fruitType, int obstacleLayer) {
        super(tileSystem, engine, texture, fruitType);
        mObstacleLayer = obstacleLayer;
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getObstacleLayer() {
        return mObstacleLayer;
    }

    public boolean isObstacle() {
        return mIsObstacle;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void playTileEffect() {
        if (mIsObstacle) {
            playObstacleEffect(mObstacleLayer);
            mObstacleLayer--;
            if (mObstacleLayer == 0) {
                // Clear the obstacle
                mIsObstacle = false;
            } else {
                // We prevent the obstacle from reset
                mTileState = TileState.IDLE;
            }
            return;
        }
        super.playTileEffect();
    }

    @Override
    public boolean isShufflable() {
        if (mIsObstacle) {
            return false;
        }
        return super.isShufflable();
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void popObstacleTile() {
        // Important to not reuse popTile() or matchTile()
        if (!mIsObstacle) {
            return;
        }
        mTileState = TileState.MATCH;
    }

    protected abstract void playObstacleEffect(int obstacleLayer);
    //========================================================

}
