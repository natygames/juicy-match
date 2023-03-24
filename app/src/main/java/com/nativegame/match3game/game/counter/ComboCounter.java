package com.nativegame.match3game.game.counter;

import com.nativegame.match3game.asset.Sounds;
import com.nativegame.match3game.asset.Textures;
import com.nativegame.match3game.game.GameEvent;
import com.nativegame.match3game.game.JuicyMatch;
import com.nativegame.match3game.game.effect.TextEffect;
import com.nativegame.nattyengine.audio.sound.Sound;
import com.nativegame.nattyengine.engine.Engine;
import com.nativegame.nattyengine.engine.event.Event;
import com.nativegame.nattyengine.engine.event.EventListener;
import com.nativegame.nattyengine.entity.Entity;
import com.nativegame.nattyengine.texture.Texture;

public class ComboCounter extends Entity implements EventListener {

    private static final int COMBO_NICE = 4;
    private static final int COMBO_GREAT = 5;
    private static final int COMBO_WONDERFUL = 6;

    private final TextEffect mComboText;

    private int mCombo;

    public ComboCounter(Engine engine) {
        super(engine);
        mComboText = new TextEffect(engine, Textures.TEXT_COMBO_NICE);
        mCombo = 0;
    }

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onEvent(Event event) {
        switch ((GameEvent) event) {
            case ADD_COMBO:
                mCombo++;
                getComboSound().play();
                break;
            case STOP_COMBO:
                if (mCombo >= COMBO_NICE) {
                    mComboText.setTexture(getComboTexture());
                    mComboText.activate(JuicyMatch.WORLD_WIDTH / 2f, JuicyMatch.WORLD_HEIGHT / 2f);
                    Sounds.ADD_COMBO.play();
                }
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
        switch (mCombo) {
            case 4:
                return Textures.TEXT_COMBO_NICE;
            case 5:
                return Textures.TEXT_COMBO_GREAT;
            default:
                return Textures.TEXT_COMBO_WONDERFUL;
        }
    }

    private Sound getComboSound() {
        switch (mCombo) {
            case 1:
                return Sounds.TILE_COMBO_01;
            case 2:
                return Sounds.TILE_COMBO_02;
            case 3:
                return Sounds.TILE_COMBO_03;
            default:
                return Sounds.TILE_COMBO_04;
        }
    }
    //========================================================

}
