package com.nativegame.match3game.game.effect.combine;

import com.nativegame.match3game.game.layer.Layer;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum ColorCombineDirection {
    LEFT,
    RIGHT;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getSpeedFactor() {
        switch (this) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
        }

        return 0;
    }

    public int getLayer() {
        switch (this) {
            case LEFT:
                return Layer.EFFECT_LAYER;
            case RIGHT:
                return Layer.EFFECT_LAYER + 1;
        }

        return 0;
    }
    //========================================================

}
