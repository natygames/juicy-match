package com.nativegame.match3game.game.layer.grid;

import com.nativegame.match3game.game.layer.LayerSystem;
import com.nativegame.match3game.level.Level;
import com.nativegame.nattyengine.engine.Engine;

public class GridSystem extends LayerSystem<Grid> {

    private final Grid[][] mGrids;

    public GridSystem(Engine engine) {
        super(engine);
        mGrids = new Grid[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getGrid().toCharArray());
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Grid[][] getChild() {
        return mGrids;
    }

    @Override
    public Grid getChildAt(int row, int col) {
        return mGrids[row][col];
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
                    // We skip the empty grid
                    continue;
                }
                GridType type = GridInitializer.getType(c);
                Grid grid = new Grid(mEngine, type.getTexture(), type);
                grid.setPosition(i, j);
                grid.addToGame();
                mGrids[i][j] = grid;
            }
        }
    }
    //========================================================

}
