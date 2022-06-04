package com.nativegame.match3game.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nativegame.match3game.R;

/**
 * Created by Oscar Liang on 2022/02/23
 */

public class LoadingFragment extends BaseFragment {

    private static final int WAIT_TIME = 800;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public LoadingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // We wait some time for the app open
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Add MapFragment to screen
                getMainActivity().navigateToFragment(new MenuFragment());
            }
        }, WAIT_TIME);

    }
}
