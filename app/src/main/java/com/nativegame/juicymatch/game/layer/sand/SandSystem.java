package com.nativegame.juicymatch.game.layer.sand;

import com.nativegame.juicymatch.game.layer.LayerSpriteSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SandSystem extends LayerSpriteSystem<Sand> {

    private final Sand[][] mSands;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public SandSystem(Engine engine) {
        super(engine);
        mSands = new Sand[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getSand().toCharArray());
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Sand[][] getChild() {
        return mSands;
    }

    @Override
    public Sand getChildAt(int row, int col) {
        return mSands[row][col];
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private void init(char[] chars) {
        for (int i = 0; i < mTotalRow; i++) {
            for (int j = 0; j < mTotalCol; j++) {
                char c = chars[i * mTotalCol + j];
                if (c == 'e') {
                    // We skip the empty type
                    continue;
                }
                SandType type = SandInitializer.getType(c);
                int layer = SandInitializer.getLayer(c);
                Sand sand = new Sand(mEngine, type.getTexture(layer), type, layer);
                sand.setPosition(i, j);
                sand.addToGame();
                mSands[i][j] = sand;
            }
        }
    }
    //========================================================

}
