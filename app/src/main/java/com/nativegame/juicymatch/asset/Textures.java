package com.nativegame.juicymatch.asset;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.nativegame.juicymatch.R;
import com.nativegame.nattyengine.texture.texture2d.Texture2D;
import com.nativegame.nattyengine.texture.texture2d.Texture2DGroup;
import com.nativegame.nattyengine.texture.texture2d.Texture2DManager;
import com.nativegame.nattyengine.util.bitmap.BitmapUtils;
import com.nativegame.nattyengine.util.bitmap.ClipUtils;
import com.nativegame.nattyengine.util.resource.ResourceUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class Textures {

    public static Texture2D GRID_CENTER;
    public static Texture2D GRID_SIDE;
    public static Texture2D GRID_CORNER;
    public static Texture2D GRID_MARGIN;
    public static Texture2D GRID_PIPE;
    public static Texture2D GRID_SOLE;

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

    public static Texture2D ROW_STRIPED_CHERRY;
    public static Texture2D ROW_STRIPED_STRAWBERRY;
    public static Texture2D ROW_STRIPED_LEMON;
    public static Texture2D ROW_STRIPED_COCONUT;
    public static Texture2D ROW_STRIPED_BANANA;
    public static Texture2D ROW_STRIPED_CHERRY_PIECE_01;
    public static Texture2D ROW_STRIPED_CHERRY_PIECE_02;
    public static Texture2D ROW_STRIPED_STRAWBERRY_PIECE_01;
    public static Texture2D ROW_STRIPED_STRAWBERRY_PIECE_02;
    public static Texture2D ROW_STRIPED_LEMON_PIECE_01;
    public static Texture2D ROW_STRIPED_LEMON_PIECE_02;
    public static Texture2D ROW_STRIPED_COCONUT_PIECE_01;
    public static Texture2D ROW_STRIPED_COCONUT_PIECE_02;
    public static Texture2D ROW_STRIPED_BANANA_PIECE_01;
    public static Texture2D ROW_STRIPED_BANANA_PIECE_02;

    public static Texture2D COLUMN_STRIPED_CHERRY;
    public static Texture2D COLUMN_STRIPED_STRAWBERRY;
    public static Texture2D COLUMN_STRIPED_LEMON;
    public static Texture2D COLUMN_STRIPED_COCONUT;
    public static Texture2D COLUMN_STRIPED_BANANA;
    public static Texture2D COLUMN_STRIPED_CHERRY_PIECE_01;
    public static Texture2D COLUMN_STRIPED_CHERRY_PIECE_02;
    public static Texture2D COLUMN_STRIPED_STRAWBERRY_PIECE_01;
    public static Texture2D COLUMN_STRIPED_STRAWBERRY_PIECE_02;
    public static Texture2D COLUMN_STRIPED_LEMON_PIECE_01;
    public static Texture2D COLUMN_STRIPED_LEMON_PIECE_02;
    public static Texture2D COLUMN_STRIPED_COCONUT_PIECE_01;
    public static Texture2D COLUMN_STRIPED_COCONUT_PIECE_02;
    public static Texture2D COLUMN_STRIPED_BANANA_PIECE_01;
    public static Texture2D COLUMN_STRIPED_BANANA_PIECE_02;

    public static Texture2D EXPLOSIVE_CHERRY;
    public static Texture2D EXPLOSIVE_STRAWBERRY;
    public static Texture2D EXPLOSIVE_LEMON;
    public static Texture2D EXPLOSIVE_COCONUT;
    public static Texture2D EXPLOSIVE_BANANA;
    public static Texture2D EXPLOSIVE_CHERRY_PIECE_01;
    public static Texture2D EXPLOSIVE_CHERRY_PIECE_02;
    public static Texture2D EXPLOSIVE_STRAWBERRY_PIECE_01;
    public static Texture2D EXPLOSIVE_STRAWBERRY_PIECE_02;
    public static Texture2D EXPLOSIVE_LEMON_PIECE_01;
    public static Texture2D EXPLOSIVE_LEMON_PIECE_02;
    public static Texture2D EXPLOSIVE_COCONUT_PIECE_01;
    public static Texture2D EXPLOSIVE_COCONUT_PIECE_02;
    public static Texture2D EXPLOSIVE_BANANA_PIECE_01;
    public static Texture2D EXPLOSIVE_BANANA_PIECE_02;

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
    public static Texture2D CAKE_PIECE;

    public static Texture2D PIE_01;
    public static Texture2D PIE_02;
    public static Texture2D PIE_03;
    public static Texture2D PIE_04;
    public static Texture2D YELLOW_PIE_PIECE;
    public static Texture2D WHITE_PIE_PIECE;
    public static Texture2D PIE_PAN_PIECE;

    public static Texture2D CANDY_01;
    public static Texture2D CANDY_02;
    public static Texture2D CANDY_PIECE;
    public static Texture2D CANDY_WRAPPER_PIECE;

    public static Texture2D SCORE_PINK;
    public static Texture2D SCORE_RED;
    public static Texture2D SCORE_YELLOW;
    public static Texture2D SCORE_BROWN;
    public static Texture2D SCORE_WHITE;

    public static Texture2D LOCK;
    public static Texture2D LOCK_PIECE;

    public static Texture2D ICE_CENTER_01;
    public static Texture2D ICE_CENTER_02;
    public static Texture2D ICE_SIDE_01;
    public static Texture2D ICE_SIDE_02;
    public static Texture2D ICE_CORNER_01;
    public static Texture2D ICE_CORNER_02;
    public static Texture2D ICE_MARGIN_01;
    public static Texture2D ICE_MARGIN_02;
    public static Texture2D ICE_PIPE_01;
    public static Texture2D ICE_PIPE_02;
    public static Texture2D ICE_SOLE_01;
    public static Texture2D ICE_SOLE_02;
    public static Texture2D BLUE_ICE_PIECE;
    public static Texture2D WHITE_ICE_PIECE;

    public static Texture2D SAND_CENTER_01;
    public static Texture2D SAND_CENTER_02;
    public static Texture2D SAND_SIDE_01;
    public static Texture2D SAND_SIDE_02;
    public static Texture2D SAND_CORNER_01;
    public static Texture2D SAND_CORNER_02;
    public static Texture2D SAND_MARGIN_01;
    public static Texture2D SAND_MARGIN_02;
    public static Texture2D SAND_PIPE_01;
    public static Texture2D SAND_PIPE_02;
    public static Texture2D SAND_SOLE_01;
    public static Texture2D SAND_SOLE_02;
    public static Texture2D YELLOW_SAND_PIECE;
    public static Texture2D BROWN_SAND_PIECE;

    public static Texture2D HONEY_CENTER;
    public static Texture2D HONEY_SIDE;
    public static Texture2D HONEY_CORNER;
    public static Texture2D HONEY_MARGIN;
    public static Texture2D HONEY_PIPE;
    public static Texture2D HONEY_SOLE;
    public static Texture2D HONEY_PIECE;

    public static Texture2D GENERATOR_ROW_STRIPED;
    public static Texture2D GENERATOR_COLUMN_STRIPED;
    public static Texture2D GENERATOR_ROW_COLUMN_STRIPED;
    public static Texture2D GENERATOR_EXPLOSIVE;
    public static Texture2D GENERATOR_PIVOT;

    public static Texture2D TEXT_COMBO_NICE;
    public static Texture2D TEXT_COMBO_GREAT;
    public static Texture2D TEXT_COMBO_WONDERFUL;
    public static Texture2D TEXT_SHUFFLE;
    public static Texture2D TEXT_BONUS;

    public static Texture2D TUTORIAL_FINGER;

    public static Texture2D HAMMER;
    public static Texture2D GLOVE;
    public static Texture2D BOMB;

    public static Texture2D PIPE;
    public static Texture2D STARFISH;
    public static Texture2D SHELL_01_VERTICAL;
    public static Texture2D SHELL_01_HORIZONTAL;
    public static Texture2D SHELL_02;
    public static Texture2D SHELL_03;
    public static Texture2D ARROW;

    public static Texture2D LIGHT_BG;
    public static Texture2D LIGHT_BG_BLUE;
    public static Texture2D LIGHT_RING;
    public static Texture2D LIGHT_CIRCLE;
    public static Texture2D GLITTER;
    public static Texture2D GLITTER_BLUE;
    public static Texture2D FLASH_BEAM;
    public static Texture2D FLASH_RING;
    public static Texture2D FLASH_RING_BLUE;
    public static Texture2D CONFETTI_BLUE;
    public static Texture2D CONFETTI_GREEN;
    public static Texture2D CONFETTI_PINK;
    public static Texture2D CONFETTI_YELLOW;
    public static Texture2DGroup FLASH_COLUMN_ANIMATION;
    public static Texture2DGroup FLASH_ROW_ANIMATION;
    public static Texture2DGroup FLASH_EXPLOSION_ANIMATION;
    public static Texture2DGroup FLASH_TRANSFORM_ANIMATION;
    public static Texture2DGroup LIGHTNING_ANIMATION;
    public static Texture2DGroup SMOKE_ANIMATION;

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static void load(Texture2DManager textureManager, Context context) {
        GRID_CENTER = textureManager.loadTexture(R.drawable.grid_center);
        GRID_SIDE = textureManager.loadTexture(R.drawable.grid_side);
        GRID_CORNER = textureManager.loadTexture(R.drawable.grid_corner);
        GRID_MARGIN = textureManager.loadTexture(R.drawable.grid_margin);
        GRID_PIPE = textureManager.loadTexture(R.drawable.grid_pipe);
        GRID_SOLE = textureManager.loadTexture(R.drawable.grid_sole);

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

        ROW_STRIPED_CHERRY = textureManager.loadTexture(R.drawable.row_striped_cherry);
        ROW_STRIPED_STRAWBERRY = textureManager.loadTexture(R.drawable.row_striped_strawberry);
        ROW_STRIPED_LEMON = textureManager.loadTexture(R.drawable.row_striped_lemon);
        ROW_STRIPED_COCONUT = textureManager.loadTexture(R.drawable.row_striped_coconut);
        ROW_STRIPED_BANANA = textureManager.loadTexture(R.drawable.row_striped_banana);
        ROW_STRIPED_CHERRY_PIECE_01 = textureManager.loadTexture(R.drawable.row_striped_cherry_piece_01);
        ROW_STRIPED_CHERRY_PIECE_02 = textureManager.loadTexture(R.drawable.row_striped_cherry_piece_02);
        ROW_STRIPED_STRAWBERRY_PIECE_01 = textureManager.loadTexture(R.drawable.row_striped_strawberry_piece_01);
        ROW_STRIPED_STRAWBERRY_PIECE_02 = textureManager.loadTexture(R.drawable.row_striped_strawberry_piece_02);
        ROW_STRIPED_LEMON_PIECE_01 = textureManager.loadTexture(R.drawable.row_striped_lemon_piece_01);
        ROW_STRIPED_LEMON_PIECE_02 = textureManager.loadTexture(R.drawable.row_striped_lemon_piece_02);
        ROW_STRIPED_COCONUT_PIECE_01 = textureManager.loadTexture(R.drawable.row_striped_coconut_piece_01);
        ROW_STRIPED_COCONUT_PIECE_02 = textureManager.loadTexture(R.drawable.row_striped_coconut_piece_02);
        ROW_STRIPED_BANANA_PIECE_01 = textureManager.loadTexture(R.drawable.row_striped_banana_piece_01);
        ROW_STRIPED_BANANA_PIECE_02 = textureManager.loadTexture(R.drawable.row_striped_banana_piece_02);

        COLUMN_STRIPED_CHERRY = textureManager.loadTexture(R.drawable.column_striped_cherry);
        COLUMN_STRIPED_STRAWBERRY = textureManager.loadTexture(R.drawable.column_striped_strawberry);
        COLUMN_STRIPED_LEMON = textureManager.loadTexture(R.drawable.column_striped_lemon);
        COLUMN_STRIPED_COCONUT = textureManager.loadTexture(R.drawable.column_striped_coconut);
        COLUMN_STRIPED_BANANA = textureManager.loadTexture(R.drawable.column_striped_banana);
        COLUMN_STRIPED_CHERRY_PIECE_01 = textureManager.loadTexture(R.drawable.column_striped_cherry_piece_01);
        COLUMN_STRIPED_CHERRY_PIECE_02 = textureManager.loadTexture(R.drawable.column_striped_cherry_piece_02);
        COLUMN_STRIPED_STRAWBERRY_PIECE_01 = textureManager.loadTexture(R.drawable.column_striped_strawberry_piece_01);
        COLUMN_STRIPED_STRAWBERRY_PIECE_02 = textureManager.loadTexture(R.drawable.column_striped_strawberry_piece_02);
        COLUMN_STRIPED_LEMON_PIECE_01 = textureManager.loadTexture(R.drawable.column_striped_lemon_piece_01);
        COLUMN_STRIPED_LEMON_PIECE_02 = textureManager.loadTexture(R.drawable.column_striped_lemon_piece_02);
        COLUMN_STRIPED_COCONUT_PIECE_01 = textureManager.loadTexture(R.drawable.column_striped_coconut_piece_01);
        COLUMN_STRIPED_COCONUT_PIECE_02 = textureManager.loadTexture(R.drawable.column_striped_coconut_piece_02);
        COLUMN_STRIPED_BANANA_PIECE_01 = textureManager.loadTexture(R.drawable.column_striped_banana_piece_01);
        COLUMN_STRIPED_BANANA_PIECE_02 = textureManager.loadTexture(R.drawable.column_striped_banana_piece_02);

        EXPLOSIVE_CHERRY = textureManager.loadTexture(R.drawable.explosive_cherry);
        EXPLOSIVE_STRAWBERRY = textureManager.loadTexture(R.drawable.explosive_strawberry);
        EXPLOSIVE_LEMON = textureManager.loadTexture(R.drawable.explosive_lemon);
        EXPLOSIVE_COCONUT = textureManager.loadTexture(R.drawable.explosive_coconut);
        EXPLOSIVE_BANANA = textureManager.loadTexture(R.drawable.explosive_banana);
        EXPLOSIVE_CHERRY_PIECE_01 = textureManager.loadTexture(R.drawable.explosive_cherry_piece_01);
        EXPLOSIVE_CHERRY_PIECE_02 = textureManager.loadTexture(R.drawable.explosive_cherry_piece_02);
        EXPLOSIVE_STRAWBERRY_PIECE_01 = textureManager.loadTexture(R.drawable.explosive_strawberry_piece_01);
        EXPLOSIVE_STRAWBERRY_PIECE_02 = textureManager.loadTexture(R.drawable.explosive_strawberry_piece_02);
        EXPLOSIVE_LEMON_PIECE_01 = textureManager.loadTexture(R.drawable.explosive_lemon_piece_01);
        EXPLOSIVE_LEMON_PIECE_02 = textureManager.loadTexture(R.drawable.explosive_lemon_piece_02);
        EXPLOSIVE_COCONUT_PIECE_01 = textureManager.loadTexture(R.drawable.explosive_coconut_piece_01);
        EXPLOSIVE_COCONUT_PIECE_02 = textureManager.loadTexture(R.drawable.explosive_coconut_piece_02);
        EXPLOSIVE_BANANA_PIECE_01 = textureManager.loadTexture(R.drawable.explosive_banana_piece_01);
        EXPLOSIVE_BANANA_PIECE_02 = textureManager.loadTexture(R.drawable.explosive_banana_piece_02);

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
        CAKE_PIECE = textureManager.loadTexture(R.drawable.cake_piece);

        PIE_01 = textureManager.loadTexture(R.drawable.pie_01);
        PIE_02 = textureManager.loadTexture(R.drawable.pie_02);
        PIE_03 = textureManager.loadTexture(R.drawable.pie_03);
        PIE_04 = textureManager.loadTexture(R.drawable.pie_04);
        Paint yellow = new Paint();
        yellow.setColor(Color.parseColor("#FFCC80"));
        YELLOW_PIE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, yellow));
        Paint white = new Paint();
        white.setColor(Color.parseColor("#FFFFFFFF"));
        WHITE_PIE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, white));
        PIE_PAN_PIECE = textureManager.loadTexture(R.drawable.pie_pan_piece);

        CANDY_01 = textureManager.loadTexture(R.drawable.candy_01);
        CANDY_02 = textureManager.loadTexture(R.drawable.candy_02);
        CANDY_PIECE = textureManager.loadTexture(R.drawable.candy_piece);
        CANDY_WRAPPER_PIECE = textureManager.loadTexture(R.drawable.candy_wapper_piece);

        SCORE_PINK = textureManager.loadTexture(R.drawable.score_pink);
        SCORE_RED = textureManager.loadTexture(R.drawable.score_red);
        SCORE_YELLOW = textureManager.loadTexture(R.drawable.score_yellow);
        SCORE_BROWN = textureManager.loadTexture(R.drawable.score_brown);
        SCORE_WHITE = textureManager.loadTexture(R.drawable.score_white);

        LOCK = textureManager.loadTexture(R.drawable.lock);
        LOCK_PIECE = textureManager.loadTexture(R.drawable.lock_piece);

        ICE_CENTER_01 = textureManager.loadTexture(R.drawable.ice_center_01);
        ICE_CENTER_02 = textureManager.loadTexture(R.drawable.ice_center_02);
        ICE_SIDE_01 = textureManager.loadTexture(R.drawable.ice_side_01);
        ICE_SIDE_02 = textureManager.loadTexture(R.drawable.ice_side_02);
        ICE_CORNER_01 = textureManager.loadTexture(R.drawable.ice_corner_01);
        ICE_CORNER_02 = textureManager.loadTexture(R.drawable.ice_corner_02);
        ICE_MARGIN_01 = textureManager.loadTexture(R.drawable.ice_margin_01);
        ICE_MARGIN_02 = textureManager.loadTexture(R.drawable.ice_margin_02);
        ICE_PIPE_01 = textureManager.loadTexture(R.drawable.ice_pipe_01);
        ICE_PIPE_02 = textureManager.loadTexture(R.drawable.ice_pipe_02);
        ICE_SOLE_01 = textureManager.loadTexture(R.drawable.ice_sole_01);
        ICE_SOLE_02 = textureManager.loadTexture(R.drawable.ice_sole_02);
        Paint blue = new Paint();
        blue.setColor(Color.parseColor("#FFBBDEFB"));
        BLUE_ICE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, blue));
        WHITE_ICE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, white));

        SAND_CENTER_01 = textureManager.loadTexture(R.drawable.sand_center_01);
        SAND_CENTER_02 = textureManager.loadTexture(R.drawable.sand_center_02);
        SAND_SIDE_01 = textureManager.loadTexture(R.drawable.sand_side_01);
        SAND_SIDE_02 = textureManager.loadTexture(R.drawable.sand_side_02);
        SAND_CORNER_01 = textureManager.loadTexture(R.drawable.sand_corner_01);
        SAND_CORNER_02 = textureManager.loadTexture(R.drawable.sand_corner_02);
        SAND_MARGIN_01 = textureManager.loadTexture(R.drawable.sand_margin_01);
        SAND_MARGIN_02 = textureManager.loadTexture(R.drawable.sand_margin_02);
        SAND_PIPE_01 = textureManager.loadTexture(R.drawable.sand_pipe_01);
        SAND_PIPE_02 = textureManager.loadTexture(R.drawable.sand_pipe_02);
        SAND_SOLE_01 = textureManager.loadTexture(R.drawable.sand_sole_01);
        SAND_SOLE_02 = textureManager.loadTexture(R.drawable.sand_sole_02);
        Paint brown = new Paint();
        brown.setColor(Color.parseColor("#AA6C39"));
        BROWN_SAND_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(10, brown));
        YELLOW_SAND_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(10, yellow));

        HONEY_CENTER = textureManager.loadTexture(R.drawable.honey_center);
        HONEY_SIDE = textureManager.loadTexture(R.drawable.honey_side);
        HONEY_CORNER = textureManager.loadTexture(R.drawable.honey_corner);
        HONEY_MARGIN = textureManager.loadTexture(R.drawable.honey_margin);
        HONEY_PIPE = textureManager.loadTexture(R.drawable.honey_pipe);
        HONEY_SOLE = textureManager.loadTexture(R.drawable.honey_sole);
        HONEY_PIECE = textureManager.loadTexture(R.drawable.honey_piece);

        GENERATOR_ROW_STRIPED = textureManager.loadTexture(R.drawable.generator_row_striped);
        GENERATOR_COLUMN_STRIPED = textureManager.loadTexture(R.drawable.generator_column_striped);
        GENERATOR_ROW_COLUMN_STRIPED = textureManager.loadTexture(R.drawable.generator_row_column_striped);
        GENERATOR_EXPLOSIVE = textureManager.loadTexture(R.drawable.generator_explosive);
        GENERATOR_PIVOT = textureManager.loadTexture(R.drawable.generator_pivot);

        TEXT_COMBO_NICE = textureManager.loadTexture(R.drawable.text_combo_nice);
        TEXT_COMBO_GREAT = textureManager.loadTexture(R.drawable.text_combo_great);
        TEXT_COMBO_WONDERFUL = textureManager.loadTexture(R.drawable.text_combo_wonderful);
        TEXT_SHUFFLE = textureManager.loadTexture(R.drawable.text_shuffle);
        TEXT_BONUS = textureManager.loadTexture(R.drawable.text_bonus);

        TUTORIAL_FINGER = textureManager.loadTexture(R.drawable.tutorial_finger);

        HAMMER = textureManager.loadTexture(R.drawable.hammer);
        GLOVE = textureManager.loadTexture(R.drawable.glove);
        BOMB = textureManager.loadTexture(R.drawable.bomb);

        PIPE = textureManager.loadTexture(R.drawable.pipe);
        STARFISH = textureManager.loadTexture(R.drawable.starfish);
        SHELL_01_VERTICAL = textureManager.loadTexture(R.drawable.shell_01_vertical);
        SHELL_01_HORIZONTAL = textureManager.loadTexture(R.drawable.shell_01_horizontal);
        SHELL_02 = textureManager.loadTexture(R.drawable.shell_02);
        SHELL_03 = textureManager.loadTexture(R.drawable.shell_03);
        ARROW = textureManager.loadTexture(R.drawable.arrow);

        LIGHT_BG = textureManager.loadTexture(R.drawable.light_bg);
        LIGHT_BG_BLUE = textureManager.loadTexture(R.drawable.light_bg_blue);
        LIGHT_RING = textureManager.loadTexture(R.drawable.light_ring);
        LIGHT_CIRCLE = textureManager.loadTexture(R.drawable.light_circle);
        GLITTER = textureManager.loadTexture(R.drawable.glitter);
        GLITTER_BLUE = textureManager.loadTexture(R.drawable.glitter_blue);
        FLASH_RING = textureManager.loadTexture(R.drawable.flash_ring);
        FLASH_RING_BLUE = textureManager.loadTexture(R.drawable.flash_ring_blue);
        FLASH_BEAM = textureManager.loadTexture(R.drawable.flash_beam);
        CONFETTI_BLUE = textureManager.loadTexture(R.drawable.confetti_blue);
        CONFETTI_GREEN = textureManager.loadTexture(R.drawable.confetti_green);
        CONFETTI_PINK = textureManager.loadTexture(R.drawable.confetti_pink);
        CONFETTI_YELLOW = textureManager.loadTexture(R.drawable.confetti_yellow);

        FLASH_ROW_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapLeftRight(ResourceUtils.getBitmap(context, R.drawable.flash_row), 10));
        FLASH_COLUMN_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapTopDown(ResourceUtils.getBitmap(context, R.drawable.flash_column), 10));
        FLASH_EXPLOSION_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapInOut(ResourceUtils.getBitmap(context, R.drawable.flash_explosion), 10));
        FLASH_TRANSFORM_ANIMATION = textureManager.loadTextureGroup(ClipUtils.clipBitmapInOut(ResourceUtils.getBitmap(context, R.drawable.flash_transform), 10));
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
