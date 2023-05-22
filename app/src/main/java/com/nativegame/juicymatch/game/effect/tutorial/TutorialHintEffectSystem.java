package com.nativegame.juicymatch.game.effect.tutorial;

import com.nativegame.juicymatch.level.Level;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.util.pool.ObjectPool;
import com.nativegame.nattyengine.util.pool.Pool;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class TutorialHintEffectSystem extends Entity {

    private final Pool<TutorialHintEffect> mEffectPool;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TutorialHintEffectSystem(Engine engine) {
        super(engine);
        mEffectPool = new ObjectPool<>(new Pool.PoolObjectFactory<TutorialHintEffect>() {
            @Override
            public TutorialHintEffect createObject() {
                return new TutorialHintEffect(engine, 300, 300);
            }
        }, 50);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(char[] chars) {
        int row = Level.LEVEL_DATA.getRow();
        int col = Level.LEVEL_DATA.getColumn();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char c = chars[i * col + j];
                if (c == 'e') {
                    // We skip the empty type
                    continue;
                }
                TutorialHintEffect effect = mEffectPool.obtainObject();
                effect.activate(j * 300, i * 300);
            }
        }
    }
    //========================================================

}
