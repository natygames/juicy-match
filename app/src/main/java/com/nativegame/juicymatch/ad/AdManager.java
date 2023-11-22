package com.nativegame.juicymatch.ad;

import android.app.Activity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.nativegame.juicymatch.R;
import com.nativegame.natyengine.util.ResourceUtils;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class AdManager {

    private final Activity mActivity;

    private RewardedAd mRewardedAd;
    private AdRewardListener mListener;
    private boolean mRewardEarned = false;

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public AdManager(Activity activity) {
        mActivity = activity;
        requestAd();
    }
    //========================================================

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public AdRewardListener getListener() {
        return mListener;
    }

    public void setListener(AdRewardListener listener) {
        mListener = listener;
    }
    //========================================================

    //--------------------------------------------------------
    // Methods
    //--------------------------------------------------------
    public void requestAd() {
        if (mRewardedAd != null) {
            return;
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(mActivity, ResourceUtils.getString(mActivity, R.string.txt_admob_reward),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        mRewardedAd = null;
                        // Toast.makeText(mActivity, "Fail!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded(RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        // Toast.makeText(mActivity, "Succeed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean showRewardAd() {
        if (mRewardedAd == null) {
            return false;
        }

        // Reset state
        mRewardEarned = false;

        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed
                mRewardedAd = null;
                // Check if user dismiss Ad before earn
                if (!mRewardEarned) {
                    mListener.onLossReward();
                }
                // Prepare next ad
                requestAd();
            }
        });

        mRewardedAd.show(mActivity, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(RewardItem rewardItem) {
                mListener.onEarnReward();
                mRewardEarned = true;
                // Toast.makeText(mActivity, "Reward!", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }
    //========================================================

    //--------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------
    public interface AdRewardListener {

        void onEarnReward();

        void onLossReward();

    }
    //========================================================

}
