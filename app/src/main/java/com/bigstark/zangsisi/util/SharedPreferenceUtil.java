package com.bigstark.zangsisi.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.bigstark.zangsisi.ZangsisiApplication;

/**
 * Created by gangdaegyu on 2017. 10. 7..
 */

public class SharedPreferenceUtil {

    private static final String PREF_NAME = "ZANGSISI-PREF";


    public static <T> void put(String key, T value) {
        if (value == null) {
            throw new NullPointerException("value must not be null");
        }

        Context context = ZangsisiApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        }

        editor.apply();
    }


    public static <T> T get(String key) {
        Context context = ZangsisiApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        T result = null;
        if (sp.contains(key)) {
            result = (T) sp.getAll().get(key);
        }

        return result;
    }


    public static <T> T get(String key, T defaultValue) {
        Context context = ZangsisiApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        T result = null;
        if (sp.contains(key)) {
            result = (T) sp.getAll().get(key);
        }

        return result == null ? defaultValue : result;
    }


    public static boolean contains(String key) {
        Context context = ZangsisiApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

}
