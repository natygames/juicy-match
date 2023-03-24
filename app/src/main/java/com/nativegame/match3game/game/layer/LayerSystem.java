package com.nativegame.match3game.game.layer;

import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.Entity;

public abstract class LayerSystem<T extends LayerSprite> extends Entity {

    protected final int mTotalRow;
    protected final int mTotalCol;

    protected LayerSystem(Engine engine) {
        super(engine);
        mTotalRow = Level.LEVEL_DATA.getRow();
        mTotalCol = Level.LEVEL_DATA.getColumn();
    }

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getTotalRow() {
        return mTotalRow;
    }

    public int getTotalColumn() {
        return mTotalCol;
    }

    public abstract T[][] getChild();

    public abstract T getChildAt(int row, int col);
    //========================================================

}
