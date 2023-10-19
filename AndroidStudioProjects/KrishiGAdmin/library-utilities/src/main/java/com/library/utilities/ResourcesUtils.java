package com.library.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;

public class ResourcesUtils {

    private ResourcesUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(Context context, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resourceId, null);
        }
        return context.getResources().getDrawable(resourceId);
    }

    @SuppressWarnings("deprecation")
    public static int getColor(@NonNull Context context, int resourceId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return context.getResources().getColor(resourceId);
        } else {
            return context.getResources().getColor(resourceId, null);
        }
    }
}
