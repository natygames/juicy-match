package com.example.matchgamesample.fragment;

import androidx.fragment.app.Fragment;

import com.example.matchgamesample.MainActivity;
import com.example.matchgamesample.dialog.BaseDialog;

public class BaseFragment extends Fragment {

    BaseDialog mCurrentDialog;

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    public void showDialog(BaseDialog newDialog) {
        showDialog(newDialog, false);
    }

    public void showDialog(BaseDialog newDialog, boolean dismissOtherDialog) {
        getMainActivity().showDialog(newDialog, dismissOtherDialog);
    }

    public boolean onBackPressed() {
        return false;
    }

}
