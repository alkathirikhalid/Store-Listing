package com.alkathirikhalid.storelisting.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.alkathirikhalid.storelisting.util.Constant;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */
public class AppPrefs {
    private SharedPreferences sharedPreferences;

    public AppPrefs(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constant.AppPrefsSettings.APP_PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void setKeyValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void cleanUp() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getId() {
        return getString(Constant.AppPrefsKey.APP_PREFS_KEY_ID, "");
    }

    public void setId(String value) {
        setKeyValue(Constant.AppPrefsKey.APP_PREFS_KEY_ID, value);
    }

    public String getToken() {
        return getString(Constant.AppPrefsKey.APP_PREFS_KEY_TOKEN, "");
    }

    public void setToken(String value) {
        setKeyValue(Constant.AppPrefsKey.APP_PREFS_KEY_TOKEN, value);
    }
}