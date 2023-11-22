package com.nativegame.juicymatch.game.layer.shell;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum ShellType {
    SMALL_VERTICAL,
    SMALL_HORIZONTAL,
    MEDIUM,
    LARGE;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture() {
        switch (this) {
            case SMALL_VERTICAL:
                return Textures.SHELL_01_VERTICAL;
            case SMALL_HORIZONTAL:
                return Textures.SHELL_01_HORIZONTAL;
            case MEDIUM:
                return Textures.SHELL_02;
            case LARGE:
                return Textures.SHELL_03;
            default:
                throw new IllegalArgumentException("Shell Texture not found!");
        }
    }

    public int getWidth() {
        switch (this) {
            case SMALL_VERTICAL:
                return 1;
            case SMALL_HORIZONTAL:
                return 3;
            case MEDIUM:
                return 2;
            case LARGE:
                return 3;
            default:
                throw new IllegalArgumentException("Shell width not found!");
        }
    }

    public int getHeight() {
        switch (this) {
            case SMALL_VERTICAL:
                return 3;
            case SMALL_HORIZONTAL:
                return 1;
            case MEDIUM:
                return 2;
            case LARGE:
                return 3;
            default:
                throw new IllegalArgumentException("Shell height not found!");
        }
    }
    //========================================================

}
