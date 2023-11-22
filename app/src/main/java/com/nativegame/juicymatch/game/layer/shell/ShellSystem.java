package com.nativegame.juicymatch.game.layer.shell;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.layer.LayerSpriteSystem;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ShellSystem extends LayerSpriteSystem<Shell> {

    private final Shell[][] mShells;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ShellSystem(Engine engine) {
        super(engine);
        mShells = new Shell[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getShell().toCharArray());
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Shell[][] getChild() {
        return mShells;
    }

    @Override
    public Shell getChildAt(int row, int col) {
        return mShells[row][col];
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
                ShellType type = ShellInitializer.getType(c);
                Shell shell = new Shell(mEngine, Textures.EMPTY, type);
                shell.setPosition(i, j);
                shell.addToGame();
                mShells[i][j] = shell;
            }
        }
    }
    //========================================================

}
