package com.prapps.iris.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by royce on 06-05-2016.
 */
@SuppressWarnings({"WeakerAccess", "unused"})

//Shared prefs implementation
public class Prefs {

    private SharedPreferences preferences;
    private static Prefs sInstance;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static synchronized Prefs getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Prefs(context);
        }
        return sInstance;
    }

    public void put(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void put(String key, Set<String> value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public Set<String> getSet(String key) {
        return preferences.getStringSet(key, new HashSet<String>());
    }

    public void increment(String key) {
        int curr = getInt(key);
        put(key, ++curr);
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean hasKey(String key) {
        return preferences.contains(key);
    }

    public void clearAccessToken() {
        SharedPreferences.Editor editor = preferences.edit();
        //   editor.remove(BuzzConstants.ACCESS_TOKEN);
        editor.commit();
    }

}