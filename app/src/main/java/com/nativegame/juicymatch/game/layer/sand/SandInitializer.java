package com.nativegame.juicymatch.game.layer.sand;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class SandInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static SandType getType(char c) {
        char lower = Character.toLowerCase(c);
        switch (lower) {
            case 'n':
                return SandType.CENTER;
            case 'u':
                return SandType.TOP;
            case 'd':
                return SandType.DOWN;
            case 'l':
                return SandType.LEFT;
            case 'r':
                return SandType.RIGHT;
            case 'q':
                return SandType.TOP_LEFT;
            case 'w':
                return SandType.TOP_RIGHT;
            case 'a':
                return SandType.DOWN_LEFT;
            case 's':
                return SandType.DOWN_RIGHT;
            case 't':
                return SandType.TOP_MARGIN;
            case 'b':
                return SandType.DOWN_MARGIN;
            case 'z':
                return SandType.LEFT_MARGIN;
            case 'x':
                return SandType.RIGHT_MARGIN;
            case 'h':
                return SandType.HORIZONTAL;
            case 'v':
                return SandType.VERTICAL;
            case 'o':
                return SandType.SOLE;
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