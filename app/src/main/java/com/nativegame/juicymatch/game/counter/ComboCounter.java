package com.nativegame.juicymatch.game.counter;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.juicymatch.game.GameEvent;
import com.nativegame.juicymatch.game.GameLayer;
import com.nativegame.juicymatch.game.GameWorld;
import com.nativegame.juicymatch.game.effect.TextEffect;
import com.nativegame.natyengine.audio.sound.Sound;
import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.Entity;
import com.nativegame.natyengine.entity.particle.ParticleSystemGroup;
import com.nativegame.natyengine.entity.particle.SpriteParticleSystem;
import com.nativegame.natyengine.event.Event;
import com.nativegame.natyengine.event.EventListener;
import com.nativegame.natyengine.texture.Texture;

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
        mLeftConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_BLUE, 15));
        mLeftConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_GREEN, 15));
        mLeftConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_PINK, 15));
        mLeftConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_YELLOW, 15));
        mRightConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_BLUE, 15));
        mRightConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_GREEN, 15));
        mRightConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_PINK, 15));
        mRightConfettiParticleSystem.addParticleSystem(new SpriteParticleSystem(engine, Textures.CONFETTI_YELLOW, 15));
        int emissionStartY = GameWorld.WORLD_HEIGHT / 2;
        int emissionEndY = emissionStartY + 2000;
        mLeftConfettiParticleSystem
                .setDuration(1500)
                .setEmissionDuration(800)
                .setEmissionRate(100)
                .setEmissionPositionX(0)
                .setEmissionRangeY(emissionStartY, emissionEndY)
                .setSpeedX(1000, 1500)
                .setSpeedY(-4000, -3000)
                .setAccelerationX(-2, 0)
                .setAccelerationY(5, 10)
                .setInitialRotation(0, 360)
                .setRotationSpeed(-720, 720)
                .setAlpha(255, 0, 500)
                .setScale(0.75f, 0, 1000)
                .setLayer(GameLayer.EFFECT_LAYER);
        mRightConfettiParticleSystem
                .setDuration(1500)
                .setEmissionDuration(800)
                .setEmissionRate(100)
                .setEmissionPositionX(GameWorld.WORLD_WIDTH)
                .setEmissionRangeY(emissionStartY, emissionEndY)
                .setSpeedX(-1500, -1000)
                .setSpeedY(-4000, -3000)
                .setAccelerationX(0, 2)
                .setAccelerationY(5, 10)
                .setInitialRotation(0, 360)
                .setRotationSpeed(-720, 720)
                .setAlpha(255, 0, 500)
                .setScale(0.75f, 0, 1000)
                .setLayer(GameLayer.EFFECT_LAYER);
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
        mComboText.activate(GameWorld.WORLD_WIDTH / 2f, GameWorld.WORLD_HEIGHT / 2f);
        Sounds.ADD_COMBO.play();
    }

    private void playConfettiEffect() {
        mLeftConfettiParticleSystem.emit();
        mRightConfettiParticleSystem.emit();
    }
    //========================================================

}
