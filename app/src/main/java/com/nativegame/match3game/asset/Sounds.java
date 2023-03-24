package com.nativegame.match3game.asset;

import com.nativegame.match3game.R;
import com.nativegame.nattyengine.audio.sound.Sound;
import com.nativegame.nattyengine.audio.sound.SoundManager;

public class Sounds {

    public static Sound GAME_START;
    public static Sound GAME_WIN;
    public static Sound GAME_OVER;
    public static Sound ADD_SCORE;
    public static Sound ADD_STAR;
    public static Sound ADD_COMBO;
    public static Sound ADD_BONUS;
    public static Sound COLLECT_STARFISH;

    public static Sound BUTTON_CLICK;
    public static Sound DIALOG_SLIDE;

    public static Sound TILE_COMBO_01;
    public static Sound TILE_COMBO_02;
    public static Sound TILE_COMBO_03;
    public static Sound TILE_COMBO_04;
    public static Sound TILE_SWAP;
    public static Sound TILE_SLIDE;
    public static Sound TILE_SHUFFLE;
    public static Sound TILE_BOUNCE;
    public static Sound TILE_UPGRADE;

    public static Sound COLOR_SPECIAL_TILE_UPGRADE;
    public static Sound COLOR_SPECIAL_TILE_TRANSFORM;
    public static Sound COLOR_SPECIAL_TILE_COMBINE;
    public static Sound COLOR_SPECIAL_TILE_EXPLODE;
    public static Sound STRIPED_SPECIAL_TILE_EXPLODE;
    public static Sound EXPLOSION_SPECIAL_TILE_EXPLODE;

    public static Sound COOKIE_EXPLODE;
    public static Sound CAKE_EXPLODE;
    public static Sound ICE_EXPLODE;
    public static Sound LOCK_EXPLODE;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(SoundManager soundManager) {
        GAME_START = soundManager.load(R.raw.game_start);
        GAME_WIN = soundManager.load(R.raw.game_win);
        GAME_OVER = soundManager.load(R.raw.game_over);
        ADD_SCORE = soundManager.load(R.raw.add_score);
        ADD_STAR = soundManager.load(R.raw.add_star);
        ADD_COMBO = soundManager.load(R.raw.add_combo);
        ADD_BONUS = soundManager.load(R.raw.add_bonus);
        COLLECT_STARFISH = soundManager.load(R.raw.collect_starfish);

        BUTTON_CLICK = soundManager.load(R.raw.button_click);
        DIALOG_SLIDE = soundManager.load(R.raw.dialog_slide);

        TILE_COMBO_01 = soundManager.load(R.raw.tile_combo_01);
        TILE_COMBO_02 = soundManager.load(R.raw.tile_combo_02);
        TILE_COMBO_03 = soundManager.load(R.raw.tile_combo_03);
        TILE_COMBO_04 = soundManager.load(R.raw.tile_combo_04);
        TILE_SWAP = soundManager.load(R.raw.tile_swap);
        TILE_SLIDE = soundManager.load(R.raw.tile_slide);
        TILE_SHUFFLE = soundManager.load(R.raw.tile_shuffle);
        TILE_BOUNCE = soundManager.load(R.raw.tile_bounce);
        TILE_UPGRADE = soundManager.load(R.raw.tile_upgrade);

        COLOR_SPECIAL_TILE_UPGRADE = soundManager.load(R.raw.color_special_tile_upgrade);
        COLOR_SPECIAL_TILE_TRANSFORM = soundManager.load(R.raw.color_special_tile_transform);
        COLOR_SPECIAL_TILE_COMBINE = soundManager.load(R.raw.color_special_tile_combine);
        COLOR_SPECIAL_TILE_EXPLODE = soundManager.load(R.raw.color_special_tile__explode);
        STRIPED_SPECIAL_TILE_EXPLODE = soundManager.load(R.raw.striped_special_tile_explode);
        EXPLOSION_SPECIAL_TILE_EXPLODE = soundManager.load(R.raw.explosion_special_tile_explode);

        COOKIE_EXPLODE = soundManager.load(R.raw.cookie_explode);
        CAKE_EXPLODE = soundManager.load(R.raw.cake_explode);
        ICE_EXPLODE = soundManager.load(R.raw.ice_explode);
        LOCK_EXPLODE = soundManager.load(R.raw.lock_explode);
    }
    //========================================================

}
