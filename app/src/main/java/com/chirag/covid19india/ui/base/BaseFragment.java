package com.chirag.covid19india.ui.base;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;


public class BaseFragment extends Fragment {

    public Context mContext;
    public Activity mActivity;

    public TextView title;
    private Snackbar snackbar;

    private ProgressDialog dialog;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    // Set false for Tab Back press management. It will manage from MainActivity
    public boolean onBackPressed() {
        return false;
    }

    public void showShortToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public ProgressDialog showProgressBar() {
        return showProgressBar(null);
    }

    public ProgressDialog showProgressBar(String message) {
        if (dialog == null) dialog = new ProgressDialog(mContext, message);
        return dialog;
    }

    public void hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (snackbar != null && snackbar.isShown()) snackbar.dismiss();
    }
}