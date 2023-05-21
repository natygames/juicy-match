package com.nativegame.match3game.game.layer.ice;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum IceType {
    CENTER,
    TOP,
    DOWN,
    LEFT,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    TOP_MARGIN,
    DOWN_MARGIN,
    LEFT_MARGIN,
    RIGHT_MARGIN,
    VERTICAL,
    HORIZONTAL,
    SOLE;

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
                throw new IllegalArgumentException("Ice Texture not found!");
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
                return Textures.ICE_SIDE_01;
            case TOP_LEFT:
            case TOP_RIGHT:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return Textures.ICE_CORNER_01;
            case TOP_MARGIN:
            case DOWN_MARGIN:
            case LEFT_MARGIN:
            case RIGHT_MARGIN:
                return Textures.ICE_MARGIN_01;
            case VERTICAL:
            case HORIZONTAL:
                return Textures.ICE_PIPE_01;
            case SOLE:
                return Textures.ICE_SOLE_01;
            default:
                throw new IllegalArgumentException("Single layer ice Texture not found!");
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
                return Textures.ICE_SIDE_02;
            case TOP_LEFT:
            case TOP_RIGHT:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return Textures.ICE_CORNER_02;
            case TOP_MARGIN:
            case DOWN_MARGIN:
            case LEFT_MARGIN:
            case RIGHT_MARGIN:
                return Textures.ICE_MARGIN_02;
            case VERTICAL:
            case HORIZONTAL:
                return Textures.ICE_PIPE_02;
            case SOLE:
                return Textures.ICE_SOLE_02;
            default:
                throw new IllegalArgumentException("Double layer ice Texture not found!");
        }
    }

    public int getAngle() {
        switch (this) {
            case CENTER:
            case TOP:
            case TOP_LEFT:
            case TOP_MARGIN:
            case HORIZONTAL:
            case SOLE:
                return 0;
            case RIGHT:
            case TOP_RIGHT:
            case RIGHT_MARGIN:
            case VERTICAL:
                return 90;
            case DOWN:
            case DOWN_RIGHT:
            case DOWN_MARGIN:
                return 180;
            case LEFT:
            case DOWN_LEFT:
            case LEFT_MARGIN:
                return 270;
            default:
                throw new IllegalArgumentException("Ice angle not found!");
        }
    }
    //========================================================

}
