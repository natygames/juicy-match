package com.nativegame.match3game.asset;

import android.content.Context;
import android.graphics.Typeface;

import com.nativegame.match3game.R;
import com.nativegame.nattyengine.util.resource.ResourceUtils;

public class Fonts {

    public static Typeface BALOO;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(Context context) {
        BALOO = ResourceUtils.getFont(context, R.font.baloo);
    }
    //========================================================

}
