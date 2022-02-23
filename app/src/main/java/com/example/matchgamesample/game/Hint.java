package com.example.matchgamesample.game;

import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import java.util.ArrayList;

import com.example.matchgamesample.R;

/* Hint class check possible match on board
 * if founded, start hint animation
 * otherwise, refresh
 */
public class Hint{
    private final int column, row;
    private final Animation animation;
    private Runnable runnable;
    private final Handler handler = new Handler();
    private final ArrayList<ImageView> hintArray;        //Put the match fruit in this
    //ID
    private static final int STAR_FISH = R.drawable.starfish;

    public Hint(Context context, int column, int row){
        this.column = column;
        this.row = row;
        animation = AnimationUtils.loadAnimation(context, R.anim.hint_animation);
        hintArray = new ArrayList<>();
    }

    public void startHint(){
        runnable = new Runnable() {
            @Override
            public void run() {
                //Start animation
                for(ImageView i:hintArray){
                    i.startAnimation(animation);
                }
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    public void stopHint(){
        hintArray.clear();
        handler.removeCallbacks(runnable);
        animation.cancel();
        animation.reset();
    }

    public boolean checkPossibleMatch(Tile[][] tileArray, ImageView[][] fruitsArray){
        /* Hint priority
         * 1. Special combine
         * 2. match in 5
         * 3. match in 4 T L
         * 4. match in 3
         * 5. ice cream
         */

        //Check special combine
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column; j++) {

                //Check swappable
                if(!tileArray[i][j].invalid) {

                    //Find two special fruit in row and column
                    if (!tileArray[i][j + 1].invalid
                            && tileArray[i][j].special && tileArray[i][j + 1].special){

                        //Check honey
                        if(!tileArray[i][j].honey || !tileArray[i][j + 1].honey) {
                            hintArray.add(fruitsArray[i - 1][j - 1]);
                            hintArray.add(fruitsArray[i - 1][j]);
                            return true;
                        }
                    } else if (!tileArray[i + 1][j].invalid
                            && tileArray[i][j].special && tileArray[i + 1][j].special){

                        //Check honey
                        if(!tileArray[i][j].honey || !tileArray[i + 1][j].honey) {
                            hintArray.add(fruitsArray[i - 1][j - 1]);
                            hintArray.add(fruitsArray[i][j - 1]);
                            return true;
                        }
                    }
                }
            }
        }

        //Check match 5 in column
        for (int i = 1; i <= row - 4; i++) {
            for (int j = 1; j <= column; j++) {
                //Check tile state
                if(!tileArray[i][j].empty
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

        //Check match 5 in row
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column - 4; j++) {
                if(!tileArray[i][j].empty
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

        //Check match 4 T L in column
        for (int i = 1; i <= row - 1; i++) {
            for (int j = 1; j <= column; j++) {
                //Check tile state
                if(!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 row
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
                                if(tileArray[i][j].kind == tileArray[i + 2][j - 2].kind) {
                                    //  O
                                    //  O
                                    //OO
                                    //  O
                                    hintArray.add(fruitsArray[i + 1][j - 3]);
                                }
                                if(tileArray[i][j].kind == tileArray[i + 2][j + 1].kind) {
                                    // O
                                    // O
                                    //O O
                                    // O
                                    hintArray.add(fruitsArray[i + 1][j]);
                                    if(tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
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
                                && tileArray[i][j].kind == tileArray[i + 3][j].kind ) {
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
                                if(tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
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
                                if(tileArray[i][j].kind == tileArray[i - 1][j - 2].kind) {
                                    //  O
                                    //OO
                                    //  O
                                    //  O
                                    hintArray.add(fruitsArray[i - 2][j - 3]);
                                }
                                if(tileArray[i][j].kind == tileArray[i - 1][j + 1].kind) {
                                    // O
                                    //O O
                                    // O
                                    // O
                                    hintArray.add(fruitsArray[i - 2][j]);
                                    if(tileArray[i][j].kind == tileArray[i - 1][j + 2].kind) {
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
                                if(tileArray[i][j].kind == tileArray[i - 1][j + 2].kind) {
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

        //Check match 4 T L in row
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column - 1; j++) {
                if(!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 column
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
                                if(tileArray[i][j].kind == tileArray[i - 2][j + 2].kind) {
                                    //  O
                                    //  O
                                    //OO O
                                    hintArray.add(fruitsArray[i - 3][j + 1]);
                                }
                                if(tileArray[i][j].kind == tileArray[i + 1][j + 2].kind) {
                                    //  O
                                    //OO O
                                    //  O
                                    hintArray.add(fruitsArray[i][j + 1]);
                                    if(tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
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
                                if(tileArray[i][j].kind == tileArray[i + 2][j + 2].kind) {
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
                                    && (!tileArray[i - 1][j - 1].honey || !tileArray[i][j - 1].honey )) {
                                // O
                                //O OO
                                hintArray.add(fruitsArray[i - 1][j - 1]);
                                hintArray.add(fruitsArray[i - 1][j]);
                                hintArray.add(fruitsArray[i - 2][j - 2]);
                                hintArray.add(fruitsArray[i - 1][j - 3]);
                                if(tileArray[i][j].kind == tileArray[i - 2][j - 1].kind) {
                                    // O
                                    // O
                                    //O OO
                                    hintArray.add(fruitsArray[i - 3][j - 2]);
                                }
                                if(tileArray[i][j].kind == tileArray[i + 1][j - 1].kind) {
                                    // O
                                    //O OO
                                    // O
                                    hintArray.add(fruitsArray[i][j - 2]);
                                    if(tileArray[i][j].kind == tileArray[i + 2][j - 1].kind) {
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
                                if(tileArray[i][j].kind == tileArray[i + 2][j - 1].kind) {
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

        //Check match 3 in column 1
        for (int i = 1; i <= row - 1; i++) {
            for (int j = 1; j <= column; j++) {
                //Check tile state
                if(!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 row
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

        //Check match 3 in column 2
        for (int i = 1; i <= row - 2; i++) {
            for (int j = 1; j <= column; j++) {
                //Check tile state
                if(!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 2 row
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

        //Check match 3 in row 1
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column - 1; j++) {
                if(!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 1 column
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

        //Check match 3 in row 2
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column - 2; j++) {
                if(!tileArray[i][j].empty
                        && !tileArray[i][j].breakable
                        && tileArray[i][j].kind != 0
                        && tileArray[i][j].kind != STAR_FISH) {
                    //Check next 2 column
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
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= column; j++) {
                if (tileArray[i][j].direct == 'I') {
                    //Check nearby fruit is swappable
                    if ( (!tileArray[i - 1][j].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i - 1][j].honey || !tileArray[i][j].honey)
                            && !tileArray[i - 1][j].breakable
                            && tileArray[i - 1][j].kind != 0
                            && tileArray[i - 1][j].kind != STAR_FISH){
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i - 2][j - 1]);
                        return true;
                    }else if( (!tileArray[i + 1][j].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i + 1][j].honey || !tileArray[i][j].honey)
                            && !tileArray[i + 1][j].breakable
                            && tileArray[i + 1][j].kind != 0
                            && tileArray[i + 1][j].kind != STAR_FISH){
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i][j - 1]);
                        return true;
                    }else if( (!tileArray[i][j - 1].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i][j - 1].honey || !tileArray[i][j].honey)
                            && !tileArray[i][j - 1].breakable
                            && tileArray[i][j - 1].kind != 0
                            && tileArray[i][j - 1].kind != STAR_FISH){
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i - 1][j - 2]);
                        return true;
                    }else if( (!tileArray[i][j + 1].invalid && !tileArray[i][j].invalid)
                            && (!tileArray[i][j + 1].honey || !tileArray[i][j].honey)
                            && !tileArray[i][j + 1].breakable
                            && tileArray[i][j + 1].kind != 0
                            && tileArray[i][j + 1].kind != STAR_FISH){
                        hintArray.add(fruitsArray[i - 1][j - 1]);
                        hintArray.add(fruitsArray[i - 1][j]);
                        return true;
                    }
                }
            }
        }

        return false;
    }

}

