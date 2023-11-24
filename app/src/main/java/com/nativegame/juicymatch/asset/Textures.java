package com.nativegame.juicymatch.asset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.nativegame.juicymatch.R;
import com.nativegame.natyengine.texture.texture2d.Texture2D;
import com.nativegame.natyengine.texture.texture2d.Texture2DGroup;
import com.nativegame.natyengine.texture.texture2d.Texture2DManager;
import com.nativegame.natyengine.util.BitmapUtils;
import com.nativegame.natyengine.util.ResourceUtils;

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
    public static Texture2D CHERRY_PIECE_01;
    public static Texture2D CHERRY_PIECE_02;
    public static Texture2D ROW_STRIPED_CHERRY;
    public static Texture2D ROW_STRIPED_CHERRY_PIECE_01;
    public static Texture2D ROW_STRIPED_CHERRY_PIECE_02;
    public static Texture2D COLUMN_STRIPED_CHERRY;
    public static Texture2D COLUMN_STRIPED_CHERRY_PIECE_01;
    public static Texture2D COLUMN_STRIPED_CHERRY_PIECE_02;
    public static Texture2D EXPLOSIVE_CHERRY;
    public static Texture2D EXPLOSIVE_CHERRY_PIECE_01;
    public static Texture2D EXPLOSIVE_CHERRY_PIECE_02;

    public static Texture2D STRAWBERRY;
    public static Texture2D STRAWBERRY_PIECE_01;
    public static Texture2D STRAWBERRY_PIECE_02;
    public static Texture2D ROW_STRIPED_STRAWBERRY;
    public static Texture2D ROW_STRIPED_STRAWBERRY_PIECE_01;
    public static Texture2D ROW_STRIPED_STRAWBERRY_PIECE_02;
    public static Texture2D COLUMN_STRIPED_STRAWBERRY;
    public static Texture2D COLUMN_STRIPED_STRAWBERRY_PIECE_01;
    public static Texture2D COLUMN_STRIPED_STRAWBERRY_PIECE_02;
    public static Texture2D EXPLOSIVE_STRAWBERRY;
    public static Texture2D EXPLOSIVE_STRAWBERRY_PIECE_01;
    public static Texture2D EXPLOSIVE_STRAWBERRY_PIECE_02;

    public static Texture2D LEMON;
    public static Texture2D LEMON_PIECE_01;
    public static Texture2D LEMON_PIECE_02;
    public static Texture2D ROW_STRIPED_LEMON;
    public static Texture2D ROW_STRIPED_LEMON_PIECE_01;
    public static Texture2D ROW_STRIPED_LEMON_PIECE_02;
    public static Texture2D COLUMN_STRIPED_LEMON;
    public static Texture2D COLUMN_STRIPED_LEMON_PIECE_01;
    public static Texture2D COLUMN_STRIPED_LEMON_PIECE_02;
    public static Texture2D EXPLOSIVE_LEMON;
    public static Texture2D EXPLOSIVE_LEMON_PIECE_01;
    public static Texture2D EXPLOSIVE_LEMON_PIECE_02;

    public static Texture2D COCONUT;
    public static Texture2D COCONUT_PIECE_01;
    public static Texture2D COCONUT_PIECE_02;
    public static Texture2D ROW_STRIPED_COCONUT;
    public static Texture2D ROW_STRIPED_COCONUT_PIECE_01;
    public static Texture2D ROW_STRIPED_COCONUT_PIECE_02;
    public static Texture2D COLUMN_STRIPED_COCONUT;
    public static Texture2D COLUMN_STRIPED_COCONUT_PIECE_01;
    public static Texture2D COLUMN_STRIPED_COCONUT_PIECE_02;
    public static Texture2D EXPLOSIVE_COCONUT;
    public static Texture2D EXPLOSIVE_COCONUT_PIECE_01;
    public static Texture2D EXPLOSIVE_COCONUT_PIECE_02;

    public static Texture2D BANANA;
    public static Texture2D BANANA_PIECE_01;
    public static Texture2D BANANA_PIECE_02;
    public static Texture2D ROW_STRIPED_BANANA;
    public static Texture2D ROW_STRIPED_BANANA_PIECE_01;
    public static Texture2D ROW_STRIPED_BANANA_PIECE_02;
    public static Texture2D COLUMN_STRIPED_BANANA;
    public static Texture2D COLUMN_STRIPED_BANANA_PIECE_01;
    public static Texture2D COLUMN_STRIPED_BANANA_PIECE_02;
    public static Texture2D EXPLOSIVE_BANANA;
    public static Texture2D EXPLOSIVE_BANANA_PIECE_01;
    public static Texture2D EXPLOSIVE_BANANA_PIECE_02;

    public static Texture2D EMPTY;
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

    public static Texture2D LOCK;
    public static Texture2D LOCK_PIECE;

    public static Texture2D ICE_CENTER_01;
    public static Texture2D ICE_SIDE_01;
    public static Texture2D ICE_CORNER_01;
    public static Texture2D ICE_MARGIN_01;
    public static Texture2D ICE_PIPE_01;
    public static Texture2D ICE_SOLE_01;

    public static Texture2D ICE_CENTER_02;
    public static Texture2D ICE_SIDE_02;
    public static Texture2D ICE_CORNER_02;
    public static Texture2D ICE_MARGIN_02;
    public static Texture2D ICE_PIPE_02;
    public static Texture2D ICE_SOLE_02;

    public static Texture2D BLUE_ICE_PIECE;
    public static Texture2D WHITE_ICE_PIECE;

    public static Texture2D SAND_CENTER_01;
    public static Texture2D SAND_SIDE_01;
    public static Texture2D SAND_CORNER_01;
    public static Texture2D SAND_MARGIN_01;
    public static Texture2D SAND_PIPE_01;
    public static Texture2D SAND_SOLE_01;

    public static Texture2D SAND_CENTER_02;
    public static Texture2D SAND_SIDE_02;
    public static Texture2D SAND_CORNER_02;
    public static Texture2D SAND_MARGIN_02;
    public static Texture2D SAND_PIPE_02;
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

    public static Texture2D SHELL_01_VERTICAL;
    public static Texture2D SHELL_01_HORIZONTAL;
    public static Texture2D SHELL_02;
    public static Texture2D SHELL_03;

    public static Texture2D STARFISH;
    public static Texture2D ARROW;
    public static Texture2D PIPE;

    public static Texture2D SCORE_PINK;
    public static Texture2D SCORE_RED;
    public static Texture2D SCORE_YELLOW;
    public static Texture2D SCORE_BROWN;
    public static Texture2D SCORE_WHITE;

    public static Texture2D TEXT_COMBO_NICE;
    public static Texture2D TEXT_COMBO_GREAT;
    public static Texture2D TEXT_COMBO_WONDERFUL;
    public static Texture2D TEXT_SHUFFLE;
    public static Texture2D TEXT_BONUS;

    public static Texture2D TUTORIAL_FINGER;

    public static Texture2D HAMMER;
    public static Texture2D BOMB;
    public static Texture2D GLOVE;

    public static Texture2D CONFETTI_BLUE;
    public static Texture2D CONFETTI_GREEN;
    public static Texture2D CONFETTI_PINK;
    public static Texture2D CONFETTI_YELLOW;

    public static Texture2D LIGHT_BG;
    public static Texture2D LIGHT_BG_BLUE;
    public static Texture2D LIGHT_RING;
    public static Texture2D LIGHT_CIRCLE;
    public static Texture2D GLITTER;
    public static Texture2D GLITTER_BLUE;
    public static Texture2D FLASH_BEAM;
    public static Texture2D FLASH_RING;
    public static Texture2D FLASH_RING_BLUE;

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
        Bitmap grid = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_grid);
        GRID_CENTER = textureManager.loadTexture(BitmapUtils.createBitmapRegion(grid, 0, 0, 300, 300));
        GRID_SIDE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(grid, 300, 0, 300, 300));
        GRID_CORNER = textureManager.loadTexture(BitmapUtils.createBitmapRegion(grid, 600, 0, 300, 300));
        GRID_MARGIN = textureManager.loadTexture(BitmapUtils.createBitmapRegion(grid, 900, 0, 300, 300));
        GRID_PIPE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(grid, 1200, 0, 300, 300));
        GRID_SOLE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(grid, 1500, 0, 300, 300));

        Bitmap cherry = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_cherry);
        CHERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 0, 0, 300, 300));
        CHERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 300, 0, 300, 300));
        CHERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 600, 0, 300, 300));
        ROW_STRIPED_CHERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 900, 0, 300, 300));
        ROW_STRIPED_CHERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 1200, 0, 300, 300));
        ROW_STRIPED_CHERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 1500, 0, 300, 300));
        COLUMN_STRIPED_CHERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 1800, 0, 300, 300));
        COLUMN_STRIPED_CHERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 2100, 0, 300, 300));
        COLUMN_STRIPED_CHERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 2400, 0, 300, 300));
        EXPLOSIVE_CHERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 2700, 0, 300, 300));
        EXPLOSIVE_CHERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 3000, 0, 300, 300));
        EXPLOSIVE_CHERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cherry, 3300, 0, 300, 300));

        Bitmap strawberry = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_strawberry);
        STRAWBERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 0, 0, 300, 300));
        STRAWBERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 300, 0, 300, 300));
        STRAWBERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 600, 0, 300, 300));
        ROW_STRIPED_STRAWBERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 900, 0, 300, 300));
        ROW_STRIPED_STRAWBERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 1200, 0, 300, 300));
        ROW_STRIPED_STRAWBERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 1500, 0, 300, 300));
        COLUMN_STRIPED_STRAWBERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 1800, 0, 300, 300));
        COLUMN_STRIPED_STRAWBERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 2100, 0, 300, 300));
        COLUMN_STRIPED_STRAWBERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 2400, 0, 300, 300));
        EXPLOSIVE_STRAWBERRY = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 2700, 0, 300, 300));
        EXPLOSIVE_STRAWBERRY_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 3000, 0, 300, 300));
        EXPLOSIVE_STRAWBERRY_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(strawberry, 3300, 0, 300, 300));

        Bitmap lemon = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_lemon);
        LEMON = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 0, 0, 300, 300));
        LEMON_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 300, 0, 300, 300));
        LEMON_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 600, 0, 300, 300));
        ROW_STRIPED_LEMON = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 900, 0, 300, 300));
        ROW_STRIPED_LEMON_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 1200, 0, 300, 300));
        ROW_STRIPED_LEMON_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 1500, 0, 300, 300));
        COLUMN_STRIPED_LEMON = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 1800, 0, 300, 300));
        COLUMN_STRIPED_LEMON_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 2100, 0, 300, 300));
        COLUMN_STRIPED_LEMON_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 2400, 0, 300, 300));
        EXPLOSIVE_LEMON = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 2700, 0, 300, 300));
        EXPLOSIVE_LEMON_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 3000, 0, 300, 300));
        EXPLOSIVE_LEMON_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lemon, 3300, 0, 300, 300));

        Bitmap coconut = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_coconut);
        COCONUT = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 0, 0, 300, 300));
        COCONUT_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 300, 0, 300, 300));
        COCONUT_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 600, 0, 300, 300));
        ROW_STRIPED_COCONUT = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 900, 0, 300, 300));
        ROW_STRIPED_COCONUT_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 1200, 0, 300, 300));
        ROW_STRIPED_COCONUT_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 1500, 0, 300, 300));
        COLUMN_STRIPED_COCONUT = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 1800, 0, 300, 300));
        COLUMN_STRIPED_COCONUT_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 2100, 0, 300, 300));
        COLUMN_STRIPED_COCONUT_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 2400, 0, 300, 300));
        EXPLOSIVE_COCONUT = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 2700, 0, 300, 300));
        EXPLOSIVE_COCONUT_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 3000, 0, 300, 300));
        EXPLOSIVE_COCONUT_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(coconut, 3300, 0, 300, 300));

        Bitmap banana = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_banana);
        BANANA = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 0, 0, 300, 300));
        BANANA_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 300, 0, 300, 300));
        BANANA_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 600, 0, 300, 300));
        ROW_STRIPED_BANANA = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 900, 0, 300, 300));
        ROW_STRIPED_BANANA_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 1200, 0, 300, 300));
        ROW_STRIPED_BANANA_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 1500, 0, 300, 300));
        COLUMN_STRIPED_BANANA = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 1800, 0, 300, 300));
        COLUMN_STRIPED_BANANA_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 2100, 0, 300, 300));
        COLUMN_STRIPED_BANANA_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 2400, 0, 300, 300));
        EXPLOSIVE_BANANA = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 2700, 0, 300, 300));
        EXPLOSIVE_BANANA_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 3000, 0, 300, 300));
        EXPLOSIVE_BANANA_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(banana, 3300, 0, 300, 300));

        EMPTY = textureManager.loadTexture(BitmapUtils.createBitmap(300, 300));
        ICE_CREAM = textureManager.loadTexture(R.drawable.sprite_ice_cream);

        Bitmap cookie = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_cookie);
        COOKIE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cookie, 0, 0, 300, 300));
        COOKIE_PIECE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cookie, 300, 0, 300, 300));
        COOKIE_PIECE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cookie, 600, 0, 300, 300));
        COOKIE_PIECE_03 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cookie, 900, 0, 300, 300));
        COOKIE_PIECE_04 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cookie, 1200, 0, 300, 300));
        COOKIE_PIECE_05 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cookie, 1500, 0, 300, 300));
        COOKIE_PIECE_06 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cookie, 1800, 0, 300, 300));

        Bitmap cake = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_cake);
        CAKE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cake, 0, 0, 300, 300));
        CAKE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cake, 300, 0, 300, 300));
        CAKE_03 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cake, 600, 0, 300, 300));
        CAKE_PIECE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(cake, 900, 0, 300, 300));

        Bitmap pie = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_pie);
        PIE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(pie, 0, 0, 600, 600));
        PIE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(pie, 600, 0, 600, 600));
        PIE_03 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(pie, 1200, 0, 600, 600));
        PIE_04 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(pie, 1800, 0, 600, 600));
        PIE_PAN_PIECE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(pie, 2400, 0, 400, 80));

        Paint yellow = new Paint();
        yellow.setColor(Color.parseColor("#FFCC80"));
        YELLOW_PIE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, yellow));
        Paint white = new Paint();
        white.setColor(Color.parseColor("#FFFFFFFF"));
        WHITE_PIE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, white));

        Bitmap candy = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_candy);
        CANDY_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(candy, 0, 0, 300, 300));
        CANDY_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(candy, 300, 0, 300, 300));
        CANDY_PIECE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(candy, 600, 0, 100, 100));
        CANDY_WRAPPER_PIECE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(candy, 700, 0, 200, 120));

        Bitmap lock = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_lock);
        LOCK = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lock, 0, 0, 300, 300));
        LOCK_PIECE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(lock, 300, 0, 300, 300));

        Bitmap ice01 = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_ice_01);
        ICE_CENTER_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice01, 0, 0, 300, 300));
        ICE_SIDE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice01, 300, 0, 300, 300));
        ICE_CORNER_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice01, 600, 0, 300, 300));
        ICE_MARGIN_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice01, 900, 0, 300, 300));
        ICE_PIPE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice01, 1200, 0, 300, 300));
        ICE_SOLE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice01, 1500, 0, 300, 300));

        Bitmap ice02 = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_ice_02);
        ICE_CENTER_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice02, 0, 0, 300, 300));
        ICE_SIDE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice02, 300, 0, 300, 300));
        ICE_CORNER_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice02, 600, 0, 300, 300));
        ICE_MARGIN_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice02, 900, 0, 300, 300));
        ICE_PIPE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice02, 1200, 0, 300, 300));
        ICE_SOLE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(ice02, 1500, 0, 300, 300));

        Paint blue = new Paint();
        blue.setColor(Color.parseColor("#FFBBDEFB"));
        BLUE_ICE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, blue));
        WHITE_ICE_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(20, white));

        Bitmap sand01 = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_sand_01);
        SAND_CENTER_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand01, 0, 0, 300, 300));
        SAND_SIDE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand01, 300, 0, 300, 300));
        SAND_CORNER_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand01, 600, 0, 300, 300));
        SAND_MARGIN_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand01, 900, 0, 300, 300));
        SAND_PIPE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand01, 1200, 0, 300, 300));
        SAND_SOLE_01 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand01, 1500, 0, 300, 300));

        Bitmap sand02 = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_sand_02);
        SAND_CENTER_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand02, 0, 0, 300, 300));
        SAND_SIDE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand02, 300, 0, 300, 300));
        SAND_CORNER_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand02, 600, 0, 300, 300));
        SAND_MARGIN_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand02, 900, 0, 300, 300));
        SAND_PIPE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand02, 1200, 0, 300, 300));
        SAND_SOLE_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(sand02, 1500, 0, 300, 300));

        Paint brown = new Paint();
        brown.setColor(Color.parseColor("#AA6C39"));
        BROWN_SAND_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(10, brown));
        YELLOW_SAND_PIECE = textureManager.loadTexture(BitmapUtils.createCircleBitmap(10, yellow));

        Bitmap honey = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_honey);
        HONEY_CENTER = textureManager.loadTexture(BitmapUtils.createBitmapRegion(honey, 0, 0, 300, 300));
        HONEY_SIDE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(honey, 300, 0, 300, 300));
        HONEY_CORNER = textureManager.loadTexture(BitmapUtils.createBitmapRegion(honey, 600, 0, 300, 300));
        HONEY_MARGIN = textureManager.loadTexture(BitmapUtils.createBitmapRegion(honey, 900, 0, 300, 300));
        HONEY_PIPE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(honey, 1200, 0, 300, 300));
        HONEY_SOLE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(honey, 1500, 0, 300, 300));
        HONEY_PIECE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(honey, 1800, 0, 120, 160));

        Bitmap generator = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_generator);
        GENERATOR_ROW_STRIPED = textureManager.loadTexture(BitmapUtils.createBitmapRegion(generator, 0, 0, 300, 300));
        GENERATOR_COLUMN_STRIPED = textureManager.loadTexture(BitmapUtils.createBitmapRegion(generator, 300, 0, 300, 300));
        GENERATOR_ROW_COLUMN_STRIPED = textureManager.loadTexture(BitmapUtils.createBitmapRegion(generator, 600, 0, 300, 300));
        GENERATOR_EXPLOSIVE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(generator, 900, 0, 300, 300));
        GENERATOR_PIVOT = textureManager.loadTexture(BitmapUtils.createBitmapRegion(generator, 1200, 0, 300, 90));

        Bitmap shell = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_shell);
        SHELL_01_VERTICAL = textureManager.loadTexture(BitmapUtils.createBitmapRegion(shell, 0, 0, 300, 900));
        SHELL_01_HORIZONTAL = textureManager.loadTexture(BitmapUtils.createBitmapRegion(shell, 300, 0, 900, 300));
        SHELL_02 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(shell, 1200, 0, 600, 600));
        SHELL_03 = textureManager.loadTexture(BitmapUtils.createBitmapRegion(shell, 1800, 0, 900, 900));

        STARFISH = textureManager.loadTexture(R.drawable.sprite_starfish);
        ARROW = textureManager.loadTexture(R.drawable.sprite_arrow);
        PIPE = textureManager.loadTexture(R.drawable.sprite_pipe);

        Bitmap score = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_score);
        SCORE_PINK = textureManager.loadTexture(BitmapUtils.createBitmapRegion(score, 0, 0, 300, 300));
        SCORE_RED = textureManager.loadTexture(BitmapUtils.createBitmapRegion(score, 300, 0, 300, 300));
        SCORE_YELLOW = textureManager.loadTexture(BitmapUtils.createBitmapRegion(score, 600, 0, 300, 300));
        SCORE_BROWN = textureManager.loadTexture(BitmapUtils.createBitmapRegion(score, 900, 0, 300, 300));
        SCORE_WHITE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(score, 1200, 0, 300, 300));

        TEXT_COMBO_NICE = textureManager.loadTexture(R.drawable.text_combo_nice);
        TEXT_COMBO_GREAT = textureManager.loadTexture(R.drawable.text_combo_great);
        TEXT_COMBO_WONDERFUL = textureManager.loadTexture(R.drawable.text_combo_wonderful);
        TEXT_SHUFFLE = textureManager.loadTexture(R.drawable.text_shuffle);
        TEXT_BONUS = textureManager.loadTexture(R.drawable.text_bonus);

        TUTORIAL_FINGER = textureManager.loadTexture(R.drawable.sprite_tutorial_finger);

        Bitmap booster = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_booster);
        HAMMER = textureManager.loadTexture(BitmapUtils.createBitmapRegion(booster, 0, 0, 900, 900));
        BOMB = textureManager.loadTexture(BitmapUtils.createBitmapRegion(booster, 900, 0, 900, 900));
        GLOVE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(booster, 1800, 0, 900, 900));

        Bitmap confetti = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_confetti);
        CONFETTI_BLUE = textureManager.loadTexture(BitmapUtils.createBitmapRegion(confetti, 0, 0, 200, 200));
        CONFETTI_GREEN = textureManager.loadTexture(BitmapUtils.createBitmapRegion(confetti, 200, 0, 200, 200));
        CONFETTI_PINK = textureManager.loadTexture(BitmapUtils.createBitmapRegion(confetti, 400, 0, 200, 200));
        CONFETTI_YELLOW = textureManager.loadTexture(BitmapUtils.createBitmapRegion(confetti, 600, 0, 200, 200));

        LIGHT_BG = textureManager.loadTexture(R.drawable.sprite_light_bg);
        LIGHT_BG_BLUE = textureManager.loadTexture(R.drawable.sprite_light_bg_blue);
        LIGHT_RING = textureManager.loadTexture(R.drawable.sprite_light_ring);
        LIGHT_CIRCLE = textureManager.loadTexture(R.drawable.sprite_light_circle);
        GLITTER = textureManager.loadTexture(R.drawable.sprite_glitter);
        GLITTER_BLUE = textureManager.loadTexture(R.drawable.sprite_glitter_blue);
        FLASH_RING = textureManager.loadTexture(R.drawable.sprite_flash_ring);
        FLASH_RING_BLUE = textureManager.loadTexture(R.drawable.sprite_flash_ring_blue);
        FLASH_BEAM = textureManager.loadTexture(R.drawable.sprite_flash_beam);

        FLASH_ROW_ANIMATION = textureManager.loadTextureGroup(BitmapUtils.createRadiateBitmapRegions(
                ResourceUtils.getBitmap(context, R.drawable.sprite_flash_row), 10, BitmapUtils.BitmapRadiate.LEFT_TO_RIGHT));
        FLASH_COLUMN_ANIMATION = textureManager.loadTextureGroup(BitmapUtils.createRadiateBitmapRegions(
                ResourceUtils.getBitmap(context, R.drawable.sprite_flash_column), 10, BitmapUtils.BitmapRadiate.TOP_TO_DOWN));
        FLASH_EXPLOSION_ANIMATION = textureManager.loadTextureGroup(BitmapUtils.createRadiateBitmapRegions(
                ResourceUtils.getBitmap(context, R.drawable.sprite_flash_explosion), 10, BitmapUtils.BitmapRadiate.IN_TO_OUT));
        FLASH_TRANSFORM_ANIMATION = textureManager.loadTextureGroup(BitmapUtils.createRadiateBitmapRegions(
                ResourceUtils.getBitmap(context, R.drawable.sprite_flash_transform), 10, BitmapUtils.BitmapRadiate.IN_TO_OUT));

        Bitmap lightning = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_lightning);
        LIGHTNING_ANIMATION = textureManager.loadTextureGroup(BitmapUtils.createRadiateBitmapRegions(
                BitmapUtils.createBitmapRegion(lightning, 0, 0, 300, 1200), 5, BitmapUtils.BitmapRadiate.TOP_TO_DOWN));
        LIGHTNING_ANIMATION.addTextures(textureManager.loadTextureGroup(new Bitmap[]{
                BitmapUtils.createBitmapRegion(lightning, 0, 0, 300, 1200),
                BitmapUtils.createBitmapRegion(lightning, 300, 0, 300, 1200),
                BitmapUtils.createBitmapRegion(lightning, 600, 0, 300, 1200),
                BitmapUtils.createBitmapRegion(lightning, 900, 0, 300, 1200),
                BitmapUtils.createBitmapRegion(lightning, 1200, 0, 300, 1200)}).getTextures());

        Bitmap smoke = ResourceUtils.getBitmap(context, R.drawable.sprite_sheet_smoke);
        SMOKE_ANIMATION = textureManager.loadTextureGroup(new Bitmap[]{
                BitmapUtils.createBitmapRegion(smoke, 0, 0, 600, 600),
                BitmapUtils.createBitmapRegion(smoke, 600, 0, 600, 600),
                BitmapUtils.createBitmapRegion(smoke, 1200, 0, 600, 600),
                BitmapUtils.createBitmapRegion(smoke, 1800, 0, 600, 600),
                BitmapUtils.createBitmapRegion(smoke, 2400, 0, 600, 600),
                BitmapUtils.createBitmapRegion(smoke, 3000, 0, 600, 600),
                BitmapUtils.createBitmapRegion(smoke, 3600, 0, 600, 600),
                BitmapUtils.createBitmapRegion(smoke, 4200, 0, 600, 600)});
    }
    //========================================================

}
