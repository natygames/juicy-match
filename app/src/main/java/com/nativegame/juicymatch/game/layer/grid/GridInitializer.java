package com.nativegame.juicymatch.game.layer.grid;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class GridInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static GridType getType(char c) {
        switch (c) {
            case 'n':
                return GridType.CENTER;
            case 'u':
                return GridType.TOP;
            case 'd':
                return GridType.DOWN;
            case 'l':
                return GridType.LEFT;
            case 'r':
                return GridType.RIGHT;
            case 'q':
                return GridType.TOP_LEFT;
            case 'w':
                return GridType.TOP_RIGHT;
            case 'a':
                return GridType.DOWN_LEFT;
            case 's':
                return GridType.DOWN_RIGHT;
            case 't':
                return GridType.TOP_MARGIN;
            case 'b':
                return GridType.DOWN_MARGIN;
            case 'z':
                return GridType.LEFT_MARGIN;
            case 'x':
                return GridType.RIGHT_MARGIN;
            case 'h':
                return GridType.HORIZONTAL;
            case 'v':
                return GridType.VERTICAL;
            case 'o':
                return GridType.SOLE;
            default:
                throw new IllegalArgumentException("GridType not found!");
        }
    }
    //========================================================

}
