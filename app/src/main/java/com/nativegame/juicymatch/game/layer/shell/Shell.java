package com.nativegame.juicymatch.game.layer.shell;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.juicymatch.game.layer.LayerSprite;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.entity.modifier.tween.OvershootTweener;
import com.nativegame.nattyengine.entity.sprite.Sprite;
import com.nativegame.nattyengine.entity.sprite.modifier.FadeOutModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.PositionYModifier;
import com.nativegame.nattyengine.entity.sprite.modifier.ScaleModifier;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Shell extends LayerSprite {

    private final ShellType mShellType;
    private final ShellEffect mShellEffect;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public Shell(Engine engine, Texture texture, ShellType sandType) {
        super(engine, texture);
        mShellType = sandType;
        mShellEffect = new ShellEffect(engine, sandType.getTexture());
        setLayer(Layer.SAND_LAYER - 1);
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public ShellType getShellType() {
        return mShellType;
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mShellEffect.activate(mX, mY);
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void playShellEffect() {
        mShellEffect.showRemoveEffect();
        removeFromGame();
        Sounds.COLLECT_SHELL.play();
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    private static class ShellEffect extends Sprite {

        private final ScaleModifier mBounceOutModifier;
        private final ScaleModifier mBounceInModifier;
        private final FadeOutModifier mFadeOutModifier;
        private final PositionYModifier mPositionModifier;

        //--------------------------------------------------------
        // Constructors
        //--------------------------------------------------------
        public ShellEffect(Engine engine, Texture texture) {
            super(engine, texture);
            mBounceOutModifier = new ScaleModifier(1, 1.3f, 250);
            mBounceInModifier = new ScaleModifier(1.3f, 1, 250, 250,
                    OvershootTweener.getInstance());
            mBounceInModifier.setModifyBefore(false);
            mFadeOutModifier = new FadeOutModifier(500, 500);
            mPositionModifier = new PositionYModifier(500, 500);
            mPositionModifier.setAutoRemove(true);
            setLayer(Layer.SAND_LAYER - 1);
        }
        //========================================================

        //--------------------------------------------------------
        // Overriding methods
        //--------------------------------------------------------
        @Override
        public void onUpdate(long elapsedMillis) {
            mBounceOutModifier.update(this, elapsedMillis);
            mBounceInModifier.update(this, elapsedMillis);
            mFadeOutModifier.update(this, elapsedMillis);
            mPositionModifier.update(this, elapsedMillis);
        }
        //========================================================

        //--------------------------------------------------------
        // Methods
        //--------------------------------------------------------
        public void activate(float x, float y) {
            mX = x;
            mY = y;
            addToGame();
        }

        public void showRemoveEffect() {
            mBounceOutModifier.init(this);
            mBounceInModifier.init(this);
            mFadeOutModifier.init(this);
            mPositionModifier.setValue(mY, mY - 800);
            mPositionModifier.init(this);
            setLayer(Layer.TEXT_LAYER + 1);
        }
        //========================================================

    }
    //========================================================

}
