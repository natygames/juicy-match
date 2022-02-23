package com.example.matchgamesample.fragment;

import androidx.fragment.app.Fragment;
import com.example.matchgamesample.MainActivity;

public class BaseFragment extends Fragment {

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    public boolean onBackPressed() {
        return false;
    }

}
