package com.chirag.covid19india.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.chirag.covid19india.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean shouldPerformDispatchTouch = true;

    public Toolbar toolbar;
    private ProgressDialog dialog;


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public ProgressDialog showProgressBar() {
        return showProgressBar(null);
    }

    public ProgressDialog showProgressBar(String message) {
        if (dialog == null) dialog = new ProgressDialog(this, message);
        return dialog;
    }

    public void hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = false;
        try {
            View view = getCurrentFocus();
            ret = super.dispatchTouchEvent(event);
            if (shouldPerformDispatchTouch) {
                if (view instanceof EditText) {
                    View w = getCurrentFocus();
                    int scrCords[] = new int[2];
                    if (w != null) {
                        w.getLocationOnScreen(scrCords);
                        float x = event.getRawX() + w.getLeft() - scrCords[0];
                        float y = event.getRawY() + w.getTop() - scrCords[1];

                        if (event.getAction() == MotionEvent.ACTION_UP
                                && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
                    }
                }
            }
            return ret;
        } catch (Exception e) {
            return ret;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
