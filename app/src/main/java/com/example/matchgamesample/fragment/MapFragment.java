package com.example.matchgamesample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.R;

public class MapFragment extends BaseFragment {
    private static final int TOTAL_LEVEL = 4;
    private MainActivity mActivity;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) getMainActivity();

        //Create button listener
        for (int i = 1; i <= TOTAL_LEVEL; i++) {
            String name = "btn_type" + i;
            int id = getResources().getIdentifier(name, "id", mActivity.getPackageName());
            Button btn = (Button) getView().findViewById(id);
            int level = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startGame(level);
                }
            });
        }

    }

}