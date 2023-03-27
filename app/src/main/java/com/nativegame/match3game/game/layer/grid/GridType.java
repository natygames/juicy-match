package com.nativegame.match3game.game.layer.grid;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

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
    DOWN_RIGHT;

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
                return Textures.GRID_MARGIN;
            case TOP_LEFT:
            case TOP_RIGHT:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return Textures.GRID_CORNER;
            default:
                throw new IllegalArgumentException("Grid texture not found!");
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
                throw new IllegalArgumentException("Grid not found!");
        }
    }
    //========================================================

}
