package com.nativegame.match3game.game.layer.lock;

import com.nativegame.match3game.asset.Textures;
import com.nativegame.nattyengine.texture.Texture;

public enum LockType {
    CENTER;

    public Texture getTexture() {
        switch (this) {
            case CENTER:
                return Textures.LOCK;
            default:
                throw new IllegalArgumentException("Lock texture not found!");
        }
    }

}
