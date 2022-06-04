package com.nativegame.match3game.game.booster;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.effect.sound.SoundEvent;
import com.nativegame.match3game.effect.sound.SoundManager;
import com.nativegame.match3game.engine.GameEngine;
import com.nativegame.match3game.engine.GameEvent;
import com.nativegame.match3game.game.tile.Tile;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * A helper class to handler booster player used.
 * We first add the booster into the game, play
 * booster animation, and finally update tile
 */

public class BoosterManager {
    private final GameEngine mGameEngine;
    private final SoundManager mSoundManager;
    private final BoosterAnimation mBoosterAnimation;

    private final ImageButton mBtnHammer, mBtnGloves, mBtnBomb, mBtnPause;
    private final TextView mTxtHammer, mTxtGloves, mTxtBomb;
    private final ImageView mHighLight;

    private BOOSTER mBooster;         // The current item player use
    private int mHammerNum = 5;
    private int mGlovesNum = 5;
    private int mBombNum = 5;

    private enum BOOSTER {
        HAMMER,
        GLOVES,
        BOMB
    }

    public BoosterManager(GameEngine gameEngine) {
        mGameEngine = gameEngine;
        mSoundManager = ((MainActivity) mGameEngine.mActivity).getSoundManager();
        mBooster = null;

        // Init button
        mBtnHammer = (ImageButton) gameEngine.mActivity.findViewById(R.id.btn_hammer);
        mBtnGloves = (ImageButton) gameEngine.mActivity.findViewById(R.id.btn_gloves);
        mBtnBomb = (ImageButton) gameEngine.mActivity.findViewById(R.id.btn_bomb);
        mBtnPause = (ImageButton) gameEngine.mActivity.findViewById(R.id.btn_pause);
        // Init text
        mTxtHammer = (TextView) gameEngine.mActivity.findViewById(R.id.txt_hammer);
        mTxtGloves = (TextView) gameEngine.mActivity.findViewById(R.id.txt_gloves);
        mTxtBomb = (TextView) gameEngine.mActivity.findViewById(R.id.txt_bomb);

        //Init highlight
        mHighLight = (ImageView) gameEngine.mActivity.findViewById(R.id.highlight);

        // Init animation
        mBoosterAnimation = new BoosterAnimation(gameEngine);

        init();
    }

    private void init() {
        // Init text
        mTxtHammer.setText(String.valueOf(mHammerNum));
        mTxtGloves.setText(String.valueOf(mGlovesNum));
        mTxtBomb.setText(String.valueOf(mBombNum));

        // Init booster button listener
        Utils.createButtonEffect(mBtnHammer);
        mBtnHammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHammerNum == 0)
                    return;

                if (mBooster == null || mBooster == BOOSTER.HAMMER) {
                    mBooster = BOOSTER.HAMMER;
                    mGameEngine.onGameEvent(GameEvent.PLAYER_PRESS_HAMMER);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                }
            }
        });

        Utils.createButtonEffect(mBtnGloves);
        mBtnGloves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGlovesNum == 0)
                    return;

                if (mBooster == null || mBooster == BOOSTER.GLOVES) {
                    mBooster = BOOSTER.GLOVES;
                    mGameEngine.onGameEvent(GameEvent.PLAYER_PRESS_GLOVES);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                }
            }
        });

        Utils.createButtonEffect(mBtnBomb);
        mBtnBomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBombNum == 0)
                    return;

                if (mBooster == null || mBooster == BOOSTER.BOMB) {
                    mBooster = BOOSTER.BOMB;
                    mGameEngine.onGameEvent(GameEvent.PLAYER_PRESS_BOMB);
                    mSoundManager.playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                }
            }
        });
    }

    public void showHighlight() {
        // Show shadow background
        mHighLight.setVisibility(View.VISIBLE);

        // Disable pause button
        Utils.createColorFilter(mBtnPause);
        mBtnPause.setEnabled(false);

        // Disable other item button
        switch (mBooster) {
            case HAMMER:
                // If current item is hammer, we disable hand and bomb
                Utils.createColorFilter(mBtnGloves);
                Utils.createColorFilter(mBtnBomb);
                mBtnGloves.setEnabled(false);
                mBtnBomb.setEnabled(false);
                // Set text
                Utils.createColorFilter(mTxtGloves);
                Utils.createColorFilter(mTxtBomb);
                break;
            case GLOVES:
                // If current item is hand, we disable hammer and bomb
                Utils.createColorFilter(mBtnHammer);
                Utils.createColorFilter(mBtnBomb);
                mBtnHammer.setEnabled(false);
                mBtnBomb.setEnabled(false);
                // Set text
                Utils.createColorFilter(mTxtHammer);
                Utils.createColorFilter(mTxtBomb);
                break;
            case BOMB:
                // If current item is bomb, we disable hand and hammer
                Utils.createColorFilter(mBtnGloves);
                Utils.createColorFilter(mBtnHammer);
                mBtnGloves.setEnabled(false);
                mBtnHammer.setEnabled(false);
                // Set text
                Utils.createColorFilter(mTxtGloves);
                Utils.createColorFilter(mTxtHammer);
                break;
        }
    }

    public void clearHighlight() {
        // Resume item to null
        mBooster = null;

        // Clear shadow background
        mHighLight.setVisibility(View.GONE);

        // Resume pause button
        Utils.clearColorFilter(mBtnPause);
        mBtnPause.setEnabled(true);

        // Enable all button
        Utils.clearColorFilter(mBtnHammer);
        Utils.clearColorFilter(mBtnGloves);
        Utils.clearColorFilter(mBtnBomb);
        mBtnHammer.setEnabled(true);
        mBtnGloves.setEnabled(true);
        mBtnBomb.setEnabled(true);

        // Resume text
        Utils.clearColorFilter(mTxtHammer);
        Utils.clearColorFilter(mTxtGloves);
        Utils.clearColorFilter(mTxtBomb);
    }

    public void useHammer(Tile tile) {
        // Reduce num
        mHammerNum--;
        mTxtHammer.setText(String.valueOf(mHammerNum));

        // Clear highlight and play animation
        clearHighlight();
        mBoosterAnimation.playHammerAnimation(tile.x, tile.y);

        // We explode 1 tile
        tile.match++;
    }

    public void useGloves(Tile tileStart, Tile tileEnd) {
        // Reduce num
        mGlovesNum--;
        mTxtGloves.setText(String.valueOf(mGlovesNum));

        // Clear highlight and play animation
        clearHighlight();
        mBoosterAnimation.playGlovesAnimation(tileStart.x, tileStart.y, tileEnd.x, tileEnd.y);

        // We swap tile in Game controller, since we need Algorithm's method
    }

    public void useBomb(Tile tile, Tile[][] tileArray) {
        // Reduce num
        mBombNum--;
        mTxtBomb.setText(String.valueOf(mBombNum));

        // Clear highlight and play animation
        clearHighlight();
        mBoosterAnimation.playBombAnimation(tile.x, tile.y);

        // We explode 9 tiles
        for (int i = tile.row - 1; i <= tile.row + 1; i++) {
            for (int j = tile.col - 1; j <= tile.col + 1; j++) {

                if (i < 0 || i >= mGameEngine.mLevel.mRow || j < 0 || j >= mGameEngine.mLevel.mColumn)
                    continue;

                //Check is empty fruit
                if (!tileArray[i][j].empty) {
                    // Add match
                    tileArray[i][j].match++;
                }
            }
        }
    }

}
