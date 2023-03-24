package com.nativegame.match3game.game.layer.ice;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

public enum IceType {
    CENTER,
    TOP,
    DOWN,
    LEFT,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture(int layer) {
        switch (layer) {
            case 1:
                return getSingleLayerTexture();
            case 2:
                return getDoubleLayerTexture();
            default:
                throw new IllegalArgumentException("Ice layer not found!");
        }
    }

    public Texture getSingleLayerTexture() {
        switch (this) {
            case CENTER:
                return Textures.ICE_CENTER_01;
            case TOP:
            case DOWN:
            case LEFT:
            case RIGHT:
                return Textures.ICE_MARGIN_01;
            case TOP_LEFT:
            case TOP_RIGHT:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return Textures.ICE_CORNER_01;
            default:
                throw new IllegalArgumentException("Single layer ice texture not found!");
        }
    }

    public Texture getDoubleLayerTexture() {
        switch (this) {
            case CENTER:
                return Textures.ICE_CENTER_02;
            case TOP:
            case DOWN:
            case LEFT:
            case RIGHT:
                return Textures.ICE_MARGIN_02;
            case TOP_LEFT:
            case TOP_RIGHT:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return Textures.ICE_CORNER_02;
            default:
                throw new IllegalArgumentException("Double layer ice texture not found!");
        }
    }

    public int getAngle() {
        switch (this) {
            case CENTER:
            case TOP:
            case TOP_LEFT:
                return 0;
            case RIGHT:
            case TOP_RIGHT:
                return 90;
            case DOWN:
            case DOWN_RIGHT:
                return 180;
            case LEFT:
            case DOWN_LEFT:
                return 270;
            default:
                throw new IllegalArgumentException("Ice layer not found!");
        }
    }
    //========================================================

}
