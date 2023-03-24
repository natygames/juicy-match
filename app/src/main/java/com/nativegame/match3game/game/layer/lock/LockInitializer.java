package com.nativegame.match3game.game.layer.lock;

public class LockInitializer {

    public static LockType getType(char c) {
        switch (c) {
            case 'X':
                return LockType.CENTER;
            default:
                throw new IllegalArgumentException("LockType not found!");
        }
    }

}
