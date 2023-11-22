package com.nativegame.juicymatch.game.layer.lock;

import com.nativegame.juicymatch.game.layer.LayerSpriteSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LockSystem extends LayerSpriteSystem<Lock> {

    private final Lock[][] mLocke;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public LockSystem(Engine engine) {
        super(engine);
        mLocke = new Lock[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getLock().toCharArray());
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Lock[][] getChild() {
        return mLocke;
    }

    @Override
    public Lock getChildAt(int row, int col) {
        return mLocke[row][col];
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
                LockType type = LockInitializer.getType(c);
                Lock lock = new Lock(mEngine, type.getTexture(), type);
                lock.setPosition(i, j);
                lock.addToGame();
                mLocke[i][j] = lock;
            }
        }
    }
    //========================================================

}
