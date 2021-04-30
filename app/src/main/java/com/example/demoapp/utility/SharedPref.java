package com.example.demoapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.demoapp.repository.ToRepository;
/**
 * Created by       :ABC
 * Date             : 29/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of Stored data
 * Revisions        : 1 - XYZ     30/04/2021
 *			         Change – Add in add()
 *
 *                    2 - PQR     30-11-2021
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class SharedPref {
    public static final String FEED_PREF = "feed_pref";
    public static final String PREF_USER_NAME = "userName";
    public static final String PREF_USER_ID = "userID";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    // shared pref mode
    private int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";
    //Todo: Default constructor
    public SharedPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        mSharedPreferences = context.getSharedPreferences(FEED_PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }
    public static SharedPreferences getPreferences(Context paramContext) {
        return paramContext.getSharedPreferences(FEED_PREF, Context.MODE_PRIVATE);
    }
    //Todo: data save method
    public String saveValue(String key, String value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
        return key;
    }
    //Todo: data save method
    public String saveValue(String key, int value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.apply();
        return key;
    }
    //Todo: data save method
    public String saveValue(String key, long value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.apply();
        return key;
    }
    public void saveValue(String strKey, boolean flag) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(strKey, flag);
        mEditor.apply();
    }
    //Todo: get value method
    public String getValue(String key, String value) {
        return mSharedPreferences.getString(key, value);
    }
    public boolean getValue(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }
    public int getValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }
    public long getValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }
    public String getFCMToken() {
        return mSharedPreferences.getString("fcmtoken", "");
    }
    public void setFCMToken(String fcmToken) {
        mEditor.putString("fcmtoken", fcmToken);
        mEditor.commit();
    }
    public void setMobileNo(String accessToken) {
        mEditor.putString("mobileno", accessToken);
        mEditor.commit();
    }
    public void setAccessToken(String accessToken) {
        mEditor.putString("accessToken", accessToken);
        mEditor.commit();
    }
    //Todo: clear data method
    public void clear() {
        String fcmToken = getFCMToken();
        String thttp = getValue("BASE_HTTP", ToRepository.BASE_HTTP);
        String turl = getValue("BASE_URL", ToRepository.TEMP_BASE_URL);
        String isdemo = getValue("IS_DEMO", "false");

        mSharedPreferences.edit().clear().apply();
        setFCMToken(fcmToken);
        saveValue("BASE_HTTP", thttp);
        saveValue("BASE_URL", turl);
        saveValue("IS_DEMO", isdemo);
    }
    public void setUserEmail(String email) {
        mEditor.putString("userEmail", email).commit();
    }

    public void setUserOldPwd(String oldPassword) {
        mEditor.putString("oldPassword", oldPassword).commit();
    }
    public void setUserID(int userID) {
        mEditor.putInt(PREF_USER_ID, userID).commit();
    }
    public void setUserName(String userName) {
        mEditor.putString(PREF_USER_NAME, userName).commit();
    }

    public String getAccessToken() {
        return "Bearer " + mSharedPreferences.getString("accessToken", "");
    }

    public String getTempToken() {
//        return "Bearer " + mSharedPreferences.getString("tempToken", "");
        return mSharedPreferences.getString("tempToken", "");
    }

    public void setTempToken(String accessToken) {
        mEditor.putString("tempToken", accessToken);
        mEditor.commit();
    }

    public String getRefreshToken() {
        return mSharedPreferences.getString(Constants.PrefKeys.PREF_REFRESH_TOKEN, "");

    }
}
