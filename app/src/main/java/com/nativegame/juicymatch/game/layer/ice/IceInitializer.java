package com.nativegame.juicymatch.game.layer.ice;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class IceInitializer {

    //--------------------------------------------------------
    // Static methods
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
            case 't':
                return IceType.TOP_MARGIN;
            case 'b':
                return IceType.DOWN_MARGIN;
            case 'z':
                return IceType.LEFT_MARGIN;
            case 'x':
                return IceType.RIGHT_MARGIN;
            case 'h':
                return IceType.HORIZONTAL;
            case 'v':
                return IceType.VERTICAL;
            case 'o':
                return IceType.SOLE;
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
