package com.nativegame.match3game.fragment;

import androidx.fragment.app.Fragment;

import com.nativegame.match3game.MainActivity;
import com.nativegame.match3game.dialog.BaseDialog;

/**
 * BaseFragment contain fragment shared operation,
 * including show dialog and press back button
 */

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
