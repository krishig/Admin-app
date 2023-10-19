package com.krishigadmin.android.Basics.UtilityTools;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.krishigadmin.android.Basics.Database.UserDataHelper;


public class AppController extends Application {
    static Context context;
    private static AppController mInstance;
    ConnectivityManager connectivityManager;
    boolean connected = false;

    /**
     * @return
     */
    public AppController() {
        mInstance = this;
    }
    public static Context getContext() {
        return mInstance;
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
    /**
     * @param ctx
     * @return
     */
    public static AppController getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return mInstance;
    }



    /**
     * @return
     */
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        new UserDataHelper(this);
        SavedData.getInstance();

    }


}

