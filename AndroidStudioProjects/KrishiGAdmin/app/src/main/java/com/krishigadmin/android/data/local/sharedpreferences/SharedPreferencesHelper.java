package com.krishigadmin.android.data.local.sharedpreferences;

import android.content.Context;

import com.library.sharedpreference.SharedPreferenceBuilder;
import com.krishigadmin.android.di.qualifier.local.SharedPreferencesName;
import com.krishigadmin.android.ui.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class SharedPreferencesHelper extends SharedPreferenceBuilder {

    @Inject
    public SharedPreferencesHelper(@ApplicationContext Context context, @SharedPreferencesName String sharedPreferencesFileName) {
        super(context, sharedPreferencesFileName);
    }

    public void setCurrentTheme(int value) {
        putInt(AppConstants.SharedPreferences.CURRENT_THEME, value);
    }

    public int getCurrentTheme() {
        return getInt(AppConstants.SharedPreferences.CURRENT_THEME, 0);
    }

    public void setIsLanguageSelect(boolean value) {
        putBoolean(AppConstants.SharedPreferences.IS_LANGUAGE_SELECT, value);
    }

    public boolean getIsLanguageSelect() {
        return getBoolean(AppConstants.SharedPreferences.IS_LANGUAGE_SELECT, false);
    }

    public String getCurrentLanguage() {
        return getString(AppConstants.SharedPreferences.CURRENT_LANGUAGE, "en");
    }

    public void setCurrentLanguage(String value) {
        putString(AppConstants.SharedPreferences.CURRENT_LANGUAGE, value);
    }

    public void setIsShowAppIntro(boolean value) {
        putBoolean(AppConstants.SharedPreferences.IS_SHOW_APP_INTRO, value);
    }

    public boolean getIsShowAppIntro() {
        return getBoolean(AppConstants.SharedPreferences.IS_SHOW_APP_INTRO, true);
    }

    public void setRemember(boolean value) {
        putBoolean(AppConstants.SharedPreferences.REMEMBER_ME, value);
    }

    public boolean getRemember() {
        return getBoolean(AppConstants.SharedPreferences.REMEMBER_ME, false);
    }

    public void setLogout(boolean value) {
        putBoolean(AppConstants.SharedPreferences.LOGOUT, value);
    }

    public boolean getLogout() {
        return getBoolean(AppConstants.SharedPreferences.LOGOUT, false);
    }

    public void setUserId(String value) {
        putString(AppConstants.SharedPreferences.USER_ID, value);
    }

    public String getUserId() {
        return getString(AppConstants.SharedPreferences.USER_ID, null);
    }

    public void setKeyToken(String value) {
        putString(AppConstants.SharedPreferences.TOKEN, value);
    }

    public String getKeyToken() {
        return getString(AppConstants.SharedPreferences.TOKEN, null);
    }

    public void setPaymentAmount(String value) {
        putString(AppConstants.SharedPreferences.AMOUNT, value);
    }

    public String getPaymentAmount() {
        return getString(AppConstants.SharedPreferences.AMOUNT, null);
    }

    public void setPicture(String value) {
        putString(AppConstants.SharedPreferences.PICTURE, value);
    }

    public String getPicture() {
        return getString(AppConstants.SharedPreferences.PICTURE);
    }

    public void setFirstName(String value) {
        putString(AppConstants.SharedPreferences.FIRST_NAME, value);
    }

    public String getFirstName() {
        return getString(AppConstants.SharedPreferences.FIRST_NAME);
    }

    public void setLastName(String value) {
        putString(AppConstants.SharedPreferences.LAST_NAME, value);
    }

    public String getLastName() {
        return getString(AppConstants.SharedPreferences.LAST_NAME);
    }

    public void setType(String value) {
        putString(AppConstants.SharedPreferences.TYPE, value);
    }

    public String getType() {
        return getString(AppConstants.SharedPreferences.TYPE);
    }

    public void setUserName(String value) {
        putString(AppConstants.SharedPreferences.CUSTOMER_NAME, value);
    }

    public String getUserName() {
        return getString(AppConstants.SharedPreferences.CUSTOMER_NAME);
    }

    public void setCustomerID(String value) {
        putString(AppConstants.SharedPreferences.CUSTOMER_ID, value);
    }

    public String getCustomerId() {
        return getString(AppConstants.SharedPreferences.CUSTOMER_ID);
    }

    public void setLeadEntryId(String value) {
        putString(AppConstants.SharedPreferences.LEAD_ENTRY_ID, value);
    }

    public String getLeadEntryId() {
        return getString(AppConstants.SharedPreferences.LEAD_ENTRY_ID);
    }

    public void setPhoneNumber(String value) {
        putString(AppConstants.SharedPreferences.PHONE_NUMBER, value);
    }

    public String getPhoneNumber() {
        return getString(AppConstants.SharedPreferences.PHONE_NUMBER);
    }


    public void setState(String value) {
        putString(AppConstants.SharedPreferences.STATE, value);
    }

    public String getState() {
        return getString(AppConstants.SharedPreferences.STATE);
    }

    public void setStatus(String value) {
        putString(AppConstants.SharedPreferences.STATUS, value);
    }

    public String getStatus() {
        return getString(AppConstants.SharedPreferences.STATUS);
    }

    public void setProgress(String value) {
        putString(AppConstants.SharedPreferences.PROGRESS, value);
    }

    public String getProgress() {
        return getString(AppConstants.SharedPreferences.PROGRESS);
    }


    public void setGoogle(boolean value) {
        putBoolean(AppConstants.SharedPreferences.REMEMBER_ME, value);
    }

    public boolean getGoogle() {
        return getBoolean(AppConstants.SharedPreferences.REMEMBER_ME, false);
    }

    public void setMarketId(String value) {
        putString(AppConstants.SharedPreferences.MARKET_ID, value);
    }

    public String getMarketId() {
        return getString(AppConstants.SharedPreferences.MARKET_ID);
    }

    public void setMarketName(String value) {
        putString(AppConstants.SharedPreferences.MARKET_NAME, value);
    }

    public String getMarketName() {
        return getString(AppConstants.SharedPreferences.MARKET_NAME);
    }

    public void setMarketStatus(String value) {
        putString(AppConstants.SharedPreferences.MARKET_STATUS, value);
    }

    public String getMarketStatus() {
        return getString(AppConstants.SharedPreferences.MARKET_STATUS);
    }

    public void setMarketOpen(String value) {
        putString(AppConstants.SharedPreferences.MARKET_OPEN, value);
    }

    public String getMarketOpen() {
        return getString(AppConstants.SharedPreferences.MARKET_OPEN);
    }

    public void setMarketClose(String value) {
        putString(AppConstants.SharedPreferences.MARKET_CLOSE, value);
    }

    public String getMarketClose() {
        return getString(AppConstants.SharedPreferences.MARKET_CLOSE);
    }

    public void setMarketDate(String value) {
        putString(AppConstants.SharedPreferences.MARKET_DATE, value);
    }

    public String getMarketDate() {
        return getString(AppConstants.SharedPreferences.MARKET_DATE);
    }

    public void setGameDate(String value) {
        putString(AppConstants.SharedPreferences.GAME_DATE, value);
    }

    public String getGameDate() {
        return getString(AppConstants.SharedPreferences.GAME_DATE);
    }

    public void setMerchantId(String value) {
        putString(AppConstants.SharedPreferences.MERCHANT_ID, value);
    }

    public String getMerchantId() {
        return getString(AppConstants.SharedPreferences.MERCHANT_ID);
    }

    public void setMerchantName(String value) {
        putString(AppConstants.SharedPreferences.MERCHANT_NAME, value);
    }

    public String getMerchantName() {
        return getString(AppConstants.SharedPreferences.MERCHANT_NAME);
    }

    public void setFcmToken(String value) {
        putString(AppConstants.SharedPreferences.FCM_TOKEN, value);
    }

    public String getFcmToken() {
        return getString(AppConstants.SharedPreferences.FCM_TOKEN);
    }
}
