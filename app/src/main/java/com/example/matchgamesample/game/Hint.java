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
    private static final int DELAY_TIME = 4000;

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
        mShowHint = false;
        mDelayTime = 0;

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

        //Check match 5 in column
        for (int i = 0; i < mRow - 4; i++) {
            for (int j = 0; j < mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].empty && tileArray[i][j].isFruit()) {

                    int kind = tileArray[i][j].kind;
                    if (kind == tileArray[i + 1][j].kind
                            && kind == tileArray[i + 3][j].kind
                            && kind == tileArray[i + 4][j].kind) {

                        //Check potential match
                        if (j > 0 && kind == tileArray[i + 2][j - 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 2][j - 1].isMovable()
                                    && tileArray[i + 2][j].isMovable()) {
                                // O
                                // O
                                //O
                                // O
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i + 2][j - 1].mImage);
                                hintArray.add(tileArray[i + 3][j].mImage);
                                hintArray.add(tileArray[i + 4][j].mImage);
                                return true;
                            }
                        } else if (j < mColumn - 1 && kind == tileArray[i + 2][j + 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 2][j + 1].isMovable()
                                    && tileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                // O
                                //O
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i + 2][j + 1].mImage);
                                hintArray.add(tileArray[i + 3][j].mImage);
                                hintArray.add(tileArray[i + 4][j].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 5 in row
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 4; j++) {
                //Check tile state
                if (!tileArray[i][j].empty && tileArray[i][j].isFruit()) {

                    int kind = tileArray[i][j].kind;
                    if (kind == tileArray[i][j + 1].kind
                            && kind == tileArray[i][j + 3].kind
                            && kind == tileArray[i][j + 4].kind) {

                        //Check potential match
                        if (i > 0 && kind == tileArray[i - 1][j + 2].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j + 2].isMovable()
                                    && tileArray[i][j + 2].isMovable()) {
                                //  O
                                //OO OO
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i - 1][j + 2].mImage);
                                hintArray.add(tileArray[i][j + 3].mImage);
                                hintArray.add(tileArray[i][j + 4].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && kind == tileArray[i + 1][j + 2].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j + 2].isMovable()
                                    && tileArray[i][j + 2].isMovable()) {
                                //OO OO
                                //  O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i + 1][j + 2].mImage);
                                hintArray.add(tileArray[i][j + 3].mImage);
                                hintArray.add(tileArray[i][j + 4].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 4 T L in column
        for (int i = 0; i < mRow - 1; i++) {
            for (int j = 0; j < mColumn; j++) {

                //Check tile state
                if (!tileArray[i][j].empty && tileArray[i][j].isFruit()) {

                    int kind = tileArray[i][j].kind;
                    //Check next 1 mRow
                    if (kind == tileArray[i + 1][j].kind) {

                        //Check potential match
                        if (i < mRow - 3 && j > 0
                                && kind == tileArray[i + 2][j - 1].kind
                                && kind == tileArray[i + 3][j].kind) {
                            //Check is swappable
                            if (tileArray[i + 2][j - 1].isMovable()
                                    && tileArray[i + 2][j].isMovable()) {
                                // O
                                // O
                                //O
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i + 2][j - 1].mImage);
                                hintArray.add(tileArray[i + 3][j].mImage);
                                if (j > 1 && kind == tileArray[i + 2][j - 2].kind) {
                                    //  O
                                    //  O
                                    //OO
                                    //  O
                                    hintArray.add(tileArray[i + 2][j - 2].mImage);
                                }
                                if (j < mColumn - 1 && kind == tileArray[i + 2][j + 1].kind) {
                                    // O
                                    // O
                                    //O O
                                    // O
                                    hintArray.add(tileArray[i + 2][j + 1].mImage);
                                    if (j < mColumn - 2 && kind == tileArray[i + 2][j + 2].kind) {
                                        // O
                                        // O
                                        //O OO
                                        // O
                                        hintArray.add(tileArray[i + 2][j + 2].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i < mRow - 3 && j < mColumn - 1
                                && kind == tileArray[i + 2][j + 1].kind
                                && kind == tileArray[i + 3][j].kind) {
                            //Check is swappable
                            if (tileArray[i + 2][j + 1].isMovable()
                                    && tileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                // O
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i + 2][j + 1].mImage);
                                hintArray.add(tileArray[i + 3][j].mImage);
                                if (j < mColumn - 2 && kind == tileArray[i + 2][j + 2].kind) {
                                    //O
                                    //O
                                    // OO
                                    //O
                                    hintArray.add(tileArray[i + 2][j + 2].mImage);
                                }
                                return true;
                            }
                        } else if (i > 1 && j > 0
                                && kind == tileArray[i - 1][j - 1].kind
                                && kind == tileArray[i - 2][j].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j - 1].isMovable() && tileArray[i - 1][j].isMovable()) {
                                // O
                                //O
                                // O
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i - 1][j - 1].mImage);
                                hintArray.add(tileArray[i - 2][j].mImage);
                                if (j > 1 && kind == tileArray[i - 1][j - 2].kind) {
                                    //  O
                                    //OO
                                    //  O
                                    //  O
                                    hintArray.add(tileArray[i - 1][j - 2].mImage);
                                }
                                if (j < mColumn - 1 && kind == tileArray[i - 1][j + 1].kind) {
                                    // O
                                    //O O
                                    // O
                                    // O
                                    hintArray.add(tileArray[i - 1][j + 1].mImage);
                                    if (j < mColumn - 2 && kind == tileArray[i - 1][j + 2].kind) {
                                        // O
                                        //O OO
                                        // O
                                        // O
                                        hintArray.add(tileArray[i - 1][j + 2].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i > 1 && j < mColumn - 1
                                && kind == tileArray[i - 1][j + 1].kind
                                && kind == tileArray[i - 2][j].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j + 1].isMovable() && tileArray[i - 1][j].isMovable()) {
                                //O
                                // O
                                //O
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i - 1][j + 1].mImage);
                                hintArray.add(tileArray[i - 2][j].mImage);
                                if (j < mColumn - 2 && kind == tileArray[i - 1][j + 2].kind) {
                                    //O
                                    // OO
                                    //O
                                    //O
                                    hintArray.add(tileArray[i - 1][j + 2].mImage);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 4 T L in row
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 1; j++) {
                if (!tileArray[i][j].empty
                        && tileArray[i][j].isFruit()) {

                    //Check next 1 mColumn
                    if (tileArray[i][j].kind == tileArray[i][j + 1].kind) {

                        int kind = tileArray[i][j].kind;
                        //Check potential match
                        if (i > 0 && j < mColumn - 3
                                && kind == tileArray[i - 1][j + 2].kind
                                && kind == tileArray[i][j + 3].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j + 2].isMovable()
                                    && tileArray[i][j + 2].isMovable()) {
                                //  O
                                //OO O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i - 1][j + 2].mImage);
                                hintArray.add(tileArray[i][j + 3].mImage);
                                if (i > 1 && kind == tileArray[i - 2][j + 2].kind) {
                                    //  O
                                    //  O
                                    //OO O
                                    hintArray.add(tileArray[i - 2][j + 2].mImage);
                                }
                                if (i < mRow - 1 && kind == tileArray[i + 1][j + 2].kind) {
                                    //  O
                                    //OO O
                                    //  O
                                    hintArray.add(tileArray[i + 1][j + 2].mImage);
                                    if (i < mRow - 2 && kind == tileArray[i + 2][j + 2].kind) {
                                        //  O
                                        //OO O
                                        //  O
                                        //  O
                                        hintArray.add(tileArray[i + 2][j + 2].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i < mRow - 1 && j < mColumn - 3
                                && kind == tileArray[i + 1][j + 2].kind
                                && kind == tileArray[i][j + 3].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j + 2].isMovable()
                                    && tileArray[i][j + 2].isMovable()) {
                                //OO O
                                //  O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i + 1][j + 2].mImage);
                                hintArray.add(tileArray[i][j + 3].mImage);
                                if (i < mRow - 2 && kind == tileArray[i + 2][j + 2].kind) {
                                    //OO O
                                    //  O
                                    //  O
                                    hintArray.add(tileArray[i + 2][j + 2].mImage);
                                }
                                return true;
                            }
                        } else if (i > 0 && j > 1
                                && kind == tileArray[i - 1][j - 1].kind
                                && kind == tileArray[i][j - 2].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j - 1].isMovable()
                                    && tileArray[i][j - 1].isMovable()) {
                                // O
                                //O OO
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i - 1][j - 1].mImage);
                                hintArray.add(tileArray[i][j - 2].mImage);
                                if (i > 1 && kind == tileArray[i - 2][j - 1].kind) {
                                    // O
                                    // O
                                    //O OO
                                    hintArray.add(tileArray[i - 2][j - 1].mImage);
                                }
                                if (i < mRow - 1 && kind == tileArray[i + 1][j - 1].kind) {
                                    // O
                                    //O OO
                                    // O
                                    hintArray.add(tileArray[i + 1][j - 1].mImage);
                                    if (i < mRow - 2 && kind == tileArray[i + 2][j - 1].kind) {
                                        // O
                                        //O OO
                                        // O
                                        // O
                                        hintArray.add(tileArray[i + 2][j - 1].mImage);
                                    }
                                }
                                return true;
                            }
                        } else if (i < mRow - 1 && j > 1
                                && kind == tileArray[i + 1][j - 1].kind
                                && kind == tileArray[i][j - 2].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j - 1].isMovable()
                                    && tileArray[i][j - 1].isMovable()) {
                                //O OO
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i + 1][j - 1].mImage);
                                hintArray.add(tileArray[i][j - 2].mImage);
                                if (i < mRow - 2 && kind == tileArray[i + 2][j - 1].kind) {
                                    //O OO
                                    // O
                                    // O
                                    hintArray.add(tileArray[i + 2][j - 1].mImage);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in column 1
        for (int i = 0; i < mRow - 1; i++) {
            for (int j = 0; j < mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].empty
                        && tileArray[i][j].isFruit()) {

                    int kind = tileArray[i][j].kind;
                    //Check next 1 mRow
                    if (kind == tileArray[i + 1][j].kind) {
                        //Check potential match
                        if (i < mRow - 2 && j > 0
                                && kind == tileArray[i + 2][j - 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 2][j - 1].isMovable()
                                    && tileArray[i + 2][j].isMovable()) {
                                // O
                                // O
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i + 2][j - 1].mImage);
                                return true;
                            }
                        } else if (i < mRow - 2 && j < mColumn - 1
                                && kind == tileArray[i + 2][j + 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 2][j + 1].isMovable()
                                    && tileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i + 2][j + 1].mImage);
                                return true;
                            }
                        } else if (i > 0 && j > 0
                                && kind == tileArray[i - 1][j - 1].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j - 1].isMovable()
                                    && tileArray[i - 1][j].isMovable()) {
                                //O
                                // O
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i - 1][j - 1].mImage);
                                return true;
                            }
                        } else if (i > 0 && j < mColumn - 1
                                && kind == tileArray[i - 1][j + 1].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j + 1].isMovable()
                                    && tileArray[i - 1][j].isMovable()) {
                                // O
                                //O
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j].mImage);
                                hintArray.add(tileArray[i - 1][j + 1].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in column 2
        for (int i = 0; i < mRow - 2; i++) {
            for (int j = 0; j < mColumn; j++) {
                //Check tile state
                if (!tileArray[i][j].empty
                        && tileArray[i][j].isFruit()) {

                    int kind = tileArray[i][j].kind;
                    //Check next 2 mRow
                    if (kind == tileArray[i + 2][j].kind) {

                        //Check potential match
                        if (j > 0 && kind == tileArray[i + 1][j - 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j - 1].isMovable()
                                    && tileArray[i + 1][j].isMovable()) {
                                // O
                                //O
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j - 1].mImage);
                                hintArray.add(tileArray[i + 2][j].mImage);
                                return true;
                            }
                        } else if (j < mColumn - 1 && kind == tileArray[i + 1][j + 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j + 1].isMovable()
                                    && tileArray[i + 1][j].isMovable()) {
                                //O
                                // O
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j + 1].mImage);
                                hintArray.add(tileArray[i + 2][j].mImage);
                                return true;
                            }
                        } else if (i < mRow - 3 && kind == tileArray[i + 3][j].kind) {
                            //Check is swappable
                            if (tileArray[i][j].isMovable()
                                    && tileArray[i + 1][j].isMovable()) {
                                //O
                                //
                                //O
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 2][j].mImage);
                                hintArray.add(tileArray[i + 3][j].mImage);
                                return true;
                            }
                        } else if (i > 0 && kind == tileArray[i - 1][j].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j].isMovable()
                                    && tileArray[i + 2][j].isMovable()) {
                                //O
                                //O
                                //
                                //O
                                hintArray.add(tileArray[i - 1][j].mImage);
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 2][j].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in row 1
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 1; j++) {
                if (!tileArray[i][j].empty && tileArray[i][j].isFruit()) {

                    int kind = tileArray[i][j].kind;
                    //Check next 1 mColumn
                    if (kind == tileArray[i][j + 1].kind) {
                        //Check potential match
                        if (i > 0 && j < mColumn - 2
                                && kind == tileArray[i - 1][j + 2].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j + 2].isMovable()
                                    && tileArray[i][j + 2].isMovable()) {
                                //  O
                                //OO
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i - 1][j + 2].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && j < mColumn - 2
                                && kind == tileArray[i + 1][j + 2].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j + 2].isMovable()
                                    && tileArray[i][j + 2].isMovable()) {
                                //OO
                                //  O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i + 1][j + 2].mImage);
                                return true;
                            }
                        } else if (i > 0 && j > 0
                                && kind == tileArray[i - 1][j - 1].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j - 1].isMovable()
                                    && tileArray[i][j - 1].isMovable()) {
                                //O
                                // OO
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i - 1][j - 1].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && j > 0
                                && kind == tileArray[i + 1][j - 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j - 1].isMovable()
                                    && tileArray[i][j - 1].isMovable()) {
                                // OO
                                //O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 1].mImage);
                                hintArray.add(tileArray[i + 1][j - 1].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check match 3 in row 2
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn - 2; j++) {
                if (!tileArray[i][j].empty
                        && tileArray[i][j].isFruit()) {

                    int kind = tileArray[i][j].kind;
                    //Check next 2 mColumn
                    if (kind == tileArray[i][j + 2].kind) {

                        //Check potential match
                        if (i > 0 && kind == tileArray[i - 1][j + 1].kind) {
                            //Check is swappable
                            if (tileArray[i - 1][j + 1].isMovable()
                                    && tileArray[i][j + 1].isMovable()) {
                                // O
                                //O O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i - 1][j + 1].mImage);
                                hintArray.add(tileArray[i][j + 2].mImage);
                                return true;
                            }
                        } else if (i < mRow - 1 && kind == tileArray[i + 1][j + 1].kind) {
                            //Check is swappable
                            if (tileArray[i + 1][j + 1].isMovable()
                                    && tileArray[i][j + 1].isMovable()) {
                                //O O
                                // O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i + 1][j + 1].mImage);
                                hintArray.add(tileArray[i][j + 2].mImage);
                                return true;
                            }
                        } else if (j < mColumn - 3 && kind == tileArray[i][j + 3].kind) {
                            //Check is swappable
                            if (tileArray[i][j].isMovable()
                                    && tileArray[i][j + 1].isMovable()) {
                                //O OO
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 2].mImage);
                                hintArray.add(tileArray[i][j + 3].mImage);
                                return true;
                            }
                        } else if (j > 0 && kind == tileArray[i][j - 1].kind) {
                            //Check is swappable
                            if (tileArray[i][j + 2].isMovable()
                                    && tileArray[i][j + 1].isMovable()) {
                                //OO O
                                hintArray.add(tileArray[i][j].mImage);
                                hintArray.add(tileArray[i][j + 2].mImage);
                                hintArray.add(tileArray[i][j - 1].mImage);
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //Check ice cream
        for (int i = 0; i < mRow; i++) {
            for (int j = 0; j < mColumn; j++) {

                if (tileArray[i][j].direct == 'I' && tileArray[i][j].isMovable()) {

                    //Check nearby fruit is swappable
                    if (i > 0 && tileArray[i - 1][j].isMovable()
                            && tileArray[i - 1][j].isFruit()) {
                        hintArray.add(tileArray[i][j].mImage);
                        hintArray.add(tileArray[i - 1][j].mImage);
                        return true;
                    } else if (i < mRow - 1 && tileArray[i + 1][j].isMovable()
                            && tileArray[i + 1][j].isFruit()) {
                        hintArray.add(tileArray[i][j].mImage);
                        hintArray.add(tileArray[i + 1][j].mImage);
                        return true;
                    } else if (j > 0 && tileArray[i][j - 1].isMovable()
                            && tileArray[i][j - 1].isFruit()) {
                        hintArray.add(tileArray[i][j].mImage);
                        hintArray.add(tileArray[i][j - 1].mImage);
                        return true;
                    } else if (j < mColumn - 1 && tileArray[i][j + 1].isMovable()
                            && tileArray[i][j + 1].isFruit()) {
                        hintArray.add(tileArray[i][j].mImage);
                        hintArray.add(tileArray[i][j + 1].mImage);
                        return true;
                    }

                }
            }
        }

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
                // When the player make a invalid swap, we stop the hint
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
                break;
        }
    }

    @Override
    public void onDraw() {
        // This game object does not draw anything
    }
}

