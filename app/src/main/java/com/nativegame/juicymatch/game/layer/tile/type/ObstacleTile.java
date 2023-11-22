package com.nativegame.juicymatch.game.layer.tile.type;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.TileSystem;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class ObstacleTile extends SolidTile {

    protected boolean mIsObstacle = true;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected ObstacleTile(TileSystem tileSystem, Engine engine, Texture texture) {
        super(tileSystem, engine, texture, FruitType.NONE);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public boolean isObstacle() {
        return mIsObstacle;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void popTile() {
        if (mIsObstacle) {
            mTileState = TileState.MATCH;
            return;
        }
        super.popTile();
    }
    //========================================================

}
