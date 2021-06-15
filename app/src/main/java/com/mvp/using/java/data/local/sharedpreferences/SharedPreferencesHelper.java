package com.mvp.using.java.data.local.sharedpreferences;

import android.content.Context;
import com.badasoftware.library.network.di.qualifier.ApplicationContext;
import com.badasoftware.library.sharedpreferences.SharedPreferenceBuilder;
import com.mvp.using.java.di.qualifier.local.SharedPreferencesFileName;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesHelper extends SharedPreferenceBuilder {

    @Inject
    public SharedPreferencesHelper(@ApplicationContext Context context, @SharedPreferencesFileName String sharedPreferencesFileName) {
        super(context, sharedPreferencesFileName);
    }

    public void setLanguageStatus(boolean value) {
        putBoolean(SharedPreferencesConstants.IS_LANGUAGE_SELECT, value);
    }

    public boolean getLanguageStatus() {
        return getBoolean(SharedPreferencesConstants.IS_LANGUAGE_SELECT);
    }

    public void setLoginStatus(boolean value) {
        putBoolean(SharedPreferencesConstants.LOGIN_STATUS, value);
    }

    public boolean getLoginStatus() {
        return getBoolean(SharedPreferencesConstants.LOGIN_STATUS);
    }

    public void setId(String value) {
        putString(SharedPreferencesConstants.ID, value);
    }

    public String getId() {
        return getString(SharedPreferencesConstants.ID);
    }

    public void setProvider(String value) {
        putString(SharedPreferencesConstants.PROVIDER, value);
    }

    public String getProvider() {
        return getString(SharedPreferencesConstants.PROVIDER);
    }

    public void setSocialId(String value) {
        putString(SharedPreferencesConstants.SOCIAL_ID, value);
    }

    public String getSocialId() {
        return getString(SharedPreferencesConstants.SOCIAL_ID);
    }

    public void setPicture(String value) {
        putString(SharedPreferencesConstants.PICTURE, value);
    }

    public String getPicture() {
        return getString(SharedPreferencesConstants.PICTURE);
    }

    public void setFirstName(String value) {
        putString(SharedPreferencesConstants.FIRST_NAME, value);
    }

    public String getFirstName() {
        return getString(SharedPreferencesConstants.FIRST_NAME);
    }

    public void setLastName(String value) {
        putString(SharedPreferencesConstants.LAST_NAME, value);
    }

    public String getLastName() {
        return getString(SharedPreferencesConstants.LAST_NAME);
    }

    public void setGender(String value) {
        putString(SharedPreferencesConstants.GENDER, value);
    }

    public String getGender() {
        return getString(SharedPreferencesConstants.GENDER);
    }

    public void setCountryCode(String value) {
        putString(SharedPreferencesConstants.COUNTRY_CODE, value);
    }

    public String getCountryCode() {
        return getString(SharedPreferencesConstants.COUNTRY_CODE);
    }

    public void setPhone(String value) {
        putString(SharedPreferencesConstants.PHONE_NUMBER, value);
    }

    public String getPhone() {
        return getString(SharedPreferencesConstants.PHONE_NUMBER);
    }

    public void setPhoneNumberVerified(String value) {
        putString(SharedPreferencesConstants.PHONE_NUMBER_VERIFIED, value);
    }

    public String getPhoneNumberVerified() {
        return getString(SharedPreferencesConstants.PHONE_NUMBER_VERIFIED);
    }

    public void setEmail(String value) {
        putString(SharedPreferencesConstants.EMAIL, value);
    }

    public String getEmail() {
        return getString(SharedPreferencesConstants.EMAIL);
    }

    public void setEmailVerified(String value) {
        putString(SharedPreferencesConstants.EMAIL_VERIFIED, value);
    }

    public String getEmailVerified() {
        return getString(SharedPreferencesConstants.EMAIL_VERIFIED);
    }

    public void setFcmToken(String value) {
        putString(SharedPreferencesConstants.FCM_TOKEN, value);
    }

    public String getFcmToken() {
        return getString(SharedPreferencesConstants.FCM_TOKEN);
    }

    public void setLastLogin(String value) {
        putString(SharedPreferencesConstants.LAST_LOGIN, value);
    }

    public String getLastLogin() {
        return getString(SharedPreferencesConstants.LAST_LOGIN);
    }

    public void setCreatedAt(String value) {
        putString(SharedPreferencesConstants.CREATED_AT, value);
    }

    public String getCreatedAt() {
        return getString(SharedPreferencesConstants.CREATED_AT);
    }

    public void setUpdatedAt(String value) {
        putString(SharedPreferencesConstants.UPDATED_AT, value);
    }

    public String getUpdatedAt() {
        return getString(SharedPreferencesConstants.UPDATED_AT);
    }

    public void setExpiredAt(String value) {
        putString(SharedPreferencesConstants.EXPIRED_AT, value);
    }

    public String getExpiredAt() {
        return getString(SharedPreferencesConstants.EXPIRED_AT);
    }

    public void setAccountVerifiedByAdmin(String value) {
        putString(SharedPreferencesConstants.ACCOUNT_VERIFIED_BY_ADMIN, value);
    }

    public String getAccountVerifiedByAdmin() {
        return getString(SharedPreferencesConstants.ACCOUNT_VERIFIED_BY_ADMIN);
    }
}
