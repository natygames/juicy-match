package com.nativegame.match3game.asset;

import android.content.Context;

import com.nativegame.match3game.R;
import com.nativegame.nattyengine.texture.texture2d.Texture2D;
import com.nativegame.nattyengine.texture.texture2d.Texture2DGroup;
import com.nativegame.nattyengine.texture.texture2d.Texture2DManager;
import com.nativegame.nattyengine.util.bitmap.BitmapUtils;
import com.nativegame.nattyengine.util.bitmap.ClipUtils;
import com.nativegame.nattyengine.util.bitmap.ShapeUtils;
import com.nativegame.nattyengine.util.resource.ResourceUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Textures {

    public static Texture2D GRID_CENTER;
    public static Texture2D GRID_MARGIN;
    public static Texture2D GRID_CORNER;

    public static Texture2D CHERRY;
    public static Texture2D STRAWBERRY;
    public static Texture2D LEMON;
    public static Texture2D COCONUT;
    public static Texture2D BANANA;
    public static Texture2D EMPTY;
    public static Texture2D CHERRY_PIECE_01;
    public static Texture2D CHERRY_PIECE_02;
    public static Texture2D STRAWBERRY_PIECE_01;
    public static Texture2D STRAWBERRY_PIECE_02;
    public static Texture2D LEMON_PIECE_01;
    public static Texture2D LEMON_PIECE_02;
    public static Texture2D COCONUT_PIECE_01;
    public static Texture2D COCONUT_PIECE_02;
    public static Texture2D BANANA_PIECE_01;
    public static Texture2D BANANA_PIECE_02;

    public static Texture2D ROW_CHERRY;
    public static Texture2D ROW_STRAWBERRY;
    public static Texture2D ROW_LEMON;
    public static Texture2D ROW_COCONUT;
    public static Texture2D ROW_BANANA;
    public static Texture2D ROW_CHERRY_PIECE_01;
    public static Texture2D ROW_CHERRY_PIECE_02;
    public static Texture2D ROW_STRAWBERRY_PIECE_01;
    public static Texture2D ROW_STRAWBERRY_PIECE_02;
    public static Texture2D ROW_LEMON_PIECE_01;
    public static Texture2D ROW_LEMON_PIECE_02;
    public static Texture2D ROW_COCONUT_PIECE_01;
    public static Texture2D ROW_COCONUT_PIECE_02;
    public static Texture2D ROW_BANANA_PIECE_01;
    public static Texture2D ROW_BANANA_PIECE_02;

    public static Texture2D COLUMN_CHERRY;
    public static Texture2D COLUMN_STRAWBERRY;
    public static Texture2D COLUMN_LEMON;
    public static Texture2D COLUMN_COCONUT;
    public static Texture2D COLUMN_BANANA;
    public static Texture2D COLUMN_CHERRY_PIECE_01;
    public static Texture2D COLUMN_CHERRY_PIECE_02;
    public static Texture2D COLUMN_STRAWBERRY_PIECE_01;
    public static Texture2D COLUMN_STRAWBERRY_PIECE_02;
    public static Texture2D COLUMN_LEMON_PIECE_01;
    public static Texture2D COLUMN_LEMON_PIECE_02;
    public static Texture2D COLUMN_COCONUT_PIECE_01;
    public static Texture2D COLUMN_COCONUT_PIECE_02;
    public static Texture2D COLUMN_BANANA_PIECE_01;
    public static Texture2D COLUMN_BANANA_PIECE_02;

    public static Texture2D EXPLOSION_CHERRY;
    public static Texture2D EXPLOSION_STRAWBERRY;
    public static Texture2D EXPLOSION_LEMON;
    public static Texture2D EXPLOSION_COCONUT;
    public static Texture2D EXPLOSION_BANANA;
    public static Texture2D EXPLOSION_CHERRY_PIECE_01;
    public static Texture2D EXPLOSION_CHERRY_PIECE_02;
    public static Texture2D EXPLOSION_STRAWBERRY_PIECE_01;
    public static Texture2D EXPLOSION_STRAWBERRY_PIECE_02;
    public static Texture2D EXPLOSION_LEMON_PIECE_01;
    public static Texture2D EXPLOSION_LEMON_PIECE_02;
    public static Texture2D EXPLOSION_COCONUT_PIECE_01;
    public static Texture2D EXPLOSION_COCONUT_PIECE_02;
    public static Texture2D EXPLOSION_BANANA_PIECE_01;
    public static Texture2D EXPLOSION_BANANA_PIECE_02;

    public static Texture2D ICE_CREAM;

    public static Texture2D COOKIE;
    public static Texture2D COOKIE_PIECE_01;
    public static Texture2D COOKIE_PIECE_02;
    public static Texture2D COOKIE_PIECE_03;
    public static Texture2D COOKIE_PIECE_04;
    public static Texture2D COOKIE_PIECE_05;
    public static Texture2D COOKIE_PIECE_06;

    public static Texture2D CAKE_01;
    public static Texture2D CAKE_02;
    public static Texture2D CAKE_03;
    public static Texture2D CAKE_04;
    public static Texture2D CAKE_PIECE_01;
    public static Texture2D CAKE_PIECE_02;
    public static Texture2D CAKE_PIECE_03;
    public static Texture2D CAKE_PIECE_04;
    public static Texture2D CAKE_PIECE_05;
    public static Texture2D CAKE_PIECE_06;

    public static Texture2D SCORE_PINK;
    public static Texture2D SCORE_RED;
    public static Texture2D SCORE_YELLOW;
    public static Texture2D SCORE_BROWN;
    public static Texture2D SCORE_WHITE;

    public static Texture2D LOCK;
    public static Texture2D LOCK_PIECE_01;
    public static Texture2D LOCK_PIECE_02;
    public static Texture2D LOCK_PIECE_03;
    public static Texture2D LOCK_PIECE_04;
    public static Texture2D LOCK_PIECE_05;

    public static Texture2D ICE_CENTER_01;
    public static Texture2D ICE_CENTER_02;
    public static Texture2D ICE_MARGIN_01;
    public static Texture2D ICE_MARGIN_02;
    public static Texture2D ICE_CORNER_01;
    public static Texture2D ICE_CORNER_02;
    public static Texture2D ICE_PIECE;

    public static Texture2D GENERATOR_ROW;
    public static Texture2D GENERATOR_COLUMN;
    public static Texture2D GENERATOR_EXPLOSION;
    public static Texture2D GENERATOR_ICE_CREAM;
    public static Texture2D GENERATOR_STARFISH;
    public static Texture2D GENERATOR_PIVOT;

    public static Texture2D TEXT_COMBO_NICE;
    public static Texture2D TEXT_COMBO_GREAT;
    public static Texture2D TEXT_COMBO_WONDERFUL;
    public static Texture2D TEXT_SHUFFLE;
    public static Texture2D TEXT_BONUS;

    public static Texture2D HAMMER;
    public static Texture2D GLOVE;
    public static Texture2D BOMB;

    public static Texture2D TUBE;
    public static Texture2D STARFISH;
    public static Texture2D ARROW;

    public static Texture2D LIGHT_BG;
    public static Texture2D BLUE_LIGHT_BG;
    public static Texture2D RING_LIGHT;
    public static Texture2D GLITTER;
    public static Texture2D LIGHTNING_GLITTER;
    public static Texture2D EXPLOSION_BEAM;
    public static Texture2D RING_FLASH;
    public static Texture2D BLUE_RING_FLASH;
    public static Texture2DGroup COLUMN_FLASH_ANIMATION;
    public static Texture2DGroup ROW_FLASH_ANIMATION;
    public static Texture2DGroup EXPLOSION_FLASH_ANIMATION;
    public static Texture2DGroup TRANSFORM_FLASH_ANIMATION;
    public static Texture2DGroup LIGHTNING_ANIMATION;
    public static Texture2DGroup SMOKE_ANIMATION;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(Texture2DManager textureManager, Context context) {
        GRID_CENTER = textureManager.loadTexture(R.drawable.grid);
        GRID_MARGIN = textureManager.loadTexture(R.drawable.grid_margin);
        GRID_CORNER = textureManager.loadTexture(R.drawable.grid_corner);

        CHERRY = textureManager.loadTexture(R.drawable.cherry);
        STRAWBERRY = textureManager.loadTexture(R.drawable.strawberry);
        LEMON = textureManager.loadTexture(R.drawable.lemon);
        COCONUT = textureManager.loadTexture(R.drawable.coconut);
        BANANA = textureManager.loadTexture(R.drawable.banana);
        EMPTY = textureManager.loadTexture(BitmapUtils.createBitmap(300, 300));
        CHERRY_PIECE_01 = textureManager.loadTexture(R.drawable.cherry_piece_01);
        CHERRY_PIECE_02 = textureManager.loadTexture(R.drawable.cherry_piece_02);
        STRAWBERRY_PIECE_01 = textureManager.loadTexture(R.drawable.strawberry_piece_01);
        STRAWBERRY_PIECE_02 = textureManager.loadTexture(R.drawable.strawberry_piece_02);
        LEMON_PIECE_01 = textureManager.loadTexture(R.drawable.lemon_piece_01);
        LEMON_PIECE_02 = textureManager.loadTexture(R.drawable.lemon_piece_02);
        COCONUT_PIECE_01 = textureManager.loadTexture(R.drawable.coconut_piece_01);
        COCONUT_PIECE_02 = textureManager.loadTexture(R.drawable.coconut_piece_02);
        BANANA_PIECE_01 = textureManager.loadTexture(R.drawable.banana_piece_01);
        BANANA_PIECE_02 = textureManager.loadTexture(R.drawable.banana_piece_02);

        ROW_CHERRY = textureManager.loadTexture(R.drawable.row_cherry);
        ROW_STRAWBERRY = textureManager.loadTexture(R.drawable.row_strawberry);
        ROW_LEMON = textureManager.loadTexture(R.drawable.row_lemon);
        ROW_COCONUT = textureManager.loadTexture(R.drawable.row_coconut);
        ROW_BANANA = textureManager.loadTexture(R.drawable.row_banana);
        ROW_CHERRY_PIECE_01 = textureManager.loadTexture(R.drawable.row_cherry_piece_01);
        ROW_CHERRY_PIECE_02 = textureManager.loadTexture(R.drawable.row_cherry_piece_02);
        ROW_STRAWBERRY_PIECE_01 = textureManager.loadTexture(R.drawable.row_strawberry_piece_01);
        ROW_STRAWBERRY_PIECE_02 = textureManager.loadTexture(R.drawable.row_strawberry_piece_02);
        ROW_LEMON_PIECE_01 = textureManager.loadTexture(R.drawable.row_lemon_piece_01);
        ROW_LEMON_PIECE_02 = textureManager.loadTexture(R.drawable.row_lemon_piece_02);
        ROW_COCONUT_PIECE_01 = textureManager.loadTexture(R.drawable.row_coconut_piece_01);
        ROW_COCONUT_PIECE_02 = textureManager.loadTexture(R.drawable.row_coconut_piece_02);
        ROW_BANANA_PIECE_01 = textureManager.loadTexture(R.drawable.row_banana_piece_01);
        ROW_BANANA_PIECE_02 = textureManager.loadTexture(R.drawable.row_banana_piece_02);

        COLUMN_CHERRY = textureManager.loadTexture(R.drawable.column_cherry);
        COLUMN_STRAWBERRY = textureManager.loadTexture(R.drawable.column_strawberry);
        COLUMN_LEMON = textureManager.loadTexture(R.drawable.column_lemon);
        COLUMN_COCONUT = textureManager.loadTexture(R.drawable.column_coconut);
        COLUMN_BANANA = textureManager.loadTexture(R.drawable.column_banana);
        COLUMN_CHERRY_PIECE_01 = textureManager.loadTexture(R.drawable.column_cherry_piece_01);
        COLUMN_CHERRY_PIECE_02 = textureManager.loadTexture(R.drawable.column_cherry_piece_02);
        COLUMN_STRAWBERRY_PIECE_01 = textureManager.loadTexture(R.drawable.column_strawberry_piece_01);
        COLUMN_STRAWBERRY_PIECE_02 = textureManager.loadTexture(R.drawable.column_strawberry_piece_02);
        COLUMN_LEMON_PIECE_01 = textureManager.loadTexture(R.drawable.column_lemon_piece_01);
        COLUMN_LEMON_PIECE_02 = textureManager.loadTexture(R.drawable.column_lemon_piece_02);
        COLUMN_COCONUT_PIECE_01 = textureManager.loadTexture(R.drawable.column_coconut_piece_01);
        COLUMN_COCONUT_PIECE_02 = textureManager.loadTexture(R.drawable.column_coconut_piece_02);
        COLUMN_BANANA_PIECE_01 = textureManager.loadTexture(R.drawable.column_banana_piece_01);
        COLUMN_BANANA_PIECE_02 = textureManager.loadTexture(R.drawable.column_banana_piece_02);

        EXPLOSION_CHERRY = textureManager.loadTexture(R.drawable.explosion_cherry);
        EXPLOSION_STRAWBERRY = textureManager.loadTexture(R.drawable.explosion_strawberry);
        EXPLOSION_LEMON = textureManager.loadTexture(R.drawable.explosion_lemon);
        EXPLOSION_COCONUT = textureManager.loadTexture(R.drawable.explosion_coconut);
        EXPLOSION_BANANA = textureManager.loadTexture(R.drawable.explosion_banana);
        EXPLOSION_CHERRY_PIECE_01 = textureManager.loadTexture(R.drawable.explosion_cherry_piece_01);
        EXPLOSION_CHERRY_PIECE_02 = textureManager.loadTexture(R.drawable.explosion_cherry_piece_02);
        EXPLOSION_STRAWBERRY_PIECE_01 = textureManager.loadTexture(R.drawable.explosion_strawberry_piece_01);
        EXPLOSION_STRAWBERRY_PIECE_02 = textureManager.loadTexture(R.drawable.explosion_strawberry_piece_02);
        EXPLOSION_LEMON_PIECE_01 = textureManager.loadTexture(R.drawable.explosion_lemon_piece_01);
        EXPLOSION_LEMON_PIECE_02 = textureManager.loadTexture(R.drawable.explosion_lemon_piece_02);
        EXPLOSION_COCONUT_PIECE_01 = textureManager.loadTexture(R.drawable.explosion_coconut_piece_01);
        EXPLOSION_COCONUT_PIECE_02 = textureManager.loadTexture(R.drawable.explosion_coconut_piece_02);
        EXPLOSION_BANANA_PIECE_01 = textureManager.loadTexture(R.drawable.explosion_banana_piece_01);
        EXPLOSION_BANANA_PIECE_02 = textureManager.loadTexture(R.drawable.explosion_banana_piece_02);

        ICE_CREAM = textureManager.loadTexture(R.drawable.ice_cream);

        COOKIE = textureManager.loadTexture(R.drawable.cookie);
        COOKIE_PIECE_01 = textureManager.loadTexture(R.drawable.cookie_piece_01);
        COOKIE_PIECE_02 = textureManager.loadTexture(R.drawable.cookie_piece_02);
        COOKIE_PIECE_03 = textureManager.loadTexture(R.drawable.cookie_piece_03);
        COOKIE_PIECE_04 = textureManager.loadTexture(R.drawable.cookie_piece_04);
        COOKIE_PIECE_05 = textureManager.loadTexture(R.drawable.cookie_piece_05);
        COOKIE_PIECE_06 = textureManager.loadTexture(R.drawable.cookie_piece_06);

        CAKE_01 = textureManager.loadTexture(R.drawable.cake_01);
        CAKE_02 = textureManager.loadTexture(R.drawable.cake_02);
        CAKE_03 = textureManager.loadTexture(R.drawable.cake_03);
        CAKE_04 = textureManager.loadTexture(R.drawable.cake_04);
        CAKE_PIECE_01 = textureManager.loadTexture(R.drawable.cake_piece_01);
        CAKE_PIECE_02 = textureManager.loadTexture(R.drawable.cake_piece_02);
        CAKE_PIECE_03 = textureManager.loadTexture(R.drawable.cake_piece_03);
        CAKE_PIECE_04 = textureManager.loadTexture(R.drawable.cake_piece_04);
        CAKE_PIECE_05 = textureManager.loadTexture(R.drawable.cake_piece_05);
        CAKE_PIECE_06 = textureManager.loadTexture(R.drawable.cake_piece_06);

        SCORE_PINK = textureManager.loadTexture(R.drawable.score_pink);
        SCORE_RED = textureManager.loadTexture(R.drawable.score_red);
        SCORE_YELLOW = textureManager.loadTexture(R.drawable.score_yellow);
        SCORE_BROWN = textureManager.loadTexture(R.drawable.score_brown);
        SCORE_WHITE = textureManager.loadTexture(R.drawable.score_white);

        LOCK = textureManager.loadTexture(R.drawable.lock);
        LOCK_PIECE_01 = textureManager.loadTexture(R.drawable.lock_piece_01);
        LOCK_PIECE_02 = textureManager.loadTexture(R.drawable.lock_piece_02);
        LOCK_PIECE_03 = textureManager.loadTexture(R.drawable.lock_piece_03);
        LOCK_PIECE_04 = textureManager.loadTexture(R.drawable.lock_piece_04);
        LOCK_PIECE_05 = textureManager.loadTexture(R.drawable.lock_piece_05);

        ICE_CENTER_01 = textureManager.loadTexture(R.drawable.ice_center_01);
        ICE_CENTER_02 = textureManager.loadTexture(R.drawable.ice_center_02);
        ICE_MARGIN_01 = textureManager.loadTexture(R.drawable.ice_margin_01);
        ICE_MARGIN_02 = textureManager.loadTexture(R.drawable.ice_margin_02);
        ICE_CORNER_01 = textureManager.loadTexture(R.drawable.ice_corner_01);
        ICE_CORNER_02 = textureManager.loadTexture(R.drawable.ice_corner_02);
        ICE_PIECE = textureManager.loadTexture(ShapeUtils.createCircleBitmap(20));

        GENERATOR_ROW = textureManager.loadTexture(R.drawable.genetator_row);
        GENERATOR_COLUMN = textureManager.loadTexture(R.drawable.generator_column);
        GENERATOR_EXPLOSION = textureManager.loadTexture(R.drawable.generator_explosion);
        GENERATOR_ICE_CREAM = textureManager.loadTexture(R.drawable.generator_ice_cream);
        GENERATOR_STARFISH = textureManager.loadTexture(R.drawable.generator_starfish);
        GENERATOR_PIVOT = textureManager.loadTexture(R.drawable.generator_pivot);

        TEXT_COMBO_NICE = textureManager.loadTexture(R.drawable.text_combo_nice);
        TEXT_COMBO_GREAT = textureManager.loadTexture(R.drawable.text_combo_great);
        TEXT_COMBO_WONDERFUL = textureManager.loadTexture(R.drawable.text_combo_wonderful);
        TEXT_SHUFFLE = textureManager.loadTexture(R.drawable.text_shuffle);
        TEXT_BONUS = textureManager.loadTexture(R.drawable.text_bonus);

        HAMMER = textureManager.loadTexture(R.drawable.hammer);
        GLOVE = textureManager.loadTexture(R.drawable.glove);
        BOMB = textureManager.loadTexture(R.drawable.bomb);

        TUBE = textureManager.loadTexture(R.drawable.tube);
        STARFISH = textureManager.loadTexture(R.drawable.starfish);
        ARROW = textureManager.loadTexture(R.drawable.arrow);

        LIGHT_BG = textureManager.loadTexture(R.drawable.light_bg);
        BLUE_LIGHT_BG = textureManager.loadTexture(R.drawable.blue_light_bg);
        RING_LIGHT = textureManager.loadTexture(R.drawable.ring_light);
        GLITTER = textureManager.loadTexture(R.drawable.glitter);
        LIGHTNING_GLITTER = textureManager.loadTexture(R.drawable.lightning_glitter);
        EXPLOSION_BEAM = textureManager.loadTexture(R.drawable.explosion_beam);
        RING_FLASH = textureManager.loadTexture(R.drawable.ring_flash);
        BLUE_RING_FLASH = textureManager.loadTexture(R.drawable.blue_ring_flash);

        COLUMN_FLASH_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapTopDown(ResourceUtils.getBitmap(context, R.drawable.column_flash), 10));
        ROW_FLASH_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapLeftRight(ResourceUtils.getBitmap(context, R.drawable.row_flash), 10));
        EXPLOSION_FLASH_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapInOut(ResourceUtils.getBitmap(context, R.drawable.explosion_flash), 10));
        TRANSFORM_FLASH_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapInOut(ResourceUtils.getBitmap(context, R.drawable.transform_flash), 10));
        Texture2DGroup clipLightnings = textureManager.loadTextureGroup(ClipUtils.clipBitmapTopDown(ResourceUtils.getBitmap(context, R.drawable.lightning_01), 5));
        Texture2DGroup lightnings = textureManager.loadTextureGroup(new int[]{
                R.drawable.lightning_01,
                R.drawable.lightning_02,
                R.drawable.lightning_03,
                R.drawable.lightning_04,
                R.drawable.lightning_05});
        clipLightnings.addTextures(lightnings.getTextures());
        LIGHTNING_ANIMATION = clipLightnings;
        SMOKE_ANIMATION = textureManager.loadTextureGroup(new int[]{
                R.drawable.smoke_01,
                R.drawable.smoke_02,
                R.drawable.smoke_03,
                R.drawable.smoke_04,
                R.drawable.smoke_05,
                R.drawable.smoke_06,
                R.drawable.smoke_07,
                R.drawable.smoke_08});
    }
    //========================================================

}
