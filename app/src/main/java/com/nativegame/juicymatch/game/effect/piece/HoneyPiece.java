package com.nativegame.juicymatch.game.effect.piece;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum HoneyPiece {
    TOP_LEFT,
    TOP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    RIGHT;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public int getAngle() {
        switch (this) {
            case TOP_LEFT:
                return 320;
            case TOP_RIGHT:
                return 30;
            case DOWN_LEFT:
                return 225;
            case DOWN_RIGHT:
                return 150;
            case RIGHT:
                return 90;
        }

        return 0;
    }

    public float getDirectionX() {
        switch (this) {
            case TOP_LEFT:
            case DOWN_LEFT:
                return -1;
            case TOP_RIGHT:
            case DOWN_RIGHT:
                return 1;
            case RIGHT:
                return 1.5f;
            default:
                throw new IllegalArgumentException("HoneyPiece directionX not found!");
        }
    }

    public float getDirectionY() {
        switch (this) {
            case TOP_LEFT:
                return -1.5f;
            case TOP_RIGHT:
                return -2;
            case DOWN_LEFT:
                return 1;
            case DOWN_RIGHT:
                return 1.5f;
            case RIGHT:
                return 0;
            default:
                throw new IllegalArgumentException("HoneyPiece directionY not found!");
        }
    }
    //========================================================

}
