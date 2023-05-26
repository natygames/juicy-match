package com.nativegame.juicymatch.game.counter;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.JuicyMatch;
import com.nativegame.juicymatch.game.effect.TextEffect;
import com.nativegame.juicymatch.game.layer.Layer;
import com.nativegame.nattyengine.audio.sound.Sound;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.entity.particles.ParticleSystem;
import com.nativegame.nattyengine.entity.particles.ParticleSystemGroup;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ComboCounter extends Entity implements EventListener {

    private static final int COMBO_NICE = 4;
    private static final int COMBO_GREAT = 5;
    private static final int COMBO_WONDERFUL = 6;

    private final ParticleSystemGroup mLeftConfettiParticleSystem = new ParticleSystemGroup();
    private final ParticleSystemGroup mRightConfettiParticleSystem = new ParticleSystemGroup();
    private final TextEffect mComboText;

    private int mCombo;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public ComboCounter(Engine engine) {
        super(engine);
        mLeftConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_BLUE, 15));
        mLeftConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_GREEN, 15));
        mLeftConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_PINK, 15));
        mLeftConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_YELLOW, 15));
        mRightConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_BLUE, 15));
        mRightConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_GREEN, 15));
        mRightConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_PINK, 15));
        mRightConfettiParticleSystem.addParticleSystem(new ParticleSystem(engine, Textures.CONFETTI_YELLOW, 15));
        mLeftConfettiParticleSystem
                .setDuration(1500)
                .setEmissionDuration(800)
                .setEmissionRate(100)
                .setEmissionPositionX(0)
                .setEmissionRangeY(1000, 3000)
                .setSpeedX(1000, 1500)
                .setSpeedY(-4000, -3000)
                .setAccelerationX(-2, 0)
                .setAccelerationY(5, 10)
                .setInitialRotation(0, 360)
                .setRotationSpeed(-720, 720)
                .setAlpha(255, 0, 500)
                .setScale(0.75f, 0, 1000)
                .setLayer(Layer.EFFECT_LAYER);
        mRightConfettiParticleSystem
                .setDuration(1500)
                .setEmissionDuration(800)
                .setEmissionRate(100)
                .setEmissionPositionX(JuicyMatch.WORLD_WIDTH)
                .setEmissionRangeY(1000, 3000)
                .setSpeedX(-1500, -1000)
                .setSpeedY(-4000, -3000)
                .setAccelerationX(0, 2)
                .setAccelerationY(5, 10)
                .setInitialRotation(0, 360)
                .setRotationSpeed(-720, 720)
                .setAlpha(255, 0, 500)
                .setScale(0.75f, 0, 1000)
                .setLayer(Layer.EFFECT_LAYER);
        mComboText = new TextEffect(engine, Textures.TEXT_COMBO_NICE);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onStart() {
        mCombo = 0;
    }

    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case ADD_COMBO:
                mCombo++;
                getComboSound().play();
                break;
            case STOP_COMBO:
                if (mCombo >= COMBO_NICE) {
                    playComboTextEffect();
                }
                if (mCombo >= COMBO_WONDERFUL) {
                    playConfettiEffect();
                }
                mCombo = 0;
                break;
            case ADD_EXTRA_MOVES:
                mCombo = 0;
                break;
            case GAME_WIN:
            case GAME_OVER:
                removeFromGame();
                break;
        }
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    private Texture getComboTexture() {
        if (mCombo == COMBO_NICE) {
            return Textures.TEXT_COMBO_NICE;
        } else if (mCombo == COMBO_GREAT) {
            return Textures.TEXT_COMBO_GREAT;
        } else {
            return Textures.TEXT_COMBO_WONDERFUL;
        }
    }

    private Sound getComboSound() {
        if (mCombo == 1) {
            return Sounds.TILE_COMBO_01;
        } else if (mCombo == 2) {
            return Sounds.TILE_COMBO_02;
        } else if (mCombo == 3) {
            return Sounds.TILE_COMBO_03;
        } else {
            return Sounds.TILE_COMBO_04;
        }
    }

    private void playComboTextEffect() {
        mComboText.setTexture(getComboTexture());
        mComboText.activate(JuicyMatch.WORLD_WIDTH / 2f, JuicyMatch.WORLD_HEIGHT / 2f);
        Sounds.ADD_COMBO.play();
    }

    private void playConfettiEffect() {
        mLeftConfettiParticleSystem.emit();
        mRightConfettiParticleSystem.emit();
    }
    //========================================================

}
