package com.nativegame.match3game.asset;

import com.nativegame.match3game.R;
import com.nativegame.nattyengine.audio.music.Music;
import com.nativegame.nattyengine.audio.music.MusicManager;

public class Musics {

    public static Music BG_MUSIC;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(MusicManager musicManager) {
        BG_MUSIC = musicManager.load(R.raw.happy_and_joyful_children);
        BG_MUSIC.setVolume(0.6f, 0.6f);
    }
    //========================================================

}
