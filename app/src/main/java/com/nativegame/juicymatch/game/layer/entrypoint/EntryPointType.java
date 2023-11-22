package com.nativegame.juicymatch.game.layer.entrypoint;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum EntryPointType {
    ARROW;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture() {
        switch (this) {
            case ARROW:
                return Textures.ARROW;
            default:
                throw new IllegalArgumentException("EntryPoint Texture not found!");
        }
    }
    //========================================================

}
