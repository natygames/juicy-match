package com.nativegame.match3game.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativegame.match3game.R;
import com.nativegame.match3game.Utils;
import com.nativegame.match3game.database.DatabaseHelper;
import com.nativegame.match3game.effect.sound.SoundEvent;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class WinDialogFragment extends BaseFragment {

    private static final String LEVEL = "LEVEL";
    private static final String SCORE = "SCORE";
    private static final String STAR = "STAR";

    private int mLevel;
    private int mScore;
    private int mStar;

    private DatabaseHelper mDatabaseHelper;

    private ConstraintLayout mDialog;
    private ImageView mStar1, mStar2, mStar3;
    private static final int EXPLODE_TIME = 250;
    private final OvershootInterpolator mInterpolator = new OvershootInterpolator();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public WinDialogFragment() {
        // Required empty public constructor
    }

    public static WinDialogFragment newInstance(int level, int score, int star) {
        WinDialogFragment fragment = new WinDialogFragment();
        Bundle args = new Bundle();
        args.putInt(LEVEL, level);
        args.putInt(SCORE, score);
        args.putInt(STAR, star);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLevel = getArguments().getInt(LEVEL);
            mScore = getArguments().getInt(SCORE);
            mStar = getArguments().getInt(STAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_win_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init view
        TextView textView = (TextView) getView().findViewById(R.id.txt_win_level);
        textView.setText(getResources().getString(R.string.txt_level, mLevel));
        mDialog = (ConstraintLayout) getView().findViewById(R.id.dialog_win);
        mStar1 = (ImageView) getView().findViewById(R.id.image_star1);
        mStar2 = (ImageView) getView().findViewById(R.id.image_star2);
        mStar3 = (ImageView) getView().findViewById(R.id.image_star3);

        // Load star data
        mDatabaseHelper = new DatabaseHelper(getMainActivity());
        insertOrUpdateStar();

        init();

    }

    private void init() {
        // Init button
        ImageButton btnNext = (ImageButton) getView().findViewById(R.id.btn_win_next);
        Utils.createButtonEffect(btnNext);
        btnNext.setScaleX(0);
        btnNext.setScaleY(0);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.BUTTON_CLICK);
                getMainActivity().navigateToFragment(new MapFragment());
            }
        });
        btnNext.animate().setStartDelay(800).setDuration(500)
                .scaleX(1).scaleY(1).setInterpolator(mInterpolator);

        startAnimation();
    }

    private void insertOrUpdateStar() {
        int oldStar = mDatabaseHelper.getLevelStar(mLevel);

        if (oldStar == -1) {
            // If data doesn't exist, we add one
            mDatabaseHelper.insertLevelStar(mStar);
        } else {
            // If data exist and new star is bigger, we update
            if (mStar > oldStar) {
                mDatabaseHelper.updateLevelStar(mLevel, mStar);
            }
        }

    }

    private void startAnimation() {
        // Dialog animation
        Animation inAnim = AnimationUtils.loadAnimation(getMainActivity(), R.anim.enter_from_left);
        mDialog.startAnimation(inAnim);

        // Run score
        TextView mScoreText = (TextView) getView().findViewById(R.id.txt_win_score);
        ValueAnimator animator = ValueAnimator.ofFloat(mScore - 150, mScore);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mScoreText.setText(String.valueOf((int) value));
            }
        });
        animator.start();

        // Play score calculate sound
        getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.SCORE_COUNT);

        // Star animation
        createStarAnim(mStar);
    }

    private void createStarAnim(int star) {
        if (star >= 1) {
            // Init value
            mStar1.setScaleX(0.25f);
            mStar1.setScaleY(0.25f);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mStar1.animate().setDuration(300).scaleX(2).scaleY(2).alpha(1)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mStar1.animate().setDuration(100).scaleX(1).scaleY(1)
                                            .setInterpolator(mInterpolator);
                                }
                            });
                    createExplode(mStar1);
                    getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.SCORE_GET_STAR);
                }
            }, 700);
        }
        if (star >= 2) {
            // Init value
            mStar2.setScaleX(0.25f);
            mStar2.setScaleY(0.25f);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mStar2.animate().setDuration(300).scaleX(2).scaleY(2).alpha(1)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mStar2.animate().setDuration(100).scaleX(1).scaleY(1)
                                            .setInterpolator(mInterpolator);
                                }
                            });
                    createExplode(mStar2);
                    getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.SCORE_GET_STAR);
                }
            }, 1000);
        }
        if (star >= 3) {
            // Init value
            mStar3.setScaleX(0.25f);
            mStar3.setScaleY(0.25f);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mStar3.animate().setDuration(300).scaleX(2).scaleY(2).alpha(1)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mStar3.animate().setDuration(100).scaleX(1).scaleY(1)
                                            .setInterpolator(mInterpolator);
                                }
                            });
                    createExplode(mStar3);
                    getMainActivity().getSoundManager().playSoundForSoundEvent(SoundEvent.SCORE_GET_STAR);
                }
            }, 1300);
        }
    }

    private void createExplode(ImageView view) {
        int viewX = (int) view.getX();
        int viewY = (int) view.getY();
        int view_width = view.getWidth();

        //Add flash bar
        //Top right
        ImageView flash_bar1 = new ImageView(getMainActivity());
        flash_bar1.setImageResource(R.drawable.flash_bar);
        flash_bar1.setX(viewX + view_width * 0.5f);
        flash_bar1.setY(viewY + view_width * 0.25f);
        flash_bar1.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar1.setRotation(45);
        flash_bar1.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.5f + view_width).y(viewY + view_width * 0.25f - view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar1);
                    }
                });
        mDialog.addView(flash_bar1);

        //Bottom right
        ImageView flash_bar2 = new ImageView(getMainActivity());
        flash_bar2.setImageResource(R.drawable.flash_bar);
        flash_bar2.setX(viewX + view_width * 0.5f);
        flash_bar2.setY(viewY + view_width * 0.5f);
        flash_bar2.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar2.setRotation(135);
        flash_bar2.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.5f + view_width)
                .y(viewY + view_width * 0.5f + view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar2);
                    }
                });
        mDialog.addView(flash_bar2);

        //Bottom left
        ImageView flash_bar3 = new ImageView(getMainActivity());
        flash_bar3.setImageResource(R.drawable.flash_bar);
        flash_bar3.setX(viewX + view_width * 0.25f);
        flash_bar3.setY(viewY + view_width * 0.5f);
        flash_bar3.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar3.setRotation(225);
        flash_bar3.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.25f - view_width)
                .y(viewY + view_width * 0.5f + view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar3);
                    }
                });
        mDialog.addView(flash_bar3);

        //Top left
        ImageView flash_bar4 = new ImageView(getMainActivity());
        flash_bar4.setImageResource(R.drawable.flash_bar);
        flash_bar4.setX(viewX + view_width * 0.25f);
        flash_bar4.setY(viewY + view_width * 0.25f);
        flash_bar4.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar4.setRotation(315);
        flash_bar4.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.25f - view_width)
                .y(viewY + view_width * 0.25f - view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar4);
                    }
                });
        mDialog.addView(flash_bar4);

        //Top
        ImageView flash_bar5 = new ImageView(getMainActivity());
        flash_bar5.setImageResource(R.drawable.flash_bar);
        flash_bar5.setX(viewX + view_width * 0.375f);
        flash_bar5.setY(viewY + view_width * 0.25f);
        flash_bar5.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar5.setRotation(0);
        flash_bar5.animate().setDuration(EXPLODE_TIME)
                .y(viewY + view_width * 0.25f - view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar5);
                    }
                });
        mDialog.addView(flash_bar5);

        //Right
        ImageView flash_bar6 = new ImageView(getMainActivity());
        flash_bar6.setImageResource(R.drawable.flash_bar);
        flash_bar6.setX(viewX + view_width * 0.5f);
        flash_bar6.setY(viewY + view_width * 0.375f);
        flash_bar6.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar6.setRotation(90);
        flash_bar6.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.5f + view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar6);
                    }
                });
        mDialog.addView(flash_bar6);
        //Bottom
        ImageView flash_bar7 = new ImageView(getMainActivity());
        flash_bar7.setImageResource(R.drawable.flash_bar);
        flash_bar7.setX(viewX + view_width * 0.375f);
        flash_bar7.setY(viewY + view_width * 0.5f);
        flash_bar7.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar7.setRotation(180);
        flash_bar7.animate().setDuration(EXPLODE_TIME)
                .y(viewY + view_width * 0.5f + view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar7);
                    }
                });
        mDialog.addView(flash_bar7);
        //Left
        ImageView flash_bar8 = new ImageView(getMainActivity());
        flash_bar8.setImageResource(R.drawable.flash_bar);
        flash_bar8.setX(viewX + view_width * 0.25f);
        flash_bar8.setY(viewY + view_width * 0.375f);
        flash_bar8.setLayoutParams(new ViewGroup.LayoutParams(view_width / 4, view_width / 4));
        flash_bar8.setRotation(270);
        flash_bar8.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.25f - view_width)
                .scaleX(6).scaleY(6).alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDialog.removeView(flash_bar8);
                    }
                });
        mDialog.addView(flash_bar8);

        //Add sparkler
        createSparkler(view);
    }

    private void createSparkler(ImageView view) {
        int view_width = view.getWidth() / 3;
        int viewX = (int) view.getX() + view_width;
        int viewY = (int) view.getY() + view_width;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ImageView sparkler = new ImageView(getMainActivity());
                sparkler.setImageResource(R.drawable.flash_s_small);
                sparkler.setX(viewX + (int) (view_width * 4 / 9));
                sparkler.setY(viewY + (int) (view_width * 4 / 9));
                sparkler.setLayoutParams(new ViewGroup.LayoutParams((int) (view_width / 9), (int) (view_width / 9)));
                mDialog.addView(sparkler);
                //Set animation
                sparkler.animate().setDuration((long) (300 * Math.random() + 300)).alpha(0).rotation(Math.random() > 0.5 ? 180 : -180).scaleX(10).scaleY(10)
                        .x(j < 2 ? (float) (viewX + (int) (view_width * 4 / 9) - view_width * 3 * Math.random())
                                : (float) (viewX + (int) (view_width * 4 / 9) + view_width * 3 * Math.random()))
                        .y(i < 2 ? (float) (viewY + (int) (view_width * 4 / 9) - view_width * 3 * Math.random())
                                : (float) (viewY + (int) (view_width * 4 / 9) + view_width * 3 * Math.random()))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mDialog.removeView(sparkler);
                            }
                        });
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        if (!super.onBackPressed()) {
            getMainActivity().navigateToFragment(new MapFragment());
            return true;
        }
        return super.onBackPressed();
    }

}