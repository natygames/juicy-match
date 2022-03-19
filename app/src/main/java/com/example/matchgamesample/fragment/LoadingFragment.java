package com.example.matchgamesample.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.matchgamesample.R;

public class LoadingFragment extends BaseFragment {
    private final Handler mHandler = new Handler();

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

        //Add MapFragment to screen with delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMainActivity().navigateToFragment(new MenuFragment());
            }
        }, 500);

    }
}