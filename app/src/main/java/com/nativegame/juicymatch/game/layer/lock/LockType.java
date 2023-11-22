package com.nativegame.juicymatch.game.layer.lock;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.texture.Texture;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public enum LockType {
    CENTER;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture() {
        switch (this) {
            case CENTER:
                return Textures.LOCK;
            default:
                throw new IllegalArgumentException("Lock Texture not found!");
        }
    }
    //========================================================

}
