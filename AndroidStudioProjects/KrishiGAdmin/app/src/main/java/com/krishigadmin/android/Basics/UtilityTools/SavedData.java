package com.krishigadmin.android.Basics.UtilityTools;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;


/**
 * Created by Anil on 9/4/2021.
 */
public class SavedData {
    private static final String FirebaseToken = "FirebaseToken";
    private static final String Language = "Language";
    private static final String ReferralCode = "ReferralCode";
    private static final String popUp = "popUp";
    private static SharedPreferences prefs;
    private static final String latitude = "latitude";
    private static final String longitude = "longitude";
    private static final String CountryKey = "CountryKey";
    private static final String CountryId = "CountryId";
    private static final String CountryUuId = "CountryUuId";
    private static final String Shared = "Shared";
    private static final String GetStartClick = "GetStartClick";

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(AppController.getInstance());
        }
        return prefs;
    }

    public static String getLanguage() {
        return getInstance().getString(Language, Constants.Key.english);
    }

    public static void saveLanguage(String role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Language, role);
        editor.apply();
    }

    public static boolean getPopUp() {
        return getInstance().getBoolean(popUp, false);
    }

    public static void savePopUp(boolean role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(popUp, role);
        editor.apply();
    }

    public static boolean getStartClick() {
        return getInstance().getBoolean(GetStartClick, false);
    }
    public static void saveGetStartClick(boolean role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(GetStartClick, role);
        editor.apply();
    }

    public static String getFirebaseToken() {
        return getInstance().getString(FirebaseToken, Constants.Key.blank);
    }

    public static void saveFirebaseToken(String token) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FirebaseToken, token);
        editor.apply();
    }

    public static String getCountryKey(Context context) {
        return getInstance().getString(CountryKey, Constants.Key.Default_Country_Region);
    }

    public static void saveCountryKey(String key) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CountryKey, key);
        editor.apply();
    }

    public static String getReferralCode() {
        return getInstance().getString(ReferralCode, Constants.Key.blank);
    }

    public static void saveReferralCode(String token) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(ReferralCode, token);
        editor.apply();
    }

    public static String getCountryUuId() {
        return getInstance().getString(CountryUuId, Constants.Key.DefaultCountryUuId);
    }

    public static void saveCountryUuId(String Id) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CountryUuId, Id);
        editor.apply();
    }

    public static String getBankStatus() {
        return getInstance().getString(Shared, Constants.Key.blank);
    }

    public static void SaveBankStatus(String Id) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Shared, Id);
        editor.apply();

    }




    public static void saveCurrentLocation(Location location) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(latitude, String.valueOf(location.getLatitude()));
        editor.putString(longitude, String.valueOf(location.getLongitude()));
        editor.apply();
    }
}