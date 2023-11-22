package com.nativegame.juicymatch.game.layer.generator;

import com.nativegame.juicymatch.game.layer.LayerSpriteSystem;
import com.nativegame.juicymatch.game.layer.tile.TileResetter;
import com.nativegame.juicymatch.level.Level;
import com.nativegame.natyengine.engine.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class GeneratorSystem extends LayerSpriteSystem<Generator> {

    private final Generator[][] mGenerators;

    private final List<TileResetter> mResetters = new ArrayList<>();

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public GeneratorSystem(Engine engine) {
        super(engine);
        mGenerators = new Generator[mTotalRow][mTotalCol];
        init(Level.LEVEL_DATA.getGenerator().toCharArray());
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public List<TileResetter> getResetters() {
        return mResetters;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public Generator[][] getChild() {
        return mGenerators;
    }

    @Override
    public Generator getChildAt(int row, int col) {
        return mGenerators[row][col];
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
                Generator generator = GeneratorInitializer.getGenerator(mEngine, c);
                generator.setPosition(i, j);
                generator.addToGame();
                mGenerators[i][j] = generator;
                mResetters.add(generator.getResetter());
            }
        }
    }
    //========================================================

}
