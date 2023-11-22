package com.nativegame.juicymatch.game.layer.grid;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum GridType {
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
    public Texture getTexture() {
        switch (this) {
            case CENTER:
                return Textures.GRID_CENTER;
            case TOP:
            case DOWN:
            case LEFT:
            case RIGHT:
                return Textures.GRID_SIDE;
            case TOP_LEFT:
            case TOP_RIGHT:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return Textures.GRID_CORNER;
            case TOP_MARGIN:
            case DOWN_MARGIN:
            case LEFT_MARGIN:
            case RIGHT_MARGIN:
                return Textures.GRID_MARGIN;
            case VERTICAL:
            case HORIZONTAL:
                return Textures.GRID_PIPE;
            case SOLE:
                return Textures.GRID_SOLE;
            default:
                throw new IllegalArgumentException("Grid Texture not found!");
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
                throw new IllegalArgumentException("Grid angle not found!");
        }
    }
    //========================================================

}
