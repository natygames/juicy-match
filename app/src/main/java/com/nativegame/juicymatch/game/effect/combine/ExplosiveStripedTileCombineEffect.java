package com.nativegame.juicymatch.game.effect.combine;

import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.modifier.ScaleModifier;
import com.nativegame.natyengine.entity.sprite.Sprite;
import com.nativegame.natyengine.texture.Texture;
import com.nativegame.natyengine.util.modifier.tween.AnticipateTweener;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ExplosiveStripedTileCombineEffect extends Sprite {

    private static final long TIME_TO_SCALE = 300;
    private static final long TIME_TO_PAUSE = 100;

    private final ScaleModifier mScaleInModifier;
    private final ScaleModifier mScaleOutModifier;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ExplosiveStripedTileCombineEffect(Engine engine, Texture texture) {
        super(engine, texture);
        mScaleInModifier = new ScaleModifier(1, 2, TIME_TO_SCALE);
        mScaleOutModifier = new ScaleModifier(2, 1, TIME_TO_SCALE, TIME_TO_SCALE + TIME_TO_PAUSE,
                AnticipateTweener.getInstance());
        mScaleOutModifier.setModifyBefore(false);
        mScaleOutModifier.setAutoRemove(true);
        setLayer(GameLayer.EFFECT_LAYER + 1);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onUpdate(long elapsedMillis) {
        mScaleInModifier.update(this, elapsedMillis);
        mScaleOutModifier.update(this, elapsedMillis);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void activate(float x, float y, Texture texture) {
        setCenterX(x);
        setCenterY(y);
        setTexture(texture);
        mScaleInModifier.init(this);
        mScaleOutModifier.init(this);
        addToGame();
    }
    //========================================================

}
