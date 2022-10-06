package com.example.noman.myvoicerecorder;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {

    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
    private static final int PRIVATE_MODE = Context.MODE_PRIVATE;

    private SharedPreferences.Editor editor;
    private Context context;
    public DataManager(Context context) {
        this.context = context;
        mSharedPreferences = getSharedPreferences(context.getPackageName(), PRIVATE_MODE);
        editor = mSharedPreferences.edit();
        editor.apply();
    }

    private SharedPreferences getSharedPreferences(final String prefName,
                                                   final int mode) {
        return this.context.getSharedPreferences(prefName, mode);
    }

    public void saveString(String key, String value){
        editor.putString(key,value).apply();
    }
    public String getString(String key){
        return mSharedPreferences.getString(key,"");
    }

    public void clearPrefs() {
        mSharedPreferences.edit().clear().apply();
    }
}