package com.licrafter.basenet.utils;

import android.util.Log;

import com.licrafter.basenet.Config;

/**
 * Created by lijx on 2017/7/30.
 */

public class LogUtils {

    public static void debug(Class classes, String msg) {
        if (Config.isDebuggable()) {
            Log.d(classes.getName(), msg);
        }
    }
}
