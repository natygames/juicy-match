package com.example.matchgamesample.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.example.matchgamesample.R;
import com.example.matchgamesample.engine.GameEngine;
import com.example.matchgamesample.game.Tile;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.explosion.Explosion;

public class AnimationManager {
    private final Activity mActivity;
    private final RelativeLayout mEffect_board;
    private final int mTileSize;
    //Score text
    private static final int UPGRADE_TIME = 300;
    private final AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
    private final DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private final AnticipateInterpolator anticipateInterpolator = new AnticipateInterpolator();
    private final OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    //Explode lock animation
    private final Explosion explosion;
    private final Animation shaking_anim, shaking_small_anim, machine_anim;
    private final Handler mHandler = new Handler();

    public AnimationManager(GameEngine gameEngine) {
        mEffect_board = gameEngine.mActivity.findViewById(R.id.effect_board);
        mActivity = gameEngine.mActivity;
        this.mTileSize = gameEngine.mImageSize;
        explosion = Explosion.attach2Window(gameEngine.mActivity);
        shaking_anim = AnimationUtils.loadAnimation(mActivity, R.anim.shaking_animation);
        shaking_small_anim = AnimationUtils.loadAnimation(mActivity, R.anim.shaking_small_animation);
        machine_anim = AnimationUtils.loadAnimation(mActivity, R.anim.machine_animation);
    }

    //This create horizontal flash
    public void createHorizontalFlash(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);

        //Add flash1
        ImageView flash1 = new ImageView(mActivity);
        flash1.setBackgroundResource(R.drawable.flash_h_right_clip);
        flash1.setX(tile.x - mTileSize * 3);
        flash1.setY(tile.y - mTileSize);
        flash1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 3, mTileSize));
        mEffect_board.addView(flash1);
        //Add flash2
        ImageView flash2 = new ImageView(mActivity);
        flash2.setBackgroundResource(R.drawable.flash_h_left_clip);
        flash2.setX(tile.x - mTileSize);
        flash2.setY(tile.y - mTileSize);
        flash2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 3, mTileSize));
        mEffect_board.addView(flash2);

        //Set clip
        ClipDrawable clip1 = (ClipDrawable) flash1.getBackground();
        clip1.setLevel(0);
        ClipDrawable clip2 = (ClipDrawable) flash2.getBackground();
        clip2.setLevel(0);

        final int[] level = {0};
        //Set runnable
        new Runnable() {
            @Override
            public void run() {
                level[0] += 1400;
                clip1.setLevel(level[0]);
                clip2.setLevel(level[0]);
                if (level[0] < 10000) {
                    mHandler.postDelayed(this, 10);
                } else {
                    flash1.animate().setDuration(600).x(tile.col * mTileSize - mTileSize * 9).scaleX(1.5f).scaleY(0.6f).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mEffect_board.removeView(flash1);
                        }
                    });
                    flash2.animate().setDuration(600).x(tile.col * mTileSize + mTileSize * 5).scaleX(1.5f).scaleY(0.6f).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mEffect_board.removeView(flash2);
                        }
                    });
                    mHandler.removeCallbacks(this);
                }
            }
        }.run();
    }

    //This create vertical flash
    public void createVerticalFlash(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);

        //Add flash1
        ImageView flash1 = new ImageView(mActivity);
        flash1.setBackgroundResource(R.drawable.flash_v_bottom_clip);
        flash1.setX(tile.x - mTileSize);
        flash1.setY(tile.y - mTileSize * 3);
        flash1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize * 3));
        mEffect_board.addView(flash1);
        //Add flash2
        ImageView flash2 = new ImageView(mActivity);
        flash2.setBackgroundResource(R.drawable.flash_v_top_clip);
        flash2.setX(tile.x - mTileSize);
        flash2.setY(tile.y - mTileSize);
        flash2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize * 3));
        mEffect_board.addView(flash2);

        //Set clip
        ClipDrawable clip1 = (ClipDrawable) flash1.getBackground();
        clip1.setLevel(0);
        ClipDrawable clip2 = (ClipDrawable) flash2.getBackground();
        clip2.setLevel(0);

        final int[] level = {0};
        //Set runnable
        new Runnable() {
            @Override
            public void run() {
                level[0] += 1400;
                clip1.setLevel(level[0]);
                clip2.setLevel(level[0]);
                if (level[0] < 10000) {
                    mHandler.postDelayed(this, 10);
                } else {
                    flash1.animate().setDuration(600).y(tile.row * mTileSize - mTileSize * 9).scaleY(1.5f).scaleX(0.6f).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mEffect_board.removeView(flash1);
                        }
                    });
                    flash2.animate().setDuration(600).y(tile.row * mTileSize + mTileSize * 5).scaleY(1.5f).scaleX(0.6f).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mEffect_board.removeView(flash2);
                        }
                    });
                    mHandler.removeCallbacks(this);
                }
            }
        }.run();
    }

    //This create square flash
    public void createSquareFlash(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 5);
        //Add flash
        ImageView flash = new ImageView(mActivity);
        flash.setImageResource(R.drawable.flash_s_clip);
        flash.setX(tile.x - (int) (mTileSize * 5 / 2));
        flash.setY(tile.y - (int) (mTileSize * 5 / 2));
        flash.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 4, mTileSize * 4));
        mEffect_board.addView(flash);

        //Set clip
        ClipDrawable clip = (ClipDrawable) flash.getDrawable();
        clip.setLevel(0);

        final int[] level = {0};
        //Set runnable
        new Runnable() {
            @Override
            public void run() {
                level[0] += 1000;
                clip.setLevel(level[0]);
                if (level[0] < 10000) {
                    mHandler.postDelayed(this, 10);
                } else {
                    flash.animate().setDuration(500).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mEffect_board.removeView(flash);
                        }
                    });
                    mHandler.removeCallbacks(this);
                }
            }
        }.run();

        //Add sparkler
        createSparkler(tile, 4, 3, 0.4f, 850);
        createSparkler(tile, 4, 2.5f, 0.5f, 800);

        //Add flash bar
        //Top right
        ImageView flash_bar1 = new ImageView(mActivity);
        flash_bar1.setImageResource(R.drawable.flash_bar);
        flash_bar1.setX(tile.x - mTileSize + mTileSize / 2);
        flash_bar1.setY(tile.y - mTileSize);
        flash_bar1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar1.setRotation(45);
        flash_bar1.animate().setDuration(500).x(tile.x - mTileSize + mTileSize / 2 + mTileSize * 2).y(tile.y - mTileSize - mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar1);
            }
        });
        mEffect_board.addView(flash_bar1);
        //Bottom right
        ImageView flash_bar2 = new ImageView(mActivity);
        flash_bar2.setImageResource(R.drawable.flash_bar);
        flash_bar2.setX(tile.x - mTileSize + mTileSize / 2);
        flash_bar2.setY(tile.y - mTileSize + mTileSize / 2);
        flash_bar2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar2.setRotation(135);
        flash_bar2.animate().setDuration(500).x(tile.x - mTileSize + mTileSize / 2 + mTileSize * 2).y(tile.y - mTileSize + mTileSize / 2 + mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar2);
            }
        });
        mEffect_board.addView(flash_bar2);
        //Bottom left
        ImageView flash_bar3 = new ImageView(mActivity);
        flash_bar3.setImageResource(R.drawable.flash_bar);
        flash_bar3.setX(tile.x - mTileSize);
        flash_bar3.setY(tile.y - mTileSize + mTileSize / 2);
        flash_bar3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar3.setRotation(225);
        flash_bar3.animate().setDuration(500).x(tile.x - mTileSize - mTileSize * 2).y(tile.y - mTileSize + mTileSize / 2 + mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar3);
            }
        });
        mEffect_board.addView(flash_bar3);
        //Top left
        ImageView flash_bar4 = new ImageView(mActivity);
        flash_bar4.setImageResource(R.drawable.flash_bar);
        flash_bar4.setX(tile.x - mTileSize);
        flash_bar4.setY(tile.y - mTileSize);
        flash_bar4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar4.setRotation(315);
        flash_bar4.animate().setDuration(500).x(tile.x - mTileSize - mTileSize * 2).y(tile.y - mTileSize - mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar4);
            }
        });
        mEffect_board.addView(flash_bar4);
        //Top
        ImageView flash_bar5 = new ImageView(mActivity);
        flash_bar5.setImageResource(R.drawable.flash_bar);
        flash_bar5.setX(tile.x - mTileSize + mTileSize / 4);
        flash_bar5.setY(tile.y - mTileSize);
        flash_bar5.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar5.setRotation(0);
        flash_bar5.animate().setDuration(500).y(tile.y - mTileSize - mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar5);
            }
        });
        mEffect_board.addView(flash_bar5);
        //Right
        ImageView flash_bar6 = new ImageView(mActivity);
        flash_bar6.setImageResource(R.drawable.flash_bar);
        flash_bar6.setX(tile.x - mTileSize + mTileSize / 2);
        flash_bar6.setY(tile.y - mTileSize + mTileSize / 4);
        flash_bar6.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar6.setRotation(90);
        flash_bar6.animate().setDuration(500).x(tile.x - mTileSize + mTileSize / 2 + mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar6);
            }
        });
        mEffect_board.addView(flash_bar6);
        //Bottom
        ImageView flash_bar7 = new ImageView(mActivity);
        flash_bar7.setImageResource(R.drawable.flash_bar);
        flash_bar7.setX(tile.x - mTileSize + mTileSize / 4);
        flash_bar7.setY(tile.y - mTileSize + mTileSize / 2);
        flash_bar7.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar7.setRotation(180);
        flash_bar7.animate().setDuration(500).y(tile.y - mTileSize + mTileSize / 2 + mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar7);
            }
        });
        mEffect_board.addView(flash_bar7);
        //Left
        ImageView flash_bar8 = new ImageView(mActivity);
        flash_bar8.setImageResource(R.drawable.flash_bar);
        flash_bar8.setX(tile.x - mTileSize);
        flash_bar8.setY(tile.y - mTileSize + mTileSize / 4);
        flash_bar8.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar8.setRotation(270);
        flash_bar8.animate().setDuration(500).x(tile.x - mTileSize - mTileSize * 2)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar8);
            }
        });
        mEffect_board.addView(flash_bar8);
    }

    //This create square flash
    public void createSquareFlash_big(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 7);
        //Add wave
        createExplodeWave_small(tile);
        //Add flash
        ImageView flash = new ImageView(mActivity);
        flash.setImageResource(R.drawable.flash_s_clip);
        flash.setX(tile.x - mTileSize * 3);
        flash.setY(tile.y - mTileSize * 3);
        flash.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5, mTileSize * 5));
        mEffect_board.addView(flash);

        //Set clip
        ClipDrawable clip = (ClipDrawable) flash.getDrawable();
        clip.setLevel(0);

        final int[] level = {0};
        //Set thread
        new Runnable() {
            @Override
            public void run() {
                level[0] += 1000;
                clip.setLevel(level[0]);
                if (level[0] < 10000) {
                    mHandler.postDelayed(this, 10);
                } else {
                    flash.animate().setDuration(500).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mEffect_board.removeView(flash);
                        }
                    });
                    mHandler.removeCallbacks(this);
                }
            }
        }.run();

        //Add sparkler
        createSparkler(tile, 4, 5, 0.4f, 900);
        createSparkler(tile, 4, 4.5f, 0.5f, 850);
        createSparkler(tile, 4, 4, 0.3f, 800);

        //Add flash bar
        //Top right
        ImageView flash_bar1 = new ImageView(mActivity);
        flash_bar1.setImageResource(R.drawable.flash_bar);
        flash_bar1.setX(tile.x - mTileSize + mTileSize / 2);
        flash_bar1.setY(tile.y - mTileSize);
        flash_bar1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar1.setRotation(45);
        flash_bar1.animate().setDuration(500).x(tile.x - mTileSize + mTileSize / 2 + mTileSize * 3).y(tile.y - mTileSize - mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar1);
            }
        });
        mEffect_board.addView(flash_bar1);
        //Bottom right
        ImageView flash_bar2 = new ImageView(mActivity);
        flash_bar2.setImageResource(R.drawable.flash_bar);
        flash_bar2.setX(tile.x - mTileSize + mTileSize / 2);
        flash_bar2.setY(tile.y - mTileSize + mTileSize / 2);
        flash_bar2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar2.setRotation(135);
        flash_bar2.animate().setDuration(500).x(tile.x - mTileSize + mTileSize / 2 + mTileSize * 3).y(tile.y - mTileSize + mTileSize / 2 + mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar2);
            }
        });
        mEffect_board.addView(flash_bar2);
        //Bottom left
        ImageView flash_bar3 = new ImageView(mActivity);
        flash_bar3.setImageResource(R.drawable.flash_bar);
        flash_bar3.setX(tile.x - mTileSize);
        flash_bar3.setY(tile.y - mTileSize + mTileSize / 2);
        flash_bar3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar3.setRotation(225);
        flash_bar3.animate().setDuration(500).x(tile.x - mTileSize - mTileSize * 3).y(tile.y - mTileSize + mTileSize / 2 + mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar3);
            }
        });
        mEffect_board.addView(flash_bar3);
        //Top left
        ImageView flash_bar4 = new ImageView(mActivity);
        flash_bar4.setImageResource(R.drawable.flash_bar);
        flash_bar4.setX(tile.x - mTileSize);
        flash_bar4.setY(tile.y - mTileSize);
        flash_bar4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar4.setRotation(315);
        flash_bar4.animate().setDuration(500).x(tile.x - mTileSize - mTileSize * 3).y(tile.y - mTileSize - mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar4);
            }
        });
        mEffect_board.addView(flash_bar4);
        //Top
        ImageView flash_bar5 = new ImageView(mActivity);
        flash_bar5.setImageResource(R.drawable.flash_bar);
        flash_bar5.setX(tile.x - mTileSize + mTileSize / 4);
        flash_bar5.setY(tile.y - mTileSize);
        flash_bar5.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar5.setRotation(0);
        flash_bar5.animate().setDuration(500).y(tile.y - mTileSize - mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar5);
            }
        });
        mEffect_board.addView(flash_bar5);
        //Right
        ImageView flash_bar6 = new ImageView(mActivity);
        flash_bar6.setImageResource(R.drawable.flash_bar);
        flash_bar6.setX(tile.x - mTileSize + mTileSize / 2);
        flash_bar6.setY(tile.y - mTileSize + mTileSize / 4);
        flash_bar6.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar6.setRotation(90);
        flash_bar6.animate().setDuration(500).x(tile.x - mTileSize + mTileSize / 2 + mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar6);
            }
        });
        mEffect_board.addView(flash_bar6);
        //Bottom
        ImageView flash_bar7 = new ImageView(mActivity);
        flash_bar7.setImageResource(R.drawable.flash_bar);
        flash_bar7.setX(tile.x - mTileSize + mTileSize / 4);
        flash_bar7.setY(tile.y - mTileSize + mTileSize / 2);
        flash_bar7.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar7.setRotation(180);
        flash_bar7.animate().setDuration(500).y(tile.y - mTileSize + mTileSize / 2 + mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar7);
            }
        });
        mEffect_board.addView(flash_bar7);
        //Left
        ImageView flash_bar8 = new ImageView(mActivity);
        flash_bar8.setImageResource(R.drawable.flash_bar);
        flash_bar8.setX(tile.x - mTileSize);
        flash_bar8.setY(tile.y - mTileSize + mTileSize / 4);
        flash_bar8.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 2, mTileSize / 2));
        flash_bar8.setRotation(270);
        flash_bar8.animate().setDuration(500).x(tile.x - mTileSize - mTileSize * 3)
                .scaleX(4).scaleY(4).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash_bar8);
            }
        });
        mEffect_board.addView(flash_bar8);
    }

    //This create upgrade to hori animation
    public void upgrade2H_left(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);
        //Add first tile
        ImageView fruit1 = new ImageView(mActivity);
        fruit1.setImageResource(tile.kind);
        fruit1.setX(tile.x - mTileSize * 2 - (int) (mTileSize / 3));
        fruit1.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit1);
        //Add second tile
        ImageView fruit2 = new ImageView(mActivity);
        fruit2.setImageResource(tile.kind);
        fruit2.setX(tile.x - (int) (mTileSize / 3));               //tile.x - mTileSize + mTileSize
        fruit2.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit2);
        //Add third tile
        ImageView fruit3 = new ImageView(mActivity);
        fruit3.setImageResource(tile.kind);
        fruit3.setX(tile.x + mTileSize - (int) (mTileSize / 3));       //tile.x - mTileSize + mTileSize * 2
        fruit3.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit3);
        //Add moving animation
        fruit1.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit1);
                    }
                });
        fruit2.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit2);
                    }
                });
        fruit3.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit3);
                    }
                });
        //Add explode animation
        explodeNotSpecialFruit(tile);
    }

    //This create upgrade to hori animation
    public void upgrade2H_right(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);
        //Add first tile
        ImageView fruit1 = new ImageView(mActivity);
        fruit1.setImageResource(tile.kind);
        fruit1.setX(tile.x - mTileSize * 3 - (int) (mTileSize / 3));
        fruit1.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit1);
        //Add second tile
        ImageView fruit2 = new ImageView(mActivity);
        fruit2.setImageResource(tile.kind);
        fruit2.setX(tile.x - mTileSize * 2 - (int) (mTileSize / 3));               //tile.x - mTileSize + mTileSize
        fruit2.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit2);
        //Add thrid tile
        ImageView fruit3 = new ImageView(mActivity);
        fruit3.setImageResource(tile.kind);
        fruit3.setX(tile.x - (int) (mTileSize / 3));       //tile.x - mTileSize + mTileSize * 2
        fruit3.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit3);
        //Add moving animation
        fruit1.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit1);
                    }
                });
        fruit2.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit2);
                    }
                });
        fruit3.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit3);
                    }
                });
        //Add explode animation
        explodeNotSpecialFruit(tile);
    }

    //This create upgrade to verti animation
    public void upgrade2V_bottom(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);
        //Add first tile
        ImageView fruit1 = new ImageView(mActivity);
        fruit1.setImageResource(tile.kind);
        fruit1.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit1.setY(tile.y - (int) (mTileSize / 3));
        fruit1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit1);
        //Add second tile
        ImageView fruit2 = new ImageView(mActivity);
        fruit2.setImageResource(tile.kind);
        fruit2.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit2.setY(tile.y - mTileSize * 2 - (int) (mTileSize / 3));
        fruit2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit2);
        //Add thrid tile
        ImageView fruit3 = new ImageView(mActivity);
        fruit3.setImageResource(tile.kind);
        fruit3.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit3.setY(tile.y - mTileSize * 3 - (int) (mTileSize / 3));
        fruit3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit3);
        //Add moving animation
        fruit1.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit1);
                    }
                });
        fruit2.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit2);
                    }
                });
        fruit3.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit3);
                    }
                });
        //Add explode animation
        explodeNotSpecialFruit(tile);
    }

    //This create upgrade to verti animation
    public void upgrade2V_top(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);
        //Add first tile
        ImageView fruit1 = new ImageView(mActivity);
        fruit1.setImageResource(tile.kind);
        fruit1.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit1.setY(tile.y + mTileSize - (int) (mTileSize / 3));
        fruit1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit1);
        //Add second tile
        ImageView fruit2 = new ImageView(mActivity);
        fruit2.setImageResource(tile.kind);
        fruit2.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit2.setY(tile.y - (int) (mTileSize / 3));
        fruit2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit2);
        //Add thrid tile
        ImageView fruit3 = new ImageView(mActivity);
        fruit3.setImageResource(tile.kind);
        fruit3.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit3.setY(tile.y - mTileSize * 2 - (int) (mTileSize / 3));
        fruit3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit3);
        //Add moving animation
        fruit1.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit1);
                    }
                });
        fruit2.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit2);
                    }
                });
        fruit3.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit3);
                    }
                });
        //Add explode animation
        explodeNotSpecialFruit(tile);
    }

    //This create upgrade to horizontal coco animation
    public void upgrade2I_h(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);

        //Add first tile
        ImageView fruit1 = new ImageView(mActivity);
        fruit1.setImageResource(tile.kind);
        fruit1.setX(tile.x - mTileSize * 3 - (int) (mTileSize / 3));
        fruit1.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit1);
        //Add second tile
        ImageView fruit2 = new ImageView(mActivity);
        fruit2.setImageResource(tile.kind);
        fruit2.setX(tile.x - mTileSize * 2 - (int) (mTileSize / 3));
        fruit2.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit2);
        //Add thrid tile
        ImageView fruit3 = new ImageView(mActivity);
        fruit3.setImageResource(tile.kind);
        fruit3.setX(tile.x - (int) (mTileSize / 3));       //tile.x - mTileSize + mTileSize
        fruit3.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit3);
        //Add forth tile
        ImageView fruit4 = new ImageView(mActivity);
        fruit4.setImageResource(tile.kind);
        fruit4.setX(tile.x + mTileSize - (int) (mTileSize / 3));       //tile.x - mTileSize + mTileSize * 2
        fruit4.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        fruit4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit4);
        //Add moving animation
        fruit1.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit1);
                    }
                });
        fruit2.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit2);
                    }
                });
        fruit3.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit3);
                    }
                });
        fruit4.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit4);
                    }
                });

        //Add flash
        createTransformAnim(tile);

        //Add explode animation
        explodeNotSpecialFruit(tile);
    }

    //This create upgrade to vertical coco animation
    public void upgrade2I_v(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 3);

        //Add first tile
        ImageView fruit1 = new ImageView(mActivity);
        fruit1.setImageResource(tile.kind);
        fruit1.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit1.setY(tile.y + mTileSize - (int) (mTileSize / 3));              //tile.y - mTileSize + mTileSize
        fruit1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit1);
        //Add second tile
        ImageView fruit2 = new ImageView(mActivity);
        fruit2.setImageResource(tile.kind);
        fruit2.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit2.setY(tile.y - (int) (mTileSize / 3));
        fruit2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit2);
        //Add thrid tile
        ImageView fruit3 = new ImageView(mActivity);
        fruit3.setImageResource(tile.kind);
        fruit3.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit3.setY(tile.y - mTileSize * 2 - (int) (mTileSize / 3));
        fruit3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit3);
        //Add forth tile
        ImageView fruit4 = new ImageView(mActivity);
        fruit4.setImageResource(tile.kind);
        fruit4.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        fruit4.setY(tile.y - mTileSize * 3 - (int) (mTileSize / 3));
        fruit4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit4);
        //Add moving animation
        fruit1.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)         //tile.y - mTileSize + mTileSize * 2
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit1);
                    }
                });
        fruit2.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit2);
                    }
                });
        fruit3.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit3);
                    }
                });
        fruit4.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit4);
                    }
                });

        //Add flash
        createTransformAnim(tile);

        //Add explode animation
        explodeNotSpecialFruit(tile);
    }

    //This create upgrade to squ animation
    public void upgrade2S(Tile tile, char direction, int type) {
        //Add explode
        createExplodeBackground(tile, 3);
        //Add first tile
        ImageView fruit1 = new ImageView(mActivity);
        fruit1.setImageResource(tile.kind);
        if (direction == 'L') {
            fruit1.setX(tile.x - (int) (mTileSize / 3));
            fruit1.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        } else if (direction == 'C') {
            fruit1.setX(tile.x - (int) (mTileSize / 3));
            fruit1.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        } else {
            fruit1.setX(tile.x - mTileSize * 2 - (int) (mTileSize / 3));
            fruit1.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        }
        fruit1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit1);
        //Add second tile
        ImageView fruit2 = new ImageView(mActivity);
        fruit2.setImageResource(tile.kind);
        if (direction == 'L') {
            fruit2.setX(tile.x + mTileSize - (int) (mTileSize / 3));
            fruit2.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        } else if (direction == 'C') {
            fruit2.setX(tile.x - mTileSize * 2 - (int) (mTileSize / 3));
            fruit2.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        } else {
            fruit2.setX(tile.x - mTileSize * 3 - (int) (mTileSize / 3));
            fruit2.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        }
        fruit2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit2);
        //Add third tile
        ImageView fruit3 = new ImageView(mActivity);
        fruit3.setImageResource(tile.kind);
        if (type == 1) {
            fruit3.setX(tile.x - mTileSize - (int) (mTileSize / 3));
            fruit3.setY(tile.y - mTileSize * 2 - (int) (mTileSize / 3));
        } else if (type == 2) {
            fruit3.setX(tile.x - mTileSize - (int) (mTileSize / 3));
            fruit3.setY(tile.y - mTileSize * 2 - (int) (mTileSize / 3));
        } else {
            fruit3.setX(tile.x - mTileSize - (int) (mTileSize / 3));
            fruit3.setY(tile.y - (int) (mTileSize / 3));
        }
        fruit3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit3);
        //Add forth tile
        ImageView fruit4 = new ImageView(mActivity);
        fruit4.setImageResource(tile.kind);
        if (type == 1) {
            fruit4.setX(tile.x - mTileSize - (int) (mTileSize / 3));
            fruit4.setY(tile.y - mTileSize * 3 - (int) (mTileSize / 3));
        } else if (type == 2) {
            fruit4.setX(tile.x - mTileSize - (int) (mTileSize / 3));
            fruit4.setY(tile.y - (int) (mTileSize / 3));
        } else {
            fruit4.setX(tile.x - mTileSize - (int) (mTileSize / 3));
            fruit4.setY(tile.y + mTileSize - (int) (mTileSize / 3));
        }
        fruit4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(fruit4);
        //Add moving animation
        fruit1.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit1);
                    }
                });
        fruit2.animate().setDuration(UPGRADE_TIME).x(tile.x - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit2);
                    }
                });
        fruit3.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit3);
                    }
                });
        fruit4.animate().setDuration(UPGRADE_TIME).y(tile.y - mTileSize - (int) (mTileSize / 3)).scaleX(0.5f).scaleY(0.5f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(fruit4);
                    }
                });
        //Add explode animation
        explodeNotSpecialFruit(tile);
    }

    //This create score effect when tile match
    public void createScore(Tile tile) {
        //Get position (in case it move)
        int positX = tile.x;
        int positY = tile.y;

        //Add score image
        ImageView score = new ImageView(mActivity);
        //Set color base on kind
        if (tile.kind == R.drawable.coconut) {
            score.setBackgroundResource(R.drawable.score_brown);     //coconut is brown
        } else if (tile.kind == R.drawable.cherry) {
            score.setBackgroundResource(R.drawable.score_pink);         //cherry is pink
        } else if (tile.kind == R.drawable.lemon) {
            score.setBackgroundResource(R.drawable.score_yellow);      //lemon is yellow
        } else if (tile.kind == R.drawable.strawberry) {
            score.setBackgroundResource(R.drawable.score_red);       //strawberry is red
        } else if (tile.kind == R.drawable.banana) {
            score.setBackgroundResource(R.drawable.score_white);       //banana is white
        } else if (tile.kind == R.drawable.icecream) {
            score.setBackgroundResource(R.drawable.score_white);       //ice cream is white
        }
        //Set location and size
        score.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        score.setX(positX);
        score.setY(positY);
        score.setScaleX(0);
        score.setScaleY(0);
        mEffect_board.addView(score);

        //Set animation
        score.animate().setDuration(400).scaleX(1).scaleY(1).setInterpolator(overshootInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        score.animate().setDuration(1500).y((float) (positY - mTileSize / 2)).alpha(0)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        mEffect_board.removeView(score);
                                    }
                                });
                    }
                });

    }

    //This create tile explode effect
    public void explodeFruit(Tile tile) {
        int positX = tile.x;
        int positY = tile.y;

        if (tile.direct == 'I') {
            //Add explode
            createExplodeBackground(tile, 3);
            //Add sparkler
            createSparkler(tile, 4, 0.7f, 0.2f, 750);
            //Add ice cream
            ImageView icecream = new ImageView(mActivity);
            icecream.setImageResource(R.drawable.icecream);
            icecream.setX(positX);
            icecream.setY(positY);
            icecream.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
            icecream.animate().setDuration(500).scaleX(0).scaleY(0);
            mEffect_board.addView(icecream);
            return;
        }

        //Add sparkler
        createSparkler(tile, 4, 0.7f, 0.2f, 750);
        //Add flash
        ImageView flash = new ImageView(mActivity);
        flash.setImageResource(R.drawable.flash_s_small);
        flash.setX(positX);
        flash.setY(positY);
        flash.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        flash.animate().setDuration(500).rotation(Math.random() > 0.5 ? 30 : -30).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(flash);
                    }
                });
        mEffect_board.addView(flash);

        //Add tile fragment
        ImageView fruit_frag1 = new ImageView(mActivity);
        ImageView fruit_frag2 = new ImageView(mActivity);

        //Set image base on kind
        if (tile.special) {
            if (tile.direct == 'H') {
                if (tile.kind == R.drawable.coconut) {
                    fruit_frag1.setImageResource(R.drawable.speci_h_coconut_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_h_coconut_frag2);
                } else if (tile.kind == R.drawable.banana) {
                    fruit_frag1.setImageResource(R.drawable.speci_h_banana_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_h_banana_frag2);
                } else if (tile.kind == R.drawable.cherry) {
                    fruit_frag1.setImageResource(R.drawable.speci_h_cherry_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_h_cherry_frag2);
                } else if (tile.kind == R.drawable.lemon) {
                    fruit_frag1.setImageResource(R.drawable.speci_h_lemon_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_h_lemon_frag2);
                } else {
                    fruit_frag1.setImageResource(R.drawable.speci_h_strawberry_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_h_strawberry_frag2);
                }
            } else if (tile.direct == 'V') {
                if (tile.kind == R.drawable.coconut) {
                    fruit_frag1.setImageResource(R.drawable.speci_v_coconut_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_v_coconut_frag2);
                } else if (tile.kind == R.drawable.banana) {
                    fruit_frag1.setImageResource(R.drawable.speci_v_banana_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_v_banana_frag2);
                } else if (tile.kind == R.drawable.cherry) {
                    fruit_frag1.setImageResource(R.drawable.speci_v_cherry_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_v_cherry_frag2);
                } else if (tile.kind == R.drawable.lemon) {
                    fruit_frag1.setImageResource(R.drawable.speci_v_lemon_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_v_lemon_frag2);
                } else {
                    fruit_frag1.setImageResource(R.drawable.speci_v_strawberry_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_v_strawberry_frag2);
                }
            } else if (tile.direct == 'S') {
                if (tile.kind == R.drawable.coconut) {
                    fruit_frag1.setImageResource(R.drawable.speci_s_coconut_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_s_coconut_frag2);
                } else if (tile.kind == R.drawable.banana) {
                    fruit_frag1.setImageResource(R.drawable.speci_s_banana_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_s_banana_frag2);
                } else if (tile.kind == R.drawable.cherry) {
                    fruit_frag1.setImageResource(R.drawable.speci_s_cherry_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_s_cherry_frag2);
                } else if (tile.kind == R.drawable.lemon) {
                    fruit_frag1.setImageResource(R.drawable.speci_s_lemon_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_s_lemon_frag2);
                } else {
                    fruit_frag1.setImageResource(R.drawable.speci_s_strawberry_frag1);
                    fruit_frag2.setImageResource(R.drawable.speci_s_strawberry_frag2);
                }
            }

        } else {
            if (tile.kind == R.drawable.coconut) {
                fruit_frag1.setImageResource(R.drawable.coconut_frag1);
                fruit_frag2.setImageResource(R.drawable.coconut_frag2);
            } else if (tile.kind == R.drawable.banana) {
                fruit_frag1.setImageResource(R.drawable.banana_frag1);
                fruit_frag2.setImageResource(R.drawable.banana_frag2);
            } else if (tile.kind == R.drawable.cherry) {
                fruit_frag1.setImageResource(R.drawable.cherry_frag1);
                fruit_frag2.setImageResource(R.drawable.cherry_frag2);
            } else if (tile.kind == R.drawable.lemon) {
                fruit_frag1.setImageResource(R.drawable.lemon_frag1);
                fruit_frag2.setImageResource(R.drawable.lemon_frag2);
            } else {
                fruit_frag1.setImageResource(R.drawable.strawberry_frag1);
                fruit_frag2.setImageResource(R.drawable.strawberry_frag2);
            }
        }

        //Set location
        fruit_frag1.setX(positX);
        fruit_frag1.setY(positY);
        fruit_frag2.setX(positX);
        fruit_frag2.setY(positY);

        //Set size
        fruit_frag1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        fruit_frag2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));

        //Add view
        mEffect_board.addView(fruit_frag1);
        mEffect_board.addView(fruit_frag2);

        //Add animation
        fruit_frag1.animate().setDuration(500).x((float) (positX - mTileSize / 4)).y((float) (positY + mTileSize / 4))
                .rotation(-45).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(fruit_frag1);

            }
        });
        fruit_frag2.animate().setDuration(500).x((float) (positX + mTileSize / 4)).y((float) (positY + mTileSize / 4))
                .rotation(-45).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(fruit_frag2);

            }
        });
    }

    //This create not special tile explode effect
    private void explodeNotSpecialFruit(Tile tile) {
        //Add sparkler
        createSparkler(tile, 4, 0.7f, 0.2f, 750);
        //Add flash
        ImageView flash = new ImageView(mActivity);
        flash.setImageResource(R.drawable.flash_s_small);
        flash.setX(tile.x - mTileSize);
        flash.setY(tile.y - mTileSize);
        flash.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        flash.animate().setDuration(500).rotation(Math.random() > 0.5 ? 30 : -30).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(flash);
                    }
                });
        mEffect_board.addView(flash);

        //Add tile fragment
        ImageView fruit_frag1 = new ImageView(mActivity);
        ImageView fruit_frag2 = new ImageView(mActivity);

        //Set image base on kind
        if (tile.kind == R.drawable.coconut) {
            fruit_frag1.setImageResource(R.drawable.coconut_frag1);
            fruit_frag2.setImageResource(R.drawable.coconut_frag2);
        } else if (tile.kind == R.drawable.banana) {
            fruit_frag1.setImageResource(R.drawable.banana_frag1);
            fruit_frag2.setImageResource(R.drawable.banana_frag2);
        } else if (tile.kind == R.drawable.cherry) {
            fruit_frag1.setImageResource(R.drawable.cherry_frag1);
            fruit_frag2.setImageResource(R.drawable.cherry_frag2);
        } else if (tile.kind == R.drawable.lemon) {
            fruit_frag1.setImageResource(R.drawable.lemon_frag1);
            fruit_frag2.setImageResource(R.drawable.lemon_frag2);
        } else {
            fruit_frag1.setImageResource(R.drawable.strawberry_frag1);
            fruit_frag2.setImageResource(R.drawable.strawberry_frag2);
        }

        //Set location
        fruit_frag1.setX(tile.x - mTileSize);
        fruit_frag1.setY(tile.y - mTileSize);
        fruit_frag2.setX(tile.x - mTileSize);
        fruit_frag2.setY(tile.y - mTileSize);

        //Set size
        fruit_frag1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        fruit_frag2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));

        //Add view
        mEffect_board.addView(fruit_frag1);
        mEffect_board.addView(fruit_frag2);

        //Add animation
        fruit_frag1.animate().setDuration(500).x((float) (tile.x - mTileSize - mTileSize / 4)).y((float) (tile.y - mTileSize + mTileSize / 4))
                .rotation(-45).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(fruit_frag1);

            }
        });
        fruit_frag2.animate().setDuration(500).x((float) (tile.x - mTileSize + mTileSize / 4)).y((float) (tile.y - mTileSize + mTileSize / 4))
                .rotation(-45).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(fruit_frag2);

            }
        });
    }

    public void explodeCracker(Tile tile) {
        //Add explode
        createExplodeBackground(tile, 1);
        //Add sparkler
        createSparkler(tile, 4, 0.7f, 0.2f, 750);
        //Add frag
        ImageView frag1 = new ImageView(mActivity);
        frag1.setImageResource(R.drawable.cracker_frag1);
        ImageView frag2 = new ImageView(mActivity);
        frag2.setImageResource(R.drawable.cracker_frag2);
        ImageView frag3 = new ImageView(mActivity);
        frag3.setImageResource(R.drawable.cracker_frag3);
        ImageView frag4 = new ImageView(mActivity);
        frag4.setImageResource(R.drawable.cracker_frag4);
        ImageView frag5 = new ImageView(mActivity);
        frag5.setImageResource(R.drawable.cracker_frag5);
        ImageView frag6 = new ImageView(mActivity);
        frag6.setImageResource(R.drawable.cracker_frag6);
        //Set location
        frag1.setX(tile.x - mTileSize);
        frag1.setY(tile.y - mTileSize);
        frag2.setX(tile.x - mTileSize);
        frag2.setY(tile.y - mTileSize);
        frag3.setX(tile.x - mTileSize);
        frag3.setY(tile.y - mTileSize);
        frag4.setX(tile.x - mTileSize);
        frag4.setY(tile.y - mTileSize);
        frag5.setX(tile.x - mTileSize);
        frag5.setY(tile.y - mTileSize);
        frag6.setX(tile.x - mTileSize);
        frag6.setY(tile.y - mTileSize);
        //Set size
        frag1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag5.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag6.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        //Add view
        mEffect_board.addView(frag1);
        mEffect_board.addView(frag2);
        mEffect_board.addView(frag3);
        mEffect_board.addView(frag4);
        mEffect_board.addView(frag5);
        mEffect_board.addView(frag6);
        //Add animation
        frag1.animate().setDuration(200 + (int) (Math.random() * 4) * 100).x((float) (tile.x - mTileSize - mTileSize / 3)).y((float) (tile.y - mTileSize - mTileSize / 3))
                .rotation(Math.random() > 0.5 ? -45 : 45).scaleX(0.7f).scaleY(0.7f).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(frag1);
            }
        });
        frag2.animate().setDuration(200 + (int) (Math.random() * 4) * 100).y((float) (tile.y - mTileSize - mTileSize / 3))
                .rotation(Math.random() > 0.5 ? -30 : 30).scaleX(0.7f).scaleY(0.7f).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(frag2);
            }
        });
        frag3.animate().setDuration(200 + (int) (Math.random() * 4) * 100).x((float) (tile.x - mTileSize + mTileSize / 3)).y((float) (tile.y - mTileSize - mTileSize / 3))
                .rotation(Math.random() > 0.5 ? -45 : 45).scaleX(0.7f).scaleY(0.7f).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(frag3);
            }
        });
        frag4.animate().setDuration(200 + (int) (Math.random() * 4) * 100).x((float) (tile.x - mTileSize - mTileSize / 3)).y((float) (tile.y - mTileSize + mTileSize / 3))
                .rotation(Math.random() > 0.5 ? -45 : 45).scaleX(0.7f).scaleY(0.7f).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(frag4);
            }
        });
        frag5.animate().setDuration(200 + (int) (Math.random() * 4) * 100).y((float) (tile.y - mTileSize + mTileSize / 3))
                .rotation(Math.random() > 0.5 ? -30 : 30).scaleX(0.7f).scaleY(0.7f).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(frag5);
            }
        });
        frag6.animate().setDuration(200 + (int) (Math.random() * 4) * 100).x((float) (tile.x - mTileSize + mTileSize / 3)).y((float) (tile.y - mTileSize + mTileSize / 3))
                .rotation(Math.random() > 0.5 ? -45 : 45).scaleX(0.7f).scaleY(0.7f).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(frag6);
            }
        });
    }

    public void explodeCookie(Tile tile) {
        int positX = tile.x - mTileSize;
        int positY = tile.y - mTileSize;

        //Add frag
        ImageView frag1 = new ImageView(mActivity);
        frag1.setImageResource(R.drawable.cookie_frag1);
        ImageView frag2 = new ImageView(mActivity);
        frag2.setImageResource(R.drawable.cookie_frag2);
        ImageView frag3 = new ImageView(mActivity);
        frag3.setImageResource(R.drawable.cookie_frag3);
        ImageView frag4 = new ImageView(mActivity);
        frag4.setImageResource(R.drawable.cookie_frag4);
        ImageView frag5 = new ImageView(mActivity);
        frag5.setImageResource(R.drawable.cookie_frag5);
        ImageView frag6 = new ImageView(mActivity);
        frag6.setImageResource(R.drawable.cookie_frag6);
        //Set location
        frag1.setX(positX);
        frag1.setY(positY);
        frag2.setX(positX);
        frag2.setY(positY);
        frag3.setX(positX);
        frag3.setY(positY);
        frag4.setX(positX);
        frag4.setY(positY);
        frag5.setX(positX);
        frag5.setY(positY);
        frag6.setX(positX);
        frag6.setY(positY);
        //Set size
        frag1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag5.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag6.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        //Add view
        mEffect_board.addView(frag1);
        mEffect_board.addView(frag2);
        mEffect_board.addView(frag3);
        mEffect_board.addView(frag4);
        mEffect_board.addView(frag5);
        mEffect_board.addView(frag6);
        //Add animation
        frag1.animate().setDuration(200).rotation(-90).x((float) (positX - mTileSize / 4)).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag1.animate().setDuration(350 + (int) (Math.random() * 3) * 100).x((float) (positX - mTileSize / 2)).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag1);
                    }
                });
            }
        });
        frag2.animate().setDuration(200).rotation(-45).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag2.animate().setDuration(350 + (int) (Math.random() * 3) * 100).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag2);
                    }
                });
            }
        });
        frag3.animate().setDuration(200).rotation(90).x((float) (positX + mTileSize / 4)).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag3.animate().setDuration(350 + (int) (Math.random() * 3) * 100).x((float) (positX + mTileSize / 2)).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag3);
                    }
                });
            }
        });
        frag4.animate().setDuration(200).rotation(-60).x((float) (positX - mTileSize / 4)).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag4.animate().setDuration(350 + (int) (Math.random() * 3) * 100).x((float) (positX - mTileSize / 2)).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag4);
                    }
                });
            }
        });
        frag5.animate().setDuration(200).rotation(-30).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag5.animate().setDuration(350 + (int) (Math.random() * 3) * 100).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag5);
                    }
                });
            }
        });
        frag6.animate().setDuration(200).rotation(60).x((float) (positX + mTileSize / 4)).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag6.animate().setDuration(350 + (int) (Math.random() * 3) * 100).x((float) (positX + mTileSize / 2)).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag6);
                    }
                });
            }
        });

    }

    public void explodeCookieLayer(Tile tile) {
        //Smoke effect
        createSmoke(tile);
    }

    public void explodePie(Tile tile) {
        // Pie cup effect
        int positX = tile.x - mTileSize;
        int positY = tile.y - mTileSize;
        ImageView cup = new ImageView(mActivity);
        cup.setBackgroundResource(R.drawable.pie_cup);
        //Set location
        cup.setX(positX);
        cup.setY(positY);
        //Set size
        cup.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 2, mTileSize * 2));
        //Add view
        mEffect_board.addView(cup);
        cup.animate().setDuration(400).scaleX(0).scaleY(0).alpha(0.5f).setInterpolator(anticipateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(cup);
            }
        });
    }

    public void explodePieLayer(Tile tile) {
        //Smoke effect
        int positX = tile.x - mTileSize;
        int positY = tile.y - mTileSize;
        //Smoke effect
        ImageView smoke = new ImageView(mActivity);
        smoke.setBackgroundResource(R.drawable.smoke_animation);
        AnimationDrawable anim = (AnimationDrawable) smoke.getBackground();
        //Set location
        smoke.setX(positX - mTileSize);
        smoke.setY(positY - mTileSize);
        //Set size
        smoke.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 4, mTileSize * 4));
        //Add view
        mEffect_board.addView(smoke);
        smoke.animate().setDuration(400).alpha(0.5f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(smoke);
            }
        });
        anim.start();
    }

    public void explodeLock(ImageView lock, Tile tile) {
        lock.animate().setDuration(100).scaleX(0).scaleY(0);

        int positX = tile.x - mTileSize;
        int positY = tile.y - mTileSize;

        //Add frag
        ImageView frag1 = new ImageView(mActivity);
        frag1.setImageResource(R.drawable.lock_frag1);
        ImageView frag2 = new ImageView(mActivity);
        frag2.setImageResource(R.drawable.lock_frag2);
        ImageView frag3 = new ImageView(mActivity);
        frag3.setImageResource(R.drawable.lock_frag3);
        ImageView frag4 = new ImageView(mActivity);
        frag4.setImageResource(R.drawable.lock_frag4);
        ImageView frag5 = new ImageView(mActivity);
        frag5.setImageResource(R.drawable.lock_frag5);
        //Set location
        frag1.setX(positX);
        frag1.setY(positY);
        frag2.setX(positX);
        frag2.setY(positY);
        frag3.setX(positX);
        frag3.setY(positY);
        frag4.setX(positX);
        frag4.setY(positY);
        frag5.setX(positX);
        frag5.setY(positY);
        //Set size
        frag1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        frag5.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        //Add view
        mEffect_board.addView(frag1);
        mEffect_board.addView(frag2);
        mEffect_board.addView(frag3);
        mEffect_board.addView(frag4);
        mEffect_board.addView(frag5);
        //Add animation
        frag1.animate().setDuration(200).rotation(-90).x((float) (positX - mTileSize / 4)).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag1.animate().setDuration(350 + (int) (Math.random() * 3) * 100).x((float) (positX - mTileSize / 2)).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag1);
                    }
                });
            }
        });
        frag2.animate().setDuration(200).rotation(-45).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag2.animate().setDuration(350 + (int) (Math.random() * 3) * 100).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag2);
                    }
                });
            }
        });
        frag3.animate().setDuration(200).rotation(90).x((float) (positX + mTileSize / 4)).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag3.animate().setDuration(350 + (int) (Math.random() * 3) * 100).x((float) (positX + mTileSize / 2)).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag3);
                    }
                });
            }
        });
        frag4.animate().setDuration(200).rotation(-60).x((float) (positX - mTileSize / 4)).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag4.animate().setDuration(350 + (int) (Math.random() * 3) * 100).x((float) (positX - mTileSize / 2)).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag4);
                    }
                });
            }
        });
        frag5.animate().setDuration(200).rotation(-30).y((float) (positY - mTileSize * (0.8 + Math.random() * 0.6)))
                .setInterpolator(decelerateInterpolator).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                frag5.animate().setDuration(350 + (int) (Math.random() * 3) * 100).y((float) (positY + mTileSize * 3))
                        .scaleX(0.7f).scaleY(0.7f).alpha(0).setInterpolator(accelerateInterpolator).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mEffect_board.removeView(frag5);
                    }
                });
            }
        });

    }

    public void explodeHoney(ImageView honey, Tile tile) {
        honey.animate().setDuration(300).scaleX(0).scaleY(0).setInterpolator(anticipateInterpolator);

        float positX = tile.x - mTileSize;
        float positY = tile.y - mTileSize;

        //Add honey frag
        //Top right
        ImageView honey_frag1 = new ImageView(mActivity);
        honey_frag1.setImageResource(R.drawable.honey_frag);
        honey_frag1.setX(positX + mTileSize / 2);
        honey_frag1.setY(positY + mTileSize / 4);
        honey_frag1.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 4, mTileSize / 4));
        honey_frag1.setRotation(45);
        mEffect_board.addView(honey_frag1);
        honey_frag1.animate().setDuration(300).x(positX + (float) mTileSize).y(positY - (float) mTileSize * 0.3f)
                .scaleX(3).scaleY(3).alpha(0.2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(honey_frag1);
            }
        });


        //Bottom right
        ImageView honey_frag2 = new ImageView(mActivity);
        honey_frag2.setImageResource(R.drawable.honey_frag);
        honey_frag2.setX(positX + mTileSize / 2);
        honey_frag2.setY(positY + mTileSize / 2);
        honey_frag2.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 4, mTileSize / 4));
        honey_frag2.setRotation(120);
        mEffect_board.addView(honey_frag2);
        honey_frag2.animate().setDuration(200).x(positX + (float) mTileSize).y(positY + (float) mTileSize * 0.6f)
                .scaleX(2.5f).scaleY(2.5f).alpha(0.2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(honey_frag2);
            }
        });

        //Bottom left
        ImageView honey_frag3 = new ImageView(mActivity);
        honey_frag3.setImageResource(R.drawable.honey_frag);
        honey_frag3.setX(positX + mTileSize / 4);
        honey_frag3.setY(positY + mTileSize / 2);
        honey_frag3.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 4, mTileSize / 4));
        honey_frag3.setRotation(200);
        mEffect_board.addView(honey_frag3);
        honey_frag3.animate().setDuration(250).x(positX - (float) mTileSize * 0.2f).y(positY + (float) mTileSize)
                .scaleX(1.5f).scaleY(1.5f).alpha(0.2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(honey_frag3);
            }
        });

        //Top left
        ImageView honey_frag4 = new ImageView(mActivity);
        honey_frag4.setImageResource(R.drawable.honey_frag);
        honey_frag4.setX(positX + mTileSize / 4);
        honey_frag4.setY(positY + mTileSize / 4);
        honey_frag4.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 4, mTileSize / 4));
        honey_frag4.setRotation(340);
        mEffect_board.addView(honey_frag4);
        honey_frag4.animate().setDuration(300).x(positX + (float) mTileSize * 0.2f).y(positY - (float) mTileSize * 0.5f)
                .scaleX(2).scaleY(2).alpha(0.2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(honey_frag4);
            }
        });

        //Bottom left big
        ImageView honey_frag5 = new ImageView(mActivity);
        honey_frag5.setImageResource(R.drawable.honey_frag);
        honey_frag5.setX(positX + mTileSize / 4);
        honey_frag5.setY(positY + mTileSize / 2);
        honey_frag5.setLayoutParams(new ViewGroup.LayoutParams(mTileSize / 4, mTileSize / 4));
        honey_frag5.setRotation(250);
        mEffect_board.addView(honey_frag5);
        honey_frag5.animate().setDuration(400).x(positX - mTileSize * 0.3f).y(positY + mTileSize * 0.6f)
                .scaleX(3.5f).scaleY(3.5f).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(honey_frag5);
            }
        });

    }

    public void explodeStarFish(Tile tile) {
        //Add sparkler
        createSparkler(tile, 4, 0.7f, 0.2f, 750);
        //Add ice cream
        ImageView starfish = new ImageView(mActivity);
        starfish.setImageResource(R.drawable.starfish);
        starfish.setX(tile.x - mTileSize);
        starfish.setY(tile.y - mTileSize);
        starfish.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        starfish.animate().setDuration(500).scaleX(0).scaleY(0).y(tile.y);
        mEffect_board.addView(starfish);
    }

    //This create lightning
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createLightning(Tile ice_cream, Tile target) {
        //Add image
        ImageView lightning = new ImageView(mActivity);
        lightning.setBackgroundResource(R.drawable.lightning_clip);

        //Set angle
        int w = ice_cream.x - target.x;
        int h = ice_cream.y - target.y;
        double edge = Math.pow((w * w + h * h), 0.5);
        double angle = Utils.angle(Math.abs(h), Math.abs(w), edge);
        lightning.setPivotX((float) (mTileSize / 2));
        lightning.setPivotY((float) edge);
        if (w < 0 && h > 0) {
            //First quadrant
            lightning.setRotation((float) (90 - angle));
        } else if (w < 0 && h < 0) {
            //Fourth quadrant
            lightning.setRotation((float) (angle + 90));
        } else if (w > 0 && h < 0) {
            //Third quadrant
            lightning.setRotation((float) (270 - angle));
        } else if (w > 0 && h > 0) {
            //Second quadrant
            lightning.setRotation((float) (270 + angle));
        } else if (w == 0 && h > 0) {
            //Top
            lightning.setRotation(0);
        } else if (w == 0 && h < 0) {
            //Down
            lightning.setRotation(180);
        } else if (w < 0 && h == 0) {
            //Right
            lightning.setRotation(90);
        } else if (w > 0 && h == 0) {
            //Left
            lightning.setRotation(270);
        }

        //Set location
        lightning.setX(ice_cream.x - mTileSize);
        lightning.setY((float) (ice_cream.y - mTileSize + mTileSize / 2 - edge));
        //Set size
        lightning.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, (int) edge));

        //Add view
        mEffect_board.addView(lightning);

        ClipDrawable clip = (ClipDrawable) lightning.getBackground();
        AnimationDrawable anim = (AnimationDrawable) clip.getDrawable();
        anim.start();

        clip.setLevel(0);
        final int[] level = {0};
        //Set thread
        new Runnable() {
            @Override
            public void run() {
                level[0] += 800;
                clip.setLevel(level[0]);
                if (level[0] < 10000) {
                    mHandler.postDelayed(this, 10);
                } else {
                    lightning.animate().setDuration(500).alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mEffect_board.removeView(lightning);
                        }
                    });
                    mHandler.removeCallbacks(this);
                }
            }
        }.run();
    }

    //This create explosion when tile hit by lightning
    public void createLightning_fruit(Tile tile, boolean isExplode) {
        //Add image
        ImageView flash = new ImageView(mActivity);
        flash.setBackgroundResource(R.drawable.flash_s_small_blue_clip);
        flash.setX(tile.x - mTileSize - (int) (mTileSize / 3));
        flash.setY(tile.y - mTileSize - (int) (mTileSize / 3));
        flash.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 5 / 3, mTileSize * 5 / 3));
        mEffect_board.addView(flash);

        //Set clip
        ClipDrawable clip = (ClipDrawable) flash.getBackground();
        clip.setLevel(0);

        final int[] level = {0};
        //Set thread
        new Runnable() {
            @Override
            public void run() {
                level[0] += 800;
                clip.setLevel(level[0]);
                if (level[0] < 10000) {
                    mHandler.postDelayed(this, 10);
                } else {
                    int angle = Math.random() > 0.5 ? 30 : -30;
                    flash.animate().setDuration(100).rotation(angle).scaleX(0.8f).scaleY(0.8f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            flash.animate().setDuration(100).rotation(angle).scaleX(1).scaleY(1).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    flash.animate().setDuration(300).rotation(angle).scaleX(0).scaleY(0).alpha(0).setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            mEffect_board.removeView(flash);
                                            if (isExplode) {
                                                tile.match++;
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                    mHandler.removeCallbacks(this);
                }
            }
        }.run();
    }

    public void createExplodeWave(Tile tile) {
        //Add image
        ImageView flash = new ImageView(mActivity);
        flash.setBackgroundResource(R.drawable.flash_wave);
        flash.setX(tile.x - mTileSize);
        flash.setY(tile.y - mTileSize);
        flash.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        mEffect_board.addView(flash);
        flash.animate().setDuration(500).scaleY(30).scaleX(30).alpha(0.3f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash);
            }
        });
    }

    public void createExplodeWave_small(Tile tile) {
        ImageView wave = new ImageView(mActivity);
        wave.setBackgroundResource(R.drawable.flash_wave_small);
        wave.setX(tile.x - mTileSize);
        wave.setY(tile.y - mTileSize);
        wave.setLayoutParams(new ViewGroup.LayoutParams(mTileSize, mTileSize));
        mEffect_board.addView(wave);
        wave.animate().setDuration(500).scaleY(10).scaleX(10).alpha(0.1f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(wave);
            }
        });
    }

    //This create explode sparkler
    private void createSparkler(Tile tile, int number, float range, float scale, int time) {
        /* number able to convert to sqrt root
         * size must be 0 < size < 1
         * (range + 2 / 3) * Math.random()
         */
        int positX = tile.x;
        int positY = tile.y;
        int sqrt = (int) Math.sqrt(number);
        for (int i = 0; i < sqrt; i++) {
            for (int j = 0; j < sqrt; j++) {
                ImageView sparkler = new ImageView(mActivity);
                sparkler.setImageResource(R.drawable.flash_s_small);
                sparkler.setX(positX + (int) (mTileSize / 4));
                sparkler.setY(positY + (int) (mTileSize / 4));
                sparkler.setLayoutParams(new ViewGroup.LayoutParams((int) (mTileSize / 2), (int) (mTileSize / 2)));
                mEffect_board.addView(sparkler);
                //Set animation
                sparkler.animate().setDuration(time).alpha(0).rotation(Math.random() > 0.5 ? 60 : -60).scaleX(scale).scaleY(scale)
                        .x(j < sqrt / 2 ? (float) (positX + mTileSize / 4 - mTileSize * range * Math.random())
                                : (float) (positX + mTileSize / 4 + mTileSize * range * Math.random()))
                        .y(i < sqrt / 2 ? (float) (positY + mTileSize / 4 - mTileSize * range * Math.random())
                                : (float) (positY + mTileSize / 4 + mTileSize * range * Math.random()))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mEffect_board.removeView(sparkler);
                            }
                        });
            }
        }
    }

    //This create explode background
    private void createExplodeBackground(Tile tile, double size) {
        /*
         * size has to be odd
         */
        int positX = tile.x;
        int positY = tile.y;

        ImageView flash = new ImageView(mActivity);
        flash.setBackgroundResource(R.drawable.flash_s_circle);
        flash.setX((float) (positX - mTileSize * (size / 2 - 0.5)));
        flash.setY((float) (positY - mTileSize * (size / 2 - 0.5)));
        flash.setLayoutParams(new ViewGroup.LayoutParams((int) (mTileSize * size), (int) (mTileSize * size)));
        mEffect_board.addView(flash);
        flash.animate().setDuration(750).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(flash);
            }
        });
    }

    private void createSmoke(Tile tile) {
        int positX = tile.x - mTileSize;
        int positY = tile.y - mTileSize;
        //Smoke effect
        ImageView smoke = new ImageView(mActivity);
        smoke.setBackgroundResource(R.drawable.smoke_animation);
        AnimationDrawable anim = (AnimationDrawable) smoke.getBackground();
        //Set location
        smoke.setX(positX - mTileSize * 0.5f);
        smoke.setY(positY - mTileSize * 0.5f);
        //Set size
        smoke.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 2, mTileSize * 2));
        //Add view
        mEffect_board.addView(smoke);
        smoke.animate().setDuration(400).alpha(0.5f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mEffect_board.removeView(smoke);
            }
        });
        anim.start();
    }

    public void explodeIce(ImageView view, int partLen) {
        explosion.explode(view, partLen);
    }

    public void createLightBounceAnim(ImageView tile) {
        tile.setPivotY(tile.getMeasuredHeight());
        tile.animate().scaleY(0.9f).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tile.animate().scaleY(1).setDuration(200);
            }
        });
    }

    public void createHeavyBounceAnim(ImageView tile) {
        tile.setPivotY(tile.getMeasuredHeight());
        tile.animate().scaleY(0.8f).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tile.animate().scaleY(1).setDuration(200).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tile.animate().scaleY(0.9f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                tile.animate().scaleY(1).setDuration(200);
                            }
                        });
                    }
                });
            }
        });
    }

    public void createRefreshAnim(ImageView tile) {
        tile.setPivotY((int) (tile.getMeasuredHeight() / 2));
        tile.setPivotX((int) (tile.getMeasuredWidth() / 2));
        tile.animate().scaleX(0).scaleY(0).alpha(0).setDuration(750).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tile.animate().scaleX(1).scaleY(1).alpha(1).setStartDelay(200).setDuration((long) (500 * Math.random() + 200))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                tile.animate().setStartDelay(0);
                            }
                        });
            }
        });
    }

    public void createTransformAnim(Tile tile) {
        //Add flash
        ImageView flash = new ImageView(mActivity);
        flash.setImageResource(R.drawable.flash_s_small_lightning_clip);
        flash.setX(tile.col * mTileSize - mTileSize - mTileSize);
        flash.setY(tile.row * mTileSize - mTileSize - mTileSize);
        flash.setLayoutParams(new ViewGroup.LayoutParams(mTileSize * 3, mTileSize * 3));
        flash.animate().setDuration(500).rotation(Math.random() > 0.5 ? 60 : -60);
        mEffect_board.addView(flash);

        //Set clip
        ClipDrawable clip = (ClipDrawable) flash.getDrawable();
        clip.setLevel(0);

        final int[] level = {0};
        //Set thread
        new Runnable() {
            @Override
            public void run() {
                level[0] += 750;
                clip.setLevel(level[0]);
                if (level[0] < 10000) {
                    mHandler.postDelayed(this, 10);
                } else {
                    flash.animate().setDuration(400).scaleX(0).scaleY(0).alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mEffect_board.removeView(flash);
                                }
                            });
                    mHandler.removeCallbacks(this);
                }
            }
        }.run();
    }

    public void createShakingAnim(View board) {
        board.startAnimation(shaking_anim);
    }

    public void createShakingAnim_small(View board) {
        board.startAnimation(shaking_small_anim);
    }

    public void createTextAnim(View view) {
        view.animate().cancel();
        view.animate().scaleX(1.5f).scaleY(1.5f).setDuration(100).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().scaleX(1).scaleY(1).setDuration(100);
            }
        });
    }

    public void createInAnim(View board) {
        board.animate().setDuration(600).x(0).setInterpolator(overshootInterpolator);
    }

    public void createOutAnim(View board) {
        board.animate().setDuration(600).x(board.getWidth()).setInterpolator(anticipateInterpolator);
    }

    public void createStarAnim(RelativeLayout star) {
        int view_width = star.getWidth();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ImageView sparkler = new ImageView(mActivity);
                sparkler.setImageResource(R.drawable.flash_s_small);
                sparkler.setX((int) (view_width * 4 / 9));
                sparkler.setY((int) (view_width * 4 / 9));
                sparkler.setLayoutParams(new ViewGroup.LayoutParams((int) (view_width / 9), (int) (view_width / 9)));
                star.addView(sparkler);
                //Set animation
                sparkler.animate().setDuration((long) (250 * Math.random() + 250)).alpha(0).rotation(Math.random() > 0.5 ? 180 : -180).scaleX(10).scaleY(10)
                        .x(j < 2 ? (float) ((int) (view_width * 4 / 9) - view_width * 3 * Math.random())
                                : (float) ((int) (view_width * 4 / 9) + view_width * 3 * Math.random()))
                        .y(i < 2 ? (float) ((int) (view_width * 4 / 9) - view_width * 3 * Math.random())
                                : (float) ((int) (view_width * 4 / 9) + view_width * 3 * Math.random()))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                star.removeView(sparkler);
                            }
                        });
            }
        }
    }

    public void createBtnSkipAnim(View view) {
        view.animate().setStartDelay(300).setDuration(400).scaleX(2).scaleY(2).alpha(1).setInterpolator(overshootInterpolator);
    }

    public void createMachineAnim(ImageView view) {
        view.startAnimation(machine_anim);
    }

    public void clear() {
        explosion.clear();
    }

}

