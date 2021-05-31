package com.example.microcampus.demo.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class SharedHander {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedHander(Context context, String fileName) {
        sharedPreferences = Objects.requireNonNull(context).
            getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "#");
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }
}
