package com.nativegame.match3game.game.layer.ice;

public class IceInitializer {

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public static IceType getType(char c) {
        char lower = Character.toLowerCase(c);
        switch (lower) {
            case 'n':
                return IceType.CENTER;
            case 'u':
                return IceType.TOP;
            case 'd':
                return IceType.DOWN;
            case 'l':
                return IceType.LEFT;
            case 'r':
                return IceType.RIGHT;
            case 'q':
                return IceType.TOP_LEFT;
            case 'w':
                return IceType.TOP_RIGHT;
            case 'a':
                return IceType.DOWN_LEFT;
            case 's':
                return IceType.DOWN_RIGHT;
            default:
                throw new IllegalArgumentException("IceType not found!");
        }
    }

    public static int getLayer(char c) {
        if (Character.isLowerCase(c)) {
            return 1;
        } else {
            return 2;
        }
    }
    //========================================================

}
