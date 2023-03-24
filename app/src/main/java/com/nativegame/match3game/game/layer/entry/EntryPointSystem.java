package com.nativegame.match3game.game.layer.entry;

import com.nativegame.match3game.game.layer.LayerSystem;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;

public class EntryPointSystem extends LayerSystem<EntryPoint> {

    private final EntryPoint[][] mEntryPoints;

    public EntryPointSystem(Engine engine) {
        super(engine);
        mEntryPoints = new EntryPoint[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getEntry().toCharArray());
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public EntryPoint[][] getChild() {
        return mEntryPoints;
    }

    @Override
    public EntryPoint getChildAt(int row, int col) {
        return mEntryPoints[row][col];
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
                    // We skip the empty entry
                    continue;
                }
                EntryPointType type = EntryPointInitializer.getType(c);
                EntryPoint entry = new EntryPoint(mEngine, type.getTexture(), type);
                entry.setPosition(i, j);
                entry.addToGame();
                mEntryPoints[i][j] = entry;
            }
        }
    }
    //========================================================

}
