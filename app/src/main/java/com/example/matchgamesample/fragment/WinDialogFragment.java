package com.example.matchgamesample.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matchgamesample.R;
import com.example.matchgamesample.Utils;
import com.example.matchgamesample.dialog.ExitDialog;

public class WinDialogFragment extends BaseFragment implements ExitDialog.ExitDialogListener{
    private static final String LEVEL = "LEVEL";
    private static final String SCORE = "SCORE";
    private static final String STAR = "STAR";
    private int mLevel;
    private int mScore;
    private int mStar;

    private ConstraintLayout mDialog;
    private ImageView mStar1, mStar2, mStar3;
    private static final int EXPLODE_TIME = 250;
    private final OvershootInterpolator mInterpolator = new OvershootInterpolator();
    private final Handler mHandler = new Handler();

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
        TextView textView = (TextView) getView().findViewById(R.id.txt_level_win);
        textView.setText("Level " + String.valueOf(mLevel));
        mDialog = (ConstraintLayout) getView().findViewById(R.id.dialog_win);
        mStar1 = (ImageView) getView().findViewById(R.id.image_star1);
        mStar2 = (ImageView) getView().findViewById(R.id.image_star2);
        mStar3 = (ImageView) getView().findViewById(R.id.image_star3);

        // Init button
        ImageButton btnNext = (ImageButton) getView().findViewById(R.id.btn_next_win);
        Utils.createButtonEffect(btnNext);
        btnNext.setScaleX(0);
        btnNext.setScaleY(0);
        btnNext.animate().setDuration(1000).scaleX(1).scaleY(1).setInterpolator(mInterpolator);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().navigateToFragment(new MapFragment());
            }
        });

        // Dialog animation
        Animation inAnim = AnimationUtils.loadAnimation(getMainActivity(), R.anim.enter_from_left);
        mDialog.startAnimation(inAnim);

        // Run score
        TextView mScoreText = (TextView) getView().findViewById(R.id.txt_score_win);
        ValueAnimator animator = ValueAnimator.ofFloat(mScore - 150, mScore);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mScoreText.setText(String.valueOf((int) value));
            }
        });
        animator.start();

        // Star animation
        createStarAnim(mStar);

    }

    private void createStarAnim(int star) {
        if (star >= 1) {
            mStar1.animate().setStartDelay(700).setDuration(300).scaleX(4).scaleY(4).alpha(1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mStar1.animate().setStartDelay(0).setDuration(100).scaleX(3.15f).scaleY(3.15f)
                                    .setInterpolator(mInterpolator);
                        }
                    });

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    createExplode(mStar1);
                }
            }, 700);
        }
        if (star >= 2) {
            mStar2.animate().setStartDelay(1000).setDuration(300).scaleX(4).scaleY(4).alpha(1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mStar2.animate().setStartDelay(0).setDuration(100).scaleX(3.15f).scaleY(3.15f)
                                    .setInterpolator(mInterpolator);
                        }
                    });

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    createExplode(mStar2);
                }
            }, 1000);
        }
        if (star >= 3) {
            mStar3.animate().setStartDelay(1300).setDuration(300).scaleX(4).scaleY(4).alpha(1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mStar3.animate().setStartDelay(0).setDuration(100).scaleX(3.15f).scaleY(3.15f)
                                    .setInterpolator(mInterpolator);
                        }
                    });

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    createExplode(mStar3);
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
        flash_bar1.setY(viewY);
        flash_bar1.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar1.setRotation(45);
        flash_bar1.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.5f + view_width * 2)
                .y(viewY - view_width * 2).scaleX(6).scaleY(6).alpha(0.1f)
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
        flash_bar2.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar2.setRotation(135);
        flash_bar2.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.5f + view_width * 2)
                .y(viewY + view_width * 0.5f + view_width * 2)
                .scaleX(6).scaleY(6).alpha(0.1f)
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
        flash_bar3.setX(viewX);
        flash_bar3.setY(viewY + view_width * 0.5f);
        flash_bar3.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar3.setRotation(225);
        flash_bar3.animate().setDuration(EXPLODE_TIME)
                .x(viewX - view_width * 2)
                .y(viewY + view_width * 0.5f + view_width * 2)
                .scaleX(6).scaleY(6).alpha(0.1f)
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
        flash_bar4.setX(viewX);
        flash_bar4.setY(viewY);
        flash_bar4.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar4.setRotation(315);
        flash_bar4.animate().setDuration(EXPLODE_TIME)
                .x(viewX - view_width * 2)
                .y(viewY - view_width * 2)
                .scaleX(6).scaleY(6).alpha(0.1f)
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
        flash_bar5.setX(viewX + view_width * 0.25f);
        flash_bar5.setY(viewY);
        flash_bar5.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar5.setRotation(0);
        flash_bar5.animate().setDuration(EXPLODE_TIME)
                .y(viewY - view_width * 2)
                .scaleX(6).scaleY(6).alpha(0.1f)
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
        flash_bar6.setY(viewY + view_width * 0.25f);
        flash_bar6.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar6.setRotation(90);
        flash_bar6.animate().setDuration(EXPLODE_TIME)
                .x(viewX + view_width * 0.5f + view_width * 2)
                .scaleX(6).scaleY(6).alpha(0.1f)
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
        flash_bar7.setX(viewX + view_width * 0.25f);
        flash_bar7.setY(viewY + view_width * 0.5f);
        flash_bar7.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar7.setRotation(180);
        flash_bar7.animate().setDuration(EXPLODE_TIME)
                .y(viewY + view_width * 0.5f + view_width * 2)
                .scaleX(6).scaleY(6).alpha(0.1f)
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
        flash_bar8.setX(viewX);
        flash_bar8.setY(viewY + view_width * 0.25f);
        flash_bar8.setLayoutParams(new ViewGroup.LayoutParams(view_width / 2, view_width / 2));
        flash_bar8.setRotation(270);
        flash_bar8.animate().setDuration(EXPLODE_TIME)
                .x(viewX - view_width * 2)
                .scaleX(6).scaleY(6).alpha(0.1f)
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
        int viewX = (int) view.getX();
        int viewY = (int) view.getY();
        int view_width = view.getWidth();

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
    public void exit() {
        getMainActivity().finish();
    }

    @Override
    public boolean onBackPressed() {
        if (!super.onBackPressed()) {
            ExitDialog quitDialog = new ExitDialog(getMainActivity());
            quitDialog.setListener(this);
            showDialog(quitDialog);
            return true;
        }
        return super.onBackPressed();
    }

}