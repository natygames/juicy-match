package com.nativegame.juicymatch.game.layer;

import com.nativegame.juicymatch.game.JuicyMatch;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public abstract class LayerSprite extends Sprite {

    protected final int mMarginX;
    protected final int mMarginY;

    protected int mRow;
    protected int mCol;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    protected LayerSprite(Engine engine, Texture texture) {
        super(engine, texture);
        mMarginX = (JuicyMatch.WORLD_WIDTH - Level.LEVEL_DATA.getColumn() * mWidth) / 2;
        mMarginY = (JuicyMatch.WORLD_HEIGHT - Level.LEVEL_DATA.getRow() * mHeight) / 2;
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getRow() {
        return mRow;
    }

    public void setRow(int row) {
        mRow = row;
    }

    public int getColumn() {
        return mCol;
    }

    public void setColumn(int column) {
        mCol = column;
    }

    public void setPosition(int row, int col) {
        mRow = row;
        mCol = col;
        mX = col * mWidth + mMarginX;
        mY = row * mHeight + mMarginY;
    }
    //========================================================

}
