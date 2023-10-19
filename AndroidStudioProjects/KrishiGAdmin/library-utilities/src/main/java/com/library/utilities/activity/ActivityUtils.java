package com.library.utilities.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /*
     * Method to check activity exists or not
     *
     * @param context - context
     * @param parentPackageName - package name e.g., com.bidfrail
     * @param activityName - activity name with full path e.g., "com.bidfrail.MainActivity"
     * @return true if exists, false if not exists
     *
     * Example :
     *              ActivityUtils.isActivityExists(getApplicationContext(), "com.bidfrail","com.bidfrail.TestActivity")
     *              ActivityUtil.isActivityExists(getApplicationContext(),getPackageName(),"com.bidfrail.TestActivity")
     */
    @SuppressLint("QueryPermissionsNeeded")
    public static boolean isActivityExists(Context context,
                                           @NonNull final String parentPackageName,
                                           @NonNull final String activityName) {
        Intent intent = new Intent();
        intent.setClassName(parentPackageName, activityName);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null || intent.resolveActivity(context.getPackageManager()) == null || context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * Method to launch activity
     *
     * @param currentActivity - Current activity
     * @param nextActivity    - Next Activity that should be launched
     */
    public static Intent launchActivity(@NonNull final Activity currentActivity,
                                        @NonNull final Class<? extends Activity> nextActivity) {
        return new Intent(currentActivity, nextActivity);
    }

    /**
     * Method to launch activity with bundle
     *
     * @param currentActivity - Current activity
     * @param nextActivity    - Next Activity that should be launched
     * @param bundle          - Pass bundle from one class to another
     */
    public static Intent launchActivity(@NonNull final Activity currentActivity,
                                        @NonNull final Class<? extends Activity> nextActivity,
                                        @Nullable final Bundle bundle) {
        Intent intent = new Intent(currentActivity, nextActivity);
        intent.putExtra("bundle", bundle);
        return intent;
    }

    /**
     * Method to launch activity with finish all BackStack activity
     *
     * @param currentActivity - Current activity
     * @param nextActivity    - Next Activity that should be launched
     */
    public static Intent launchActivityWithClearBackStack(@NonNull final Activity currentActivity,
                                                          @NonNull final Class<? extends Activity> nextActivity) {
        Intent intent = new Intent(currentActivity, nextActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    /**
     * Method to launch activity with bundle and finish all BackStack activity
     *
     * @param currentActivity - Current activity
     * @param nextActivity    - Next Activity that should be launched
     * @param bundle          - Pass bundle from one class to another
     */
    public static Intent launchActivityWithClearBackStack(@NonNull final Activity currentActivity,
                                                          @NonNull final Class<? extends Activity> nextActivity,
                                                          @Nullable final Bundle bundle) {
        Intent intent = new Intent(currentActivity, nextActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("bundle", bundle);
        return intent;
    }

    /**
     * Method to get previous activity bundle data
     *
     * @param currentActivity - Current activity
     */
    public static Bundle getBundle(@NonNull final Activity currentActivity) {
        Intent data = currentActivity.getIntent();
        return data.getBundleExtra("bundle");
    }

    /**
     * Method to finish current activity
     *
     * @param currentActivity - Current activity
     */
    public static void finishActivity(@NonNull final Activity currentActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentActivity.finishAfterTransition();
        } else {
            currentActivity.finish();
        }
    }

    /**
     * Method to launch next activity with bundle value data as model class (i.e startIntent)
     *
     * @param currentActivity - Context of the activity
     * @param nextActivity    - Activity class that should be launched
     */
    public static <T> Intent launchActivity(@NonNull final Activity currentActivity, @NonNull final Class<? extends Activity> nextActivity, String key, T data) {
        Intent intent = new Intent(currentActivity, nextActivity);
        if (data != null) {
            putObjectInBundle(key, data, intent);
        }
        return intent;
    }

    public static <T> void putObjectInBundle(String key, T data, Intent intent) {
        Bundle bundle = new Bundle();
        try {
            if (data instanceof Parcelable) {
                bundle.putParcelable(key, (Parcelable) data);
            } else if (data instanceof Serializable) {
                bundle.putSerializable(key, (Serializable) data);
            }
            intent.putExtras(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getBundleParcelableExtra(Activity activity, String key) {
        return activity.getIntent().getParcelableExtra(key);
    }

    public static <T> T getBundleSerializableExtra(Activity activity, String key) {
        return (T) activity.getIntent().getSerializableExtra(key);
    }
}
