package com.bigstark.zangsisi.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.bigstark.zangsisi.ZangsisiApplication;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class ToastUtil {
    public static void toast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(ZangsisiApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
