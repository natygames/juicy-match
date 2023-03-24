package com.nativegame.match3game.game.effect.piece;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

public enum CookiePiece {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    DOWN_LEFT,
    DOWN,
    DOWN_RIGHT;

    public Texture getTexture() {
        switch (this) {
            case TOP_LEFT:
                return Textures.COOKIE_PIECE_01;
            case TOP:
                return Textures.COOKIE_PIECE_02;
            case TOP_RIGHT:
                return Textures.COOKIE_PIECE_03;
            case DOWN_LEFT:
                return Textures.COOKIE_PIECE_04;
            case DOWN:
                return Textures.COOKIE_PIECE_05;
            case DOWN_RIGHT:
                return Textures.COOKIE_PIECE_06;
            default:
                throw new IllegalArgumentException("CookiePiece texture not found!");
        }
    }

    public int getDirectionX() {
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
                throw new IllegalArgumentException("CookiePiece not found!");
        }
    }

    public int getDirectionY() {
        switch (this) {
            case TOP_LEFT:
            case TOP:
            case TOP_RIGHT:
                return -1;
            case DOWN:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                return 1;
            default:
                throw new IllegalArgumentException("CookiePiece not found!");
        }
    }

}
