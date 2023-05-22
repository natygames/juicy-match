package com.nativegame.juicymatch.game.layer.honey;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class HoneyInitialize {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static HoneyType getType(char c) {
        switch (c) {
            case 'n':
                return HoneyType.CENTER;
            case 'u':
                return HoneyType.TOP;
            case 'd':
                return HoneyType.DOWN;
            case 'l':
                return HoneyType.LEFT;
            case 'r':
                return HoneyType.RIGHT;
            case 'q':
                return HoneyType.TOP_LEFT;
            case 'w':
                return HoneyType.TOP_RIGHT;
            case 'a':
                return HoneyType.DOWN_LEFT;
            case 's':
                return HoneyType.DOWN_RIGHT;
            case 't':
                return HoneyType.TOP_MARGIN;
            case 'b':
                return HoneyType.DOWN_MARGIN;
            case 'z':
                return HoneyType.LEFT_MARGIN;
            case 'x':
                return HoneyType.RIGHT_MARGIN;
            case 'h':
                return HoneyType.HORIZONTAL;
            case 'v':
                return HoneyType.VERTICAL;
            case 'o':
                return HoneyType.SOLE;
            default:
                throw new IllegalArgumentException("HoneyType not found!");
        }
    }
    //========================================================

}

