package com.example.matchgamesample.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;

public class GuideManager {
    private final Activity mActivity;
    private final RelativeLayout mRoot;
    private final int mTileSize;
    //Duration
    private static final int FALL_TIME_SHORT = 300;
    private static final int FALL_TIME_LONG = 600;
    private static final int PAUSE_TIME_SHORT = 200;
    private static final int PAUSE_TIME_LONG = 400;
    private static final int RETRY_TIME = 600;
    //Interpolator
    private final AnticipateInterpolator anticipateInterpolator = new AnticipateInterpolator();
    private final OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    private final DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();

    public GuideManager(GameEngine gameEngine){
        mActivity = gameEngine.mActivity;
        mTileSize = gameEngine.mImageSize;
        mRoot = gameEngine.mActivity.findViewById(R.id.guide_board);
    }

    private void createBlackScreen(){
        //Add black screen
        ImageView black_screen = new ImageView(mActivity);
        black_screen.setImageResource(R.color.black);
        black_screen.setImageAlpha(150);
        black_screen.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRoot.addView(black_screen);
    }

    public void createRefreshGuide(){
        //Add guide
        ImageView refresh = new ImageView(mActivity);
        refresh.setImageResource(R.drawable.guide_refresh);
        refresh.setX((int)(mRoot.getWidth() / 2 - mTileSize * 4));
        refresh.setY(-mTileSize * 3);
        refresh.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 8, mTileSize * 3));
        mRoot.addView(refresh);

        //Set animation
        //Set dropping
        refresh.animate().setDuration(FALL_TIME_LONG).y((float) (mRoot.getHeight() / 2 - mTileSize * 1.5)).setInterpolator(overshootInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Set retry
                refresh.animate().setStartDelay(PAUSE_TIME_LONG).setDuration(RETRY_TIME).y(-mTileSize * 3).setInterpolator(anticipateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRoot.removeView(refresh);
                    }
                });
            }
        });
    }

    public void createComboGuide(int combo){
        /* This method create guide when player make combo
         *  nice for 4 combo
         * great for 5 combo
         * wonderful for 6 combo
         */
        //Add guide
        ImageView guide = new ImageView(mActivity);
        if(combo == 4){
            guide.setImageResource(R.drawable.guide_nice);
        }else if(combo == 5){
            guide.setImageResource(R.drawable.guide_great);
        }else{
            guide.setImageResource(R.drawable.guide_wonderful);
        }
        guide.setX((int)(mRoot.getWidth() / 2 - mTileSize * 4));
        guide.setY(-mTileSize * 3);
        guide.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 8, mTileSize * 3));
        mRoot.addView(guide);

        //Set animation
        //Set dropping
        guide.animate().setDuration(FALL_TIME_SHORT).y((float) (mRoot.getHeight() / 2 - mTileSize * 1.5)).setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Set retry
                guide.animate().setStartDelay(PAUSE_TIME_LONG).setDuration(RETRY_TIME).y(-mTileSize * 3).setInterpolator(anticipateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRoot.removeView(guide);
                    }
                });
            }
        });
    }

    public void createBonusTime(){
        //Add guide
        ImageView bonusTime = new ImageView(mActivity);
        bonusTime.setImageResource(R.drawable.guide_bonus);
        bonusTime.setX((int)(mRoot.getWidth() / 2 - mTileSize * 4));
        bonusTime.setY(-mTileSize * 6);
        bonusTime.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 8, mTileSize * 6));
        mRoot.addView(bonusTime);

        //Set animation
        //Set dropping
        bonusTime.animate().setDuration(FALL_TIME_LONG).y((float) (mRoot.getHeight() / 2 - mTileSize * 3)).setInterpolator(overshootInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Set retry
                bonusTime.animate().setStartDelay(PAUSE_TIME_SHORT).setDuration(RETRY_TIME).y(-mTileSize * 6).setInterpolator(anticipateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRoot.removeView(bonusTime);
                    }
                });
            }
        });
    }

    public void createBeginGuide(int type){
        /* This method create guide when fruit begin
         *  type is the target type:
         *  1 for reach target score
         *  2 for collect items
         *  3 for eliminate item
         */
        //Add black screen
        createBlackScreen();
        //Add board
        LinearLayout board = new LinearLayout(mActivity);
        board.setOrientation(LinearLayout.HORIZONTAL);
        board.setGravity(Gravity.CENTER);
        board.setBackgroundResource(R.drawable.guide_board);
        board.setX(0);
        board.setY(-mTileSize * 4);
        board.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTileSize * 4));
        mRoot.addView(board);
        //Add guide
        ImageView guide = new ImageView(mActivity);
        if(type == 1) {
            guide.setImageResource(R.drawable.guide_taeget_score);
        }else if(type == 3){
            guide.setImageResource(R.drawable.guide_target_ice);
            ImageView ice = new ImageView(mActivity);
            ice.setBackgroundResource(R.drawable.ice);
            ice.setLayoutParams(new ViewGroup.LayoutParams((int)(mTileSize * 1.5), (int)(mTileSize * 1.5)));
            ice.animate().setStartDelay(200).setDuration(200).rotation(-30)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ice.animate().setStartDelay(0).setDuration(400).rotation(30)
                            .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ice.animate().setDuration(400).rotation(-30)
                                    .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    ice.animate().setDuration(400).rotation(30)
                                            .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            animation.cancel();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
            board.addView(ice);
        }else{
            guide.setImageResource(R.drawable.guide_target_collect);
        }
        guide.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 7, mTileSize * 2));
        board.addView(guide);

        //Set animation
        //Set dropping
        board.animate().setDuration(FALL_TIME_LONG).y((float) (mRoot.getHeight() / 2 - mTileSize * 2))
                .setInterpolator(overshootInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Set retry
                board.animate().setStartDelay(PAUSE_TIME_LONG * 2).setDuration(RETRY_TIME).y(mRoot.getHeight()).setInterpolator(anticipateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRoot.removeAllViews();
                    }
                });
            }
        });
    }

    public void createEndGuide(int type){
        /* This method create guide when fruit end
         *  type is the state:
         *  0 for player loss
         *  1 for player win
         */
        //Add black screen
        createBlackScreen();
        //Add board
        RelativeLayout board = new RelativeLayout(mActivity);
        board.setGravity(Gravity.CENTER);
        board.setBackgroundResource(R.drawable.guide_board);
        board.setX(0);
        board.setY(-mTileSize * 4);
        board.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTileSize * 4));
        mRoot.addView(board);
        //Add guide
        ImageView guide = new ImageView(mActivity);
        if(type == 1){
            guide.setImageResource(R.drawable.guide_win);
        }else{
            guide.setImageResource(R.drawable.guide_loss);
        }
        guide.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 7, mTileSize * 2));
        board.addView(guide);

        //Set animation
        //Set dropping
        board.animate().setDuration(FALL_TIME_LONG).y((float) (mRoot.getHeight() / 2 - mTileSize * 2)).setInterpolator(overshootInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Set retry
                board.animate().setStartDelay(PAUSE_TIME_LONG).setDuration(RETRY_TIME).y(mRoot.getHeight()).setInterpolator(anticipateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRoot.removeAllViews();
                    }
                });
            }
        });
    }

}

