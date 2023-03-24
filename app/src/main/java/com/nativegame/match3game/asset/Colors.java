package com.nativegame.match3game.asset;

import android.content.Context;

import com.nativegame.match3game.R;
import com.nativegame.nattyengine.util.resource.ResourceUtils;

public class Colors {

    public static int BLUE;
    public static int WHITE;
    public static int WHITE_25;
    public static int BLACK_60;
    public static int BLACK_80;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(Context context) {
        BLUE = ResourceUtils.getColor(context, R.color.blue);
        WHITE = ResourceUtils.getColor(context, R.color.white);
        WHITE_25 = ResourceUtils.getColor(context, R.color.white_25);
        BLACK_60 = ResourceUtils.getColor(context, R.color.black_60);
        BLACK_80 = ResourceUtils.getColor(context, R.color.black_80);
    }
    //========================================================

}
