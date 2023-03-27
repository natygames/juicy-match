package com.nativegame.match3game.game.effect.piece;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum LockPiece {
    CENTER_LEFT,
    CENTER_RIGHT,
    DOWN_LEFT,
    TOP_LEFT,
    RIGHT;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture() {
        switch (this) {
            case CENTER_LEFT:
                return Textures.LOCK_PIECE_01;
            case CENTER_RIGHT:
                return Textures.LOCK_PIECE_02;
            case DOWN_LEFT:
                return Textures.LOCK_PIECE_03;
            case TOP_LEFT:
                return Textures.LOCK_PIECE_04;
            case RIGHT:
                return Textures.LOCK_PIECE_05;
            default:
                throw new IllegalArgumentException("LockPiece texture not found!");
        }
    }

    public int getDirection() {
        switch (this) {
            case CENTER_LEFT:
            case CENTER_RIGHT:
                return 0;
            case TOP_LEFT:
            case DOWN_LEFT:
                return -1;
            case RIGHT:
                return 1;
            default:
                throw new IllegalArgumentException("LockPiece texture not found!");
        }
    }
    //========================================================

}
