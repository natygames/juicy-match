package com.nativegame.match3game.game.layer.entry;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum EntryPointType {
    ENTRY;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture() {
        switch (this) {
            case ENTRY:
                return Textures.ARROW;
            default:
                throw new IllegalArgumentException("EntryPoint texture not found!");
        }
    }
    //========================================================

}
