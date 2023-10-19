package com.krishigadmin.android.Basics.UtilityTools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Anil on 9/4/2021.
 */

public class KeyboardUtils {
    /**
     *
     * @param activity
     */

    public static void showKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}