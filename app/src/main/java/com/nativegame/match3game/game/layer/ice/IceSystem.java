package com.nativegame.match3game.game.layer.ice;

import com.nativegame.match3game.game.layer.LayerSystem;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;

public class IceSystem extends LayerSystem<Ice> {

    private final Ice[][] mIces;

    public IceSystem(Engine engine) {
        super(engine);
        mIces = new Ice[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getIce().toCharArray());
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Ice[][] getChild() {
        return mIces;
    }

    @Override
    public Ice getChildAt(int row, int col) {
        return mIces[row][col];
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
                    // We skip the empty ice
                    continue;
                }
                IceType type = IceInitializer.getType(c);
                int layer = IceInitializer.getLayer(c);
                Ice ice = new Ice(mEngine, type.getTexture(layer), type, layer);
                ice.setPosition(i, j);
                ice.addToGame();
                mIces[i][j] = ice;
            }
        }
    }
    //========================================================

}
