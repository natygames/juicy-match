package com.nativegame.juicymatch.game.layer.shell;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class ShellInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static ShellType getType(char c) {
        switch (c) {
            case 'V':
                return ShellType.SMALL_VERTICAL;
            case 'H':
                return ShellType.SMALL_HORIZONTAL;
            case 'M':
                return ShellType.MEDIUM;
            case 'L':
                return ShellType.LARGE;
            default:
                throw new IllegalArgumentException("ShellType not found!");
        }
    }
    //========================================================

}
