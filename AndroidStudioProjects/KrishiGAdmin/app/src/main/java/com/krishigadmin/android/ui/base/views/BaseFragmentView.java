package com.krishigadmin.android.ui.base.views;

import android.view.View;

import androidx.annotation.StringRes;

public interface BaseFragmentView {
    void showProgressBar();
    void hideProgressBar();

    void showProgressDialog();
    void hideProgressDialog();

    void showToast(String message);
    void showToast(@StringRes int messageResId);

    void showSnackBar(View parent, String message);
    void showSnackBar(View parent, @StringRes int messageResId);

    void hideKeyboard();
}