package com.nativegame.juicymatch.game.layer.tile.type;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class LayerObstacleTile extends ObstacleTile {

    protected int mObstacleLayer;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected LayerObstacleTile(TileSystem tileSystem, Engine engine, Texture texture, int obstacleLayer) {
        super(tileSystem, engine, texture);
        mObstacleLayer = obstacleLayer;
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getObstacleLayer() {
        return mObstacleLayer;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void playTileEffect() {
        if (mIsObstacle) {
            // Reduce one layer and update state
            mObstacleLayer--;
            onUpdateLayer(mObstacleLayer);
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
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    protected abstract void onUpdateLayer(int obstacleLayer);
    //========================================================

}
