package com.nativegame.match3game.asset;

import android.content.Context;

import com.nativegame.nattyengine.util.storage.preference.Preference;

public class Preferences {

    private static final String PREFS_SETTING_NAME = "prefs_setting";
    public static final String KEY_SOUND = "sound";
    public static final String KEY_MUSIC = "music";
    public static final String KEY_HINT = "hint";

    public static Preference PREF_SETTING;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(Context context) {
        PREF_SETTING = new Preference(context, PREFS_SETTING_NAME);
    }
    //========================================================

}
