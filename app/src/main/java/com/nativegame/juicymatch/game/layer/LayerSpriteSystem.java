package com.nativegame.juicymatch.game.layer;

import com.nativegame.juicymatch.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.Entity;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class LayerSpriteSystem<T extends LayerSprite> extends Entity {

    protected final int mTotalRow;
    protected final int mTotalCol;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected LayerSpriteSystem(Engine engine) {
        super(engine);
        mTotalRow = Level.LEVEL_DATA.getRow();
        mTotalCol = Level.LEVEL_DATA.getColumn();
    }
    //========================================================

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
