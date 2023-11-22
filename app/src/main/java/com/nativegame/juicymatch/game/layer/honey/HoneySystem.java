package com.nativegame.juicymatch.game.layer.honey;

import com.nativegame.juicymatch.game.layer.LayerSpriteSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HoneySystem extends LayerSpriteSystem<Honey> {

    private final Honey[][] mHoneys;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public HoneySystem(Engine engine) {
        super(engine);
        mHoneys = new Honey[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getHoney().toCharArray());
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Honey[][] getChild() {
        return mHoneys;
    }

    @Override
    public Honey getChildAt(int row, int col) {
        return mHoneys[row][col];
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
                HoneyType type = HoneyInitialize.getType(Character.toLowerCase(c));
                Honey honey = new Honey(mEngine, type.getTexture(), type);
                honey.setPosition(i, j);
                if (Character.isUpperCase(c)) {
                    honey.addToGame();
                }
                mHoneys[i][j] = honey;
            }
        }
    }
    //========================================================

}

