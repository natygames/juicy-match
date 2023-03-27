package com.nativegame.match3game.game.effect.piece;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum CakePiece {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    DOWN_LEFT,
    DOWN,
    DOWN_RIGHT;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture() {
        switch (this) {
            case TOP_LEFT:
                return Textures.CAKE_PIECE_01;
            case TOP:
                return Textures.CAKE_PIECE_02;
            case TOP_RIGHT:
                return Textures.CAKE_PIECE_03;
            case DOWN_LEFT:
                return Textures.CAKE_PIECE_04;
            case DOWN:
                return Textures.CAKE_PIECE_05;
            case DOWN_RIGHT:
                return Textures.CAKE_PIECE_06;
            default:
                throw new IllegalArgumentException("CakePiece texture not found!");
        }
    }

    public int getDirection() {
        switch (this) {
            case TOP_LEFT:
            case DOWN_LEFT:
                return -1;
            case TOP:
            case DOWN:
                return 0;
            case TOP_RIGHT:
            case DOWN_RIGHT:
                return 1;
            default:
                throw new IllegalArgumentException("CakePiece not found!");
        }
    }
    //========================================================

}
