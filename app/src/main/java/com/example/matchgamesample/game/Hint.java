package com.example.matchgamesample.game;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.engine.GameEvent;
import com.example.matchgamesample.engine.GameObject;

/* Hint class check possible match on board
 * if founded, start hint animation
 * otherwise, refresh
 */
public class Hint extends GameObject {
    private final GameEngine mGameEngine;
    private final int mColumn, mRow;
    private Tile[][] tileArray;
    private final Animation animation;
    private final ArrayList<ImageView> hintArray = new ArrayList<>();

    private boolean mShowHint;
    private int mDelayTime;
    private static final int DELAY_TIME = 5000;

    public Hint(GameEngine gameEngine, Tile[][] tileArray) {
        mGameEngine = gameEngine;
        mColumn = gameEngine.mLevel.mColumn;
        mRow = gameEngine.mLevel.mRow;
        this.tileArray = tileArray;
        animation = AnimationUtils.loadAnimation(gameEngine.mActivity, R.anim.hint_animation);
        mShowHint = false;
    }

    private void startHint() {
        // Start hint animation
        int size = hintArray.size();
        for (int i = 0; i < size; i++) {
            hintArray.get(i).startAnimation(animation);
        }
    }

    private void stopHint() {
        hintArray.clear();
        animation.cancel();
        animation.reset();
    }

    private boolean checkPossibleMatch() {
        /* Hint priority
         * 1. Special combine
         * 2. match in 5
         * 3. match in 4 T L
         * 4. match in 3
         * 5. ice cream
         */

        //Check special combine
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 1; j++) {

                // Find two special fruit in row and column
                if (!tileArray[i][j].invalid && !tileArray[i][j + 1].invalid
                        && tileArray[i][j].special && tileArray[i][j + 1].special) {

                    hintArray.add(tileArray[i][j].mImage);
                    hintArray.add(tileArray[i][j + 1].mImage);
                    return true;
                }
            }
        }

        for (int j = 0; j < mColumn; j++) {
            for (int i = 0; i < mRow - 1; i++) {

                // Find two special fruit in row and column
                if (!tileArray[i][j].invalid && !tileArray[i + 1][j].invalid
                        && tileArray[i][j].special && tileArray[i + 1][j].special) {

                    //Check honey
                    hintArray.add(tileArray[i][j].mImage);
                    hintArray.add(tileArray[i + 1][j].mImage);
                    return true;
                }
            }
        }

        /*
        //Check match 5 in mColumn
        for (int i = 1; i <= mRow - 4; i++) {
            for (int j = 1; j <= mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {

                    if (tileArray[i][j].kind == tileArray[i + 1][j].kind
                            && tileArray[i][j].kind == tileArray[i + 3][j].kind
                            && tileArray[i][j].kind == tileArray[i + 4][j].kind) {

                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i + 2][j - 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 2][j - 1].invalid && !tileArray[i + 2][j].invalid && tileArray[i + 2][j].kind != 0
                                    && (!tileArray[i + 2][j - 1].honey || !tileArray[i + 2][j].honey)) {
                                // O
                                // O
                                //O
                                // O
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j - 2]);
                                hintArray.add(fruitsArray[i + 2][j - 1]);
                                hintArray.add(fruitsArray[i + 3][j - 1]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 2][j + 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 2][j + 1].invalid && !tileArray[i + 2][j].invalid && tileArray[i + 2][j].kind != 0
                                    && (!tileArray[i + 2][j + 1].honey || !tileArray[i + 2][j].honey)) {
                                //O
                                //O
                                // O
                                //O
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j]);
                                hintArray.add(fruitsArray[i + 2][j - 1]);
                                hintArray.add(fruitsArray[i + 3][j - 1]);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 5 in mRow
        for (int i = 1; i <= mRow; i++) {
            for (int j = 1; j <= mColumn - 4; j++) {
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {

                    if (tileArray[i][j].kind == tileArray[i][j + 1].kind
                            && tileArray[i][j].kind == tileArray[i][j + 3].kind
                            && tileArray[i][j].kind == tileArray[i][j + 4].kind) {

                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i - 1][j + 2].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j + 2].invalid && !tileArray[i][j + 2].invalid && tileArray[i][j + 2].kind != 0
                                    && (!tileArray[i - 1][j + 2].honey || !tileArray[i][j + 2].honey)) {
                                //  O
                                //OO OO
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i - 2][j + 1]);
                                hintArray.add(fruitsArray[i - 1][j + 2]);
                                hintArray.add(fruitsArray[i - 1][j + 3]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 1][j + 2].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j + 2].invalid && !tileArray[i][j + 2].invalid && tileArray[i][j + 2].kind != 0
                                    && (!tileArray[i + 1][j + 2].honey || !tileArray[i][j + 2].honey)) {
                                //OO OO
                                //  O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i][j + 1]);
                                hintArray.add(fruitsArray[i - 1][j + 2]);
                                hintArray.add(fruitsArray[i - 1][j + 3]);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 4 T L in mColumn
        for (int i = 1; i <= mRow - 1; i++) {
            for (int j = 1; j <= mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 mRow
                    if (tileArray[i][j].kind == tileArray[i + 1][j].kind) {

                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i + 2][j - 1].kind
                                && tileArray[i][j].kind == tileArray[i + 3][j].kind) {
                            //Check is swappable
                            if (!tileArray[i + 2][j - 1].invalid && !tileArray[i + 2][j].invalid && tileArray[i + 2][j].kind != 0
                                    && (!tileArray[i + 2][j - 1].honey || !tileArray[i + 2][j].honey)) {
                                // O
                                // O
                                //O
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j - 2]);
                                hintArray.add(fruitsArray[i + 2][j - 1]);
                                if (tileArray[i][j].kind == tileArray[i + 2][j - 2].kind) {
                                    //  O
                                    //  O
                                    //OO
                                    //  O
                                    hintArray.add(fruitsArray[i + 1][j - 3]);
                                }
                                if (tileArray[i][j].kind == tileArray[i + 2][j + 1].kind) {
                                    // O
                                    // O
                                    //O O
                                    // O
                                    hintArray.add(fruitsArray[i + 1][j]);
                                    if (tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
                                        // O
                                        // O
                                        //O OO
                                        // O
                                        hintArray.add(fruitsArray[i + 1][j + 1]);
                                    }
                                }
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 2][j + 1].kind
                                && tileArray[i][j].kind == tileArray[i + 3][j].kind) {
                            //Check is swappable
                            if (!tileArray[i + 2][j + 1].invalid && !tileArray[i + 2][j].invalid && tileArray[i + 2][j].kind != 0
                                    && (!tileArray[i + 2][j + 1].honey || !tileArray[i + 2][j].honey)) {
                                //O
                                //O
                                // O
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j]);
                                hintArray.add(fruitsArray[i + 2][j - 1]);
                                if (tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
                                    //O
                                    //O
                                    // OO
                                    //O
                                    hintArray.add(fruitsArray[i + 1][j + 1]);
                                }
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i - 1][j - 1].kind
                                && tileArray[i][j].kind == tileArray[i - 2][j].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j - 1].invalid && !tileArray[i - 1][j].invalid && tileArray[i - 1][j].kind != 0
                                    && (!tileArray[i - 1][j - 1].honey || !tileArray[i - 1][j].honey)) {
                                // O
                                //O
                                // O
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i - 2][j - 2]);
                                hintArray.add(fruitsArray[i - 3][j - 1]);
                                if (tileArray[i][j].kind == tileArray[i - 1][j - 2].kind) {
                                    //  O
                                    //OO
                                    //  O
                                    //  O
                                    hintArray.add(fruitsArray[i - 2][j - 3]);
                                }
                                if (tileArray[i][j].kind == tileArray[i - 1][j + 1].kind) {
                                    // O
                                    //O O
                                    // O
                                    // O
                                    hintArray.add(fruitsArray[i - 2][j]);
                                    if (tileArray[i][j].kind == tileArray[i - 1][j + 2].kind) {
                                        // O
                                        //O OO
                                        // O
                                        // O
                                        hintArray.add(fruitsArray[i - 2][j + 1]);
                                    }
                                }
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i - 1][j + 1].kind
                                && tileArray[i][j].kind == tileArray[i - 2][j].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j + 1].invalid && !tileArray[i - 1][j].invalid && tileArray[i - 1][j].kind != 0
                                    && (!tileArray[i - 1][j + 1].honey || !tileArray[i - 1][j].honey)) {
                                //O
                                // O
                                //O
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i - 2][j]);
                                hintArray.add(fruitsArray[i - 3][j - 1]);
                                if (tileArray[i][j].kind == tileArray[i - 1][j + 2].kind) {
                                    //O
                                    // OO
                                    //O
                                    //O
                                    hintArray.add(fruitsArray[i - 2][j + 1]);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 4 T L in mRow
        for (int i = 1; i <= mRow; i++) {
            for (int j = 1; j <= mColumn - 1; j++) {
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 mColumn
                    if (tileArray[i][j].kind == tileArray[i][j + 1].kind) {

                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i - 1][j + 2].kind
                                && tileArray[i][j].kind == tileArray[i][j + 3].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j + 2].invalid && !tileArray[i][j + 2].invalid && tileArray[i][j + 2].kind != 0
                                    && (!tileArray[i - 1][j + 2].honey || !tileArray[i][j + 2].honey)) {
                                //  O
                                //OO O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i - 2][j + 1]);
                                hintArray.add(fruitsArray[i - 1][j + 2]);
                                if (tileArray[i][j].kind == tileArray[i - 2][j + 2].kind) {
                                    //  O
                                    //  O
                                    //OO O
                                    hintArray.add(fruitsArray[i - 3][j + 1]);
                                }
                                if (tileArray[i][j].kind == tileArray[i + 1][j + 2].kind) {
                                    //  O
                                    //OO O
                                    //  O
                                    hintArray.add(fruitsArray[i][j + 1]);
                                    if (tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
                                        //  O
                                        //OO O
                                        //  O
                                        //  O
                                        hintArray.add(fruitsArray[i + 1][j + 1]);
                                    }
                                }
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 1][j + 2].kind
                                && tileArray[i][j].kind == tileArray[i][j + 3].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j + 2].invalid && !tileArray[i][j + 2].invalid && tileArray[i][j + 2].kind != 0
                                    && (!tileArray[i + 1][j + 2].honey || !tileArray[i][j + 2].honey)) {
                                //OO O
                                //  O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i][j + 1]);
                                hintArray.add(fruitsArray[i - 1][j + 2]);
                                if (tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
                                    //OO O
                                    //  O
                                    //  O
                                    hintArray.add(fruitsArray[i + 1][j + 1]);
                                }
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i - 1][j - 1].kind
                                && tileArray[i][j].kind == tileArray[i][j - 2].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j - 1].invalid && !tileArray[i][j - 1].invalid && tileArray[i][j - 1].kind != 0
                                    && (!tileArray[i - 1][j - 1].honey || !tileArray[i][j - 1].honey)) {
                                // O
                                //O OO
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i - 2][j - 2]);
                                hintArray.add(fruitsArray[i - 1][j - 3]);
                                if (tileArray[i][j].kind == tileArray[i - 2][j - 1].kind) {
                                    // O
                                    // O
                                    //O OO
                                    hintArray.add(fruitsArray[i - 3][j - 2]);
                                }
                                if (tileArray[i][j].kind == tileArray[i + 1][j - 1].kind) {
                                    // O
                                    //O OO
                                    // O
                                    hintArray.add(fruitsArray[i][j - 2]);
                                    if (tileArray[i][j].kind == tileArray[i + 2][j - 1].kind) {
                                        // O
                                        //O OO
                                        // O
                                        // O
                                        hintArray.add(fruitsArray[i + 1][j - 2]);
                                    }
                                }
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 1][j - 1].kind
                                && tileArray[i][j].kind == tileArray[i][j - 2].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j - 1].invalid && !tileArray[i][j - 1].invalid && tileArray[i][j - 1].kind != 0
                                    && (!tileArray[i + 1][j - 1].honey || !tileArray[i][j - 1].honey)) {
                                //O OO
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i][j - 2]);
                                hintArray.add(fruitsArray[i - 1][j - 3]);
                                if (tileArray[i][j].kind == tileArray[i + 2][j - 1].kind) {
                                    //O OO
                                    // O
                                    // O
                                    hintArray.add(fruitsArray[i + 1][j - 2]);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in mColumn 1
        for (int i = 1; i <= mRow - 1; i++) {
            for (int j = 1; j <= mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 mRow
                    if (tileArray[i][j].kind == tileArray[i + 1][j].kind) {
                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i + 2][j - 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 2][j - 1].invalid && !tileArray[i + 2][j].invalid && tileArray[i + 2][j].kind != 0
                                    && (!tileArray[i + 2][j - 1].honey || !tileArray[i + 2][j].honey)) {
                                // O
                                // O
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j - 2]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 2][j + 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 2][j + 1].invalid && !tileArray[i + 2][j].invalid && tileArray[i + 2][j].kind != 0
                                    && (!tileArray[i + 2][j + 1].honey || !tileArray[i + 2][j].honey)) {
                                //O
                                //O
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i - 1][j - 1].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j - 1].invalid && !tileArray[i - 1][j].invalid && tileArray[i - 1][j].kind != 0
                                    && (!tileArray[i - 1][j - 1].honey || !tileArray[i - 1][j].honey)) {
                                //O
                                // O
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i - 2][j - 2]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i - 1][j + 1].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j + 1].invalid && !tileArray[i - 1][j].invalid && tileArray[i - 1][j].kind != 0
                                    && (!tileArray[i - 1][j + 1].honey || !tileArray[i - 1][j].honey)) {
                                // O
                                //O
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 1]);
                                hintArray.add(fruitsArray[i - 2][j]);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in mColumn 2
        for (int i = 1; i <= mRow - 2; i++) {
            for (int j = 1; j <= mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 2 mRow
                    if (tileArray[i][j].kind == tileArray[i + 2][j].kind) {
                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i + 1][j - 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j - 1].invalid && !tileArray[i + 1][j].invalid && tileArray[i + 1][j].kind != 0
                                    && (!tileArray[i + 1][j - 1].honey || !tileArray[i + 1][j].honey)) {
                                // O
                                //O
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j - 1]);
                                hintArray.add(fruitsArray[i][j - 2]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 1][j + 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j + 1].invalid && !tileArray[i + 1][j].invalid && tileArray[i + 1][j].kind != 0
                                    && (!tileArray[i + 1][j + 1].honey || !tileArray[i + 1][j].honey)) {
                                //O
                                // O
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j - 1]);
                                hintArray.add(fruitsArray[i][j]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 3][j].kind) {
                            //Check is swappable
                            if (!tileArray[i][j].invalid && !tileArray[i + 1][j].invalid && tileArray[i + 1][j].kind != 0
                                    && (!tileArray[i][j].honey || !tileArray[i + 1][j].honey)) {
                                //O
                                //
                                //O
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j - 1]);
                                hintArray.add(fruitsArray[i + 2][j - 1]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i - 1][j].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j].invalid && !tileArray[i + 2][j].invalid && tileArray[i + 2][j].kind != 0
                                    && (!tileArray[i + 1][j].honey || !tileArray[i + 2][j].honey)) {
                                //O
                                //O
                                //
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i + 1][j - 1]);
                                hintArray.add(fruitsArray[i - 2][j - 1]);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in mRow 1
        for (int i = 1; i <= mRow; i++) {
            for (int j = 1; j <= mColumn - 1; j++) {
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 mColumn
                    if (tileArray[i][j].kind == tileArray[i][j + 1].kind) {
                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i - 1][j + 2].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j + 2].invalid && !tileArray[i][j + 2].invalid && tileArray[i][j + 2].kind != 0
                                    && (!tileArray[i - 1][j + 2].honey || !tileArray[i][j + 2].honey)) {
                                //  O
                                //OO
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i - 2][j + 1]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 1][j + 2].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j + 2].invalid && !tileArray[i][j + 2].invalid && tileArray[i][j + 2].kind != 0
                                    && (!tileArray[i + 1][j + 2].honey || !tileArray[i][j + 2].honey)) {
                                //OO
                                //  O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i][j + 1]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i - 1][j - 1].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j - 1].invalid && !tileArray[i][j - 1].invalid && tileArray[i][j - 1].kind != 0
                                    && (!tileArray[i - 1][j - 1].honey || !tileArray[i][j - 1].honey)) {
                                //O
                                // OO
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i - 2][j - 2]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 1][j - 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j - 1].invalid && !tileArray[i][j - 1].invalid && tileArray[i][j - 1].kind != 0
                                    && (!tileArray[i + 1][j - 1].honey || !tileArray[i][j - 1].honey)) {
                                // OO
                                //O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i][j - 2]);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in mRow 2
        for (int i = 1; i <= mRow; i++) {
            for (int j = 1; j <= mColumn - 2; j++) {
                if (!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 2 mColumn
                    if (tileArray[i][j].kind == tileArray[i][j + 2].kind) {
                        //Check potential match
                        if (tileArray[i][j].kind == tileArray[i - 1][j + 1].kind) {
                            //Check is swappable
                            if (!tileArray[i - 1][j + 1].invalid && !tileArray[i][j + 1].invalid && tileArray[i][j + 1].kind != 0
                                    && (!tileArray[i - 1][j + 1].honey || !tileArray[i][j + 1].honey)) {
                                // O
                                //O O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j + 1]);
                                hintArray.add(fruitsArray[i - 2][j]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i + 1][j + 1].kind) {
                            //Check is swappable
                            if (!tileArray[i + 1][j + 1].invalid && !tileArray[i][j + 1].invalid && tileArray[i][j + 1].kind != 0
                                    && (!tileArray[i + 1][j + 1].honey || !tileArray[i][j + 1].honey)) {
                                //O O
                                // O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j + 1]);
                                hintArray.add(fruitsArray[i][j]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i][j + 3].kind) {
                            //Check is swappable
                            if (!tileArray[i][j].invalid && !tileArray[i][j + 1].invalid && tileArray[i][j + 1].kind != 0
                                    && (!tileArray[i][j].honey || !tileArray[i][j + 1].honey)) {
                                //O OO
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j + 1]);
                                hintArray.add(fruitsArray[i - 1][j + 2]);
                                return true;
                            }
                        } else if (tileArray[i][j].kind == tileArray[i][j - 1].kind) {
                            //Check is swappable
                            if (!tileArray[i][j + 2].invalid && !tileArray[i][j + 1].invalid && tileArray[i][j + 1].kind != 0
                                    && (!tileArray[i][j + 2].honey || !tileArray[i][j + 1].honey)) {
                                //OO O
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j + 1]);
                                hintArray.add(fruitsArray[i - 1][j - 2]);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check ice cream
        for (int i = 1; i <= mRow; i++) {
            for (int j = 1; j <= mColumn; j++) {
                if (tileArray[i][j].direct == 'I') {
                    //Check nearby fruit is swappable
                    if ((!tileArray[i - 1][j].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i - 1][j].honey || !tileArray[i][j].honey)
                            && !tileArray[i - 1][j].breakable
                            && tileArray[i - 1][j].kind != 0
                            && tileArray[i - 1][j].kind != STAR_FISH) {
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i - 2][j - 1]);
                        return true;
                    } else if ((!tileArray[i + 1][j].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i + 1][j].honey || !tileArray[i][j].honey)
                            && !tileArray[i + 1][j].breakable
                            && tileArray[i + 1][j].kind != 0
                            && tileArray[i + 1][j].kind != STAR_FISH) {
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i][j - 1]);
                        return true;
                    } else if ((!tileArray[i][j - 1].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i][j - 1].honey || !tileArray[i][j].honey)
                            && !tileArray[i][j - 1].breakable
                            && tileArray[i][j - 1].kind != 0
                            && tileArray[i][j - 1].kind != STAR_FISH) {
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i - 1][j - 2]);
                        return true;
                    } else if ((!tileArray[i][j + 1].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i][j + 1].honey || !tileArray[i][j].honey)
                            && !tileArray[i][j + 1].breakable
                            && tileArray[i][j + 1].kind != 0
                            && tileArray[i][j + 1].kind != STAR_FISH) {
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i - 1][j]);
                        return true;
                    }
                }
            }
        }


         */
        return false;
    }

    @Override
    public void startGame() {
        mDelayTime = 0;
        mShowHint = true;
    }

    @Override
    public void onUpdate(long elapsedMillis) {

        if (mShowHint) {
            mDelayTime += elapsedMillis;
            if (mDelayTime > DELAY_TIME) {
                startHint();
                mDelayTime = 0;
                mShowHint = false;
            }
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        switch (gameEvent) {
            case START_HINT:
                stopHint();

                boolean isFindMatch = checkPossibleMatch();
                if (!isFindMatch) {
                    mGameEngine.onGameEvent(GameEvent.REFRESH);
                } else {
                    mShowHint = true;
                }
                break;
            case PLAYER_SWAP:
                stopHint();
                mShowHint = false;
                mDelayTime = 0;
                break;
        }
    }

    @Override
    public void onDraw() {
        // This game object does not draw anything
    }
}

